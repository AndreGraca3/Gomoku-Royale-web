export type User = {
    name: string,
    email: string
    rank: string
}

export type match = {
    board: BoardType,
    user1: User,
    user2: User
}

export type BoardType = {
    size: number;
    stones: Array<Stone>;
    turn: Player;
};

export type Stone = {
    player: Player;
    dot: Dot;
};

export type Dot = {
    rowIdx: number;
    colIdx: number;
};

export type Player = {
    symbol: "b" | "w";
};

export function getStoneOrNull(board: BoardType, dot: Dot) {
    return board.stones.find(it => it.dot.rowIdx == dot.rowIdx && it.dot.colIdx == dot.colIdx)
}