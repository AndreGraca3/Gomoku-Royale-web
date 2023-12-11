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
  row: Row;
  column: Column;
};

export type Row = {
  number: number;
};

export type Column = {
  symbol: string;
};

export type Player = "BLACK" | "WHITE";

export function getStoneOrNull(
  board: BoardType,
  rowNum: number,
  columnSymbol: string
) {
  return board.stones.find(
    (it) => it.dot.row.number == rowNum && it.dot.column.symbol == columnSymbol
  );
}

export function toSymbol(colIdx: number) {
  const codeA = "a".charCodeAt(0);
  return String.fromCharCode(codeA + colIdx);
}
