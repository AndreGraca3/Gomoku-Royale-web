import { fetchReq } from "../utils/http";
import {UserStats} from "../types/stats";

async function top(limit: number = 10) {
  var ret = await fetchReq(`/stats/top?limit=${limit}`, "GET", null);
  console.log(ret)
  return ret
}

async function getUserStats(id: number){
  return await fetchReq(`/stats/users/${id}`, "GET", null)
}

export default {
  top,
  getUserStats
};
