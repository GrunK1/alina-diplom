/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}", // ищет классы в src/
    "./public/index.html", // и в HTML-шаблоне
  ],
  theme: {
    extend: {
      colors: {
        primary: "#1766db",
        secondary: "#3f8cff33",
        background: "#f6f8fb",
        "light-gray": "#efefef",
        "dark-gray": "#272727",
        "text-gray": "#b1b1b1",
      },
      fontFamily: {
        inter: ['"Inter"', "sans-serif"],
      },
      borderRadius: {
        xl: "20px",
        "2xl": "25px",
      },
    },
  },
  plugins: [],
};
