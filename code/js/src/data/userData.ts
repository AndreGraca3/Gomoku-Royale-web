import { SirenEntity } from "../types/siren";
import { fetchAPI } from "../utils/http";
import {UserCreationInput, UserDetails, UserInfo} from "../types/user";
import {homeLinks} from "../index";

async function login(email: string, password: string): Promise<any> {
  const loginAction = homeLinks.createToken();
  return await fetchAPI(loginAction.href, loginAction.method, { email, password });
}

async function getAuthenticatedUser(): Promise<SirenEntity<UserDetails>> {
  const userLink = homeLinks.authenticatedUser();
  return await fetchAPI(userLink.href);
}

async function signUp(user: UserCreationInput) {
  const signUpAction = homeLinks.createUser();
  return await fetchAPI(signUpAction.href, signUpAction.method, user);
}

export default {
  login,
  getAuthenticatedUser,
  signUp
};
