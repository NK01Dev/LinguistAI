import { Component, inject } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { AuthService } from "../../../core/auth/auth.service";

@Component({
  selector: "app-auth-callback",
  standalone: true,
  template: `<div class="p-4">Signing you in…</div>`
})
export class AuthCallbackComponent {
  private readonly route = inject(ActivatedRoute);
  private readonly router = inject(Router);
  private readonly auth = inject(AuthService);

  ngOnInit(): void {
    const code = this.route.snapshot.queryParamMap.get("code");
    const state = this.route.snapshot.queryParamMap.get("state");

    if (!code || !state) {
      this.router.navigateByUrl("/login");
      return;
    }

    this.auth.handleRedirectCallback(code, state).subscribe({
      next: () => this.router.navigateByUrl("/app"),
      error: () => this.router.navigateByUrl("/login")
    });
  }
}