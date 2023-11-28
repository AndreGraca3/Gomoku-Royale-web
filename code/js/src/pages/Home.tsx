import React from "react";
import {BoardType, Player, Stone} from "../domain/board";
import {MatchesStats, UserStats, WinStats} from "../components/UserStats/UserStats";
import {Match} from "./Match";

const player: Player = {
    symbol: "b",
};

const dot = {
    rowIdx: 1,
    colIdx: 0,
};

let currentStone: Stone = {
    player: player,
    dot: dot,
};

export let board: BoardType = {
    size: 15,
    stones: [currentStone],
    turn: player,
};

export const user1 = {
    name: "Diogo",
    email: "Diogo@gmail.com",
    rank: "Silver"
}

const user2 = {
    name: "André",
    email: "André@gmail.com",
    rank: "Silver"
}

const winStats: WinStats = {
    totalWins: 2,
    winsAsBlack: 2,
    winsAsWhite: 0,
    winRate: 2,
    draws: 0,
    loses: 0
}

const matchesStats: MatchesStats = {
    totalMatches: 2,
    matchesAsBlack: 2,
    matchesAsWhite: 0
}

export const userStats: UserStats = {
    winStats: winStats,
    matchesStats: matchesStats
}

export default function Home() {
    return (
        <Match match={{
            board: board,
            user1: user1,
            user2: user2
        }
        }></Match>
    );
}