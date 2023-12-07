import {fetchAPI} from "../utils/http";
import {SirenEntity} from "../types/siren";

async function top(limit: number = 10): Promise<SirenEntity<any>> {
  return await fetchAPI(`api/stats/top?limit=${limit}`);
}

async function getUserStats(id: number): Promise<SirenEntity<any>>{
  return await fetchAPI(`api/stats/users/${id}`, "GET", null)
}

export default {
  top,
  getUserStats
};
