import Cell from "./Cell";
import {BoardType, getStoneOrNull} from '../../types/board';
import {createContext, useContext, useState} from "react";
import * as React from "react";

export default function Board({board}: {board: BoardType}) {
    const setBoardFunction = setBoard()
    setBoardFunction(board)

    let arr = Array(board.size * board.size);

    for (let i = 0; i < arr.length; i++) {
        arr[i] = i
    }

    return (
        <div className="flex justify-center items-center">
            <div className="border-4 border-yellow-900 w-1/3">
                <div className="grid grid-cols-15 gap-0">
                    {
                        arr.map(it =>
                            getStoneOrNull(board, {
                                    rowIdx: Math.floor(it / board.size),
                                    colIdx: Math.floor(it % board.size),
                                }
                            )
                        )
                            .map(
                                (it) => <Cell stone={it}></Cell>
                            )
                    }
                </div>
            </div>
        </div>
    )
}

type BoardContextType = {
    board: BoardType,
    setBoard: (b: BoardType) => void
}

const initialBoard: BoardType =  {
    size: 15,
    stones: [],
    turn: {
        symbol: "b"
    }
}

const BoardContext = createContext<BoardContextType>({
    board: initialBoard,
    setBoard: () => {}
})

export function BoardContextProvider({children}: {children: React.ReactNode}) {
    const [board, setBoard] = useState(initialBoard)
    return (
        <BoardContext.Provider value={{board: board, setBoard: setBoard}}>
            {children}
        </BoardContext.Provider>
    )
}

export function getBoard(): BoardType {
    return useContext(BoardContext).board
}

export function setBoard() {
    return useContext(BoardContext).setBoard
}