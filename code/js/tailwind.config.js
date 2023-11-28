/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{js,jsx,ts,tsx}"],
  theme: {
    extend: {
      gridTemplateColumns: {
        // Simple 15 column grid
        '15': 'repeat(15, minmax(0, 1fr))',
        // Simple 19 column grid
        '19': 'repeat(19, minmax(0, 1fr))'
      },
      spacing: {
        '120': '30rem',
      }
    },
  },
  plugins: [],
};
