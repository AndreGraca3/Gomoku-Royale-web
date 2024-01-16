import { useSound } from "../hooks/Sound/Sound";
import { Icons } from "./Icons";

export default function InputField({
  name,
  value,
  placeholder,
  type,
  handleChange,
  isSubmittable = false,
}: {
  name?: string;
  value: string;
  placeholder?: string;
  type?: string;
  handleChange: (ev: React.FormEvent<HTMLInputElement>) => void;
  isSubmittable?: boolean;
}) {
  const [sounds, playSound] = useSound();
  return (
    <div className="flex items-center gap-x-1">
      <input
        className="bg-dark-theme-color border focus:border-gr-yellow focus:shadow-gr-yellow focus:shadow-sm p-2 rounded-xl focus:outline-none"
        name={name || "input-field"}
        type={type || "text"}
        placeholder={placeholder}
        value={value}
        onChange={handleChange}
      />
      {isSubmittable && (
        <button
          type="submit"
          onMouseEnter={() => playSound(sounds.ui_highlight)}
          onClick={() => playSound(sounds.ui_click_1)}
          className="h-full w-full border rounded-xl p-2 bg-dark-green hover:bg-green-600 hover:scale-105 transition-all text-xs rotate-180"
        >
          <Icons.arrowLeft />
        </button>
      )}
    </div>
  );
}
