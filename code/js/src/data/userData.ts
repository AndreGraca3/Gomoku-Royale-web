import { UserInfo } from "../types/user";
import { fetchReq } from "../utils/http";

async function login(email: string, password: string): Promise<void> {
  return await fetchReq("/users/token", "PUT", { email, password });
}

async function getUserHome(): Promise<UserInfo> {
  return await fetchReq("/users/me");
}

export default {
  login,
  getUserHome,
};
