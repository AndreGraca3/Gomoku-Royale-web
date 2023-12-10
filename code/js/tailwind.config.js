/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      gridTemplateColumns: {
        // Simple 15 column grid
        15: "repeat(15, minmax(0, 1fr))",
        // Simple 19 column grid
        19: "repeat(19, minmax(0, 1fr))",
      },
      spacing: {
        120: "30rem",
      },
      colors: {
        "theme-color": "var(--theme-color)",
        "dark-theme-color": "var(--dark-theme-color)",
        "gr-yellow": "var(--gr-yellow)",
        "dark-green": "rgb(12, 78, 1)",
      },
      animation: {
        "heart-beat": "heart-beat 2s infinite",
        "pop-up": "pop-up 0.2s forwards",
        shine: "shine 3s infinite alternate linear",
        "pulse-scale": "pulse-scale 400ms alternate infinite",
      },
      keyframes: {
        "pulse-scale": {
          "0%": {
            transform: "scale(1)",
          },
          "100%": {
            transform: "scale(1.3)",
          },
        },
        shine: {
          "0%": {
            "box-shadow": "0 0 5px rgba(255, 255, 255, 0.5)",
          },
          "50%": {
            "box-shadow": "0 0 8px rgba(255, 255, 255, 0.9)",
          },
          "100%": {
            "box-shadow": "0 0 5px rgba(255, 255, 255, 0.5)",
          },
        },
        "heart-beat": {
          "0%": { transform: "scale(1)" },
          "10%": { transform: "scale(1.2)" },
          "20%": { transform: "scale(1.1)" },
          "30%": { transform: "scale(1.2)" },
          "50%": { transform: "scale(1)" },
          "100%": { transform: "scale(1)" },
        },
        "pop-up": {
          "0%": {
            opacity: "0",
            transform: "scale(0%)",
          },
          "100%": {
            opacity: "1",
            transform: "scale(100%)",
          },
        },
      },
    },
  },
  plugins: [],
};
