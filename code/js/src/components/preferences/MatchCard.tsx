export function MatchCard({ text, onClick }) {
  return (
    <button
      className="bg-blue-500 hover:bg-blue-400 text-white font-bold py-2 px-4 border-b-4 border-blue-700 hover:border-blue-500 rounded h-40 w-56"
      onClick={onClick}
    >
      {text}
    </button>
  );
}
