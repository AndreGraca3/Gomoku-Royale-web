import { Link } from "react-router-dom";

export default function NavItem({
  to,
  title,
  isActive,
}: {
  to: string;
  title: string;
  isActive?: boolean;
}) {
  return (
    <div
      className={
        "nav-item h-full block text-lg hover:text-gr-yellow " +
        `${isActive ? "rounded border-b-4 border-gr-yellow" : ""}`
      }
    >
      <Link
        to={to}
        className="w-full h-full flex px-2 items-center justify-center text-sm opacity-80 hover:opacity-100"
      >
        {title}
      </Link>
    </div>
  );
}
