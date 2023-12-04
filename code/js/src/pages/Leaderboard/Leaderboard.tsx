import React, { useState, useEffect } from "react";
import statsData from "../../data/statsData";

const Leaderboard = () => {
  const [leaderboard, setLeaderboard] = useState([]);
  const [rankImages, setRankImages] = useState({});
  const [selectedTop, setSelectedTop] = useState(10);

  const fetchLeaderboard = async () => {
    try {
      let data;

      // if (process.env.NODE_ENV === "development") {
      //   // Use test data for development
      //   data = [
      //     {
      //       id: 1,
      //       userName: "User1",
      //       rank: {
      //         name: "Bronze",
      //         iconUrl:
      //           "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-1.png",
      //       },
      //     },
      //     {
      //       id: 2,
      //       userName: "User2",
      //       rank: {
      //         name: "Silver",
      //         iconUrl:
      //           "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-4.png",
      //       },
      //     },
      //     {
      //       id: 3,
      //       userName: "User3",
      //       rank: {
      //         name: "Gold",
      //         iconUrl:
      //           "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-7.png",
      //       },
      //     },
      //     {
      //       id: 4,
      //       userName: "User4",
      //       rank: {
      //         name: "Platinum",
      //         iconUrl:
      //           "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-10.png",
      //       },
      //     },
      //     {
      //       id: 5,
      //       userName: "User5",
      //       rank: {
      //         name: "Diamond",
      //         iconUrl:
      //           "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-13.png",
      //       },
      //     },
      //     {
      //       id: 6,
      //       userName: "User6",
      //       rank: {
      //         name: "Champion",
      //         iconUrl:
      //           "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-16.png",
      //       },
      //     },
      //     {
      //       id: 7,
      //       userName: "User7",
      //       rank: {
      //         name: "Grand Champion",
      //         iconUrl:
      //           "https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s15rank19.png",
      //       },
      //     },
      //   ];
      // } else {
        // Fetch actual data from the API with the selected top limit
        data = await statsData.top(selectedTop);
      //}

      setLeaderboard(data.properties);

      // Fetch all images and create a dictionary
      const images = await Promise.all(
        data.map((userRank) =>
          fetch(userRank.rank.iconUrl)
            .then((response) => response.blob())
            .then((blob) => URL.createObjectURL(blob))
        )
      );

      const imagesDictionary = {};
      data.forEach((userRank, index) => {
        imagesDictionary[userRank.rank.name] = images[index];
      });

      setRankImages(imagesDictionary);
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

  const handleRefreshClick = () => {
    // You can update the URI or perform any action before fetching data
    fetchLeaderboard();
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
          {[10, 25, 50, 100].map((value) => (
            <option key={value} value={value}>
              {value}
            </option>
          ))}
        </select>
        <button
          onClick={handleRefreshClick}
          className="ml-2 bg-blue-500 text-white p-2 rounded-md"
        >
          🔍
        </button>
      </div>
      <div className="flex flex-col space-y-2">
        <div className="overflow-y-auto max-h-80">
          {leaderboard.map((userRank, index) => (
            <div
              key={userRank.id}
              className="flex items-center justify-between bg-gray-200 p-4 rounded-md mb-2"
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
              <span>{index + 1}</span>
              <span>{userRank.userName}</span>
              <span>{userRank.rank.name}</span>
              {userRank.rank.iconUrl && (
                <img
                  src={userRank.rank.iconUrl}
                  alt={`${userRank.rank.name} Icon`}
                  className="w-6 h-6 ml-2"
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
