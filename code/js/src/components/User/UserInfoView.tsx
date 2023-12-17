import { UnderlinedHeader } from "../UnderlinedHeader";

export function UserInfoView({ user }) {
  console.log(user);
  return (
    <div className="flex items-center">
      <div className="grid grid-cols-1 gap-y-4 text-center">
        <img
          className="rounded-full w-40 h-40 justify-center object-cover"
          src={user.avatarUrl ?? "/user_icon.png"}
        />
        <div>
          <UnderlinedHeader>Name :</UnderlinedHeader>
          <h1 className="inline-flex">{user.name}</h1>
        </div>
        <div>
          <UnderlinedHeader>Rank :</UnderlinedHeader>
          <p className="text-sm">{user.rank.name}</p>
          <div className="flex justify-center items-center">
            {user.rank.iconUrl && (
              <img
                className="w-16 h-16"
                src={user.rank.iconUrl}
                alt="Rank Icon"
              />
            )}
          </div>
        </div>
        <div>
          <UnderlinedHeader>Created At :</UnderlinedHeader>
          <p>{new Date(user.createdAt).toDateString()}</p>
        </div>
      </div>
    </div>
  );
}
