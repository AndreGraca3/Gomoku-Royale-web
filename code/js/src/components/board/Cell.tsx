import React, { useCallback, useState } from "react";
import { Stone } from "../../types/board";

export default function Cell({
  cellStone,
  canPlay,
  pendingStone,
  onClick,
}: {
  cellStone: Stone | null;
  canPlay: boolean;
  pendingStone?: Stone;
  onClick: () => void;
}) {
  const [isHovered, setIsHovered] = useState(false);

  const getPiecePng = useCallback((stone: Stone): string => {
    return stone?.player == "BLACK" ? "/black_piece.png" : "/white_piece.png";
  }, []);

  return (
    <button
      className="aspect-square bg-dark-green resize w-7 h-7 relative"
      onClick={onClick}
      disabled={!canPlay}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div className="relative">
        <img className="relative" src="/cross.png" alt="Cross" />
        {pendingStone && (
          <img
            className="absolute top-0 left-0 opacity-30 animate-scale-in"
            src={getPiecePng(pendingStone)}
          />
        )}
        {cellStone && (
          <img
            className="absolute top-0 left-0"
            src={getPiecePng(cellStone)}
          />
        )}
        {canPlay && isHovered && !cellStone && (
          <img
            className="absolute top-0 left-0 z-50 animate-pulse-scale"
            src="/selector.png"
            alt="Selector"
          />
        )}
      </div>
    </button>
  );
}
