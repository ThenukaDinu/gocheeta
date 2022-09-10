import Navbar from '../Navbar/Navbar';
import './App.scss';
import { createTheme, ThemeProvider } from '@mui/material/styles';
import Home from '../Home/Home';
import SignIn from '../SignIn/SignIn';
import { Route, Routes, useLocation, useNavigate } from 'react-router-dom';
import SignUp from '../SignUp/SignUp';
import Driver from '../Driver/Driver';
import { useMediaQuery } from '@mui/material';
import React, { useEffect } from 'react';
import { ToastContainer } from 'react-toastify';
// import 'react-toastify/dist/ReactToastify.css';
import 'react-toastify/dist/ReactToastify.min.css';
import { useSelector } from 'react-redux';
import Vehicle from '../Vehicle/Vehicle';

function App() {
  const userDetails = useSelector((state) => state.user.userDetails);
  const navigate = useNavigate();
  const location = useLocation();
  useEffect(() => {
    const guestPaths = ['/signUp', '/'];
    if (!userDetails && !guestPaths.includes(location.pathname)) {
      navigate('/signIn');
    }
  }, [location.pathname, userDetails, navigate]);
  const prefersDarkMode = useMediaQuery('(prefers-color-scheme: dark)');
  const theme = React.useMemo(
    () =>
      createTheme({
        palette: {
          mode: prefersDarkMode ? 'dark' : 'light',
        },
      }),
    [prefersDarkMode]
  );
  return (
    <div className='App'>
      <ThemeProvider theme={theme}>
        <Navbar />
        <ToastContainer
          position='top-right'
          autoClose={5000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick
          rtl={false}
          pauseOnFocusLoss={false}
          draggable
          pauseOnHover
          theme='dark'
        />
        <div className='body-container'>
          <Routes>
            <Route path='/' element={<Home />} />
            <Route path='/signIn' element={<SignIn />} />
            <Route path='/signUp' element={<SignUp />} />
            <Route path='/drivers' element={<Driver />} />
            <Route path='/vehicles' element={<Vehicle />} />
          </Routes>
        </div>
      </ThemeProvider>
    </div>
  );
}

export default App;
