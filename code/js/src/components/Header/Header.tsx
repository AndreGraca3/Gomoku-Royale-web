import { useEffect } from "react";
import { useLocation } from "react-router-dom";
import Nprogress from "../../../dist/nprogress/nprogress";
import NavItem from "./NavItem";
import HeaderBrand from "./HeaderBrand";
import UserMenu from "./UserMenu";

export default function Header() {
  const location = useLocation();

  useEffect(() => {
    Nprogress.done();
    return () => {
      Nprogress.start();
    };
  }, [location]);

  type PageLink = {
    to: string;
    title: string;
  };

  const links: Array<PageLink> = [
    //TODO - Change to variable limit
    {
      to: "/top?limit=10",
      title: "Leaderboard",
    },
    {
      to: "/play",
      title: "Play",
    },
    {
      to: "/about",
      title: "About",
    },
  ];

  return (
    <header className="items-center bg-theme-color border-b border-dark-theme-color flex px-2 fixed w-full h-14 shadow-2xl">
      <HeaderBrand />
      <div className="header-sac relative flex flex-grow flex-shrink-0 basis-28"></div>

      <nav className="header-menu flex h-full items-center">
        {links.map(({ to, title }, i) => (
          <NavItem
            key={i}
            to={to}
            title={title}
            isActive={location.pathname == to}
          />
        ))}
      </nav>
      <UserMenu />
    </header>
  );
}
