export function UserStatsView({userStats}) {
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