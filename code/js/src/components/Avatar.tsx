export default function Avatar({ url }: { url?: string }) {
  return (
    <img
      src={url || "/user_icon.png"}
      className="border rounded-full w-10 h-10 cursor-pointer justify-center transition-all duration-200 ease-in-out object-cover"
    />
  );
}
