import { useState } from "react";
import { UserDetails } from "../../types/user";
import { timeSince } from "../../utils/time";

export function UserDetailsView({
  user,
  updateUser,
}: {
  user: UserDetails;
  updateUser: (name?: string, avatarUrl?: string) => Promise<void>;
}) {
  const [userName, setUserName] = useState(user.name);
  const [isEditing, setIsEditing] = useState(false);

  const handleNameChange = (ev: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(ev.target.value);
  };

  const handleSubmit = async (ev: React.FormEvent<HTMLFormElement>) => {
    ev.preventDefault();
    await updateUser(userName);
    setIsEditing(false);
  };

  const selectImage = async (): Promise<string> => {
    return new Promise((resolve, reject) => {
      const input = document.createElement("input");
      input.type = "file";
      input.accept = "image/*";
      input.onchange = (event) => {
        const file = (event.target as HTMLInputElement).files?.[0];
        if (file) {
          const reader = new FileReader();
          reader.onload = (readerEvent) => {
            const imageUrl = readerEvent.target.result as string;
            resolve(imageUrl);
          };
          reader.readAsDataURL(file);
        } else {
          reject("No image selected");
        }
      };
      input.click();
    });
  };

  const handleImageClick = async () => {
    try {
      const selectedImageUrl = await selectImage();
      await updateUser(userName, selectedImageUrl);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="flex justify-center items-center">
      <div className="grid grid-cols-1 gap-y-4">
        <button className="w-40 h-40" onClick={handleImageClick}>
          <img
            className="w-full h-full rounded-full object-cover"
            src={user.avatarUrl ?? "/user_icon.png"}
          />
        </button>
        <div>
          <h1 className="underline">Name :</h1>
          {isEditing ? (
            <form onSubmit={handleSubmit}>
              <div className="relative">
                <input
                  className="w-32 text-gray-900 inline-flex rounded"
                  type="text"
                  placeholder="new name..."
                  onChange={handleNameChange}
                  value={userName}
                ></input>
                <button className="absolute inset-y-0 right-0" type="submit">
                  ✔️
                </button>
              </div>
            </form>
          ) : (
            <div className="relative">
              <h1 className="inline-flex">{userName}</h1>
              <button
                className="absolute inset-y-0 right-0"
                onClick={() => {
                  setIsEditing(true);
                }}
              >
                ✏️
              </button>
            </div>
          )}
        </div>
        <div>
          <h1 className="underline">Email :</h1>
          <h1 className="inline-flex">{user.email}</h1>
          <h1>Joined: {timeSince(new Date(user.createdAt).toDateString())}</h1>
        </div>
      </div>
    </div>
  );
}
