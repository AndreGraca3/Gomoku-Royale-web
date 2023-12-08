import {useState} from "react";
import {Navigate, useNavigate} from "react-router-dom";
import {MatchCard} from "../../components/preferences/MatchCard";
import {SizeSelector} from "../../components/preferences/SizeSelector";
import matchData from "../../data/matchData";

export function Preferences() {
    const [redirect, setRedirect] = useState(undefined)

    const [selectedSize, setSelectedSize] = useState(15)

    async function createMatch(isPrivate: boolean) {
        const sirenMatch = await matchData.createMatch({
            isPrivate: isPrivate,
            size: selectedSize,
            variant: "FreeStyle"
        })
        const matchId = sirenMatch.properties.id
        setRedirect("/match/" + matchId)
    }

    if (redirect) {
        return (
            <Navigate to={redirect} replace={true}/>
        )
    }

    return (
        <div>
            <div className="flex justify-center items-center">
                <div className="grid grid-rows-2">
                    <SizeSelector sizeSelected={selectedSize} onClick={setSelectedSize}/>
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
    )
}

