/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {
      colors: {
        obsidian: '#080B12',
        midnight: '#0D1322',
        slateCard: '#131B2E',
        slateBorder: '#233252',
        cyanFocus: '#00E5FF',
        cyanLight: '#67E8F9',
        indigoMind: '#4F46E5',
        amberWisdom: '#F59E0B',
      }
    },
  },
  plugins: [],
}
