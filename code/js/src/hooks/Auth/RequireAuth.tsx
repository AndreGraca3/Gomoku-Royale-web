import { Navigate, useLocation } from "react-router-dom";
import { useSession } from "./AuthnStatus";

export function RequireAuthn({
  children,
}: {
  children?: React.ReactNode;
}): React.ReactElement {
  const [authUser] = useSession();
  const location = useLocation();

  if (authUser) {
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
