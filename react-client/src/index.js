import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './components/App/App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';
import { persistor, store } from './store/store';
import { PersistGate } from 'redux-persist/integration/react';
import { Provider } from 'react-redux';

axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;

// add jwt barer token for every request.
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (config.url === '/users/login' || config.url === '/users/register') {
      delete config.headers.common.authorization;
    } else if (!token) {
      delete config.headers.common.authorization;
    } else {
      config.headers.common.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

const guestPaths = ['/signIn', '/', '/signUp'];
// Redirect to login when unauthenticated.
axios.interceptors.response.use(
  (response) => response,
  (error) => {
    if (
      error.response.status === 401 &&
      !guestPaths.includes(window.location.pathname)
    ) {
      window.location = '/signIn';
    } else if (
      error.response.status === 401 &&
      guestPaths.includes(window.location.pathname)
    ) {
      return Promise.reject(error);
    }
    return Promise.reject(error);
  }
);

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <BrowserRouter>
    <Provider store={store}>
      <PersistGate loading={null} persistor={persistor}>
        <App />
      </PersistGate>
    </Provider>
  </BrowserRouter>
);

reportWebVitals();
