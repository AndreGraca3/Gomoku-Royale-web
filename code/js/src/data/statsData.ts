import { fetchReq } from "../utils/http";

async function top(): Promise<void> {
  return await fetchReq("/top?limit=10", "GET", null);
}

export default {
  top
};