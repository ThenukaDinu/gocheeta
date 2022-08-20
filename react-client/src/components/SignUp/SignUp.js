import React from 'react';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers';
import './SignUp.scss';
import image from './../../assets/images/toyota_sign_up.jpg';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { TextField, Button } from '@mui/material';
import { useState } from 'react';
import { format } from 'date-fns';
import axios from 'axios';

export default function SignUp({ children }) {
  const [firstName, setFirstName] = useState('');
  const onFirstNameChange = (event) => {
    setFirstName(event.target.value);
  };
  const [lastName, setLastName] = useState('');
  const onLastNameChange = (event) => {
    setLastName(event.target.value);
  };
  const [emailAddress, setEmailAddress] = useState('');
  const onEmailAddressChange = (event) => {
    setEmailAddress(event.target.value);
  };
  const [password, setPassword] = useState('');
  const onPasswordChange = (event) => {
    setPassword(event.target.value);
  };
  const [dateOfBirth, setDateOfBirth] = useState(null);
  const onDateOfBirthChange = (newValue) => {
    console.log(format(newValue, 'yyyy-MM-dd'));
    setDateOfBirth(newValue);
  };
  const [address, setAddress] = useState('');
  const onAddressChange = (event) => {
    setAddress(event.target.value);
  };
  const [mobile, setMobile] = useState('');
  const onMobileChange = (event) => {
    setMobile(event.target.value);
  };
  const registerUser = async () => {
    const response = await axios.post('/users/register', {
      username: emailAddress,
      password: password,
      mobile: mobile,
      firstName: firstName,
      lastName: lastName,
      dateOfBirth: dateOfBirth ? format(dateOfBirth, 'yyyy-MM-dd') : null,
      address: address,
    });
    console.log(response);
  };

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
            />
            <TextField
              id='last_name'
              name='last_name'
              type='text'
              label='Last Name'
              fullWidth={true}
              onChange={onLastNameChange}
              required
            />
            <TextField
              id='email_address'
              name='email_address'
              type='text'
              label='Email Address'
              fullWidth={true}
              onChange={onEmailAddressChange}
              required
            />
            <TextField
              id='password'
              name='password'
              type='password'
              label='Password'
              fullWidth={true}
              onChange={onPasswordChange}
              InputProps={{
                autocomplete: 'new-password',
              }}
              required
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
              value={dateOfBirth}
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
