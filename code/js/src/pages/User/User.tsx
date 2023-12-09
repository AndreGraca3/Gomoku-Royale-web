import React, { useEffect, useState } from "react";
import userData from "../../data/userData";
import { UserDetailsView } from "../../components/User/UserDetails";
import { UserStatsView } from "../../components/User/UserStats";
import { Loading } from "../Loading/Loading";
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
  const [deleteUser, setDeleteUser] = useState(undefined);

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

    setDeleteUser((prev) => {
      return async () => {
        const deleteUserAction = userData.getDeleteUserAction(userSiren);
        await fetchAPI(deleteUserAction.href, deleteUserAction.method);
        localStorage.removeItem("loggedIn");
        setRedirect(true);
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
        <Loading message="Loading..." onCancel={undefined} />
      </div>
    );
  }

  return (
    <div>
      <div className="flex justify-center items-center">
        <div className="grid grid-cols-3 gap-x-20">
          <UserDetailsView
            user={user}
            updateUser={updateUser}
          ></UserDetailsView>
          <UserStatsView userStats={userStats}></UserStatsView>
          <UserRankView rank={userStats.rank}></UserRankView>
        </div>
      </div>
      <div className="flex justify-center items-center">
        <ScaledButton onClick={deleteUser} text="Delete" color="red" />
      </div>
    </div>
  );
}
