import Cell from "./Cell";
import { BoardType, Dot, getStoneOrNull, toSymbol } from "../../types/board";

export default function Board({
  board,
  canPlay,
  onPlay,
}: {
  board: BoardType;
  canPlay: boolean;
  onPlay: (rowNumber: number, columnSymbol: string) => void;
}) {
  const boardTemplate = {
    15: "grid grid-cols-15",
    19: "grid grid-cols-19",
  };

  return (
    <div className="flex justify-center items-center">
      <div className="rounded-md p-4 border-2 border-black bg-dark-theme-color shadow-md shadow-black">
        <div className={"border-theme-color border-8 rounded-md overflow-hidden " + boardTemplate[board.size]}>
          {Array.from({ length: board.size ** 2 }).map((_, idx) => {
            const rowIdx = Math.floor(idx / board.size);
            const colIdx = Math.floor(idx % board.size);
            const rowNum = rowIdx + 1
            const columnSymbol = toSymbol(colIdx)
            return (
              <Cell
                key={idx}
                canPlay={canPlay}
                stone={getStoneOrNull(board, rowNum, columnSymbol)}
                onClick={() => {
                  onPlay(rowNum, columnSymbol);
                }}
              />
            );
          })}
        </div>
      </div>
    </div>
  );
}
