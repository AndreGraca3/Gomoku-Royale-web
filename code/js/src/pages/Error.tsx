import { Loading } from "../components/Loading";

export default function Error() {
  return (
    <div className="flex justify-center items-center h-screen bg-theme-color">
      <div className="bg-dark-theme-color p-8 rounded shadow-md max-w-md w-full space-y-2">
        <div className="flex justify-center items-center">
          <h1 className="text-3xl text-center text-red-500 font-bold">
            500 Internal Server Error
          </h1>
        </div>
        <p className="text-center">
          Oops! Something went wrong on our end. We are working to fix the
          issue.
        </p>
        <Loading />
      </div>
    </div>
  );
}
