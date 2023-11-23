import { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import Nprogress from "../../../dist/nprogress/nprogress";
import NavItem from "./NavItem";

export default function Header() {
  const location = useLocation();

  useEffect(() => {
    Nprogress.done();
    return () => {
      Nprogress.start();
    };
  }, [location]);

  return (
    <header className="items-center bg-theme-color border-b border-dark-theme-color flex px-2 fixed w-full h-14 shadow-2xl">
      <Link
        to="/"
        className="header-logo flex items-center border-dark-theme-color group"
      >
        <div className="w-12 h-12 m-2 animate-heart-beat group-hover:animate-none">
          <img src="royale_games_logo.png" alt="logo" />
        </div>
        <span className="text-2xl">Gomoku Royale</span>
      </Link>

      <div className="header-sac relative flex flex-grow flex-shrink-0 basis-28"></div>

      <nav className="header-menu flex h-full items-center">
        <NavItem to="/about" title="About" isActive={false} />
      </nav>
    </header>
  );
}
