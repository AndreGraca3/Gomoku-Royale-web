import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./global.css";
import Home from "./pages/Home/Home";
import Layout from "./layouts/Layout";
import { Login } from "./pages/Login/Login";
import Logout from "./pages/Login/Logout";
import { fetchAPI } from "./utils/http";
import { SignUp } from "./pages/SignUp/SignUp";
import Leaderboard from "./pages/Leaderboard/Leaderboard";
import { User } from "./pages/User/User";
import { UserNoAuth } from "./pages/User/UserNoAuth";
import { HomeData } from "./data/homeData";
import { Match } from "./pages/Match/Match";
import { Preferences } from "./pages/Preferences/Preferences";
import About from "./pages/About/About";
import Error from "./pages/Error";
import userData from "./data/userData";
import { UserDetails } from "./types/user";

export let homeLinks: HomeData;
export let authUser: UserDetails;

async function initializeApp() {
  try {
    const res = await fetchAPI("/api");
    homeLinks = new HomeData(res);
    await verifyLogin();
    const root = createRoot(document.getElementById("container"));
    root.render(<RouterProvider router={router} />);
  } catch (e) {
    const root = createRoot(document.getElementById("container"));
    root.render(<Error />);
  }
}

async function verifyLogin() {
  try {
    const res = await userData.getAuthenticatedUser();
    authUser = res.properties;
  } catch (e) {
    console.log("Not logged in");
  }
}

const router = createBrowserRouter([
  {
    path: "/",
    element: <Layout />,
    children: [
      {
        path: "",
        element: <Home />,
      },
      {
        path: "about",
        element: <About />,
      },
      {
        path: "login",
        element: <Login />,
      },
      {
        path: "logout",
        element: <Logout />,
      },
      {
        path: "signup",
        element: <SignUp />,
      },
      {
        path: "top",
        element: <Leaderboard />,
      },
      {
        path: "me",
        element: <User />,
      },
      {
        path: "user/:id",
        element: <UserNoAuth/>
      },
      {
        path: "play",
        element: <Preferences />,
      },
      {
        path: "match/:id",
        element: <Match />,
      },
    ],
  },
]);

initializeApp();
