export type BoardType = {
  size: number;
  stones: Array<Stone>;
  turn: Player;
};

export class Stone {
  constructor(player: Player, dot: Dot) {
    this.player = player;
    this.dot = dot;
  }
  player: Player;
  dot: Dot;
}

export class Dot {
  constructor(rowNum: number, columnSymbol: string) {
    this.row = { number: rowNum };
    this.column = { symbol: columnSymbol };
  }
  row: Row;
  column: Column;

  equals(otherDot: Dot): boolean {
    return (
      this.row.number === otherDot.row.number &&
      this.column.symbol === otherDot.column.symbol
    );
  }
}

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
