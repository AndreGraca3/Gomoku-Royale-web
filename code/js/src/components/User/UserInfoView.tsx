import { timeSince } from "../../utils/time";
import Avatar from "../Avatar";
import InfoDisplayer from "../InfoDisplayer";

export function UserInfoView({ user }) {
  return (
    <div className="flex flex-col gap-y-6 items-center">
      <div className="flex flex-col gap-y-2 items-center">
        <Avatar size="large" url={user.avatarUrl ?? "/user_icon.png"} />
        <h1 className="inline-flex">{user.name}</h1>
      </div>
      <InfoDisplayer title={user.rank.name} imgUrl={user.rank.iconUrl} />
      <div>
        <p>Joined: {timeSince(user.createdAt)}</p>
      </div>
    </div>
  );
}
