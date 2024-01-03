import { homeLinks } from "../index";
import { fetchAPI, requestBuilder } from "../utils/http";
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

async function joinMatch(matchId: string): Promise<SirenEntity<any>> {
  const joinMatchAction = homeLinks.joinMatch(matchId);
  const joinMatchUrl = requestBuilder(joinMatchAction.href, [matchId]);
  return await fetchAPI(joinMatchUrl, joinMatchAction.method);
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

function getForfeitMatchAction(siren: SirenEntity<any>) {
  return siren.actions.find((it) => {
    return it.name == "forfeit-match";
  });
}

export default {
  createMatch,
  joinMatch,
  getPlayMatchAction,
  getDeleteMatchAction,
  getForfeitMatchAction,
  getWhitePlayerHref,
  getBlackPlayerHref,
};
