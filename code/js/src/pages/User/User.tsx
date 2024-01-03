import { useEffect, useState } from "react";
import userData from "../../data/userData";
import { UserDetailsView } from "../../components/User/UserDetailsView";
import { UserStatsView } from "../../components/User/UserStats";
import { Loading } from "../../components/Loading";
import { fetchAPI } from "../../utils/http";
import { UserRankView } from "../../components/User/UserRankView";
import { useSession } from "../../hooks/Auth/AuthnStatus";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";

export function User() {
  const [currentUser, setCurrentUser] = useSession();
  const [userStats, setUserStats] = useState(undefined);

  const [updateUser, setUpdateUser] = useState(undefined);

  const fetchUser = async () => {
    const userSiren = await userData.getAuthenticatedUser();

    const statsSiren = await fetchAPI(userData.getStatsHref(userSiren));
    setUserStats(statsSiren.properties);

    setUpdateUser(() => {
      return async (name: string, avatarUrl: string) => {
        const updateUserAction = userData.getUpdateUserAction(userSiren);
        const body = {
          name,
          avatarUrl,
        };
        await fetchAPI(updateUserAction.href, updateUserAction.method, body);
        setCurrentUser({ ...currentUser, name, avatarUrl });
      };
    });
  };

  if (!currentUser) {
    return <RequireAuthn />;
  }

  useEffect(() => {
    fetchUser();
  }, []);

  if (!userStats) {
    return <Loading message="Fetching Stats..." />;
  }

  return (
    <div>
      <h1 className="text-3xl">User Profile</h1>
      <div className="flex justify-center items-center">
        <div className="grid grid-cols-3 gap-x-20">
          <UserDetailsView user={currentUser} updateUser={updateUser} />
          <UserStatsView userStats={userStats}></UserStatsView>
          <UserRankView rank={userStats.rank}></UserRankView>
        </div>
      </div>
    </div>
  );
}
