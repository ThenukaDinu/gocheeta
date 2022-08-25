import React, { useEffect } from 'react';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { LocalizationProvider } from '@mui/x-date-pickers';
import './SignUp.scss';
import image from './../../assets/images/toyota_sign_up.jpg';
import { DesktopDatePicker } from '@mui/x-date-pickers/DesktopDatePicker';
import { TextField } from '@mui/material';
import LoadingButton from '@mui/lab/LoadingButton';
import { useState } from 'react';
import { format } from 'date-fns';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import validator from 'validator';
import { toast } from 'react-toastify';

export default function SignUp({ children }) {
  const navigate = useNavigate();
  const invalidDetails = () =>
    toast.error('Invalid details provided!, Please validate and try again.');
  const otherError = () =>
    toast.error('Something went wrong, Please contact support service.');
  const [loading, setLoading] = useState(false);
  const [firstName, setFirstName] = useState({ error: false, value: '' });
  const [lastName, setLastName] = useState({ error: false, value: '' });
  const [emailAddress, setEmailAddress] = useState({ error: false, value: '' });
  const [password, setPassword] = useState({ error: false, value: '' });
  const [dateOfBirth, setDateOfBirth] = useState({});
  const [address, setAddress] = useState({ value: '' });
  const [mobile, setMobile] = useState({ value: '' });
  const onFirstNameChange = (event) => {
    setFirstName({ ...firstName, value: event.target.value });
  };
  const onLastNameChange = (event) => {
    setLastName({ ...lastName, value: event.target.value });
  };
  const onEmailAddressChange = (event) => {
    setEmailAddress({ ...emailAddress, value: event.target.value });
  };
  const onPasswordChange = (event) => {
    setPassword({ ...password, value: event.target.value });
  };
  const onDateOfBirthChange = (newValue) => {
    setDateOfBirth({ ...dateOfBirth, value: newValue });
  };
  const onAddressChange = (event) => {
    setAddress({ ...address, value: event.target.value });
  };
  const onMobileChange = (event) => {
    setMobile({ ...mobile, value: event.target.value });
  };
  const registerUser = async () => {
    setLoading(true);
    setFirstName({ ...firstName, error: false, helperText: '' });
    setLastName({ ...lastName, error: false, helperText: '' });
    setEmailAddress({ ...emailAddress, error: false, helperText: '' });
    setPassword({ ...password, error: false, helperText: '' });
    let notValid = false;
    if (validator.isEmpty(firstName.value)) {
      setFirstName({
        ...firstName,
        error: true,
        helperText: 'First name is required',
      });
      notValid = true;
    }
    if (validator.isEmpty(lastName.value)) {
      setLastName({
        ...lastName,
        error: true,
        helperText: 'Last name is required',
      });
      notValid = true;
    }
    if (validator.isEmpty(emailAddress.value)) {
      setEmailAddress({
        ...emailAddress,
        error: true,
        helperText: 'Email address is required',
      });
      notValid = true;
    }
    if (emailAddress.value && !validator.isEmail(emailAddress.value)) {
      setEmailAddress({
        ...emailAddress,
        error: true,
        helperText: 'Please enter a valid email address',
      });
      notValid = true;
    }
    if (validator.isEmpty(password.value)) {
      setPassword({
        ...password,
        error: true,
        helperText: 'Password is required',
      });
      notValid = true;
    }

    if (notValid) {
      setLoading(false);
      return;
    }

    let response = null;
    try {
      response = await axios.post('/users/register', {
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
      setLoading(false);
    } catch (error) {
      setLoading(false);
      console.error(error);
      if (error.response.status === 400) {
        invalidDetails();
        return;
      }
      otherError();
    }

    if (response && response.status === 201) {
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
              helperText={firstName.helperText}
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
              helperText={lastName.helperText}
            />
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
            <TextField
              id='mobile'
              name='mobile'
              type='number'
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
          <LoadingButton
            variant='contained'
            color='primary'
            size='large'
            loading={loading}
            fullWidth={true}
            onClick={registerUser}
          >
            Sign Up
          </LoadingButton>
        </div>
      </div>
      <div className='side right'>
        <img src={image} alt='sign_up_image' className='image_right' />
      </div>
    </div>
  );
}
