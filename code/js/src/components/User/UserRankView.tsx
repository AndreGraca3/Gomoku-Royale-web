export function UserRankView({rank}) {
    return (
        <div className="flex justify-center items-center">
            <div className="grid grid-rows-2">
                <img className="w-40 h-40" src={rank.iconUrl}/>
                <p className="text-center">{rank.name}</p>
            </div>
        </div>
    )
}