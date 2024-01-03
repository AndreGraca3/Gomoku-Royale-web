import { UserInfo } from "../../types/user";

export function PlayerCard({
  user,
  reverseOrder = false,
  isActive,
  isWinner,
}: {
  user: UserInfo;
  reverseOrder?: boolean;
  isActive: boolean;
  isWinner: boolean;
}) {
  const cardClasses = `flex border-4 max-w-lg items-center p-4 overflow-hidden ${
    isActive ? "border-gr-yellow animate-shine scale-110" : "opacity-50"
  } bg-gradient-to-r from-indigo-600 to-purple-800 rounded-xl w-52 h-24 flex transition-all duration-200 ${
    reverseOrder ? "flex-row-reverse" : ""
  }`;

  return (
    <div className={cardClasses}>
      <div className="flex flex-col items-center justify-center truncate w-2/3 h-full p-2">
        <p className="text-sm font-semibold h-1/2 truncate">{user.name}</p>
        <img className="justify-center h-1/2" src={user.rank.iconUrl} />
      </div>
      <div className="avatar flex-col items-center">
        <img
          className="rounded-full w-14 h-14 object-cover border"
          src={user.avatarUrl}
          alt={`${user.name}'s avatar`}
        />
        {isWinner && (
          <p className="text-sm text-center font-semibold animate-heart-beat text-gr-yellow">Winner</p>
        )}
      </div>
    </div>
  );
}
