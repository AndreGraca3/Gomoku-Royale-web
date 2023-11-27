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
        "nav-item h-full block text-lg border-4 border-transparent hover:text-gr-yellow " +
        `${isActive ? "rounded border-b-gr-yellow" : "border-b-transparent"}`
      }
    >
      <Link
        to={to}
        className={
          "w-full h-full flex px-2 items-center justify-center text-sm hover:opacity-100 " +
          `${isActive ? "font-bold opacity-100" : "opacity-80"}`
        }
      >
        {title}
      </Link>
    </div>
  );
}
