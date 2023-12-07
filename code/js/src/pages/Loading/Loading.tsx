import React, {useEffect, useState} from "react";
import ScaledButton from "../../components/ScaledButton";

export function Loading({message, onCancel}) {
    const totalPieces = 5
    const delayTime = 500 // ms
    const initialPieces = ["black_piece.png"];
    const [pieces, setPieces] = useState(initialPieces);

    useEffect(() => {
        const tid = setInterval(
            () => {
                if (pieces.length < totalPieces) {
                    setPieces((oldPieces) => [
                        ...oldPieces,
                        "black_piece.png" // new element
                    ]);
                } else {
                    setPieces(initialPieces); // back to start
                }
            },
            delayTime
        )
        return () => clearInterval(tid);
    }, [pieces]);

    return (
        <div className="grid grid-rows-3 gap-y-8">
            <div className={`grid grid-cols-5`}>
                {
                    pieces.map(
                        (it) => {
                            return <img src={it}/>
                        }
                    )
                }
            </div>
            <div className="text-center">{message}</div>
            {onCancel
                ? <ScaledButton
                    onClick={onCancel}
                    text="Cancel"
                    color="red"
                />
                : <div/>
            }
        </div>
    )
}