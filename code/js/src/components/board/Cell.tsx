import React, { useState } from "react";

export default function Cell({ stone, onClick }) {
  const [isHovered, setIsHovered] = useState(false);

  function getPiecePng() {
    if (!stone) return null;
    switch (stone.player) {
      case "BLACK":
        return <img className="absolute top-0 left-0" src="/black_piece.png" />;
      case "WHITE":
        return <img className="absolute top-0 left-0" src="/white_piece.png" />;
      default:
        return null;
    }
  }

  return (
    <button
      className="aspect-square bg-dark-green resize w-7 h-7 relative"
      onClick={onClick}
      onMouseEnter={() => setIsHovered(true)}
      onMouseLeave={() => setIsHovered(false)}
    >
      <div className="relative">
        <img className="relative" src="/cross.png" alt="Cross" />
        {getPiecePng()}
        {isHovered && !stone && (
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
