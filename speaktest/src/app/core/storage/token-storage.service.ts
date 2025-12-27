import { Injectable } from "@angular/core";

type StoredTokens = {
  accessToken: string;
  idToken: string;
  refreshToken?: string;
  expiresAt: number; // epoch ms
};

@Injectable({ providedIn: "root" })
export class TokenStorageService {
  private static readonly ACCESS = "cognito_access_token";
  private static readonly ID = "cognito_id_token";
  private static readonly REFRESH = "cognito_refresh_token";
  private static readonly EXPIRES_AT = "cognito_expires_at";

  private readFromAny(key: string): string | null {
    return sessionStorage.getItem(key) ?? localStorage.getItem(key);
  }

  private clearFromBoth(key: string): void {
    sessionStorage.removeItem(key);
    localStorage.removeItem(key);
  }

  getAccessToken(): string | null {
    return this.readFromAny(TokenStorageService.ACCESS);
  }

  getIdToken(): string | null {
    return this.readFromAny(TokenStorageService.ID);
  }

  getRefreshToken(): string | null {
    return this.readFromAny(TokenStorageService.REFRESH);
  }

  getExpiresAt(): number | null {
    const raw = this.readFromAny(TokenStorageService.EXPIRES_AT);
    if (!raw) return null;
    const n = Number(raw);
    return Number.isFinite(n) ? n : null;
  }

  setTokens(tokens: StoredTokens, remember: boolean): void {
    this.clear();

    const store = remember ? localStorage : sessionStorage;
    store.setItem(TokenStorageService.ACCESS, tokens.accessToken);
    store.setItem(TokenStorageService.ID, tokens.idToken);
    store.setItem(TokenStorageService.EXPIRES_AT, String(tokens.expiresAt));
    if (tokens.refreshToken) store.setItem(TokenStorageService.REFRESH, tokens.refreshToken);
  }

  clear(): void {
    this.clearFromBoth(TokenStorageService.ACCESS);
    this.clearFromBoth(TokenStorageService.ID);
    this.clearFromBoth(TokenStorageService.REFRESH);
    this.clearFromBoth(TokenStorageService.EXPIRES_AT);
  }
}
