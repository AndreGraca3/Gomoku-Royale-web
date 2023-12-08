import React, { useEffect, useState } from "react";
import { UserDetails } from "../../types/user";
import userData from "../../data/userData";
import statsData from "../../data/statsData";
import { UserStats } from "../../types/stats";
import { UserDetailsView } from "../../components/User/UserDetails";
import { UserStatsView } from "../../components/User/UserStats";

export function User() {
  const [user, setUser] = useState({});
  const [userStats, setUserStats] = useState({});

  const fetchInfo = async () => {
    const syren = await userData.getAuthenticatedUser();
    setUser(syren.properties);

    // const stats = await statsData.getUserStats(syren.properties.id)
    // setUserStats(stats.properties)
  };

  useEffect(() => {
    fetchInfo();
  }, [user, userStats]);

  return (
    <div className="flex justify-center items-center">
      <div className="grid grid-cols-2 gap-x-20">
        <UserDetailsView user={user}></UserDetailsView>
        <UserStatsView userStats={userStats}></UserStatsView>
      </div>
    </div>
  );
}
