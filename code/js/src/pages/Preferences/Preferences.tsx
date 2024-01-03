import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { MatchCard } from "../../components/preferences/MatchCard";
import { SizeSelector } from "../../components/preferences/SizeSelector";
import matchData from "../../data/matchData";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";
import toast from "react-hot-toast";
import InputField from "../../components/InputField";
import { Icons } from "../../components/Icons";

export function Preferences() {
  const navigate = useNavigate();

  const [selectedSize, setSelectedSize] = useState(15);

  const [privateMatchId, setPrivateMatchId] = useState("");

  async function createMatch(isPrivate: boolean) {
    try {
      const sirenMatch = await matchData.createMatch({
        isPrivate: isPrivate,
        size: selectedSize,
        variant: "FreeStyle",
      });
      const matchId = sirenMatch.properties.id;
      navigate("/match/" + matchId);
    } catch (e) {
      toast.error(e.detail);
      if (e.status == 409) {
        setTimeout(() => {
          toast.error("Match already exists, redirecting...");
          navigate("/match/" + e.data.matchId);
        }, 1000);
      }
    }
  }

  async function handleJoinMatch(ev: React.FormEvent<HTMLFormElement>) {
    ev.preventDefault();
    try {
      if(privateMatchId.length < 1) throw new Error("Please enter a match ID");
      const sirenMatch = await matchData.joinMatch(privateMatchId);
      const matchId = sirenMatch.properties.id;
      navigate(`/match/${matchId}`);
    } catch (e) {
      console.log(e);
      toast.error(e.detail || e.message);
    }
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
            <div className="flex flex-col gap-2">
              <MatchCard
                text="👥 Private Match 🔐"
                onClick={() => createMatch(true)}
              />
              <form className="flex gap-1" onSubmit={handleJoinMatch}>
                <InputField
                  name="matchId"
                  placeholder="Private Match ID"
                  value={privateMatchId}
                  handleChange={(e) => {
                    setPrivateMatchId(e.currentTarget.value);
                  }}
                />
                <button
                  type="submit"
                  className="h-full w-full border rounded-xl p-2 bg-dark-green hover:bg-green-600 hover:scale-105 transition-all text-xs rotate-180"
                >
                  <Icons.arrowLeft />
                </button>
              </form>
            </div>
          </div>
        </div>
      </div>
    </RequireAuthn>
  );
}
