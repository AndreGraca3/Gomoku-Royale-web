import * as React from "react";
import { useState, createContext, useContext } from "react";
import { authUser } from "../..";
import { UserDetails } from "../../types/user";

// The state that will be in the context
type AuthnContextType = {
  currentUser: UserDetails;
  setCurrentUser: (v: UserDetails) => void;
};

// Create a context for the defined types
// This happens only once
const AuthnContext = createContext<AuthnContextType>({
  currentUser: undefined,
  setCurrentUser: () => {},
});

export function AuthnStatusProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [currUser, setCurrUser] = useState(authUser);

  return (
    <AuthnContext.Provider
      value={{ currentUser: currUser, setCurrentUser: setCurrUser }}
    >
      {children}
    </AuthnContext.Provider>
  );
}

export function useSession(): [UserDetails, (v: UserDetails) => void] {
  return [
    useContext(AuthnContext).currentUser,
    useContext(AuthnContext).setCurrentUser,
  ];
}
