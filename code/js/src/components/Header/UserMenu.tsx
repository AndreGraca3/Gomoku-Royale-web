import { Menu, Transition } from "@headlessui/react";
import Avatar from "../Avatar";
import { useCurrentUser } from "../../hooks/Auth/Auth";
import { Fragment } from "react";

const UserMenuLinks = () => {
  const currentUser = useCurrentUser();

  const userLinks = currentUser
    ? [
        { to: "/me", title: "🙋‍♂️ Profile" },
        { to: "/logout", title: "🔓 Logout" },
      ]
    : [
        { to: "/login", title: "🔐 Login" },
        { to: "/signup", title: "📝 Register" },
      ];

  return (
    <Menu.Items
      as="div"
      className="z-50 absolute p-2 right-2 max-h-96 bg-dark-theme-color rounded text-sm shadow shadow-black overflow-y-auto focus:outline-none"
    >
      {userLinks.map(({ to, title }, i) => (
        <Menu.Item as="div" key={i} className="flex w-full">
          {({ active }) => (
            <a
              className={`w-full whitespace-nowrap p-2 ${
                active ? "text-gr-yellow" : ""
              }`}
              href={to}
            >
              {title}
            </a>
          )}
        </Menu.Item>
      ))}
    </Menu.Items>
  );
};

export default function UserMenu() {
  return (
    <Menu as="div" className="h-full">
      <Menu.Button className="h-full hover:scale-105 transition-all duration-200">
        <Avatar />
      </Menu.Button>
      <UserMenuLinks />
    </Menu>
  );
}
