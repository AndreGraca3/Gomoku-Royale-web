import { PlayerCard } from "../../components/players/PlayerCard";
import Board from "../../components/board/Board";
import { useEffect, useReducer, useState } from "react";
import { useParams } from "react-router-dom";
import { homeLinks } from "../../index";
import { fetchAPI, requestBuilder } from "../../utils/http";
import { SirenEntity } from "../../types/siren";
import { Match } from "../../types/match";
import userData from "../../data/userData";
import { UserInfo } from "../../types/user";
import { Loading } from "../../components/Loading";
import matchData from "../../data/matchData";
import ScaledButton from "../../components/ScaledButton";
import { useNavigate } from "react-router-dom";
import { BoardType } from "../../types/board";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";

type State =
  | { type: "LOADING"; board: BoardType }
  | { type: "MY_TURN"; board: BoardType }
  | { type: "OPPONENT_TURN"; board: BoardType }
  | { type: "FINISHED"; board: BoardType }
  | { type: "ERROR"; error: string };

export function Match() {
  const navigate = useNavigate();
  const { id } = useParams();
  const getMatchUrl = requestBuilder(homeLinks.matchById().href, [id]);

  const [sirenMatch, setSirenMatch] = useState(undefined);

  const [currentUser, setCurrentUser] = useState(undefined);
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
        console.log("actual state", state);
        return { type: getTurn(), board: action.board };

      case "PLAY":
        if (state.type != "MY_TURN") {
          alert("Not your turn");
          return state;
        }

        const playMatchUrl = matchData.getPlayMatchAction(sirenMatch); // TODO: make the play request

        return {
          type: state.type == "MY_TURN" ? "OPPONENT_TURN" : "MY_TURN",
          board: {
            ...state.board,
            stones: [...action.board.stones, action.newStone],
          },
        };

      case "FINISHED":
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
    dispatch({
      type: "PLAY",
      newStone: {
        row: {
          number: rowNumber,
        },
        column: {
          symbol: columnSymbol,
        },
      },
    });
  };

  const polling = async () => {
    const matchSiren = await getMatch();
    setSirenMatch(matchSiren);
    if (!matchSiren) return;
    const match = matchSiren.properties;

    console.log("before if:", state);
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
    userData.getAuthenticatedUser().then((authUserSiren) => {
      setCurrentUser(authUserSiren.properties);
    });

    const tid = setInterval(() => {
      polling();
    }, 3000);
    return () => clearInterval(tid);
  }, []);

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
