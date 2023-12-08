import {PlayerCard} from "../../components/players/PlayerCard";
import {ReversedPlayerCard} from "../../components/players/ReversedPlayerCard";
import Board from "../../components/board/Board";
import * as React from "react";
import {useEffect, useState} from "react";
import {Navigate, useParams} from "react-router-dom";
import {homeLinks} from "../../index";
import {fetchAPI, requestBuilder} from "../../utils/http";
import {SirenEntity} from "../../types/siren";
import {Match} from "../../types/match";
import {Loading} from "../Loading/Loading";
import {BoardProvider} from "../../hooks/Board/Board";
import userData from "../../data/userData";
import {UserInfo} from "../../types/user";

export function Match() {
    const {id} = useParams()

    const [isLoading, setIsLoading] = useState(true)

    const [match, setMatch] = useState(undefined)
    const [currentUser, setCurrentUser] = useState(undefined)

    const [oppositeUser, setOppositeUser] = useState(undefined)
    const [deleteMatch, setDeleteMatch] = useState(undefined)

    const [redirect, setRedirect] = useState(undefined)


    async function polling(request: string) {
        const matchSiren: SirenEntity<Match> = await fetchAPI(request)
        const match = matchSiren.properties
        setMatch(match)
        setDeleteMatch((prev) => {
            return async () => {
                const deleteMatchAction = getDeleteMatchAction(matchSiren)
                await fetchAPI(deleteMatchAction.href, deleteMatchAction.method)
                setRedirect("/play")
            }
        })

        const currentUser = (await userData.getAuthenticatedUser()).properties
        setCurrentUser(currentUser)

        if (match.state == "ONGOING") {
            const blackPlayer: UserInfo = (await fetchAPI<UserInfo>(getBlackPlayerHref(matchSiren))).properties
            if (currentUser.id == blackPlayer.id) {
                const whitePlayer: UserInfo = (await fetchAPI<UserInfo>(getWhitePlayerHref(matchSiren))).properties
                setOppositeUser(whitePlayer)
            } else {
                setOppositeUser(blackPlayer)
            }

            setIsLoading(false)
        }
    }

    function getBlackPlayerHref(siren: SirenEntity<any>) {
        return siren.links.find(
            (it) => {
                return it.rel.find(
                    (it) => {
                        return it == "playerBlack"
                    }
                )
            }
        ).href;
    }

    function getWhitePlayerHref(siren: SirenEntity<any>) {
        return siren.links.find(
            (it) => {
                return it.rel.find(
                    (it) => {
                        return it == "whitePlayer"
                    }
                )
            }
        ).href;
    }

    function getDeleteMatchAction(siren: SirenEntity<any>) {
        return siren.actions.find(
            (it) => {
                return it.name == "delete-match"
            }
        )
    }

    useEffect(() => {
        const url = requestBuilder(homeLinks.matchById().href, [id])
        const tid = setInterval(
            () => {
                polling(url)
                    .then(r => {
                            if (match && match.state != "SETUP") {
                                setIsLoading(false)
                            }
                        }
                    )
            },
            500
        )
        return () => clearInterval(tid)
    }, []);

    if (isLoading) {
        return <Loading message="Searching for other player..." onCancel={deleteMatch}/>
    }

    if (redirect) {
        return <Navigate to={redirect} replace={true}/>
    }

    return (
        <div className="grid gap-y-8">
            <div className="flex justify-center items-center">
                <div className="grid grid-cols-2 gap-x-120">
                    <PlayerCard user={currentUser}></PlayerCard>
                    <ReversedPlayerCard user={oppositeUser}></ReversedPlayerCard>
                </div>
            </div>
            <BoardProvider>
                <Board board={match.board}></Board>
            </BoardProvider>
        </div>
    )
}