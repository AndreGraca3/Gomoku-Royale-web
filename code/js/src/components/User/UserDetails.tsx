export function UserDetailsView(props) {
    const user = props.user

    return (
        <div className="flex justify-center items-center">
            <div className="grid grid-cols-1 gap-y-4">
                <button className="w-40 h-40">
                    <img className="w-full h-full rounded-full" src="profile_pic.png"/>
                </button>
                <h1 className="text-center">{user.name}</h1>
                <h1 className="text-center">{user.email}</h1>
            </div>
        </div>
    )
}