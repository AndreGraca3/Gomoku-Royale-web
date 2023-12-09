import { fetchAPI } from "../utils/http";
import { UserStats } from "../types/stats";
import { SirenEntity } from "../types/siren";

async function top(limit: number = 10) {
  var ret = await fetchAPI(`/stats/users/top?limit=${limit}`, "GET", null);
  console.log(ret);
  return ret;
}

async function getUserStats(id: number): Promise<SirenEntity<UserStats>> {
  return await fetchAPI(`/stats/users/${id}`, "GET", null);
}

export default {
  top,
  getUserStats,
};
