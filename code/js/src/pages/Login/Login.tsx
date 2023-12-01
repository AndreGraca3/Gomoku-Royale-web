import { useState } from "react";
import { Navigate, useLocation } from "react-router-dom";
import InputField from "../../components/InputField";
import ScaledButton from "../../components/ScaledButton";
import userData from "../../data/userData";
import { useLogin } from "../../hooks/Auth/AuthnStatus";

export function Login() {
  const [inputs, setInputs] = useState({
    email: "",
    password: "",
  });
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [error, setError] = useState(undefined);
  const [redirect, setRedirect] = useState(undefined);
  const setLoggedIn = useLogin();
  const location = useLocation();

  if (redirect) {
    return (
      <Navigate
        to={location.state?.source?.pathname || redirect}
        replace={true}
      />
    );
  }

  function handleChange(ev: React.FormEvent<HTMLInputElement>) {
    const name = ev.currentTarget.name;
    setInputs({ ...inputs, [name]: ev.currentTarget.value });
    setError(undefined);
  }

  function handleSubmit(ev: React.FormEvent<HTMLFormElement>) {
    ev.preventDefault();
    setIsSubmitting(true);
    const email = inputs.email;
    const password = inputs.password;
    userData
      .login(email, password)
      .then(() => {
        setIsSubmitting(false);
        setLoggedIn(true);
        userData.getUserHome().then((res) => {
          
        });
        localStorage.setItem("loggedIn", "true");
        console.log("Logged in!");
        // setRedirect("/me");
      })
      .catch((error) => {
        setIsSubmitting(false);
        setError(error.detail);
      });
  }

  return (
    <div className="flex flex-col space-y-4">
      <h1 className="text-3xl font-bold text-center">Welcome back! 👋</h1>
      <form
        onSubmit={handleSubmit}
        className="flex flex-col space-y-2 items-center border-b-2 border-white border-opacity-20 p-4"
      >
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
        <ScaledButton disabled={isSubmitting} type="submit" text="Login" />

        <div className="h-4">
          {(isSubmitting && <p className="animate-pulse">Loading...</p>) || (
            <p className="text-red-600 font-bold">{error}</p>
          )}
        </div>
      </form>
      <ScaledButton
        onClick={() => setRedirect("/signup")}
        text="Sign Up"
        color="green"
      />
    </div>
  );
}
