import { SirenEntity } from "../types/siren";
import { UserInfo } from "../types/user";
import { fetchAPI } from "../utils/http";

async function login(email: string, password: string): Promise<SirenEntity<void>> {
  return await fetchAPI("/users/token", "PUT", { email, password });
}

async function getUserHome(): Promise<SirenEntity<UserInfo>> {
  return await fetchAPI("/users/me");
}

export default {
  login,
  getUserHome,
};
