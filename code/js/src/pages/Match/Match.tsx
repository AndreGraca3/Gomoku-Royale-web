import { PlayerCard } from "../../components/players/PlayerCard";
import Board from "../../components/board/Board";
import { useEffect, useReducer, useState } from "react";
import { useParams } from "react-router-dom";
import { homeLinks } from "../../index";
import { fetchAPI, requestBuilder } from "../../utils/http";
import { SirenEntity } from "../../types/siren";
import { Match } from "../../types/match";
import { UserInfo } from "../../types/user";
import { Loading } from "../../components/Loading";
import matchData from "../../data/matchData";
import ScaledButton from "../../components/ScaledButton";
import { useNavigate } from "react-router-dom";
import { BoardType } from "../../types/board";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";
import { useCurrentUser } from "../../hooks/Auth/AuthnStatus";

type State =
  | { type: "LOADING"; board: BoardType }
  | { type: "MY_TURN"; board: BoardType }
  | { type: "OPPONENT_TURN"; board: BoardType }
  | { type: "FINISHED"; board: BoardType }
  | { type: "ERROR"; board?: undefined; error: string };

export function Match() {
  const navigate = useNavigate();
  const { id } = useParams();
  const currentUser = useCurrentUser();
  const getMatchUrl = requestBuilder(homeLinks.matchById().href, [id]);

  const [sirenMatch, setSirenMatch] = useState(undefined);

  const [blackUser, setBlackUser] = useState(undefined);
  const [whiteUser, setWhiteUser] = useState(undefined);

  function getTurn() {
    if (
      (currentUser.id == blackUser.id &&
        sirenMatch.properties.board.turn == "BLACK") ||
      (currentUser.id == whiteUser.id &&
        sirenMatch.properties.board.turn == "WHITE")
    )
      return "MY_TURN";
    else return "OPPONENT_TURN";
  }

  function reducer(state: State, action: any): State {
    switch (action.type) {
      case "OPPONENT_JOINED":
        console.log("OPPONENT_JOINED");
        return { type: getTurn(), board: action.board };

      case "CHANGE_TURN":
        console.log("CHANGE_TURN");
        return { type: action.turn, board: action.board };

      case "PLAY":
        console.log("PLAY");
        const board = {
          ...state.board,
          stones: [...state.board.stones, action.newStone],
        }
        console.log("While play", board)
        return {
          type: "OPPONENT_TURN",
          board,
        };

      case "FINISHED":
        console.log("FINISHED");
        return { type: "FINISHED", board: action.board };

      case "MATCH_NOT_FOUND":
        return { type: "ERROR", error: "Match Not found" };
    }
  }

  const [state, dispatch] = useReducer(reducer, {
    type: "LOADING",
    board: undefined,
  });

  const deleteMatch = async () => {
    const deleteMatchAction = matchData.getDeleteMatchAction(sirenMatch);
    await fetchAPI(deleteMatchAction.href, deleteMatchAction.method);
    navigate("/play", { replace: true });
  };

  const playMatch = async (rowNumber: number, columnSymbol: string) => {
    const playMatchUrl = matchData.getPlayMatchAction(sirenMatch);
    try {
      const newStone = {
        dot: {
          row: {
            number: rowNumber,
          },
          column: {
            symbol: columnSymbol,
          },
          player: blackUser.id == currentUser.id ? "BLACK" : "WHITE",
        },
      };

      await fetchAPI(playMatchUrl.href, playMatchUrl.method, newStone.dot);
      dispatch({ type: "PLAY", newStone });
      console.log("AFTER PLAYED", state.board);
    } catch (e) {
      switch (e.status) {
        case 400:
          alert("Invalid move");
          break;
        case 403:
          alert("Not your turn");
          break;
        case 409:
          alert("Invalid move");
          break;
      }
    }
  };

  const polling = async () => {
    const matchSiren = await getMatch();
    setSirenMatch(matchSiren);
    if (!matchSiren) return;
    const match = matchSiren.properties;

    if (state.type == "LOADING" && match.state == "ONGOING") {
      const blackPlayer: UserInfo = (
        await fetchAPI<UserInfo>(matchData.getBlackPlayerHref(matchSiren))
      ).properties;
      const whitePlayer: UserInfo = (
        await fetchAPI<UserInfo>(matchData.getWhitePlayerHref(matchSiren))
      ).properties;
      setBlackUser(blackPlayer);
      setWhiteUser(whitePlayer);
      dispatch({ type: "OPPONENT_JOINED", board: match.board });
    }

    if (state.type == "OPPONENT_TURN" && getTurn() == "MY_TURN") {
      dispatch({ type: "CHANGE_TURN", board: match.board });
    }
  };

  const getMatch: () => Promise<SirenEntity<Match>> = async () => {
    try {
      return await fetchAPI(getMatchUrl);
    } catch (e) {
      switch (e.status) {
        case 404:
          dispatch({ type: "MATCH_NOT_FOUND", error: "Match Not found" });
      }
    }
  };

  useEffect(() => {
    const tid = setInterval(polling, 3000);
    return () => clearInterval(tid);
  }, [state]);

  if (state.type == "ERROR") {
    return <p>Error</p>;
  }

  if (state.type == "LOADING") {
    return (
      <RequireAuthn>
        <Loading message="Waiting for opponent" />
        <ScaledButton onClick={deleteMatch} color="red" text="Cancel" />
      </RequireAuthn>
    );
  }

  return (
    <div className="flex flex-col w-full gap-y-8">
      <div className="flex justify-center gap-24 items-center">
        <PlayerCard
          user={blackUser.id == currentUser.id ? blackUser : whiteUser}
        />
        <p className="text-3xl text-center">VS</p>
        <PlayerCard
          user={blackUser.id == currentUser.id ? whiteUser : blackUser}
          reverseOrder={true}
        />
      </div>
      <Board board={state.board} onPlay={playMatch} />
    </div>
  );
}
