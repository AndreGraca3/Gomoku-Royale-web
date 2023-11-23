import { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import Nprogress from "../../../dist/nprogress/nprogress";

export default function Header() {
  const location = useLocation();

  useEffect(() => {
    Nprogress.done();
    return () => {
      Nprogress.start();
    };
  }, [location]);

  return (
    <header className="items-center border-b border-dark-theme-color flex px-2 fixed w-full shadow-2xl">
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

      <nav className="header-menu block px-2">
        <Link
          to="/about"
          className="inline-block nav-item text-lg hover:text-gr-yellow border-b-4 border-transparent hover:border-gr-yellow hover:rounded-b"
        >
          <div>About</div>
        </Link>
      </nav>
    </header>
  );
}
