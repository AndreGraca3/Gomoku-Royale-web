import { useEffect, useState } from "react";

export function Loading({
  message,
  delayTime = 500,
}: {
  message?: string;
  delayTime?: number;
}) {
  const initial = 1;
  const final = 4;
  const [count, setCount] = useState(initial);

  useEffect(() => {
    const tid = setInterval(() => {
      if (count < final) {
        setCount((oldPieces) => oldPieces + 1);
      } else {
        setCount(initial);
      }
    }, delayTime);
    return () => clearInterval(tid);
  }, [count]);

  return (
    <div className="flex flex-col items-center h-fit">
      <div className={`grid grid-cols-4`}>
        {Array.from({ length: count }).map((_, idx) => {
          return (
            <img
              key={idx}
              src={"/black_piece.png"}
              className="animate-pop-up"
            />
          );
        })}
      </div>
      {message && <span className="text-center animate-pulse">{message}</span>}
    </div>
  );
}
