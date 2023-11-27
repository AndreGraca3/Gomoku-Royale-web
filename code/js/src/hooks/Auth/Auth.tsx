import { useState, createContext, useContext } from "react";
import { UserInfo } from "../../types/user";

type ContextType = {
  user: UserInfo | undefined;
  setUser: (v: UserInfo | undefined) => void;
};
const LoggedInContext = createContext<ContextType>({
  user: undefined,
  setUser: () => {},
});

export function AuthnContainer({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState(undefined);
  return (
    <LoggedInContext.Provider value={{ user: user, setUser: setUser }}>
      {children}
    </LoggedInContext.Provider>
  );
}

export function useCurrentUser(): UserInfo | undefined {
  return useContext(LoggedInContext).user;
}

export function useSetUser(): (u: UserInfo | undefined) => void {
  return useContext(LoggedInContext).setUser;
}
