import {PlayerCard} from "../../components/players/PlayerCard";
import {ReversedPlayerCard} from "../../components/players/ReversedPlayerCard";
import Board, {setBoard} from "../../components/board/Board";
import * as React from "react";
import {BoardType, Stone} from '../../types/board';
import {useEffect, useState} from "react";
import {Loading} from "../Loading/Loading";
import {homeLinks} from "../../index";
import {fetchAPI} from "../../utils/http";
import userData from "../../data/userData";
import matchData from "../../data/matchData";

export function Match() {
    const [isLoading, setIsLoading] = useState(true)

    const polling = async () => {
        const userSiren = await userData.getAuthenticatedUser()
        //const matchSiren = matchData.createMatch()
        // get match, esta operação é obtida após criação da match/entrada na queue


    }

    useEffect(() => {

    }, []);

    if (isLoading) {
        return <Loading message="Searching for other player..." onCancel={undefined}/>
    }

    return (
        <div className="grid gap-y-8">
            <div className="flex justify-center items-center">
                <div className="grid grid-cols-2 gap-x-120">
                    <PlayerCard user={undefined}></PlayerCard>
                    <ReversedPlayerCard user={undefined}></ReversedPlayerCard>
                </div>
            </div>
            <Board board={undefined}></Board>
            <button onClick={undefined}>
                <p>Play!</p>
            </button>
        </div>
    )
}