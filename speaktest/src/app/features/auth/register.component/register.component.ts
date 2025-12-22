import { ChangeDetectionStrategy, Component, inject } from "@angular/core";
import { CommonModule } from "@angular/common";
import { NonNullableFormBuilder, ReactiveFormsModule } from "@angular/forms";
import { RouterLink } from "@angular/router";

import { CardModule } from "primeng/card";
import { InputTextModule } from "primeng/inputtext";
import { PasswordModule } from "primeng/password";
import { ButtonModule } from "primeng/button";
import { DividerModule } from "primeng/divider";
import { SelectModule } from "primeng/select";

import { AuthService } from "../../../core/auth/auth.service";

type LanguageOption = { label: string; value: string };

@Component({
  selector: "app-register",
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    RouterLink,
    CardModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    DividerModule,
    SelectModule
  ],
  templateUrl: "./register.component.html",
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class RegisterComponent {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly auth = inject(AuthService);

  loading = false;

  readonly languages: LanguageOption[] = [
    { label: "English", value: "en" },
    { label: "Français", value: "fr" },
    { label: "Español", value: "es" }
  ];

  // Hosted UI owns credentials -> disable fields in FormControls (not in template)
  readonly form = this.fb.group({
    language: this.fb.control("en"),
    email: this.fb.control({ value: "", disabled: true }),
    password: this.fb.control({ value: "", disabled: true }),
    confirmPassword: this.fb.control({ value: "", disabled: true })
  });

  register(): void {
    if (this.loading) return;
    this.loading = true;
    const { language } = this.form.getRawValue();
    this.auth.startSignupRedirect(language);
  }

  signIn(): void {
    const { language } = this.form.getRawValue();
    void this.auth.startLoginRedirect(true, language);
  }
}
