import { useCallback, useEffect, useReducer, useState } from "react";
import { homeData } from "../..";
import { BoardType, Dot, Player, Stone } from "../../types/board";
import { ForfeitOutputModel, Match, PlayOutputModel } from "../../types/match";
import { SirenEntity } from "../../types/siren";
import { fetchAPI, requestBuilder } from "../../utils/http";
import { useSession } from "../Auth/AuthnStatus";
import { UserInfo } from "../../types/user";
import matchData from "../../data/matchData";
import { useNavigate } from "react-router-dom";
import { useSound } from "../Sound/Sound";
import confetti from "canvas-confetti";

type State =
  | { type: "LOADING"; board: BoardType }
  | { type: "MY_TURN"; board: BoardType }
  | { type: "OPPONENT_TURN"; board: BoardType }
  | { type: "FINISHED"; board: BoardType };

type Action =
  | { type: "OPPONENT_JOINED"; isMyTurn: boolean; board: BoardType }
  | { type: "CHANGE_TURN"; board: BoardType }
  | { type: "PLAY"; matchState: string; board: BoardType }
  | { type: "FINISHED"; board: BoardType };

type MatchError = { status: number; message: string };

export default function useMatch(
  id: string
): [
  State,
  MatchError,
  Match | undefined,
  UserInfo | undefined,
  UserInfo | undefined,
  Stone | undefined,
  Player | undefined,
  () => Promise<void>,
  () => Promise<void>,
  (dot: Dot) => Promise<void>
] {
  const [sounds, playSound, playRandomSound] = useSound();
  const navigate = useNavigate();
  const [currentUser] = useSession();

  const reducer = useCallback((state: State, action: Action): State => {
    switch (action.type) {
      case "OPPONENT_JOINED":
        console.log("OPPONENT_JOINED");
        playSound(sounds.opponent_found);
        return {
          type: action.isMyTurn ? "MY_TURN" : "OPPONENT_TURN",
          board: action.board,
        };

      case "CHANGE_TURN":
        console.log("CHANGE_TURN");
        playRandomSound([sounds.place_piece_1, sounds.place_piece_2]);
        return { type: "MY_TURN", board: action.board };

      case "PLAY":
        console.log("PLAY");
        switch (action.matchState) {
          case "FINISHED":
            playSound(sounds.place_piece_winner);
            if (action.board.turn == myColor) confetti();
            return {
              type: "FINISHED",
              board: action.board,
            };
          default:
            playRandomSound([sounds.place_piece_1, sounds.place_piece_2]);
            return {
              type: "OPPONENT_TURN",
              board: action.board,
            };
        }

      case "FINISHED":
        console.log("FINISHED");
        return { type: "FINISHED", board: action.board };
    }
  }, []);

  const [sirenMatch, setSirenMatch] = useState<SirenEntity<Match>>();
  const [blackUser, setBlackUser] = useState<UserInfo>();
  const [whiteUser, setWhiteUser] = useState<UserInfo>();
  const [myColor, setMyColor] = useState<Player>();
  const [pendingStone, setPendingStone] = useState<Stone>();
  const [error, setError] = useState<MatchError>();

  const [state, dispatch] = useReducer(reducer, {
    type: "LOADING",
    board: undefined,
  });

  useEffect(() => {
    polling(); // load match on mount
  }, []);

  useEffect(() => {
    if (state.type == "FINISHED" || error) return;
    const tid = setInterval(polling, 2000);
    return () => clearInterval(tid);
  }, [state, error]);

  const cancelMatch = useCallback(async () => {
    try {
      const deleteMatchAction = matchData.getDeleteMatchAction(sirenMatch);
      await fetchAPI(deleteMatchAction.href, deleteMatchAction.method);
      navigate("/play", { replace: true });
    } catch (p) {
      setError({ status: p.status, message: p.detail });
    }
  }, [sirenMatch]);

  const playMatch = useCallback(
    async (dot: Dot) => {
      if (state.type != "MY_TURN") return;
      const playMatchUrl = matchData.getPlayMatchAction(sirenMatch);
      try {
        const newStone = new Stone(myColor, dot);
        setPendingStone(newStone);

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
            turn: playSiren.properties.turn,
            stones: [...state.board.stones, newStone],
          },
        });
      } catch (p) {
        setError({ status: p.status, message: p.detail });
      } finally {
        setPendingStone(undefined);
      }
    },
    [state, myColor, sirenMatch]
  );

  const forfeitMatch = useCallback(async () => {
    const forfeitMatchUrl = matchData.getForfeitMatchAction(sirenMatch);
    try {
      const forfeitSiren = await fetchAPI<ForfeitOutputModel>(
        forfeitMatchUrl.href,
        forfeitMatchUrl.method
      );
      dispatch({
        type: "FINISHED",
        board: {
          ...sirenMatch.properties.board,
          turn: forfeitSiren.properties.winner,
        },
      });
    } catch (p) {
      setError({ status: p.status, message: p.detail });
    }
  }, [sirenMatch]);

  const polling = useCallback(async () => {
    try {
      const matchSiren = await fetchAPI<Match>(
        requestBuilder(homeData.matchById().href, [id])
      );
      console.log("state", state);
      setSirenMatch(() => matchSiren);
      const match = matchSiren.properties;

      // load match
      if (state.type == "LOADING" && match.state != "SETUP") {
        const blackPlayer: UserInfo = (
          await fetchAPI<UserInfo>(matchData.getBlackPlayerHref(matchSiren))
        ).properties;
        const whitePlayer: UserInfo = (
          await fetchAPI<UserInfo>(matchData.getWhitePlayerHref(matchSiren))
        ).properties;
        setBlackUser(blackPlayer);
        setWhiteUser(whitePlayer);
        const localColor = blackPlayer.id == currentUser.id ? "BLACK" : "WHITE";
        setMyColor(localColor);
        if (match.state == "ONGOING") {
          dispatch({
            type: "OPPONENT_JOINED",
            isMyTurn: match.board.turn == localColor,
            board: match.board,
          });
        } else {
          dispatch({
            type: "FINISHED",
            board: match.board,
          });
        }
      }

      // already in match
      if (state.type == "OPPONENT_TURN" && match.board.turn == myColor) {
        dispatch({ type: "CHANGE_TURN", board: match.board });
      }

      if (match.state == "FINISHED") {
        dispatch({
          type: "FINISHED",
          board: match.board,
        });
      }
    } catch (p) {
      setError({ status: p.status, message: p.detail });
    }
  }, [sirenMatch, myColor, state]);

  return [
    state,
    error,
    sirenMatch?.properties,
    blackUser,
    whiteUser,
    pendingStone,
    myColor,
    cancelMatch,
    forfeitMatch,
    playMatch,
  ];
}
