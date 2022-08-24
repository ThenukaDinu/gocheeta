import { Button, Fab, Grid, TextField } from '@mui/material';
import React from 'react';
import './Driver.scss';
import Box from '@mui/material/Box';
import { useState } from 'react';
import { DesktopDatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import AddIcon from '@mui/icons-material/Add';
import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';

export default function Driver() {
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
  const registerDriver = () => {};
  return (
    <div className='Driver'>
      <Box sx={{ flexGrow: 1 }}>
        <Grid container spacing={2}>
          <Grid item xs={12} md={8}>
            <div className='left'>
              {showAddIcon === true ? (
                <div className='new-driver-icon'>
                  <Fab
                    className='icon'
                    color='primary'
                    aria-label='add'
                    size='small'
                  >
                    <AddIcon
                      fontSize='medium'
                      onClick={() => {
                        setShowAddIcon(!showAddIcon);
                      }}
                    />
                  </Fab>
                </div>
              ) : (
                <></>
              )}
            </div>
          </Grid>
          {showAddIcon === true ? (
            <></>
          ) : (
            <Grid item xs={12} md={4}>
              <div className='right'>
                <Grid
                  spacing={2}
                  container
                  justifyContent={'flex-end'}
                  alignItems={'center'}
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
                    >
                      <CloseOutlinedIcon
                        fontSize='medium'
                        onClick={() => {
                          setShowAddIcon(!showAddIcon);
                        }}
                      />
                    </Fab>
                  </Grid>
                </Grid>
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
                      setDateOfBirth({ ...dateOfBirth, value: val })
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
                <Button
                  variant='contained'
                  color='primary'
                  size='large'
                  fullWidth={true}
                  onClick={registerDriver}
                >
                  Sign Up
                </Button>
              </div>
            </Grid>
          )}
        </Grid>
      </Box>
    </div>
  );
}
