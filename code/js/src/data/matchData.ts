import {homeLinks} from "../index";
import {fetchAPI} from "../utils/http";
import {MatchCreationInputModel} from "../types/match";

async function createMatch(preferences: MatchCreationInputModel) {
    const createMatchAction = homeLinks.createMatch();
    return await fetchAPI(createMatchAction.href, createMatchAction.method, preferences);
}

export default {
    createMatch
}