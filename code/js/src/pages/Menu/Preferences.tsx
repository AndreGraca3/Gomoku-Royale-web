import {useState} from "react";
import {Navigate} from "react-router-dom";
import {MatchCard} from "../../components/preferences/MatchCard";
import {SizeSelector} from "../../components/preferences/SizeSelector";
import {homeLinks} from "../../index";
import matchData from "../../data/matchData";

export function Preferences() {
    const [redirect, setRedirect] = useState(undefined)

    const [selectedSize, setSelectedSize] = useState(15)

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
                        onClick={async () => {
                            await matchData.createMatch({
                                isPrivate: false,
                                size: selectedSize,
                                variant: "FreeStyle"
                            })
                            setRedirect("/public")
                        }}
                    />
                    <MatchCard
                        text="👥 Private Match 🔐"
                        onClick={async () => {
                            await matchData.createMatch({
                                isPrivate: true,
                                size: selectedSize,
                                variant: "FreeStyle"
                            })
                            setRedirect("/private")
                        }}
                    />
                </div>
            </div>
        </div>
    )
}

