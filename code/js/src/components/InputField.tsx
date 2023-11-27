export default function InputField({
  name,
  value,
  placeholder,
  type,
  handleChange,
}: {
  name?: string;
  value: string;
  placeholder?: string;
  type?: string;
  handleChange: (ev: React.FormEvent<HTMLInputElement>) => void;
}) {
  return (
    <input
      className="bg-dark-theme-color border focus:border-gr-yellow focus:shadow-gr-yellow focus:shadow-sm p-2 rounded-xl focus:outline-none"
      name={name || "input-field"}
      type={type || "text"}
      placeholder={placeholder}
      value={value}
      onChange={handleChange}
    />
  );
}
