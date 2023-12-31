import { PlayerCard } from "./PlayerCard";
import Board from "../../components/board/Board";
import { useCallback, useEffect, useReducer, useState } from "react";
import { useParams } from "react-router-dom";
import { homeLinks } from "../../index";
import { fetchAPI, requestBuilder } from "../../utils/http";
import { SirenEntity } from "../../types/siren";
import { Match, PlayOutputModel } from "../../types/match";
import { UserInfo } from "../../types/user";
import { Loading } from "../../components/Loading";
import matchData from "../../data/matchData";
import ScaledButton from "../../components/ScaledButton";
import { useNavigate } from "react-router-dom";
import { BoardType, Player, Stone } from "../../types/board";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";
import { useSession } from "../../hooks/Auth/AuthnStatus";
import toast from "react-hot-toast";
import confetti from "canvas-confetti";

const placeSounds = [
  new Audio("audio/place_piece_1.mp3"),
  new Audio("audio/place_piece_2.mp3"),
];

function playPlaceSound(isWinningSound = false) {
  if (isWinningSound) {
    const winSound = new Audio("audio/place_piece_winner.mp3");
    winSound.play();
    return;
  }
  const idx = Math.floor(Math.random() * placeSounds.length);
  placeSounds[idx].play();
}

type State =
  | { type: "LOADING"; board: BoardType }
  | { type: "MY_TURN"; board: BoardType }
  | { type: "OPPONENT_TURN"; board: BoardType }
  | { type: "FINISHED"; winner: Player; board: BoardType }
  | { type: "ERROR"; board?: undefined; error: string };

type Action =
  | { type: "OPPONENT_JOINED"; board: BoardType }
  | { type: "CHANGE_TURN"; board: BoardType }
  | { type: "PLAY"; matchState: string; board: BoardType }
  | { type: "FINISHED"; winner: Player; board: BoardType }
  | { type: "ERROR"; error: string };

export function Match() {
  const navigate = useNavigate();
  const { id } = useParams();
  const [currentUser] = useSession();
  const getMatchUrl = requestBuilder(homeLinks.matchById().href, [id]);

  const [sirenMatch, setSirenMatch] = useState(undefined);

  const [blackUser, setBlackUser] = useState(undefined);
  const [whiteUser, setWhiteUser] = useState(undefined);
  const [myColor, setMyColor] = useState(undefined);

  function reducer(state: State, action: Action): State {
    switch (action.type) {
      case "OPPONENT_JOINED":
        console.log("OPPONENT_JOINED");
        return {
          type: action.board.turn == myColor ? "MY_TURN" : "OPPONENT_TURN",
          board: action.board,
        };

      case "CHANGE_TURN":
        console.log("CHANGE_TURN");
        playPlaceSound();
        return { type: "MY_TURN", board: action.board };

      case "PLAY":
        console.log("PLAY");
        switch (action.matchState) {
          case "FINISHED":
            playPlaceSound(true);
            toast("You won!");
            confetti();
            return {
              type: "FINISHED",
              winner: action.board.turn,
              board: action.board,
            };
          default:
            playPlaceSound();
            return {
              type: "OPPONENT_TURN",
              board: action.board,
            };
        }

      case "FINISHED":
        console.log("FINISHED");
        return { type: "FINISHED", winner: action.winner, board: action.board };

      case "ERROR":
        return { type: "ERROR", error: action.error };
    }
  }

  const [state, dispatch] = useReducer(reducer, {
    type: "LOADING",
    board: undefined,
  });

  const deleteMatch = useCallback(async () => {
    const deleteMatchAction = matchData.getDeleteMatchAction(sirenMatch);
    await fetchAPI(deleteMatchAction.href, deleteMatchAction.method);
    navigate("/play", { replace: true });
  }, [sirenMatch]);

  const playMatch = async (rowNumber: number, columnSymbol: string) => {
    if (state.type != "MY_TURN") return;
    const playMatchUrl = matchData.getPlayMatchAction(sirenMatch);
    try {
      const newStone: Stone = {
        player: blackUser.id == currentUser.id ? "BLACK" : "WHITE",
        dot: {
          row: {
            number: rowNumber,
          },
          column: {
            symbol: columnSymbol,
          },
        },
      };

      const playSiren = await fetchAPI<PlayOutputModel>(
        playMatchUrl.href,
        playMatchUrl.method,
        newStone.dot
      );

      dispatch({
        type: "PLAY",
        matchState: playSiren.properties.matchState,
        board: {
          ...state.board,
          stones: [...state.board.stones, newStone],
        },
      });
    } catch (e) {
      switch (e.status) {
        case 400:
        case 403:
        case 409:
          toast.error(e.detail);
          break;
      }
    }
  };

  async function polling() {
    const matchSiren = await getMatch();
    if (!matchSiren) return;
    console.log("state", state);
    setSirenMatch(() => matchSiren);
    const match = matchSiren.properties;

    if (state.type == "LOADING" && match.state != "SETUP") {
      const blackPlayer: UserInfo = (
        await fetchAPI<UserInfo>(matchData.getBlackPlayerHref(matchSiren))
      ).properties;
      const whitePlayer: UserInfo = (
        await fetchAPI<UserInfo>(matchData.getWhitePlayerHref(matchSiren))
      ).properties;
      setBlackUser(blackPlayer);
      setWhiteUser(whitePlayer);
      setMyColor(() => (blackPlayer.id == currentUser.id ? "BLACK" : "WHITE"));
      if (match.state == "ONGOING") {
        dispatch({ type: "OPPONENT_JOINED", board: match.board });
      } else {
        dispatch({
          type: "FINISHED",
          winner: match.board.turn,
          board: match.board,
        });
      }
    }

    // if already in game

    if (state.type == "OPPONENT_TURN" && match.board.turn == myColor) {
      dispatch({ type: "CHANGE_TURN", board: match.board });
    }

    if (state.type == "OPPONENT_TURN" && match.state == "FINISHED") {
      dispatch({
        type: "FINISHED",
        winner: match.board.turn,
        board: match.board,
      });
    }
  }

  const getMatch: () => Promise<SirenEntity<Match>> = async () => {
    try {
      return await fetchAPI(getMatchUrl);
    } catch (e) {
      switch (e.status) {
        case 401:
          navigate("/login");
          break;
        default:
          dispatch({ type: "ERROR", error: e.detail });
          break;
      }
    }
  };

  useEffect(() => {
    if (state.type == "FINISHED" || state.type == "ERROR") return;
    const tid = setInterval(polling, 2000);
    return () => clearInterval(tid);
  }, [state]);

  if (state.type == "ERROR") {
    return (
      <div className="flex flex-col items-center space-y-2">
        <span className="animate-pop-up text-8xl">⚠️</span>
        <p className="text-red-600 text-xl">{state.error}</p>
      </div>
    );
  }

  if (state.type == "LOADING") {
    return (
      <RequireAuthn>
        <div className="flex flex-col space-y-2 items-center">
          <Loading message={"Waiting for oponent..."} />
          {sirenMatch && (
            <span className="items-center animate-pop-up flex flex-col">
              {sirenMatch.properties.isPrivate && (
                <p
                  onClick={() => {
                    const clipboard = navigator.clipboard;
                    if (!clipboard) {
                      toast.error("Failed copying to clipboard");
                      return;
                    }
                    clipboard.writeText(sirenMatch.properties.id);
                    toast("Copied to clipboard");
                  }}
                  className="text-gr-yellow text-xl cursor-pointer opacity-50 hover:opacity-100"
                >{`Match id: ${sirenMatch.properties.id}`}</p>
              )}
              <ScaledButton onClick={deleteMatch} color="red" text="Cancel" />
            </span>
          )}
        </div>
      </RequireAuthn>
    );
  }

  return (
    <div className="flex flex-col w-full gap-y-8">
      <div className="flex justify-center gap-24 items-center p-4">
        <PlayerCard
          user={blackUser.id == currentUser.id ? blackUser : whiteUser}
          isActive={
            state.type == "MY_TURN" ||
            (state.type == "FINISHED" && myColor == state.winner)
          }
        />
        <p className="text-3xl text-center">VS</p>
        <PlayerCard
          user={blackUser.id == currentUser.id ? whiteUser : blackUser}
          reverseOrder={true}
          isActive={
            state.type == "OPPONENT_TURN" ||
            (state.type == "FINISHED" && myColor != state.winner)
          }
        />
      </div>
      <Board
        board={state.board}
        canPlay={state.type == "MY_TURN"}
        onPlay={playMatch}
      />
    </div>
  );
}
