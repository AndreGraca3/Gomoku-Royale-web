import React from "react";

export default function Cell(props) {
    const stone = props.stone;

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
