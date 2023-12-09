import { fetchAPI, requestBuilder } from "../utils/http";
import { SirenEntity } from "../types/siren";
import { homeLinks } from "../index";

// async function top(limit: number = 10): Promise<SirenEntity<any>> {
//   const leaderBoardLink = homeLinks.leaderboard().href;
//   return await fetchAPI(requestBuilder(leaderBoardLink, [limit]));
// }

async function top(limit: number = 10): Promise<SirenEntity<any>>  {
  var ret = await fetchAPI(`/stats/users/top?limit=${limit}`, "GET", null);
  console.log(ret);
  return ret;
}

export default {
  top,
};
