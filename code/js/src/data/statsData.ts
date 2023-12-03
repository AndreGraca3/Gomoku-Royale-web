import { fetchReq } from "../utils/http";

async function top(limit: number = 10) {
  var ret = await fetchReq(`/stats/top?limit=${limit}`, "GET", null);
  console.log(ret)
  return ret
}

export default {
  top
};
