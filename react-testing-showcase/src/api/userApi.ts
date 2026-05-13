export type UserProfile = {
  id: string;
  name: string;
  balanceSol: number;
};

export async function fetchCurrentUser(signal?: AbortSignal): Promise<UserProfile> {
  const res = await fetch("/api/me", { signal });
  if (!res.ok) {
    throw new Error(`HTTP ${res.status}`);
  }
  return res.json() as Promise<UserProfile>;
}
