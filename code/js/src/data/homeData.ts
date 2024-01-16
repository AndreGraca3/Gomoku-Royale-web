import { SirenAction, SirenEntity, SirenLink } from "../types/siren";

export class HomeData {
  homeContent: SirenEntity<any>;

  constructor(homeContent: SirenEntity<any>) {
    this.homeContent = homeContent;
  }

  leaderboard(): SirenLink {
    return this.homeContent.links.find((it) => {
      return it.rel.find((it) => {
        return it == "leaderboard";
      });
    });
  }

  authenticatedUser(): SirenLink {
    return this.homeContent.links.find((it) => {
      return it.rel.find((it) => {
        return it == "authenticatedUser";
      });
    });
  }

  nonAuthenticatedUser(): SirenLink {
    return this.homeContent.links.find((it) => {
      return it.rel.find((it) => {
        return it == "nonAuthenticatedUser";
      });
    });
  }

  matchById(): SirenLink {
    return this.homeContent.links.find((it) => {
      return it.rel.find((it) => {
        return it == "match";
      });
    });
  }

  createToken(): SirenAction {
    return this.homeContent.actions.find((it) => {
      return it.name == "create-token";
    });
  }

  deleteToken(): SirenAction {
    return this.homeContent.actions.find((it) => {
      return it.name == "delete-token";
    });
  }

  verifyAuth(): SirenAction {
    return this.homeContent.actions.find((it) => {
      return it.name == "verify-auth";
    });
  }

  createUser(): SirenAction {
    return this.homeContent.actions.find((it) => {
      return it.name == "create-user";
    });
  }

  createMatch(): SirenAction {
    return this.homeContent.actions.find((it) => {
      return it.name == "create-public-match";
    });
  }

  joinMatch(matchId: string): SirenAction {
    return this.homeContent.actions.find((it) => {
      return it.name == "join-private-match";
    });
  }
}
