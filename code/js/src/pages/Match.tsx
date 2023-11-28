import {PlayerCard} from "../components/players/PlayerCard";
import {ReversedPlayerCard} from "../components/players/ReversedPlayerCard";
import Board, {setBoard} from "../components/board/Board";
import * as React from "react";
import {BoardType, Stone} from "../domain/board";

export function Match(props) {
    const match = props.match
    const board = match.board
    const user1 = match.user1
    const user2 = match.user2

    function play() {
        let newStones: Array<Stone> = board.stones
        newStones.push({
            player: {
                symbol: "w"
            },
            dot: {
                rowIdx: 3,
                colIdx: 4
            }
        })
        const newBoard: BoardType = {
            size: board.size,
            stones: newStones,
            turn: {
                symbol: "b"
            }
        }
        const setBoardFunction = setBoard()
        setBoardFunction(newBoard)
    }

    return (
        <div className="grid gap-y-8">
            <div className="flex justify-center items-center">
                <div className="grid grid-cols-2 gap-x-120">
                    <PlayerCard user={user1}></PlayerCard>
                    <ReversedPlayerCard user={user2}></ReversedPlayerCard>
                </div>
            </div>
            <Board board={board}></Board>
            <button onClick={play}>
                <p>Play!</p>
            </button>
        </div>
    )
}