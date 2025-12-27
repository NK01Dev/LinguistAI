import { inject, Injectable } from "@angular/core";
import { BehaviorSubject, Observable, tap } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Router } from "@angular/router";

import { environment } from "../../../environments/environment";
import { TokenStorageService } from "../storage/token-storage.service";
import { TokenResponse, User } from "./auth.types";
import { createPkce } from "./pkce";
import { parseJwtClaims } from "./jwt";

@Injectable({ providedIn: "root" })
export class AuthService {
  private readonly http = inject(HttpClient);
  private readonly router = inject(Router);
  private readonly storage = inject(TokenStorageService);

  private readonly currentUserSubject = new BehaviorSubject<User | null>(null);
  readonly currentUser$ = this.currentUserSubject.asObservable();

  private static readonly PKCE_STATE = "pkce_state";
  private static readonly PKCE_VERIFIER = "pkce_verifier";
  private static readonly REMEMBER_KEY = "auth_remember";

  isAuthenticated(): boolean {
    const access = this.storage.getAccessToken();
    const exp = this.storage.getExpiresAt();
    if (!access || !exp) return false;
    return Date.now() < exp - 10_000; // 10s clock skew
  }

  /**
   * Redirects browser to Cognito Managed Login using Authorization Code + PKCE.
   */
 async startLoginRedirect(remember: boolean, uiLocale?: string): Promise<void> {
    const { domain, clientId, redirectUri, scope } = environment.cognito;

    const { state, verifier, challenge } = await createPkce();
    sessionStorage.setItem(AuthService.PKCE_STATE, state);
    sessionStorage.setItem(AuthService.PKCE_VERIFIER, verifier);
    sessionStorage.setItem(AuthService.REMEMBER_KEY, remember ? "1" : "0");

    const url = new URL(`${domain}/oauth2/authorize`);
    url.searchParams.set("response_type", "code");
    url.searchParams.set("client_id", clientId);
    url.searchParams.set("redirect_uri", redirectUri);
    url.searchParams.set("scope", scope);
    url.searchParams.set("state", state);
    url.searchParams.set("code_challenge_method", "S256");
    url.searchParams.set("code_challenge", challenge);
    if (uiLocale) url.searchParams.set("ui_locales", uiLocale);

    window.location.assign(url.toString());
  }

  /**
   * Call this on /auth/callback. Exchanges code for tokens, stores them, updates currentUser$.
   */
  handleRedirectCallback(code: string, state: string): Observable<TokenResponse> {
    const expectedState = sessionStorage.getItem(AuthService.PKCE_STATE);
    const verifier = sessionStorage.getItem(AuthService.PKCE_VERIFIER);
    const remember = sessionStorage.getItem(AuthService.REMEMBER_KEY) === "1";

    if (!expectedState || expectedState !== state || !verifier) {
      this.storage.clear();
      this.currentUserSubject.next(null);
      this.router.navigateByUrl("/login");
      throw new Error("Invalid auth state (PKCE).");
    }

    const { domain, clientId, redirectUri } = environment.cognito;

    const body = new URLSearchParams();
    body.set("grant_type", "authorization_code");
    body.set("client_id", clientId);
    body.set("code", code);
    body.set("redirect_uri", redirectUri);
    body.set("code_verifier", verifier);

    const headers = new HttpHeaders({ "Content-Type": "application/x-www-form-urlencoded" });

    return this.http
      .post<TokenResponse>(`${domain}/oauth2/token`, body.toString(), { headers })
      .pipe(
        tap((t) => {
          sessionStorage.removeItem(AuthService.PKCE_STATE);
          sessionStorage.removeItem(AuthService.PKCE_VERIFIER);
          sessionStorage.removeItem(AuthService.REMEMBER_KEY);

          const expiresAt = Date.now() + t.expires_in * 1000;
          this.storage.setTokens(
            {
              accessToken: t.access_token,
              idToken: t.id_token,
              refreshToken: t.refresh_token,
              expiresAt
            },
            remember
          );

          const claims = parseJwtClaims(t.id_token) ?? {};
          const user: User = {
            id: (claims["sub"] as string | undefined) ?? undefined,
            email: (claims["email"] as string | undefined) ?? undefined,
            name:
              (claims["name"] as string | undefined) ??
              (claims["cognito:username"] as string | undefined) ??
              undefined,
            ...claims
          };

          this.currentUserSubject.next(user);
        })
      );
  }

  /**
   * Clears local tokens and redirects to Cognito logout.
   */
  logoutRedirect(): void {
    const { domain, clientId, logoutUri } = environment.cognito;

    this.storage.clear();
    this.currentUserSubject.next(null);

    const url = new URL(`${domain}/logout`);
    url.searchParams.set("client_id", clientId);
    url.searchParams.set("logout_uri", logoutUri);

    window.location.assign(url.toString());
  }
    startSignupRedirect(uiLocale?: string): void {
    const { domain, clientId, redirectUri, scope } = environment.cognito;

    const url = new URL(`${domain}/signup`);
    url.searchParams.set("response_type", "code");
    url.searchParams.set("client_id", clientId);
    url.searchParams.set("redirect_uri", redirectUri);
    url.searchParams.set("scope", scope);
    if (uiLocale) url.searchParams.set("ui_locales", uiLocale);

    window.location.assign(url.toString());
  }
}