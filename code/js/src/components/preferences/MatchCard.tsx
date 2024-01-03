import { useSound } from "../../hooks/Sound/Sound";

export function MatchCard({ text, onClick }) {
  const [sounds, playSound] = useSound();

  return (
    <button
      onMouseEnter={() => {
        playSound(sounds.ui_highlight);
      }}
      className="bg-blue-500 hover:bg-blue-400 hover:scale-105 transition-all text-white font-bold py-2 px-4 border-b-4 border-blue-700 hover:border-blue-500 rounded h-40 w-56"
      onClick={() => {
        playSound(sounds.ui_click_3);
        onClick();
      }}
    >
      {text}
    </button>
  );
}
