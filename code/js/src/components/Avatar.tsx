import { useCurrentUser } from "../hooks/Auth/Auth";

export default function Avatar() {
  const currentUser = useCurrentUser();
  return (
    <img
      src={currentUser?.avatarUrl || "/user_icon.png"}
      className="border rounded-full w-10 h-10 cursor-pointer transition-all duration-200 ease-in-out"
    />
  );
}
