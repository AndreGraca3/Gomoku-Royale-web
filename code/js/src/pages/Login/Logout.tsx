import { Navigate } from "react-router-dom";
import { useLogin } from "../../hooks/Auth/AuthnStatus";
import { useEffect } from "react";
import userData from "../../data/userData";

export default function Logout() {
  const setLogin = useLogin();
  useEffect(() => {
    setLogin(false);
    userData.logout()
  }, []);
  return <Navigate to="/" replace={true} />;
}
