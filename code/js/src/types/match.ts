import matchData from "../data/matchData";
import { fetchAPI } from "../utils/http";
import { BoardType, Player, Stone } from "./board";
import { SirenEntity } from "./siren";
import { UserInfo } from "./user";

export type MatchCreationInputModel = {
  isPrivate: boolean;
  size: number;
  variant: string;
};

export type MatchCreationOutPutModel = {
  id: string;
  state: "SETUP" | "ONGOING" | "FINISHED";
};

export type Match = {
  id: string;
  isPrivate: boolean;
  variant: string;
  blackId: number;
  whiteId: number;
  state: "SETUP" | "ONGOING" | "FINISHED";
  board: BoardType;
};

export type PlayOutputModel = {
  stone: Stone;
  matchState: string;
  turn: Player;
};

export type ForfeitOutputModel = {
  winner: Player;
  matchState: string;
};

// we could try use a class to remove some lines of code in Match.tsx, maybe next time...
/*
export class MatchSiren {
  constructor(
    sirenMatch: SirenEntity<Match>,
    currentUser: UserInfo,
    opponentUser: number
  ) {
    this.sirenMatch = sirenMatch;
    this.myColor =
      this.sirenMatch.properties.blackId === currentUser.id ? "BLACK" : "WHITE";
    this.myColor == "BLACK"
      ? (this.blackUser = currentUser)
      : (this.whiteUser = currentUser);
  }

  private sirenMatch: SirenEntity<Match>;
  myColor: Player;
  blackUser: UserInfo;
  whiteUser: UserInfo;

  get match(): Match {
    return this.sirenMatch.properties;
  }

  async play(stone: Stone): Promise<PlayOutputModel> {
    const playLink = matchData.getPlayMatchAction(this.sirenMatch);
    const response = await fetchAPI<PlayOutputModel>(
      playLink.href,
      playLink.method,
      { stone }
    );
    return response.properties;
  }

  async forfeit(): Promise<ForfeitOutputModel> {
    const forfeitLink = matchData.getForfeitMatchAction(this.sirenMatch);
    const response = await fetchAPI<ForfeitOutputModel>(
      forfeitLink.href,
      forfeitLink.method
    );
    return response.properties;
  }

  async cancel(): Promise<void> {
    const deleteLink = matchData.getDeleteMatchAction(this.sirenMatch);
    await fetchAPI(deleteLink.href, deleteLink.method);
  }
}
*/
