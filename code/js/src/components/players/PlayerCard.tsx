export function PlayerCard({ user, reverseOrder = false, isActive }) {
  const cardClasses = `border-4 items-center overflow-hidden ${
    isActive ? "border-gr-yellow animate-shine scale-110" : "opacity-50"
  } bg-gradient-to-r from-indigo-600 to-purple-800 rounded-xl w-52 h-24 flex transition-all duration-200 ${
    reverseOrder ? "flex-row-reverse" : ""
  }`;

  return (
    <div className={cardClasses}>
      <div className="flex items-center justify-center h-full p-4">
        <div className="flex flex-col items-center">
          <p className="text-sm font-semibold">{user.name}</p>
          <div className="flex items-center justify-center w-10 rounded-full">
            <img
              className="w-full"
              src={user.rank.iconUrl}
              alt={`${user.name}'s rank icon`}
            />
          </div>
        </div>
      </div>
      <div className="flex items-center justify-center w-16 h-16 overflow-hidden">
        <img
          className="w-full object-cover rounded-full"
          src={user.avatarUrl}
          alt={`${user.name}'s avatar`}
        />
      </div>
    </div>
  );
}
