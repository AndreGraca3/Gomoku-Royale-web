import React, { useState, useEffect } from "react";
import statsData from "../../data/statsData";
import LeaderBoardItem from "./LeaderBoardItem";

const Leaderboard = () => {
  const [leaderboard, setLeaderboard] = useState([]);
  const [selectedTop, setSelectedTop] = useState(10);

  const fetchLeaderboard = async (topValue) => {
    try {
      // Fetch actual data from the API with the selected top limit
      const data = await statsData.top(topValue);

      setLeaderboard(data.properties.ranks);
    } catch (error) {
      console.error("Error fetching leaderboard:", error);
    }
  };

  useEffect(() => {
    // Initial fetch
    fetchLeaderboard(selectedTop);
  }, [selectedTop]);

  const handleTopChange = (e) => {
    const newTopValue = parseInt(e.target.value, 10);
    setSelectedTop(newTopValue);
    fetchLeaderboard(newTopValue);
  };

  return (
    <div className="h-full font-bold">
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
        <div className="overflow-y-auto max-h-80 p-4 space-y-2">
          {leaderboard.map((userItem, index) => (
            <LeaderBoardItem key={index} userItem={userItem} index={index} />
          ))}
        </div>
      </div>
    </div>
  );
};

export default Leaderboard;
