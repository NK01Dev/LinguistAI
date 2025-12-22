// File: src/app/features/auth/login/login.component.ts
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NonNullableFormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { ButtonModule } from 'primeng/button';
import { DividerModule } from 'primeng/divider';
import { CheckboxModule } from 'primeng/checkbox';
import { SelectModule } from 'primeng/select';

type LanguageOption = { label: string; value: string };

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,

    CardModule,
    InputTextModule,
    PasswordModule,
    ButtonModule,
    DividerModule,
    CheckboxModule,
    SelectModule,
  ],
  templateUrl: './login.component.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginComponent {
  private readonly fb = inject(NonNullableFormBuilder);
  private readonly router = inject(Router);

  readonly languages: LanguageOption[] = [
    { label: 'English', value: 'en' },
    { label: 'Français', value: 'fr' },
  ];

  readonly form = this.fb.group({
    language: this.fb.control('en'),
    // Hosted UI owns credentials → disable fields in the FormControl, not in the template.
    email: this.fb.control({ value: '', disabled: true }, { validators: [Validators.email] }),
    password: this.fb.control({ value: '', disabled: true }),
    remember: this.fb.control(true),
  });

  loading = false;

  continue(): void {
    // For now: keep app stable. Replace with your Cognito Hosted UI redirect.
    // Example: window.location.href = buildCognitoHostedUrl(...)
    void this.router.navigateByUrl('/app');
  }

  forgotPassword(): void {
    void this.router.navigateByUrl('/forgot-password');
  }

  signUp(): void {
    void this.router.navigateByUrl('/register');
  }
}
