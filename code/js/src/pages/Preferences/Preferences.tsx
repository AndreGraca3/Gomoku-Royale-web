import { useState } from "react";
import { Navigate } from "react-router-dom";
import { MatchCard } from "../../components/preferences/MatchCard";
import { SizeSelector } from "../../components/preferences/SizeSelector";
import matchData from "../../data/matchData";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";
import toast from "react-hot-toast";

export function Preferences() {
  const [redirect, setRedirect] = useState(undefined);

  const [selectedSize, setSelectedSize] = useState(15);

  async function createMatch(isPrivate: boolean) {
    try {
      const sirenMatch = await matchData.createMatch({
        isPrivate: isPrivate,
        size: selectedSize,
        variant: "FreeStyle",
      });
      const matchId = sirenMatch.properties.id;
      setRedirect("/match/" + matchId);
    } catch (e) {
      toast.error(e.detail);
      if (e.status == 409) {
        setTimeout(() => {
          toast.error("Match already exists, redirecting...");
          setRedirect("/match/" + e.data.matchId);
        }, 1000);
      }
    }
  }

  if (redirect) {
    return <Navigate to={redirect} replace={true} />;
  }

  return (
    <RequireAuthn>
      <div>
        <div className="flex justify-center items-center">
          <div className="grid grid-rows-2">
            <SizeSelector
              sizeSelected={selectedSize}
              onClick={setSelectedSize}
            />
          </div>
        </div>
        <div className="flex justify-center items-center">
          <div className="grid grid-cols-2 gap-x-4">
            <MatchCard
              text="⚫ Public Match ⚪"
              onClick={() => createMatch(false)}
            />
            <MatchCard
              text="👥 Private Match 🔐"
              onClick={() => createMatch(true)}
            />
          </div>
        </div>
      </div>
    </RequireAuthn>
  );
}
