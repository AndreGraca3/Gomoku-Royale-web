import ScaledButton from "../../components/ScaledButton";
import { PlayerCard } from "./PlayerCard";

export default function PlayersRow({localUser, opponentUser, isMyTurn, isFinished, forfeitMatch}) {
  return (
    <div className="flex justify-center gap-24 items-center p-4">
      <PlayerCard
        user={localUser}
        isActive={isMyTurn}
        isWinner={isFinished && isMyTurn}
      />
      <div className="flex-col gap-y-4">
        <p className="text-3xl text-center">VS</p>
        {!isFinished && (
          <ScaledButton text="FORFEIT" onClick={forfeitMatch} color="red" />
        )}
      </div>
      <PlayerCard
        user={opponentUser}
        reverseOrder={true}
        isActive={!isMyTurn}
        isWinner={isFinished && !isMyTurn}
      />
    </div>
  );
}
