import Cell from "./Cell";
import { getStoneOrNull, toSymbol } from "../../types/board";
import * as React from "react";

export default function Board({ board, play }) {
  let arr = Array(board.size * board.size);

  for (let i = 0; i < arr.length; i++) {
    arr[i] = i;
  }

  let grid = "grid grid-cols-15";

  if (board.size == 19) {
    grid = "grid grid-cols-19";
  }

  return (
    <div className="flex justify-center items-center">
      <div className="border-4 border-yellow-900">
        <div className={grid}>
          {arr.map((it) => {
            const rowIdx = Math.floor(it / board.size);
            const colIdx = Math.floor(it % board.size);
            return (
              <Cell
                stone={getStoneOrNull(board, {
                  rowIdx: rowIdx,
                  colIdx: colIdx,
                })}
                onClick={() => {
                  play(rowIdx, toSymbol(colIdx));
                }}
              ></Cell>
            );
          })}
        </div>
      </div>
    </div>
  );
}
