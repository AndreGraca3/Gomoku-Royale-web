import { useEffect, useState } from "react";
import userData from "../../data/userData";
import { UserDetailsView } from "../../components/User/UserDetails";
import { UserStatsView } from "../../components/User/UserStats";
import { Loading } from "../../components/Loading";
import { fetchAPI } from "../../utils/http";
import { UserRankView } from "../../components/User/UserRankView";
import ScaledButton from "../../components/ScaledButton";
import { Navigate } from "react-router-dom";

export function User() {
  const [user, setUser] = useState(undefined);
  const [userStats, setUserStats] = useState(undefined);

  const [isLoading, setIsLoading] = useState(true);
  const [redirect, setRedirect] = useState(false);

  const [updateUser, setUpdateUser] = useState(undefined);

  const fetchUser = async () => {
    const userSiren = await userData.getAuthenticatedUser();
    const user = userSiren.properties;
    setUser(user);

    const statsSiren = await fetchAPI(userData.getStatsHref(userSiren));
    setUserStats(statsSiren.properties);

    setUpdateUser((prev) => {
      return async (name, avatarUrl) => {
        const updateUserAction = userData.getUpdateUserAction(userSiren);
        const body = {
          name: name,
          avatarUrl: avatarUrl,
        };
        await fetchAPI(updateUserAction.href, updateUserAction.method, body);
        const newUser = {
          id: user.id,
          name: name,
          email: user.email,
          avatarUrl: avatarUrl ? user.avatarUrl : avatarUrl,
          role: user.role,
          createdAt: user.created_at,
        };
        setUser(newUser);
      };
    });
  };

  useEffect(() => {
    fetchUser().then((r) => {
      setIsLoading(false);
    });
  }, []);

  if (redirect) {
    return <Navigate to="/" replace={true} />;
  }

  if (isLoading) {
    return (
      <div>
        <Loading message="Loading..." />
      </div>
    );
  }

  return (
    <div>
      <h1 className="text-3xl">User Profile</h1>
      <div className="flex justify-center items-center">
        <div className="grid grid-cols-3 gap-x-20">
          <UserDetailsView user={user} updateUser={updateUser} />
          <UserStatsView userStats={userStats}></UserStatsView>
          <UserRankView rank={userStats.rank}></UserRankView>
        </div>
      </div>
    </div>
  );
}
