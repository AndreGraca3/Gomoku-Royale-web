import React, { createContext, useContext, useEffect, useState } from "react";

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

type SoundContextType = {
  sounds: Sounds;
  playSound: (s: HTMLAudioElement) => void;
  playRandomSound: (s: HTMLAudioElement[]) => void;
};

const SoundContext = createContext<SoundContextType>({
  sounds: undefined,
  playSound: () => {},
  playRandomSound: () => {},
});

export function SoundProvider({ children }: { children: React.ReactNode }) {
  const [canPlay, setCanPlay] = useState(false);

  useEffect(() => {
    const setPlayable = () => {
      setCanPlay(true);
    };
    document.body.addEventListener("click", setPlayable);

    return () => {
      document.body.removeEventListener("click", setPlayable);
    };
  }, []);

  const playSound = (audio: HTMLAudioElement) => {
    if (canPlay) {
      audio.play().catch((e) => console.log(e));
    }
  };

  const playRandomSound = (soundPaths: HTMLAudioElement[]) => {
    if (canPlay) {
      const audio = soundPaths[Math.floor(Math.random() * soundPaths.length)];
      audio.play();
    }
  };

  return (
    <SoundContext.Provider value={{ sounds, playSound, playRandomSound }}>
      {children}
    </SoundContext.Provider>
  );
}

export function useSound(): [
  sounds: Sounds,
  playSound: (s: HTMLAudioElement) => void,
  playRandomSound: (s: HTMLAudioElement[]) => void
] {
  const { sounds, playSound, playRandomSound } = useContext(SoundContext);
  return [sounds, playSound, playRandomSound];
}
