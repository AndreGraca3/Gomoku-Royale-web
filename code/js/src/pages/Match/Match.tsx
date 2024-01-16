import Board from "../../components/board/Board";
import { useParams } from "react-router-dom";
import { Loading } from "../../components/Loading";
import ScaledButton from "../../components/ScaledButton";
import { useNavigate } from "react-router-dom";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";
import { useSession } from "../../hooks/Auth/AuthnStatus";
import toast from "react-hot-toast";
import { useSound } from "../../hooks/Sound/Sound";
import useMatch from "../../hooks/Match/MatchStatus";
import { useEffect } from "react";
import PlayersRow from "./PlayersRow";

export function Match() {
  const { id } = useParams();
  const navigate = useNavigate();
  const [currentUser] = useSession();
  const [sounds, playSound, playRandomSound] = useSound();
  const [
    state,
    error,
    match,
    blackUser,
    whiteUser,
    pendingStone,
    myColor,
    cancelMatch,
    forfeitMatch,
    playMatch,
  ] = useMatch(id);

  useEffect(() => {
    if (!error) return;
    switch (error?.status) {
      case 401:
        navigate("/login");
      case 409:
        navigate("/play");
      default:
        toast.error(error?.message);
    }
  }, [error]);

  if (error?.status == 404) {
    return (
      <div className="flex flex-col items-center space-y-2">
        <span className="animate-pop-up text-8xl">⚠️</span>
        <p className="text-red-600 text-xl">{error.message}</p>
      </div>
    );
  }

  if (state.type == "LOADING") {
    return (
      <RequireAuthn>
        <div className="flex flex-col space-y-2 items-center">
          <Loading message={"Waiting for oponent..."} />
          {match?.state == "SETUP" && (
            <span className="items-center animate-pop-up flex flex-col">
              {match.isPrivate && (
                <p
                  onClick={() => {
                    const clipboard = navigator.clipboard;
                    if (!clipboard) {
                      toast.error("Failed copying to clipboard");
                      return;
                    }
                    clipboard.writeText(match.id);
                    toast("Copied to clipboard");
                  }}
                  className="text-gr-yellow text-xl cursor-pointer opacity-50 hover:opacity-100"
                >{`Match id: ${match.id}`}</p>
              )}
              <ScaledButton onClick={cancelMatch} color="red" text="Cancel" />
            </span>
          )}
        </div>
      </RequireAuthn>
    );
  }

  return (
    <div className="flex flex-col w-full gap-y-8">
      <PlayersRow
        localUser={blackUser.id == currentUser.id ? blackUser : whiteUser}
        opponentUser={blackUser.id == currentUser.id ? whiteUser : blackUser}
        isMyTurn={state.board.turn == myColor}
        isFinished={state.type == "FINISHED"}
        forfeitMatch={forfeitMatch}
      />
      <Board
        board={state.board}
        pendingStone={pendingStone}
        canPlay={state.type == "MY_TURN" && !pendingStone}
        onPlay={playMatch}
      />
    </div>
  );
}
