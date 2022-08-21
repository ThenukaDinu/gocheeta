import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './components/App/App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import axios from 'axios';

axios.defaults.baseURL = process.env.REACT_APP_BASE_URL;
axios.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (config.url === '/users/login' || config.url === '/users/register') {
      // delete config.headers.common.authorization;
      config.headers.common.Authorization = `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWRtaW4xQGFkbWluLmVkdSIsInJvbGVzIjoiUk9MRV9BRE1JsssST0xFX1VTRVIiLCJpYXQiOjE2NjExMDMwODUsImV4cCI6MTY2MTEyMTA4NX0.C8teZn6x0oGZfmeadnSmMmgf0iPeIdiV8Q6bCiyY-vY`;
    } else if (!token) {
      // delete config.headers.common.authorization;
      config.headers.common.Authorization = `Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0YWRtaW4xQGFkbWluLmVkdSIsInJvbGVzIjoiUk9MRV9BRE1JsssST0xFX1VTRVIiLCJpYXQiOjE2NjExMDMwODUsImV4cCI6MTY2MTEyMTA4NX0.C8teZn6x0oGZfmeadnSmMmgf0iPeIdiV8Q6bCiyY-vY`;
    } else {
      config.headers.common.Authorization = `Bearer ${token}`;
      console.log(config.headers);
    }
    return config;
  },
  (error) => {
    // return Promise.reject(error);
    return Promise.reject(error);
  }
);
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <BrowserRouter>
      <App />
    </BrowserRouter>
  </React.StrictMode>
);

reportWebVitals();
