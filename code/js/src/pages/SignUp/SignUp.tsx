import React, { useState } from "react";
import { Navigate } from "react-router-dom";
import InputField from "../../components/InputField";
import ScaledButton from "../../components/ScaledButton";
import userData from "../../data/userData";
import { useSetCurrentUser } from "../../hooks/Auth/AuthnStatus";

export function SignUp() {
  const [inputs, setInputs] = useState({
    name: "",
    email: "",
    password: "",
    avatarUrl: "",
  });

  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(undefined);
  const [redirect, setRedirect] = useState(undefined);
  const setLoggedIn = useSetCurrentUser();

  const handleChange = (ev) => {
    const { name, value } = ev.target;
    setInputs({ ...inputs, [name]: value });
    setError(undefined);
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
      setInputs({ ...inputs, avatarUrl: selectedImageUrl });
    } catch (error) {
      console.error(error);
    }
  };

  const handleSubmit = async (ev) => {
    ev.preventDefault();
    setIsSubmitting(true);

    const { name, email, password, avatarUrl } = inputs;

    try {
      // Make an API request to create a new user
      await userData.signUp({ name, email, password, avatarUrl });
      setIsSubmitting(false);
      console.log("Signed up!");
      setRedirect("/login"); // Redirect to the desired page after signup
    } catch (error) {
      setIsSubmitting(false);
      setError(error.detail);
    }
  };

  if (redirect) {
    return <Navigate to={redirect} replace />;
  }

  return (
    <div className="flex flex-col space-y-4">
      <h1 className="text-3xl font-bold text-center mb-4">
        Be a New Royale! 👑
      </h1>
      <div className="flex items-center justify-center">
        <button
          onClick={handleImageClick}
          className="focus:outline-0 hover:scale-110 transition-all duration-300"
          type="button"
        >
          {inputs.avatarUrl ? (
            <img
              src={inputs.avatarUrl}
              alt="User Avatar"
              className="border rounded-full w-32 h-32 cursor-pointer transition-all duration-200 ease-in-out"
            />
          ) : (
            <img
              src="/user_icon.png"
              alt="Default User Icon"
              className="border rounded-full w-32 h-32 cursor-pointer transition-all duration-200 ease-in-out"
            />
          )}
        </button>
      </div>
      <form
        onSubmit={handleSubmit}
        className="flex flex-col space-y-2 items-center border-b-2 border-white border-opacity-20 p-4"
      >
        <InputField
          name="name"
          value={inputs.name}
          placeholder="👤 Username"
          handleChange={handleChange}
        />
        <InputField
          name="email"
          value={inputs.email}
          placeholder="📧 Email"
          handleChange={handleChange}
        />
        <InputField
          name="password"
          value={inputs.password}
          placeholder="🗝️ Password"
          type="password"
          handleChange={handleChange}
        />
        <ScaledButton disabled={isSubmitting} type="submit" text="Sign Up" />

        <div className="h-4">
          {(isSubmitting && <p className="animate-pulse">Loading...</p>) || (
            <p className="text-red-600 font-bold">{error}</p>
          )}
        </div>
      </form>
    </div>
  );
}
