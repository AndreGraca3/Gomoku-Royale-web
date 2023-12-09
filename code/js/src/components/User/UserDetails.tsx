import { useState } from "react";
import { UnderlinedHeader } from "../UnderlinedHeader";

export function UserDetailsView({ user, updateUser }) {
  const [userName, setUserName] = useState(user.name);
  const [userAvatar, setUserAvatar] = useState(user.avatarUrl);
  const [isEditing, setIsEditing] = useState(false);

  const handleChange = (ev) => {
    const newUserName = ev.target.value;
    setUserName(newUserName);
  };

  const handleSubmit = async (ev) => {
    ev.preventDefault();
    await updateUser(userName, userAvatar);
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
      setUserAvatar(selectedImageUrl);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="flex justify-center items-center">
      <div className="grid grid-cols-1 gap-y-4">
        <button className="w-40 h-40" onClick={handleImageClick}>
          <img className="w-full h-full rounded-full" src={userAvatar} />
        </button>
        <div>
          <UnderlinedHeader>Name :</UnderlinedHeader>
          {isEditing ? (
            <form onSubmit={handleSubmit}>
              <div className="relative">
                <input
                  className="w-32 text-gray-900 inline-flex rounded"
                  type="text"
                  placeholder="new name..."
                  onChange={handleChange}
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
          <UnderlinedHeader>Email :</UnderlinedHeader>
          <h1 className="inline-flex">{user.email}</h1>
        </div>
      </div>
    </div>
  );
}
