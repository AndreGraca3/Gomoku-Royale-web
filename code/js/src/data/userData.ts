import { SirenAction, SirenEntity } from "../types/siren";
import { fetchAPI, requestBuilder } from "../utils/http";
import { UserCreationInput, UserDetails, UserInfo } from "../types/user";
import { homeData } from "../index";

async function login(email: string, password: string): Promise<any> {
  const loginAction = homeData.createToken();
  return await fetchAPI(loginAction.href, loginAction.method, {
    email,
    password,
  });
}

async function logout(): Promise<any> {
  const logoutAction = homeData.deleteToken();
  return await fetchAPI(logoutAction.href, logoutAction.method);
}

async function getAuthenticatedUser(): Promise<SirenEntity<UserDetails>> {
  const userLink = homeData.authenticatedUser();
  return await fetchAPI(userLink.href);
}

async function getNonAuthenticatedUser(
  id: number
): Promise<SirenEntity<UserInfo>> {
  const userLink = homeData.nonAuthenticatedUser();
  return await fetchAPI(requestBuilder(userLink.href, [id]));
}

async function signUp(user: UserCreationInput) {
  const signUpAction = homeData.createUser();
  return await fetchAPI(signUpAction.href, signUpAction.method, user);
}

function getStatsHref(siren: SirenEntity<UserDetails>) {
  return siren.links.find((it) => {
    return it.rel.find((it) => {
      return it == "stats";
    });
  }).href;
}

function getUpdateUserAction(siren: SirenEntity<UserDetails>): SirenAction {
  return siren.actions.find((it) => {
    return it.name == "update-user";
  });
}

function getDeleteUserAction(siren): SirenAction {
  return siren.actions.find((it) => {
    return it.name == "delete-user";
  });
}

export default {
  login,
  getAuthenticatedUser,
  getNonAuthenticatedUser,
  signUp,
  getStatsHref,
  getUpdateUserAction,
  getDeleteUserAction,
  logout,
};
