import { homeLinks } from "../index";
import { fetchAPI } from "../utils/http";
import {
  MatchCreationInputModel,
  MatchCreationOutPutModel,
} from "../types/match";
import { SirenEntity } from "../types/siren";

async function createMatch(
  preferences: MatchCreationInputModel
): Promise<SirenEntity<MatchCreationOutPutModel>> {
  const createMatchAction = homeLinks.createMatch();
  return await fetchAPI(
    createMatchAction.href,
    createMatchAction.method,
    preferences
  );
}

function getBlackPlayerHref(siren: SirenEntity<any>) {
  return siren.entities.find((it) => {
    return it.rel.find((it) => {
      return it == "playerBlack";
    });
  }).href;
}

function getWhitePlayerHref(siren: SirenEntity<any>) {
  return siren.entities.find((it) => {
    return it.rel.find((it) => {
      return it == "playerWhite";
    });
  }).href;
}

function getDeleteMatchAction(siren: SirenEntity<any>) {
  return siren.actions.find((it) => {
    return it.name == "delete-match";
  });
}

function getPlayMatchAction(siren: SirenEntity<any>) {
  return siren.actions.find((it) => {
    return it.name == "play-match";
  });
}

export default {
  createMatch,
  getPlayMatchAction,
  getDeleteMatchAction,
  getWhitePlayerHref,
  getBlackPlayerHref,
};
