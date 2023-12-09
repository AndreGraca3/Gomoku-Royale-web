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
  return board.stones.find(
    (it) => it.dot.rowIdx == dot.rowIdx && it.dot.colIdx == dot.colIdx
  );
}

export function toSymbol(colIdx: number) {
  const codeA = "a".charCodeAt(0);
  return String.fromCharCode(codeA + colIdx);
}
