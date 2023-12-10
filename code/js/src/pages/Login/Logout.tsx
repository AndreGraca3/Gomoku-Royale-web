import { Navigate } from "react-router-dom";
import { useSetCurrentUser } from "../../hooks/Auth/AuthnStatus";
import { useEffect } from "react";
import userData from "../../data/userData";

export default function Logout() {
  const setLogin = useSetCurrentUser();
  useEffect(() => {
    setLogin(undefined);
    userData.logout();
  }, []);
  return <Navigate to="/" replace={true} />;
}
