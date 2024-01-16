export default function Avatar({
  url,
  size = "small",
  onClick,
}: {
  url?: string;
  size?: "small" | "medium" | "large";
  onClick?: () => void;
}) {
  const sizes = {
    small: "w-10 h-10",
    medium: "w-20 h-20",
    large: "w-40 h-40",
  };
  return (
    <img
      src={url || "/user_icon.png"}
      onClick={onClick}
      className={`border rounded-full ${
        onClick ? "cursor-pointer" : ""
      } justify-center transition-all duration-200 hover:scale-105 ease-in-out object-cover ${
        sizes[size]
      }`}
    />
  );
}
