import Navbar from '../Navbar/Navbar';
import './App.scss';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Home from '../Home/Home';
import SignIn from '../SignIn/SignIn';
import { Route, Routes } from 'react-router-dom';
import SignUp from '../SignUp/SignUp';
import Driver from '../Driver/Driver';
import { useMediaQuery } from '@mui/material';
import React from 'react';

function App() {
  const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');
  const theme = React.useMemo(
    () =>
      createTheme({
        palette: {
          mode: prefersDarkMode ? 'dark' : 'light',
        },
      }),
    [prefersDarkMode],
  );
  return (
    <div className='App'>
      <ThemeProvider theme={theme}>
        <Navbar />
        <div className='body-container'>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/signIn' element={<SignIn />} />
            <Route path='/signUp' element={<SignUp />} />
            <Route path='/drivers' element={<Driver />} />
          </Routes>
        </div>
      </ThemeProvider>
    </div>
  );
}

export default App;
