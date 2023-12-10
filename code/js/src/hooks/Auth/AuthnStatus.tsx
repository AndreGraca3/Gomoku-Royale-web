import * as React from "react";
import { useState, createContext, useContext, useEffect } from "react";
import userData from "../../data/userData";
import { initiallyLogged } from "../..";

// The state that will be in the context
type AuthnContextType = {
  loggedIn: boolean;
  setLoggedIn: (v: boolean) => void;
};

// Create a context for the defined types
// This happens only once
const AuthnContext = createContext<AuthnContextType>({
  loggedIn: false,
  setLoggedIn: () => {},
});

export function AuthnStatusProvider({
  children,
}: {
  children: React.ReactNode;
}) {
  const [observedLoggedIn, setLoggedIn] = useState(initiallyLogged);

  return (
    <AuthnContext.Provider
      value={{ loggedIn: observedLoggedIn, setLoggedIn: setLoggedIn }}
    >
      {children}
    </AuthnContext.Provider>
  );
}

export function useLoggedIn(): boolean {
  return useContext(AuthnContext).loggedIn;
}

export function useLogin() {
  return useContext(AuthnContext).setLoggedIn;
}
