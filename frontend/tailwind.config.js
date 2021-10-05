module.exports = {
  purge: { content: ["./public/**/*.html", "./src/**/*.vue"] },
  darkMode: false, // or 'media' or 'class'
  theme: {
    extend: {
      fontSize: {
        'llg': '1.18rem'
      },
      container: {
        center: true,
      },
      maxWidth: {
        '750px': '750px',
      },
      minWidth: {
        '2000px': '2000px',
      },
      width: {
        '180px': '180px',
        '200px': '200px',
        '230px': '230px',
        '250px': '250px',
        '750px': '750px',
      },
      maxHeight: {
        '250px': '250px',
        '500px': '500px',
      },
      colors: {
        'background': '#ffffff',
        'mattBlack': '#161b23',
        'pointPurple': '#e3d8ff',
      },
    },
  },
  variants: {
    extend: {},
  },
  plugins: [],
};
