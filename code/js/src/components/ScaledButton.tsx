const colors = {
  yellow: "text-black bg-gr-yellow hover:text-gr-yellow hover:border-gr-yellow",
  green: "text-black bg-green-600 hover:text-green-600 hover:border-green-600",
  black: "bg-black hover:text-black hover:border-black",
};

export default function ScaledButton({
  onClick,
  disabled,
  type,
  text,
  color,
}: {
  onClick?: () => void;
  disabled?: boolean;
  type?: "submit" | "reset" | "button";
  text: string;
  color?: "yellow" | "green" | "black";
}) {
  return (
    <button
      onClick={onClick}
      type={type || "button"}
      disabled={disabled || false}
      className={
        `px-4 py-2 rounded-3xl font-bold border-2 shadow-inner shadow-white hover:shadow-none hover:bg-transparent hover:scale-105 transition-all duration-100 ` +
        (color ? colors[color] : colors.yellow)
      }
    >
      {text}
    </button>
  );
}
