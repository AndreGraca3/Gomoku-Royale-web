export function PlayerCard({user}) {
    const red = "border-red-800"
    const green = "border-green-800"

    return (
        <div className="border-4 border-green-800 bg-yellow-900 rounded grid grid-cols-2 w-40 h-20 ">
            <img className="w-full h-full rounded-full" src={user.avatarUrl}/>
            <div className="w-full h-full content-center">
                <p className="text-center">{user.name}</p>
                <img src="silver.png"/>
            </div>
        </div>
    )
}