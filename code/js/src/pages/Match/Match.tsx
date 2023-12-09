import { PlayerCard } from "../../components/players/PlayerCard";
import { ReversedPlayerCard } from "../../components/players/ReversedPlayerCard";
import Board from "../../components/board/Board";
import { useEffect, useState } from "react";
import { Navigate, useParams } from "react-router-dom";
import { homeLinks } from "../../index";
import { fetchAPI, requestBuilder } from "../../utils/http";
import { SirenEntity } from "../../types/siren";
import { Match } from "../../types/match";
import userData from "../../data/userData";
import { UserInfo } from "../../types/user";
import { Loading } from "../Loading/Loading";
import matchData from "../../data/matchData";

export function Match() {
  const { id } = useParams();

  const [isLoading, setIsLoading] = useState(true);

  const [board, setBoard] = useState(undefined);

  const [currentUser, setCurrentUser] = useState(undefined);
  const [oppositeUser, setOppositeUser] = useState(undefined);

  const [deleteMatch, setDeleteMatch] = useState(undefined);
  const [playMatch, setPlayMatch] = useState(undefined);

  const [redirect, setRedirect] = useState(undefined);

  async function polling(request: string) {
    const matchSiren: SirenEntity<Match> = await fetchAPI(request);
    const match = matchSiren.properties;
    const currentBoard = match.board;
    setBoard((prev) => {
      return currentBoard;
    });
    setDeleteMatch((prev) => {
      return async () => {
        const deleteMatchAction = matchData.getDeleteMatchAction(matchSiren);
        await fetchAPI(deleteMatchAction.href, deleteMatchAction.method);
        setRedirect("/play");
      };
    });

    setPlayMatch((prev) => {
      return async (rowNumber, columnSymbol) => {
        const playMatchAction = matchData.getPlayMatchAction(matchSiren);
        return await fetchAPI(playMatchAction.href, playMatchAction.method, {
          row: {
            number: rowNumber,
          },
          column: {
            symbol: columnSymbol,
          },
        });
      };
    });

    if (match.state == "ONGOING") {
      const fetchedCurrentUser = await userData.getAuthenticatedUser();
      const blackPlayer: UserInfo = (
        await fetchAPI<UserInfo>(matchData.getBlackPlayerHref(matchSiren))
      ).properties;
      const whitePlayer: UserInfo = (
        await fetchAPI<UserInfo>(matchData.getWhitePlayerHref(matchSiren))
      ).properties;
      if (fetchedCurrentUser.properties.id == blackPlayer.id) {
        setCurrentUser(blackPlayer);
        setOppositeUser(whitePlayer);
      } else {
        setOppositeUser(blackPlayer);
        setCurrentUser(whitePlayer);
      }

      setIsLoading(false);
    }
    console.log(matchSiren)
    console.log(match)
    console.log(currentBoard)
    console.log(board)
  }

  useEffect(() => {
    const url = requestBuilder(homeLinks.matchById().href, [id]);
    const tid = setInterval(() => {
      console.log("I'm polling");
      polling(url);
    }, 2000);
    return () => clearInterval(tid);
  }, []);

  if (redirect) {
    return <Navigate to={redirect} replace={true} />;
  }

  if (isLoading) {
    return (
      <Loading message="Searching for other player..." onCancel={deleteMatch} />
    );
  }

  return (
    <div className="grid gap-y-4">
      <div className="flex justify-center items-center">
        <div className="grid grid-cols-2 gap-x-120">
          <PlayerCard user={currentUser}></PlayerCard>
          <ReversedPlayerCard user={oppositeUser}></ReversedPlayerCard>
        </div>
      </div>
      <Board board={board} play={playMatch}></Board>
    </div>
  );
}
