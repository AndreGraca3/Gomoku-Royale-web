import { Menu, Transition } from '@headlessui/react';
import Avatar from "../Avatar";
import { useLoggedIn } from "../../hooks/Auth/AuthnStatus";
import { Fragment, useEffect, useState } from "react";
import { Link } from "react-router-dom";
import userData from "../../data/userData";

const UserMenuLinks = ({ loggedIn }) => {
  const userLinks = loggedIn
    ? [
        { to: "/me", title: "🙋‍♂️ Profile" },
        { to: "/logout", title: "🔓 Logout" },
      ]
    : [
        { to: "/login", title: "🔐 Login" },
        { to: "/signup", title: "📝 Register" },
      ];

  return (
    <Transition
      as={Fragment}
      enter="transition ease-out duration-200"
      enterFrom="transform opacity-0 scale-90"
      enterTo="transform opacity-100 scale-100"
      leave="transition ease-in duration-75"
      leaveFrom="transform opacity-100 scale-100"
      leaveTo="transform opacity-0 scale-90"
    >
      <Menu.Items
        as="div"
        className="z-50 absolute p-2 right-2 max-h-96 bg-dark-theme-color rounded text-sm shadow shadow-black overflow-y-auto focus:outline-none"
      >
        {userLinks.map(({ to, title }, i) => (
          <Menu.Item as="div" key={i} className="flex w-full">
            {({ active, close }) => (
              <Link
                onClick={close}
                className={`w-full whitespace-nowrap p-2 ${
                  active ? "text-gr-yellow" : ""
                }`}
                to={to}
              >
                {title}
              </Link>
            )}
          </Menu.Item>
        ))}
      </Menu.Items>
    </Transition>
  );
};

export default function UserMenu() {
  const loggedIn = useLoggedIn();
  const [avatar, setAvatar] = useState(undefined);

  useEffect(() => {
    if (loggedIn) {
      userData.getUserHome().then((res) => {
        console.log("Got user home from UserMenu");
        setAvatar(res.properties.avatarUrl);
      });
    }
  }, [loggedIn]);

  return (
    <Menu as="div" className="h-full">
      <Menu.Button className="h-full focus:outline-0 hover:scale-105 transition-all duration-200">
        <Avatar url={avatar} />
      </Menu.Button>
      <UserMenuLinks loggedIn={loggedIn} />
    </Menu>
  );
}
