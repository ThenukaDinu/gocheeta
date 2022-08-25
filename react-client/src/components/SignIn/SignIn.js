import React from 'react';
import './SignIn.scss';
import image from './../../assets/images/benz_sign_in.jpg';
import { TextField } from '@mui/material';
import LoadingButton from '@mui/lab/LoadingButton';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { setUserDetails } from '../../store/userSlice';
import { toast } from 'react-toastify';

export default function SignIn() {
  const [loading, setLoading] = useState(false);
  const invalidCredentials = () =>
    toast.error('Invalid credentials!, Please try again.');
  const dispatch = useDispatch();

  const [emailAddress, setEmailAddress] = useState({
    error: false,
    value: null,
  });
  const onEmailAddressChange = (event) => {
    setEmailAddress({ ...emailAddress, value: event.target.value });
  };
  const [password, setPassword] = useState({
    error: false,
    value: null,
  });
  const onPasswordChange = (event) => {
    setPassword({ ...password, value: event.target.value });
  };

  const loginUser = async () => {
    setLoading(true);
    let notValid = false;
    setEmailAddress({ ...emailAddress, error: false, helperText: '' });
    setPassword({ ...password, error: false, helperText: '' });
    if (!emailAddress.value) {
      notValid = true;
      setEmailAddress({
        ...emailAddress,
        error: true,
        helperText: 'Email is required',
      });
    }
    if (!password.value) {
      notValid = true;
      setPassword({
        ...password,
        error: true,
        helperText: 'Password is required',
      });
    }
    if (notValid) {
      setLoading(false);
      return;
    }
    let response = null;
    try {
      response = await axios.post('/users/login', {
        username: emailAddress.value,
        password: password.value,
      });
      setLoading(false);
    } catch (error) {
      setLoading(false);
      if (error.response.status === 400) {
        invalidCredentials();
      }
      console.error(error.response);
    }
    if (!response) return;
    console.log(response);
    if (response.status === 200) {
      saveJwtCookie(response);
      dispatch(setUserDetails(response.data.user));
      navigate('/');
    }
  };
  const saveJwtCookie = (response) => {
    if (response.status === 200) {
      localStorage.setItem('token', response.data.jwtToken);
    }
  };
  const navigate = useNavigate();
  useEffect(() => {
    const checkAlreadyLoggedIn = async () => {
      if (!localStorage.getItem('token')) {
        return;
      }
      if (localStorage.getItem('token')) {
        const response = await axios.get('/secured');
        if (response.status === 200) {
          navigate('/');
        }
      }
    };
    checkAlreadyLoggedIn();
    // eslint-disable-next-line
  }, []);

  return (
    <div className='SignIn'>
      <div className='side left'>
        <h2 className='title'>Sign In</h2>
        <div className='inputs'>
          <TextField
            id='email_address'
            name='email_address'
            type='email'
            label='Email Address'
            fullWidth={true}
            onChange={onEmailAddressChange}
            required
            error={emailAddress.error}
            helperText={emailAddress.helperText}
            autoFocus={true}
          />
          <TextField
            id='password'
            name='password'
            type='password'
            label='Password'
            fullWidth={true}
            onChange={onPasswordChange}
            required
            error={password.error}
            helperText={password.helperText}
          />
        </div>
        <div className='buttons'>
          <LoadingButton
            variant='contained'
            color='primary'
            size='large'
            fullWidth={true}
            onClick={loginUser}
            loading={loading}
          >
            Sign In
          </LoadingButton>
        </div>
      </div>
      <div className='side right'>
        <img src={image} alt='sign_up_image' className='image_right' />
      </div>
    </div>
  );
}
