import { SirenAction, SirenEntity } from "../types/siren";
import { fetchAPI } from "../utils/http";
import { UserCreationInput, UserDetails } from "../types/user";
import { homeLinks } from "../index";

async function login(email: string, password: string): Promise<any> {
  const loginAction = homeLinks.createToken();
  return await fetchAPI(loginAction.href, loginAction.method, {
    email,
    password,
  });
}

async function getAuthenticatedUser(): Promise<SirenEntity<UserDetails>> {
  const userLink = homeLinks.authenticatedUser();
  return await fetchAPI(userLink.href);
}

async function signUp(user: UserCreationInput) {
  const signUpAction = homeLinks.createUser();
  return await fetchAPI(signUpAction.href, signUpAction.method, user);
}

function getStatsHref(siren) {
  return siren.links.find((it) => {
    return it.rel.find((it) => {
      return it == "stats";
    });
  }).href;
}

function getUpdateUserAction(siren): SirenAction {
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
  signUp,
  getStatsHref,
  getUpdateUserAction,
  getDeleteUserAction,
};
