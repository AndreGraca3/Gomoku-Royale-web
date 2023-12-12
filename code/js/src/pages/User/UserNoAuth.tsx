import React, { useEffect, useState } from "react";
import userData from "../../data/userData";
import { Loading } from "../../components/Loading";
import { Navigate, useParams } from "react-router-dom";
import { UserInfoView } from "../../components/User/UserInfoView";
import { UserRankView } from "../../components/User/UserRankView";

export function UserNoAuth() {
  const {id} = useParams()
  const [user, setUser] = useState(undefined);
  const [isLoading, setIsLoading] = useState(true);
  const [redirect, setRedirect] = useState(false);

  const fetchUser = async () => {
    const userSiren = await userData.getNonAuthenticatedUser(parseInt(id));
    console.log(userSiren)
    setUser(userSiren.properties);
  };

  useEffect(() => {
    fetchUser().then(() => {
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

  const { idUser, name, avatarUrl, role, createdAt, rank } = user;

  return (
    <div>
      <UserInfoView
        user={user}
      ></UserInfoView>
    </div>
  );
}

