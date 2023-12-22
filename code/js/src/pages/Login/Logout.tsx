import { Navigate } from "react-router-dom";
import { useEffect } from "react";
import userData from "../../data/userData";
import { useSession } from "../../hooks/Auth/AuthnStatus";

export default function Logout() {
  const [_, setCurrentUser] = useSession();
  useEffect(() => {
    userData.logout().then(() => {
      setCurrentUser(undefined);
    });
  }, []);
  return <Navigate to="/" replace={true} />;
}
