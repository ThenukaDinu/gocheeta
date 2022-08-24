import React, { useEffect } from 'react';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers';
import './SignUp.scss';
import image from './../../assets/images/toyota_sign_up.jpg';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { TextField, Button } from '@mui/material';
import { useState } from 'react';
import { format } from 'date-fns';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

export default function SignUp({ children }) {
  const navigate = useNavigate();
  const [firstName, setFirstName] = useState({ error: false });
  const onFirstNameChange = (event) => {
    setFirstName({ ...firstName, value: event.target.value });
  };
  const [lastName, setLastName] = useState({ error: false });
  const onLastNameChange = (event) => {
    setLastName({ ...lastName, value: event.target.value });
  };
  const [emailAddress, setEmailAddress] = useState({ error: false });
  const onEmailAddressChange = (event) => {
    setEmailAddress({ ...emailAddress, value: event.target.value });
  };
  const [password, setPassword] = useState({ error: false });
  const onPasswordChange = (event) => {
    setPassword({ ...password, value: event.target.value });
  };
  const [dateOfBirth, setDateOfBirth] = useState({});
  const onDateOfBirthChange = (newValue) => {
    console.log(format(newValue, 'yyyy-MM-dd'));
    setDateOfBirth({ ...dateOfBirth, value: newValue });
  };
  const [address, setAddress] = useState({});
  const onAddressChange = (event) => {
    setAddress({ ...address, value: event.target.value });
  };
  const [mobile, setMobile] = useState({});
  const onMobileChange = (event) => {
    setMobile({ ...mobile, value: event.target.value });
  };
  const registerUser = async () => {
    setFirstName({ ...firstName, error: false });
    setLastName({ ...lastName, error: false });
    setEmailAddress({ ...emailAddress, error: false });
    setPassword({ ...password, error: false });
    if (!firstName.value) setFirstName({ ...firstName, error: true });
    if (!lastName.value) setLastName({ ...lastName, error: true });
    if (!emailAddress.value) setEmailAddress({ ...emailAddress, error: true });
    if (!password.value) setPassword({ ...password, error: true });
    if (
      !firstName.value ||
      !lastName.value ||
      !emailAddress.value ||
      !password.value
    )
      return;
    const response = await axios.post('/users/register', {
      username: emailAddress.value,
      password: password.value,
      mobile: mobile.value,
      firstName: firstName.value,
      lastName: lastName.value,
      dateOfBirth:
        dateOfBirth && dateOfBirth.value
          ? format(dateOfBirth.value, 'yyyy-MM-dd')
          : null,
      address: address.value,
    });
    if (response.status === 201) {
      navigate('/signIn');
    }
  };
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
    <div className='SignUp'>
      <div className='side left'>
        <h2 className='title'>Sign Up</h2>
        <LocalizationProvider dateAdapter={AdapterDateFns}>
          <div className='inputs'>
            <TextField
              id='first_name'
              name='first_name'
              type='text'
              label='First Name'
              fullWidth={true}
              onChange={onFirstNameChange}
              autoFocus={true}
              required
              error={firstName.error}
            />
            <TextField
              id='last_name'
              name='last_name'
              type='text'
              label='Last Name'
              fullWidth={true}
              onChange={onLastNameChange}
              required
              error={lastName.error}
            />
            <TextField
              id='email_address'
              name='email_address'
              type='text'
              label='Email Address'
              fullWidth={true}
              onChange={onEmailAddressChange}
              required
              error={emailAddress.error}
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
            />
            <TextField
              id='mobile'
              name='mobile'
              type='text'
              label='Mobile No'
              fullWidth={true}
              onChange={onMobileChange}
            />
            <TextField
              id='address'
              name='address'
              type='text'
              label='Address'
              fullWidth={true}
              onChange={onAddressChange}
              multiline
              maxRows={4}
            />
            <DesktopDatePicker
              label='Date Of Birth'
              onChange={onDateOfBirthChange}
              value={dateOfBirth.value}
              inputFormat='yyyy/MM/dd'
              renderInput={(params) => (
                <TextField
                  {...params}
                  fullWidth={true}
                  name='date_of_birth_field'
                  id='date_of_birth_field'
                />
              )}
            />
          </div>
        </LocalizationProvider>
        <div className='buttons'>
          <Button
            variant='contained'
            color='primary'
            size='large'
            fullWidth={true}
            onClick={registerUser}
          >
            Sign Up
          </Button>
        </div>
      </div>
      <div className='side right'>
        <img src={image} alt='sign_up_image' className='image_right' />
      </div>
    </div>
  );
}
