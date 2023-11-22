import { useEffect } from "react";
import { Link, useLocation } from "react-router-dom";
import Nprogress from "../../dist/nprogress/nprogress";

export default function Header() {
  const location = useLocation();

  useEffect(() => {
    Nprogress.done();
    return () => {
      Nprogress.start();
    };
  }, [location]);

  return (
    <header>
      <div className="h-12 flex items-center border-b border-dark-theme-color">
        <div className="p-4">
          <div className="w-10 h-10">
            <Link to="/">
              <img src="icon.png" alt="logo" className="w-10 h-10" />
            </Link>
          </div>
        </div>
        <span className="text-3xl">Gomoku Royale</span>
        <nav></nav>
      </div>
    </header>
  );
}
