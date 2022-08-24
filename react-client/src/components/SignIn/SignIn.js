import React from 'react';
import './SignIn.scss';
import image from './../../assets/images/benz_sign_in.jpg';
import { TextField, Button } from '@mui/material';
import { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import { setUserDetails } from '../../store/userSlice';

export default function SignIn() {
  const userDetails = useSelector((state) => state.user.userDetails);
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
    if (notValid) return;
    const response = await axios.post('/users/login', {
      username: emailAddress.value,
      password: password.value,
    });
    console.log(response);
    saveJwtCookie(response);
    dispatch(setUserDetails(response.data.user));
    navigate('/');
  };
  const saveJwtCookie = (response) => {
    if (response.status === 200) {
      localStorage.setItem('token', response.data.jwtToken);
    }
  };
  const navigate = useNavigate();
  useEffect(() => {
    const checkAlreadyLoggedIn = async () => {
      console.log(userDetails);
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
            type='text'
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
          <Button
            variant='contained'
            color='primary'
            size='large'
            fullWidth={true}
            onClick={loginUser}
          >
            Sign In
          </Button>
        </div>
      </div>
      <div className='side right'>
        <img src={image} alt='sign_up_image' className='image_right' />
      </div>
    </div>
  );
}
