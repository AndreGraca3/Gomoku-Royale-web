export function PlayerCard({ user, reverseOrder = false }) {
  const cardClasses = `animate-shine border-4 items-center overflow-hidden border-gr-yellow bg-gradient-to-r from-indigo-600 to-purple-800 rounded-xl w-52 h-24 flex ${
    reverseOrder ? "flex-row-reverse" : ""
  }`;

  return (
    <div className={cardClasses}>
      <div className="flex items-center justify-center h-full p-4">
        <div className="flex flex-col items-center">
          <p className="text-sm font-semibold">{user.name}</p>
          <div className="flex items-center justify-center w-10 mt-2 rounded-full">
            <img
              className="w-full"
              src={user.rank.iconUrl}
              alt={`${user.name}'s rank icon`}
            />
          </div>
        </div>
      </div>
      <div className="flex items-center justify-center w-16 h-full overflow-hidden">
        <img
          className="w-full object-cover rounded-full"
          src={user.avatarUrl}
          alt={`${user.name}'s avatar`}
        />
      </div>
    </div>
  );
}
