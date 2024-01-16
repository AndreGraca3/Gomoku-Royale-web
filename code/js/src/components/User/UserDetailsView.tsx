import { useCallback, useState } from "react";
import { UserDetails } from "../../types/user";
import { timeSince } from "../../utils/time";
import Avatar from "../Avatar";
import InputField from "../InputField";

export function UserDetailsView({
  user,
  updateUser,
}: {
  user: UserDetails;
  updateUser: (name?: string, avatarUrl?: string) => Promise<void>;
}) {
  const [userName, setUserName] = useState(user.name);

  const handleNameChange = (ev: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(ev.target.value);
  };

  const handleSubmit = useCallback(
    async (ev: React.FormEvent<HTMLFormElement>) => {
      ev.preventDefault();
      await updateUser(userName);
    },
    [userName]
  );

  const selectImage = useCallback(async (): Promise<string> => {
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
  }, []);

  const handleImageClick = useCallback(async () => {
    try {
      const selectedImageUrl = await selectImage();
      await updateUser(userName, selectedImageUrl);
    } catch (error) {
      console.error(error);
    }
  }, [selectImage, userName]);

  return (
    <div className="flex flex-col justify-center items-center gap-y-8">
      <Avatar
        onClick={handleImageClick}
        url={user.avatarUrl ?? "/user_icon.png"}
        size="large"
      />
      <div>
        <h1 className="underline">Name :</h1>
        <form onSubmit={handleSubmit}>
          <InputField
            handleChange={handleNameChange}
            value={userName}
            isSubmittable
          />
        </form>
      </div>
      <div>
        <h1 className="inline-flex">{user.email}</h1>
        <h1>Joined: {timeSince(new Date(user.createdAt).toDateString())}</h1>
      </div>
    </div>
  );
}
