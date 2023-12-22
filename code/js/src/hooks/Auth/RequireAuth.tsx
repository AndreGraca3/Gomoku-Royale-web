import { Navigate, useLocation } from "react-router-dom";
import { useSession } from "./AuthnStatus";

export function RequireAuthn({
  children,
}: {
  children: React.ReactNode;
}): React.ReactElement {
  const isLoggedIn = useSession();
  const location = useLocation();

  if (isLoggedIn) {
    return <>{children}</>;
  } else {
    return (
      <Navigate
        to="/login"
        state={{ source: location.pathname }}
        replace={true}
      />
    );
  }
}
