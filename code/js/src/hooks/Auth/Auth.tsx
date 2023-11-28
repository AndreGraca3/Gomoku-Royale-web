import { useState, createContext, useContext, useEffect } from "react";
import { UserAvatar } from "../../types/user";
import userData from "../../data/userData";

type ContextType = {
  user: UserAvatar | undefined;
  setUser: (v: UserAvatar | undefined) => void;
};
const LoggedInContext = createContext<ContextType>({
  user: undefined,
  setUser: () => {},
});

export function AuthnContainer({ children }: { children: React.ReactNode }) {
  const [user, setUser] = useState(undefined);
  useEffect(() => {
    const user = localStorage.getItem("loggedIn");
    if (user) {
      userData.getUserHome().then((res) => {
        setUser({ name: res.name, avatarUrl: res.avatarUrl });
      });
    }
  }, []);

  return (
    <LoggedInContext.Provider value={{ user: user, setUser: setUser }}>
      {children}
    </LoggedInContext.Provider>
  );
}

export function useCurrentUser(): UserAvatar | undefined {
  return useContext(LoggedInContext).user;
}

export function useSetUser(): (u: UserAvatar | undefined) => void {
  return useContext(LoggedInContext).setUser;
}
