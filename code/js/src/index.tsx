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
import {User} from "./pages/User/User";
import {HomeData} from "./data/homeData";
import {Match} from "./pages/Match/Match";
import {Preferences} from "./pages/Preferences/Preferences";

export let homeLinks: HomeData;

fetchAPI("/api")
    .then((res) => {
      homeLinks = new HomeData(res);
      const root = createRoot(document.getElementById("container"));
      root.render(<RouterProvider router={router} />);
    });

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
        element: <div>About Page</div>,
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
        element: <Leaderboard/>,
      },
      {
        path: "me",
        element: <User />
      },
      {
        path: "play",
        element: <Preferences />
      },
      {
        path: "match/:id",
        element: <Match/>
      }
    ],
  }
]);