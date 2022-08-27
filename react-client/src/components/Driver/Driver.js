import { Fab, Grid, TextField } from '@mui/material';
import React from 'react';
import './Driver.scss';
import Box from '@mui/material/Box';
import { useState } from 'react';
import { DesktopDatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import AddIcon from '@mui/icons-material/Add';
import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';
import { LoadingButton } from '@mui/lab';
import { toast } from 'react-toastify';
import validator from 'validator';
import axios from 'axios';
import { format } from 'date-fns';
import { useEffect } from 'react';
import DriverCard from './DriverCard';

export default function Driver() {
  const invalidDetails = () =>
    toast.error('Invalid details provided!, Please validate and try again.');
  const otherError = () =>
    toast.error('Something went wrong, Please contact support service.');

  const [drivers, setDrivers] = useState([]);
  const [showAddIcon, setShowAddIcon] = useState(true);
  const [emailAddress, setEmailAddress] = useState({
    error: false,
    value: null,
  });
  const [password, setPassword] = useState({ error: false, value: null });
  const [mobile, setMobile] = useState({ error: false, value: null });
  const [firstName, setFirstName] = useState({ error: false, value: null });
  const [lastName, setLastName] = useState({ error: false, value: null });
  const [dateOfBirth, setDateOfBirth] = useState({ error: false, value: null });
  const [address, setAddress] = useState({ error: false, value: null });
  const [nic, setNic] = useState({ error: false, value: null });
  const [backupMobile, setBackupMobile] = useState({
    error: false,
    value: null,
  });
  const [loading, setLoading] = useState(false);
  const registerDriver = async () => {
    setLoading(true);
    setFirstName({ ...firstName, error: false, helperText: '' });
    setLastName({ ...lastName, error: false, helperText: '' });
    setEmailAddress({ ...emailAddress, error: false, helperText: '' });
    setPassword({ ...password, error: false, helperText: '' });
    setDateOfBirth({
      ...dateOfBirth,
      error: false,
      helperText: '',
    });
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
    if (
      !validator.isEmpty(emailAddress.value) &&
      !validator.isEmail(emailAddress.value)
    ) {
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
    if (validator.isEmpty(dateOfBirth.value)) {
      setDateOfBirth({
        ...dateOfBirth,
        error: true,
        helperText: 'Date of Birth is required',
      });
    }

    if (notValid) {
      setLoading(false);
      return;
    }

    let response = null;
    try {
      response = await axios.post('/drivers', {
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
        nic: nic.value,
        backupMobile: backupMobile.value,
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
    if (response && (response.status === 201 || response.status === 200)) {
      setDrivers((drivers) => [...drivers, response.data]);
    }
  };
  const renderDriversList = () => {
    return (
      <Grid
        container
        className={`drivers-list ${showAddIcon ? '' : 'showScroll'}`}
        spacing={3}
        padding={3}
        marginTop={showAddIcon ? -2 : 3.5}
      >
        {drivers.map((d) => {
          return <DriverCard key={d.userId} driver={d}></DriverCard>;
        })}
      </Grid>
    );
  };
  useEffect(() => {
    const getAllDrivers = async () => {
      try {
        const response = await axios.get('/drivers');
        setDrivers(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    getAllDrivers();
  }, []);
  return (
    <div className='Driver'>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={2}>
          <Grid item md={true} className='left'>
            {showAddIcon === true ? (
              <div className='new-driver-icon'>
                <Fab
                  sx={{ marginLeft: 15 }}
                  className='icon'
                  color='primary'
                  aria-label='add'
                  size='small'
                  onClick={() => {
                    setShowAddIcon(!showAddIcon);
                  }}
                >
                  <AddIcon fontSize='medium' />
                </Fab>
              </div>
            ) : (
              <></>
            )}
            {renderDriversList()}
          </Grid>
          {showAddIcon === true ? (
            <></>
          ) : (
            <Grid md={3.6} className='right' marginTop={2}>
              <Grid
                spacing={2}
                container
                justifyContent={'flex-end'}
                align
                s={'center'}
              >
                <Grid item md={5}>
                  <h3>Add New Driver</h3>
                </Grid>
                <Grid item md={3} justifySelf='flex-end'>
                  <Fab
                    className='icon'
                    color='primary'
                    aria-label='add'
                    size='small'
                    onClick={() => {
                      setShowAddIcon(!showAddIcon);
                    }}
                  >
                    <CloseOutlinedIcon fontSize='medium' />
                  </Fab>
                </Grid>
              </Grid>
              <div className='fields'>
                <TextField
                  name='email_address'
                  type='email'
                  label='Email Address'
                  fullWidth={true}
                  onChange={(e) => {
                    setEmailAddress({ ...emailAddress, value: e.target.value });
                  }}
                  required
                  error={emailAddress.error}
                  helperText={emailAddress.helperText}
                  autoFocus={true}
                />
                <TextField
                  name='password'
                  type='password'
                  label='Password'
                  fullWidth={true}
                  onChange={(e) => {
                    setPassword({ ...password, value: e.target.value });
                  }}
                  required
                  error={password.error}
                  helperText={password.helperText}
                />
                <TextField
                  name='mobile'
                  type='text'
                  label='Mobile'
                  fullWidth={true}
                  onChange={(e) => {
                    setMobile({ ...mobile, value: e.target.value });
                  }}
                  required
                  error={mobile.error}
                  helperText={mobile.helperText}
                />
                <TextField
                  name='first_name'
                  type='text'
                  label='First Name'
                  fullWidth={true}
                  onChange={(e) => {
                    setFirstName({ ...firstName, value: e.target.value });
                  }}
                  required
                  error={firstName.error}
                  helperText={firstName.helperText}
                />
                <TextField
                  name='last_name'
                  type='text'
                  label='Last Name'
                  fullWidth={true}
                  onChange={(e) => {
                    setLastName({ ...lastName, value: e.target.value });
                  }}
                  required
                  error={lastName.error}
                  helperText={lastName.helperText}
                />
                <LocalizationProvider dateAdapter={AdapterDateFns}>
                  <DesktopDatePicker
                    label='Date Of Birth'
                    onChange={(val) =>
                      setDateOfBirth({
                        ...dateOfBirth,
                        error: true,
                        helperText: 'Date of Birth is required',
                      })
                    }
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
                </LocalizationProvider>
                <TextField
                  name='address'
                  type='text'
                  label='Address'
                  fullWidth={true}
                  onChange={(e) => {
                    setAddress({ ...address, value: e.target.value });
                  }}
                  required
                  error={address.error}
                  helperText={address.helperText}
                  multiline
                  maxRows={4}
                />
                <TextField
                  name='nic'
                  type='text'
                  label='NIC'
                  fullWidth={true}
                  onChange={(e) => {
                    setNic({ ...nic, value: e.target.value });
                  }}
                  required
                  error={nic.error}
                  helperText={nic.helperText}
                  multiline
                  maxRows={4}
                />
                <TextField
                  name='backup_mobile_number'
                  type='text'
                  label='Backup Mobile Number'
                  fullWidth={true}
                  onChange={(e) => {
                    setBackupMobile({ ...backupMobile, value: e.target.value });
                  }}
                  required
                  error={backupMobile.error}
                  helperText={backupMobile.helperText}
                />
                <LoadingButton
                  variant='contained'
                  color='primary'
                  size='large'
                  fullWidth={true}
                  onClick={registerDriver}
                  loading={loading}
                >
                  Sign Up
                </LoadingButton>
              </div>
            </Grid>
          )}
        </Grid>
      </Box>
    </div>
  );
}
