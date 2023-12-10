export default function Cell({ stone, onClick }) {
  function getPiecePng() {
    if (!stone) return;
    switch (stone.player) {
      case "BLACK":
        return <img className="absolute top-0 left-0" src="/black_piece.png" />;
      case "WHITE":
        return <img className="absolute top-0 left-0" src="/white_piece.png" />;
    }
  }

  return (
    <button
      className="aspect-square bg-green-900 resize w-7 h-7"
      onClick={onClick}
    >
      <div className="relative hover:border-red-700 border-transparent hover:border-2">
        <img className="relative" src="/cross.png" />
        {getPiecePng()}
      </div>
    </button>
  );
}
