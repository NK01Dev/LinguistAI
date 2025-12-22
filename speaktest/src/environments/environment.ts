export const environment = {
  production: false,
  cognito: {
    domain: "https://YOUR_DOMAIN.auth.YOUR_REGION.amazoncognito.com",
    clientId: "YOUR_APP_CLIENT_ID",
    redirectUri: "http://localhost:4200/auth/callback",
    logoutUri: "http://localhost:4200/",
    scope: "openid email profile"
  }
};
