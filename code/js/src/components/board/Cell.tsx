import React from "react";
import {getBoard, setBoard} from "./Board";
import {BoardType} from "../../domain/board";

export default function Cell(props) {
    const stone = props.stone;

    function clickHandler() {
        const board = getBoard()
        let newStones = board.stones
        newStones.push({
            player: {
                symbol: "w"
            },
            dot: {
                rowIdx: 2,
                colIdx: 2
            }
        })
        const newBoard: BoardType = {
            size: board.size,
            stones: newStones,
            turn: {
                symbol: "b"
            }
        }
        const functionSetBoard = setBoard()
        functionSetBoard(newBoard)
    }

    function getPiecePng() {
        if (!stone) return;
        switch (stone.player.symbol) {
            case "b":
                return <img className="absolute top-0 left-0" src="black_piece.png"/>;
            case "w":
                return <img className="absolute top-0 left-0" src="white_piece.png"/>;
            default:
                return;
        }
    }

    return (
        <button className="aspect-square bg-green-900 resize w-7 h-7">
            <div className="relative">
                <img className="relative" src="cross.png"/>
                {getPiecePng()}
            </div>
        </button>
    );
}
