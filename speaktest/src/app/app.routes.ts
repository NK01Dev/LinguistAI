import { Routes } from '@angular/router';
import { LoginComponent } from './features/auth/login.component/login.component';
import { RegisterComponent } from './features/auth/register.component/register.component';
import { ForgotPasswordComponent } from './features/auth/forgot-password.component/forgot-password.component';
import { DashboardComponent } from './features/app/dashboard.component/dashboard.component';
import { authGuard } from './core/auth/auth.guard';
import { AuthCallbackComponent } from './features/auth/callback/auth-callback.component';

export const routes: Routes = [
  { path: '', pathMatch: 'full', redirectTo: 'login' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'auth/callback', component: AuthCallbackComponent },
  { path: 'forgot-password', component: ForgotPasswordComponent },
  { path: 'app', canActivate: [authGuard], component: DashboardComponent },
  { path: '**', redirectTo: 'login' },
];
