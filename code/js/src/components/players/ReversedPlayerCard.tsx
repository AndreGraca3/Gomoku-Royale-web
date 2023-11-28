export function ReversedPlayerCard(props) {
    const user = props.user
    return (
        <div className="border-4 border-green-800 bg-yellow-900 rounded grid grid-cols-2 w-40 h-20 ">
            <div className="w-full h-full content-center">
                <p className="text-center">{user.name}</p>
                <img src="silver.png"/>
            </div>
            <img className="w-full h-full rounded-full" src="profile_pic.png"/>
        </div>
    )
}