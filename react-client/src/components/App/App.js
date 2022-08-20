import Navbar from '../Navbar/Navbar';
import './App.scss';

import { createTheme, ThemeProvider } from '@mui/material/styles';

const theme = createTheme({
  status: {
    danger: '#e53e3e',
  },
  palette: {
    custom: {
      main: '#ff7800',
      light: '#ffa726',
      dark: '#0087FF',
      contrastText: '#fff',
    },
  },
});

function App() {
  return (
    <div className='App'>
      <ThemeProvider theme={theme}>
        <Navbar />
      </ThemeProvider>
    </div>
  );
}

export default App;
