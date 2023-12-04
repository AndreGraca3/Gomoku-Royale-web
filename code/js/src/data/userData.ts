import {UserCreationInput, UserDetails, UserInfo} from "../types/user";
import { fetchReq } from "../utils/http";

async function login(email: string, password: string): Promise<void> {
  return await fetchReq("/users/token", "PUT", { email, password });
}

async function getUserHome(): Promise<any> {
  return await fetchReq("/users/me");
}

async function signUp(user: UserCreationInput): Promise<void> {
  return await fetchReq("/users", "POST", user);
}

async function getAuthenticatedUser(){
  return await fetchReq("/users/me", "GET", null);
}

export default {
  login,
  getUserHome,
  signUp,
  getAuthenticatedUser
};
