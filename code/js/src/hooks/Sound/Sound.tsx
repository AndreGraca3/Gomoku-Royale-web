const sounds = {
  place_piece_1: new Audio("audio/place_piece_1.mp3"),
  place_piece_2: new Audio("audio/place_piece_2.mp3"),
  place_piece_winner: new Audio("audio/place_piece_winner.mp3"),
  opponent_found: new Audio("audio/opponent_found.mp3"),
  ui_click_1: new Audio("audio/ui_click_1.mp3"),
  ui_click_2: new Audio("audio/ui_click_2.mp3"),
  ui_click_3: new Audio("audio/ui_click_3.mp3"),
  ui_click_4: new Audio("audio/ui_click_4.mp3"),
  ui_highlight: new Audio("audio/ui_highlight.mp3"),
};

type Sounds = typeof sounds;

export function useSound(): [
  sounds: Sounds,
  playSound: (s: HTMLAudioElement) => void,
  playRandomSound: (s: HTMLAudioElement[]) => void
] {
  const playSound = (s: HTMLAudioElement) => {
    s.play().catch(() => {});
  };
  const playRandomSound = (s: HTMLAudioElement[]) => {
    const randomSound = s[Math.floor(Math.random() * s.length)];
    playSound(randomSound);
  };
  return [sounds, playSound, playRandomSound];
}
