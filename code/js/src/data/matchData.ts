import {homeLinks} from "../index";
import {fetchAPI} from "../utils/http";
import {MatchCreationInputModel, MatchCreationOutPutModel} from "../types/match";
import {SirenEntity} from "../types/siren";

async function createMatch(preferences: MatchCreationInputModel): Promise<SirenEntity<MatchCreationOutPutModel>> {
    const createMatchAction = homeLinks.createMatch();
    return await fetchAPI(createMatchAction.href, createMatchAction.method, preferences);
}

export default {
    createMatch
}