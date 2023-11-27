import { UserInfo } from "../types/user";
import { fetchReq } from "../utils/http";

async function login(email: string, password: string): Promise<UserInfo> {
  return {
    id: 1,
    name: "Graça",
    avatarUrl: "https://i.imgur.com/jv0tbT8.png",
    role: "admin",
    rank: "silver",
  }
  // return await fetchReq("/login", "POST", { email, password });
}

export default {
  login,
};
