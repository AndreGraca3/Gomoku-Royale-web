import { fetchReq } from "../utils/http";
import {UserStats} from "../types/stats";

async function top(limit: number = 10): Promise<void> {
  return await fetchReq(`/top?limit=${limit}`, "GET", null);
}

async function getUserStats(id: number){
  return await fetchReq(`/stats/users/${id}`, "GET", null)
}

export default {
  top,
  getUserStats
};
