import Navbar from '../Navbar/Navbar';
import './App.scss';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Home from '../Home/Home';
import SignIn from '../SignIn/SignIn';
import { Route, Routes } from 'react-router-dom';
import SignUp from '../SignUp/SignUp';

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
        <div className='body-container'>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='signIn' element={<SignIn />} />
            <Route path='signUp' element={<SignUp />} />
          </Routes>
        </div>
      </ThemeProvider>
    </div>
  );
}

export default App;
