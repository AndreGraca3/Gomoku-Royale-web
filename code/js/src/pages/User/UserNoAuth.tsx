import { useEffect, useState } from "react";
import userData from "../../data/userData";
import { Loading } from "../../components/Loading";
import { useParams } from "react-router-dom";
import { UserInfoView } from "../../components/User/UserInfoView";

export function UserNoAuth() {
  const { id } = useParams();
  const [user, setUser] = useState(undefined);
  const [isLoading, setIsLoading] = useState(true);

  const fetchUser = async () => {
    const userSiren = await userData.getNonAuthenticatedUser(parseInt(id));
    setUser(userSiren.properties);
  };

  useEffect(() => {
    fetchUser().then(() => {
      setIsLoading(false);
    });
  }, []);

  if (isLoading) {
    return (
      <div>
        <Loading message="Loading..." />
      </div>
    );
  }

  return <UserInfoView user={user}></UserInfoView>;
}
