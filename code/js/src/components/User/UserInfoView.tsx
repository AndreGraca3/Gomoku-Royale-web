import React, { useState } from "react";
import { UnderlinedHeader } from "../UnderlinedHeader";

export function UserInfoView({ user }) {
  const [userAvatar, setUserAvatar] = useState(user.avatarUrl);

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
      setUserAvatar(selectedImageUrl);
    } catch (error) {
      console.error(error);
    }
  };

  return (
    <div className="flex items-center">
      <div className="grid grid-cols-1 gap-y-4 text-center">
          <img className="rounded-full w-40 h-40 justify-center" src={userAvatar} alt="User Avatar" />
        <div>
          <UnderlinedHeader>Name :</UnderlinedHeader>
          <h1 className="inline-flex">{user.name}</h1>
        </div>
        <div>
          <UnderlinedHeader>Rank :</UnderlinedHeader>
          <p className="text-sm">{user.rank.name}</p>
          <div className="flex justify-center items-center">
            {user.rank.iconUrl && <img className="w-16 h-16" src={user.rank.iconUrl} alt="Rank Icon" />}
          </div>
        </div>
        <div>
          <UnderlinedHeader>Created At :</UnderlinedHeader>
          <p>{new Date(user.created_at).toDateString()}</p>
        </div>
      </div>
    </div>
  );
}
