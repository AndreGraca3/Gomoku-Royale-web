import { createRoot } from "react-dom/client";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import "./global.css";
import Home from "./pages/Home/Home";
import Layout from "./layouts/Layout";
import { Login } from "./pages/Login/Login";
import Logout from "./pages/Login/Logout";
import { SignUp } from "./pages/SignUp/SignUp";
import Leaderboard from "./pages/Leaderboard/Leaderboard";

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
      }
    ],
  },
]);

const root = createRoot(document.getElementById("container"));
root.render(<RouterProvider router={router} />);
