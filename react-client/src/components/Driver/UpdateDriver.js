import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import {
  Chip,
  FormControl,
  Grid,
  InputLabel,
  MenuItem,
  OutlinedInput,
  Select,
  TextField,
} from '@mui/material';
import React, { useEffect } from 'react';
import { useState } from 'react';
import { DesktopDatePicker, LocalizationProvider } from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import { toast } from 'react-toastify';
import validator from 'validator';
import axios from 'axios';
import { format } from 'date-fns';
import { Box } from '@mui/system';

export default function UpdateDriver(props) {
  const invalidDetails = () =>
    toast.error('Invalid details provided!, Please validate and try again.');
  const otherError = () =>
    toast.error('Something went wrong, Please contact support service.');

  const [emailAddress, setEmailAddress] = useState({
    error: false,
    value: '',
  });
  // const [password, setPassword] = useState({ error: false, value: '' });
  const [mobile, setMobile] = useState({ error: false, value: '' });
  const [firstName, setFirstName] = useState({ error: false, value: '' });
  const [lastName, setLastName] = useState({ error: false, value: '' });
  const [dateOfBirth, setDateOfBirth] = useState({ error: false, value: null });
  const [address, setAddress] = useState({ error: false, value: '' });
  const [nic, setNic] = useState({ error: false, value: '' });
  const [backupMobile, setBackupMobile] = useState({
    error: false,
    value: '',
  });
  const [branch, setBranch] = useState({ value: '' });
  const [branches, setBranches] = useState([]);
  const [vehicles, setVehicles] = useState([]);
  const [selectedVehicles, setSelectedVehicles] = useState([]);
  const registerDriver = async () => {
    setFirstName({ ...firstName, error: false, helperText: '' });
    setLastName({ ...lastName, error: false, helperText: '' });
    setEmailAddress({ ...emailAddress, error: false, helperText: '' });
    // setPassword({ ...password, error: false, helperText: '' });
    setDateOfBirth({
      ...dateOfBirth,
      error: false,
      helperText: '',
    });
    setAddress({
      ...address,
      error: false,
      helperText: '',
    });
    setMobile({
      ...mobile,
      error: false,
      helperText: '',
    });
    setNic({
      ...nic,
      error: false,
      helperText: '',
    });
    setBackupMobile({
      ...backupMobile,
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
    // if (validator.isEmpty(password.value)) {
    //   setPassword({
    //     ...password,
    //     error: true,
    //     helperText: 'Password is required',
    //   });
    //   notValid = true;
    // }
    if (!dateOfBirth.value) {
      setDateOfBirth({
        ...dateOfBirth,
        error: true,
        helperText: 'Date of Birth is required',
      });
      notValid = true;
    }
    if (validator.isEmpty(mobile.value)) {
      setMobile({
        ...mobile,
        error: true,
        helperText: 'Mobile is required.',
      });
      notValid = true;
    }
    if (validator.isEmpty(address.value)) {
      setAddress({
        ...address,
        error: true,
        helperText: 'Address is required.',
      });
      notValid = true;
    }
    if (validator.isEmpty(nic.value)) {
      setNic({
        ...nic,
        error: true,
        helperText: 'NIC is required.',
      });
      notValid = true;
    }
    if (validator.isEmpty(backupMobile.value)) {
      setBackupMobile({
        ...backupMobile,
        error: true,
        helperText: 'Backup mobile is request.',
      });
    }

    if (notValid) {
      return;
    }

    let response = null;
    try {
      const vehiclesIdsList = [];
      selectedVehicles.forEach((v) => vehiclesIdsList.push(v.id));
      response = await axios.put('/drivers/' + props.driver.driverId, {
        username: emailAddress.value,
        password: null,
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
        branchId: branch.value,
        vehicles: vehiclesIdsList,
      });
    } catch (error) {
      console.error(error);
      if (
        error &&
        error.response &&
        error.response.status &&
        error.response.status === 400
      ) {
        if (error.response && error.response.data) {
          const returnedError = () => toast.error(error.response.data);
          returnedError();
        } else {
          invalidDetails();
        }
        return;
      }
      otherError();
    }
    if (response && (response.status === 201 || response.status === 200)) {
      props.setDrivers((drivers) => [
        ...drivers.filter((a) => a.driverId !== response.data.driverId),
        response.data,
      ]);
      const updateSuccess = () => toast.success('Driver updated successfully.');
      updateSuccess();
      props.handleClose();
    }
  };

  const ITEM_HEIGHT = 48;
  const ITEM_PADDING_TOP = 8;
  const MenuProps = {
    PaperProps: {
      style: {
        maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
        width: 250,
      },
    },
  };
  useEffect(() => {
    setEmailAddress({ ...emailAddress, value: props.driver.email });
    setMobile({ ...mobile, value: props.driver.mobile });
    setFirstName({ ...firstName, value: props.driver.firstName });
    setLastName({ ...lastName, value: props.driver.lastName });
    setDateOfBirth({
      ...dateOfBirth,
      value: new Date(props.driver.dateOfBirth),
    });
    setAddress({ ...address, value: props.driver.address });
    setNic({ ...nic, value: props.driver.nic });
    setBackupMobile({ ...backupMobile, value: props.driver.backupMobile });
    setBranch({
      ...branch,
      value: props.driver.branch ? props.driver.branch.id : '',
    });
    setSelectedVehicles(props.driver.vehicles);

    const getBranches = async () => {
      try {
        const response = await axios.get('/branches');
        if (
          response &&
          response &&
          response.status &&
          response.status === 200
        ) {
          setBranches(response.data);
        }
      } catch (error) {
        console.log(error);
      }
    };
    getBranches();

    const getVehicles = async () => {
      try {
        const response = await axios.get('/vehicles');
        if (
          response &&
          response &&
          response.status &&
          response.status === 200
        ) {
          setVehicles(response.data);
        }
      } catch (error) {
        console.error(error);
      }
    };
    getVehicles();
    // eslint-disable-next-line
  }, []);
  return (
    <div>
      <Dialog open={props.open} onClose={props.handleClose}>
        <DialogTitle>Update Driver</DialogTitle>
        <DialogContent>
          <Grid item md={12} marginTop={2} className='update-modal'>
            <div className='fields'>
              <TextField
                name='email_address'
                type='email'
                label='Email Address'
                fullWidth={true}
                onChange={(e) => {
                  setEmailAddress({ ...emailAddress, value: e.target.value });
                }}
                error={emailAddress.error}
                helperText={emailAddress.helperText}
                disabled
                value={emailAddress.value}
              />
              {/* <TextField
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
                disabled
              /> */}
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
                value={mobile.value}
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
                value={firstName.value}
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
                value={lastName.value}
              />
              <LocalizationProvider dateAdapter={AdapterDateFns}>
                <DesktopDatePicker
                  label='Date Of Birth'
                  onChange={(val) =>
                    setDateOfBirth({
                      ...dateOfBirth,
                      value: val,
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
                      error={dateOfBirth.error}
                      helperText={dateOfBirth.helperText}
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
                value={address.value}
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
                value={nic.value}
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
                value={backupMobile.value}
              />
              <FormControl fullWidth>
                <InputLabel id='demo-simple-select-label'>Branch</InputLabel>
                <Select
                  labelId='demo-simple-select-label'
                  id='demo-simple-select'
                  value={branch.value}
                  label='Branch'
                  onChange={(e) => {
                    setBranch({ ...branch, value: e.target.value });
                  }}
                >
                  {branches.map((b) => {
                    return (
                      <MenuItem key={`branch-${b.id}`} value={b.id}>
                        {b.name}
                      </MenuItem>
                    );
                  })}
                </Select>
              </FormControl>
              <FormControl fullWidth>
                <InputLabel id='demo-multiple-chip-label'>Chip</InputLabel>
                <Select
                  labelId='demo-multiple-chip-label'
                  id='demo-multiple-chip'
                  multiple
                  value={selectedVehicles}
                  onChange={(e) => {
                    setSelectedVehicles(e.target.value);
                  }}
                  input={
                    <OutlinedInput id='select-multiple-chip' label='Vehicles' />
                  }
                  renderValue={(selected) => (
                    <Box sx={{ display: 'flex', flexWrap: 'wrap', gap: 0.5 }}>
                      {selected.map((selVehicle) => (
                        <Chip
                          key={`sel-veh-${selVehicle.id}`}
                          label={selVehicle.model}
                        />
                      ))}
                    </Box>
                  )}
                  MenuProps={MenuProps}
                >
                  {vehicles.map((v) => (
                    <MenuItem key={`vehicle-${v.id}`} value={v}>
                      {v.model}
                    </MenuItem>
                  ))}
                </Select>
              </FormControl>
            </div>
          </Grid>
        </DialogContent>
        <DialogActions sx={{ marginRight: 2 }}>
          <Button onClick={props.handleClose}>Cancel</Button>
          <Button variant='contained' onClick={registerDriver}>
            Update Driver
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
