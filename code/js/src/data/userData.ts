import {UserCreationInput, UserInfo} from "../types/user";
import { SirenEntity } from "../types/siren";
import { fetchAPI } from "../utils/http";

async function login(email: string, password: string): Promise<SirenEntity<void>> {
  return await fetchAPI("/users/token", "PUT", { email, password });
}

async function getUserHome(): Promise<SirenEntity<UserInfo>> {
  return await fetchAPI("/users/me");
}

async function signUp(user: UserCreationInput): Promise<void> {
  await fetchAPI("/users", "POST", user);
}

async function getAuthenticatedUser(){
  return await fetchAPI("/users/me", "GET", null);
}

export default {
  login,
  getUserHome,
  signUp,
  getAuthenticatedUser
};
