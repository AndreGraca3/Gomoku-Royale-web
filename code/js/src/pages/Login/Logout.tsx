import { Navigate } from "react-router-dom";
import { useLogin } from "../../hooks/Auth/AuthnStatus";
import { useEffect } from "react";

export default function Logout() {
  const setLogin = useLogin();
  useEffect(() => {
    setLogin(false);
    localStorage.removeItem("loggedIn");
  }, []);
  return <Navigate to="/" replace={true} />;
}
