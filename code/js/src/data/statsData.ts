import { fetchAPI, requestBuilder } from "../utils/http";
import { SirenEntity } from "../types/siren";
import { homeLinks } from "../index";

async function top(limit: number = 10, skip: number = 0): Promise<SirenEntity<any>> {
  const leaderBoardLink = homeLinks.leaderboard().href;
  console.log(leaderBoardLink)
  var path = requestBuilder(leaderBoardLink, { skip: skip, limit: limit });
  console.log(path)
  return await fetchAPI(path);
}

// async function top(limit: number): Promise<SirenEntity<any>>  {
//   var ret = await fetchAPI(`api/stats/users/top?limit=${limit}`, "GET");
//   return ret;
// }

export default {
  top,
};
