import { Navigate, useLocation } from "react-router-dom";
import { useLoggedIn } from "./AuthnStatus";

export function RequireAuthn({
  children,
}: {
  children: React.ReactNode;
}): React.ReactElement {
  const currentUser = useLoggedIn();
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
