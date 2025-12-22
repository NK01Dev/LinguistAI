function base64UrlEncode(bytes: Uint8Array): string {
  return btoa(String.fromCharCode(...bytes))
    .replace(/\+/g, "-")
    .replace(/\//g, "_")
    .replace(/=+$/g, "");
}

function randomString(byteLen: number): string {
  const bytes = crypto.getRandomValues(new Uint8Array(byteLen));
  return base64UrlEncode(bytes);
}

async function sha256(input: string): Promise<Uint8Array> {
  const data = new TextEncoder().encode(input);
  const digest = await crypto.subtle.digest("SHA-256", data);
  return new Uint8Array(digest);
}

export async function createPkce(): Promise<{
  state: string;
  verifier: string;
  challenge: string;
}> {
  const state = randomString(24);
  const verifier = randomString(64);
  const challenge = base64UrlEncode(await sha256(verifier));
  return { state, verifier, challenge };
}
