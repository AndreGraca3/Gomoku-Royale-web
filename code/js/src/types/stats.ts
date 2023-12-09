export type UserStats = {
  rank: Rank;
  winStats: WinStats;
  matchesStats: MatchesStats;
};

export type Rank = {
  name: string;
  iconUrl: string;
};

export type WinStats = {
  totalWins: number;
  winsAsBlack: number;
  winsAsWhite: number;
  winRate: number;
  draws: number;
  loses: number;
};

export type MatchesStats = {
  totalMatches: number;
  matchesAsBlack: number;
  matchesAsWhite: number;
};

export type PaginationResult<T> = {
    results: T[];
    total: number;
}

export type UserItem = {
    id: number;
    name: string;
    role: string;
    count?: number | null;
    rank: {
        name: string;
        iconUrl: string;
    }
}