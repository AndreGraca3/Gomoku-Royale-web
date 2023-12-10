import { Navigate, useLocation } from "react-router-dom";
import { useCurrentUser } from "./AuthnStatus";

export function RequireAuthn({
  children,
}: {
  children: React.ReactNode;
}): React.ReactElement {
  const isLoggedIn = useCurrentUser();
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
