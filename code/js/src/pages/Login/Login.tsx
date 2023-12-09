import { useReducer } from "react";
import { useNavigate } from "react-router-dom";
import InputField from "../../components/InputField";
import ScaledButton from "../../components/ScaledButton";
import userData from "../../data/userData";
import { useLogin } from "../../hooks/Auth/AuthnStatus";

type State =
  | { type: "valid"; inputs: { email: string; password: string } }
  | { type: "submitting"; inputs: { email: string; password: string } }
  | { type: "success"; inputs: { email: string; password: string } }
  | { type: "error"; inputs: { email: ""; password: "" }; error: string };

function reducer(state: State, action: any): State {
  switch (action.type) {
    case "valid":
      return { type: "valid", inputs: action.inputs };
    case "submitting":
      return { type: "submitting", inputs: action.inputs };
    case "success":
      return { type: "success", inputs: action.inputs };
    case "error":
      return { type: "error", inputs: action.inputs, error: action.error };
  }
}

export function Login() {
  const [state, dispatch] = useReducer(reducer, {
    type: "valid",
    inputs: { email: "", password: "" },
  });
  const setLoggedIn = useLogin();
  const navigate = useNavigate();

  function handleChange(ev: React.FormEvent<HTMLInputElement>) {
    const name = ev.currentTarget.name;
    const action = {
      type: state.type,
      inputs: {
        ...state.inputs,
        [name]: ev.currentTarget.value,
      },
    };
    dispatch(action);
  }

  function handleSubmit(ev: React.FormEvent<HTMLFormElement>) {
    ev.preventDefault();
    dispatch({ type: "submitting", inputs: state.inputs });
    userData
      .login(state.inputs.email, state.inputs.password)
      .then(() => {
        dispatch({ type: "success", inputs: state.inputs });
        setLoggedIn(true);
        setTimeout(() => {
          navigate("/me");
        }, 1000);
      })
      .catch((error) => {
        console.log(error);
        dispatch({ type: "error", inputs: state.inputs, error: error.detail });
      });
  }

  return (
    <div className="flex flex-col space-y-4 bg-dark-theme-color p-10 rounded-3xl">
      <h1 className="text-3xl font-bold text-center">Welcome back! 👋</h1>
      <form
        onSubmit={handleSubmit}
        className="flex flex-col space-y-4 items-center border-b-2 border-white border-opacity-20 p-2"
      >
        <InputField
          name="email"
          value={state.inputs.email}
          placeholder="📧 Email"
          handleChange={handleChange}
        />
        <InputField
          name="password"
          value={state.inputs.password}
          placeholder="🗝️ Password"
          type="password"
          handleChange={handleChange}
        />
        <ScaledButton
          disabled={state.type == "submitting"}
          type="submit"
          text="Login"
        />

        <div className="h-4 w-full flex justify-center items-center">
          {state.type === "submitting" && (
            <p className="animate-pulse">Loading...</p>
          )}
          {state.type === "error" && (
            <p className="text-red-600 font-bold">{state.error}</p>
          )}
          {state.type === "success" && (
            <p className="text-green-600 font-bold">Success!</p>
          )}
        </div>
      </form>
      <ScaledButton
        onClick={() => navigate("/signup")}
        text="Sign Up"
        color="green"
      />
    </div>
  );
}
