export interface User {
  id?: string;
  email?: string;
  name?: string;
  [k: string]: unknown;
}

export type TokenResponse = {
  access_token: string;
  id_token: string;
  refresh_token?: string;
  expires_in: number;
  token_type: "Bearer";
  scope?: string;
};