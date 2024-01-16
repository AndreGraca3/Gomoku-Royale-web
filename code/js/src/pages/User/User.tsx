import { useCallback, useEffect, useReducer, useState } from "react";
import userData from "../../data/userData";
import { UserDetailsView } from "../../components/User/UserDetailsView";
import { UserStatsView } from "../../components/User/UserStatsView";
import { Loading } from "../../components/Loading";
import { UserRankView } from "../../components/User/UserRankView";
import { useSession } from "../../hooks/Auth/AuthnStatus";
import { RequireAuthn } from "../../hooks/Auth/RequireAuth";
import { UserDetails, UserDetailsSiren } from "../../types/user";
import { UserStats } from "../../types/stats";
import toast from "react-hot-toast";

type Action =
  | { type: "LOADED"; userSiren: UserDetailsSiren; userStats: UserStats }
  | { type: "SET_ERROR"; error: string }
  | { type: "UPDATE" };

type State =
  | { type: "LOADING"; userSiren?: UserDetailsSiren; userStats?: UserStats }
  | { type: "LOADED"; userSiren: UserDetailsSiren; userStats: UserStats }
  | { type: "UPDATING"; userSiren: UserDetailsSiren; userStats: UserStats };

export function User() {
  const [currentUser, setCurrentUser] = useSession();

  const reducer = (state: State, action: Action): State => {
    switch (action.type) {
      case "LOADED":
        return {
          type: "LOADED",
          userSiren: action.userSiren,
          userStats: action.userStats,
        };

      case "SET_ERROR":
        if (state.type != "UPDATING") return state;
        toast.error(action.error);
        return { ...state, type: "LOADED" };

      case "UPDATE":
        if (state.type != "LOADED") return state;
        return {
          type: "UPDATING",
          userSiren: state.userSiren,
          userStats: state.userStats,
        };
    }
  };

  const [state, dispatch] = useReducer(reducer, { type: "LOADING" });

  const updateUser = useCallback(
    async (name: string, avatarUrl?: string) => {
      dispatch({ type: "UPDATE" });
      try {
        const newUser = await state.userSiren.updateUser(name, avatarUrl);
        setCurrentUser(newUser.properties);
        dispatch({
          type: "LOADED",
          userSiren: state.userSiren,
          userStats: state.userStats,
        });
      } catch (problem) {
        dispatch({ type: "SET_ERROR", error: problem.detail });
      }
    },
    [state.type]
  );

  useEffect(() => {
    userData.getAuthenticatedUser().then(async (rsp) => {
      try {
        const userSiren = new UserDetailsSiren(rsp);
        const stats = await userSiren.fetchStats();
        dispatch({
          type: "LOADED",
          userSiren: userSiren,
          userStats: stats.properties,
        });
      } catch (problem) {
        dispatch({ type: "SET_ERROR", error: problem.detail });
      }
    });
  }, []);

  if (!currentUser) {
    return <RequireAuthn />;
  }

  if (state.type == "LOADING") {
    return <Loading message="Fetching Stats..." />;
  }

  return (
    <div className="flex justify-center items-center gap-x-24">
      <div className="w-80 items-center">
        {state.type == "UPDATING" ? (
          <Loading message="Updating..." />
        ) : (
          <UserDetailsView user={currentUser} updateUser={updateUser} />
        )}
      </div>
      <UserStatsView userStats={state.userStats} />
      <UserRankView rank={state.userStats.rank} />
    </div>
  );
}
