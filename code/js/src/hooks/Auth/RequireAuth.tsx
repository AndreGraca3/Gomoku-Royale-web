import { Navigate, useLocation } from "react-router-dom";
import { useCurrentUser } from "./Auth";

export function RequireAuthn({
  children,
}: {
  children: React.ReactNode;
}): React.ReactElement {
  const currentUser = useCurrentUser();
  const location = useLocation();
  if (currentUser) {
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
