import React, { useState, useEffect } from "react";
import statsData from "../../data/statsData";

const Leaderboard = () => {
  const [leaderboard, setLeaderboard] = useState([]);
  const [selectedTop, setSelectedTop] = useState(10);

  const fetchLeaderboard = async () => {
    try {
      let data;
      
        // Fetch actual data from the API with the selected top limit
        data = await statsData.top(selectedTop);

      setLeaderboard(data.properties.ranks);

    } catch (error) {
      console.error("Error fetching leaderboard:", error);
    }
  };

  useEffect(() => {
    // Initial fetch
    fetchLeaderboard();
  }, [selectedTop]);

  const handleTopChange = (e) => {
    setSelectedTop(parseInt(e.target.value, 10));
  };

  return (
      <div className="overflow-y-auto h-full font-bold">
        <h1 className="text-3xl font-bold text-center mb-4">
          Top Players Leaderboard
        </h1>
        <div className="flex items-center justify-center mb-4">
          <label htmlFor="topSelector" className="mr-2">
            Select Top:
          </label>
          <select
              id="topSelector"
              value={selectedTop}
              onChange={handleTopChange}
              className="border rounded-md p-2 text-black"
          >
            {[3, 5, 10, 20, 50].map((value) => (
                <option key={value} value={value}>
                  {value}
                </option>
            ))}
          </select>
        </div>
        <div className="flex flex-col space-y-2">
          <div className="overflow-y-auto max-h-80">
            {leaderboard.map((userItem, index) => (
                <div
                    key={userItem.id}
                    className="flex items-center bg-gray-200 p-4 rounded mb-2"
                    style={{
                      color:
                          index === 0
                              ? "goldenrod"
                              : index === 1
                                  ? "grey"
                                  : index === 2
                                      ? "brown"
                                      : "black",
                    }}
                >
                  <div className="truncate w-6">
                    <span>{index + 1}</span>
                  </div>
                  <div className={"w-48 truncate"}>
                    <span>{userItem.name}</span>
                  </div>
                  <div className="truncate w-24">
                    <span >{userItem.rank.name}</span>

                  </div>
                  {userItem.rank.iconUrl && (
                    <img
                        src={userItem.rank.iconUrl}
                        alt={`${userItem.rank.name} Icon`}
                        className="w-6 h-6 ml-2 right-0"
                    />
                )}
                </div>
            ))}
          </div>
        </div>
      </div>
  );
};

export default Leaderboard;
