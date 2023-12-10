import { Link } from "react-router-dom";

const topColors = ["text-gr-yellow", "text-zinc-500", "text-amber-700"];

export default function LeaderBoardItem({ userItem, index }) {
  return (
    <Link
      to={`/user/${userItem.id}`}
      className={`flex group items-center bg-dark-theme-color shadow-white shadow-inner border border-white hover:border-transparent hover:scale-105 transition-all duration-200 p-4 rounded-lg`}
    >
      <div className="truncate w-6">
        <span>{index + 1}</span>
      </div>
      <div
        className={`w-48 truncate group-hover:text-red-600 transition-all duration-200 ${topColors[index]}`}
      >
        <span>{userItem.name}</span>
      </div>
      <div className="truncate w-24">
        <span>{userItem.rank.name}</span>
      </div>
      {userItem.rank.iconUrl && (
        <img
          src={userItem.rank.iconUrl}
          alt={`${userItem.rank.name} Icon`}
          className="w-6 h-6 ml-2 right-0"
        />
      )}
    </Link>
  );
}
