import { Link } from "react-router-dom";

export default function HeaderBrand() {
  return (
    <Link
      to="/"
      className="header-logo flex items-center border-dark-theme-color group"
    >
      <div className="w-12 h-12 m-2 animate-heart-beat group-hover:animate-none">
        <img src="royale_games_logo.png" alt="logo" />
      </div>
      <span className="text-2xl font-bold">Gomoku Royale</span>
    </Link>
  );
}
