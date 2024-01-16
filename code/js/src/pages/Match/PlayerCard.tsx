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
  const cardClasses = `flex max-w-lg gap-2 overflow-hidden justify-evenly border-4 p-4 ${
    isActive ? "border-gr-yellow animate-shine scale-110" : "opacity-50"
  } bg-gradient-to-r from-indigo-600 to-purple-800 rounded-xl w-52 h-24 flex transition-all duration-200 ${
    reverseOrder ? "flex-row-reverse" : ""
  }`;

  return (
    <div className={cardClasses}>
      <div className="flex flex-col items-center justify-center truncate w-3/4 h-full">
        <span className="text-sm text-center font-semibold w-full truncate">
          {user.name}
        </span>
        <img className="h-1/2 object-cover" src={user.rank.iconUrl} />
      </div>
      <div className="avatar flex flex-col items-center justify-center h-full">
        <img
          className="rounded-full w-12 h-12 object-cover border"
          src={user.avatarUrl || "/user_icon.png"}
          alt={user.name}
        />
        {isWinner && (
          <p className="text-sm text-center font-semibold animate-heart-beat text-gr-yellow">
            Winner
          </p>
        )}
      </div>
    </div>
  );
}
