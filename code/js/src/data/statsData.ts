import { fetchReq } from "../utils/http";

async function top(limit: number = 10): Promise<void> {
  return await fetchReq(`/top?limit=${limit}`, "GET", null);
}

export default {
  top
};
