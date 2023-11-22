/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      colors: {
        "theme-color": "var(--theme-color)",
        "dark-theme-color": "var(--dark-theme-color)",
      }
    },
  },
  plugins: [],
};
