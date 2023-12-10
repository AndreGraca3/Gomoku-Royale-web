import Cell from "./Cell";
import { BoardType, Dot, getStoneOrNull, toSymbol } from "../../types/board";

export default function Board({
  board,
  onPlay,
}: {
  board: BoardType;
  onPlay: (rowNumber: number, columnSymbol: string) => void;
}) {
  const boardTemplate = {
    15: "grid grid-cols-15",
    19: "grid grid-cols-19",
  };

  // console.log("new board render:", board); // Possibly rendering too much

  return (
    <div className="flex justify-center items-center">
      <div className="border-4 border-yellow-900">
        <div className={boardTemplate[board.size]}>
          {Array.from({ length: board.size ** 2 }).map((_, idx) => {
            const rowIdx = Math.floor(idx / board.size);
            const colIdx = Math.floor(idx % board.size);
            const rowNum = rowIdx + 1
            const columnSymbol = toSymbol(colIdx)
            return (
              <Cell
                key={idx}
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
