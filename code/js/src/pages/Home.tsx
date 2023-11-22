import { Link } from "react-router-dom";

export default function Home() {
  return (
    <div className="items-center text-center align-middle">
      <h1 className="text-red-600 text-3xl font-bold underline">Home Page using TAILWIND CSS!!</h1>
      <Link to="/about">About</Link>
    </div>
  );
}