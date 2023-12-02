export function UserStats({userStats}) {
    return (
        <div>
            {propertyToHtml(userStats)}
        </div>
    )
}

function propertyToHtml(object: Object) {
    return Object.keys(object).map(
        it => {
            if (typeof object[it] == "object") return (
                <div>
                    <h1 className="underline">{it}</h1>
                    {propertyToHtml(object[it])}
                </div>
            )
            else
                return <p className="indent-8">{it + ": " + object[it]}</p>
        }
    )
}

export type UserStats = {
    winStats: WinStats,
    matchesStats: MatchesStats
}

export type WinStats = {
    totalWins: number,
    winsAsBlack: number,
    winsAsWhite: number,
    winRate: number,
    draws: number,
    loses: number
}

export type MatchesStats = {
    totalMatches: number,
    matchesAsBlack: number,
    matchesAsWhite: number
}