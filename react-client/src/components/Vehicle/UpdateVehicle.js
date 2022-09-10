import React, { useEffect, useState } from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogTitle from '@mui/material/DialogTitle';
import { toast } from 'react-toastify';
import validator from 'validator';
import axios from 'axios';

import {
  FormControl,
  FormHelperText,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from '@mui/material';
import vehicleTypes from './vehicleTypes';

export default function UpdateVehicle({
  setVehicles,
  handleClose,
  vehicle,
  open,
  driver,
  branch,
  branches,
  drivers,
}) {
  const invalidDetails = () =>
    toast.error('Invalid details provided!, Please validate and try again.');
  const otherError = () =>
    toast.error('Something went wrong, Please contact support service.');
  const [model, setModel] = useState({
    value: '',
    error: false,
    helperText: '',
  });
  const [vehicleType, setVehicleType] = useState({
    value: '',
    error: false,
    helperText: '',
  });
  const [brand, setBrand] = useState({
    value: '',
    error: false,
    helperText: '',
  });
  const [numberPlateNo, setNumberPlateNo] = useState({
    value: '',
    error: false,
    helperText: '',
  });
  const [driverAssigned, setDriverAssigned] = useState({
    value: '',
    error: false,
    helperText: '',
  });
  const [branchAssigned, setBranchAssigned] = useState({
    value: '',
    error: false,
    helperText: '',
  });
  const registerVehicle = async () => {
    setModel({ ...model, error: false, helperText: '' });
    setVehicleType({ ...vehicleType, error: false, helperText: '' });
    setBrand({ ...brand, error: false, helperText: '' });
    setNumberPlateNo({ ...numberPlateNo, error: false, helperText: '' });
    let notValid = false;

    if (validator.isEmpty(model.value)) {
      setModel({
        ...model,
        error: true,
        helperText: 'Vehicle model is required',
      });
      notValid = true;
    }
    if (validator.isEmpty(vehicleType.value)) {
      setVehicleType({
        ...vehicleType,
        error: true,
        helperText: 'Vehicle type is required',
      });
      notValid = true;
    }
    if (validator.isEmpty(brand.value)) {
      setBrand({
        ...brand,
        error: true,
        helperText: 'Vehicle brand is required',
      });
      notValid = true;
    }
    if (validator.isEmpty(numberPlateNo.value)) {
      setNumberPlateNo({
        ...numberPlateNo,
        error: true,
        helperText: 'Number Plate No is required',
      });
      notValid = true;
    }

    if (notValid) {
      return;
    }

    let response = null;
    try {
      response = await axios.put('/vehicles/' + vehicle.id, {
        model: model.value,
        vehicleType: vehicleType.value,
        brand: brand.value,
        numberPlateNo: numberPlateNo.value,
        branchId: branchAssigned.value,
        driverId: driverAssigned.value,
      });
    } catch (error) {
      console.error(error);
      if (error.response.status === 400) {
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
      setVehicles((vehicles) => [
        ...vehicles.filter((a) => a.id !== response.data.id),
        response.data,
      ]);
      const updateSuccess = () =>
        toast.success('Vehicle updated successfully.');
      updateSuccess();
      handleClose();
    }
  };
  useEffect(() => {
    setModel({ ...model, value: vehicle.model });
    setVehicleType({ ...vehicleType, value: vehicle.vehicleType });
    setNumberPlateNo({ ...numberPlateNo, value: vehicle.numberPlateNo });
    setBrand({ ...brand, value: vehicle.brand });
    if (driver) {
      setDriverAssigned({ ...driverAssigned, value: driver.driverId });
    }
    if (branch) {
      setBranchAssigned({ ...branchAssigned, value: branch.id });
    }
    // eslint-disable-next-line
  }, []);
  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Update Driver</DialogTitle>
        <DialogContent>
          <Grid item md={12} marginTop={2} className='update-modal'>
            <div className='fields'>
              <TextField
                name='model'
                type='text'
                label='Model'
                fullWidth={true}
                onChange={(e) => {
                  setModel({ ...model, value: e.target.value });
                }}
                required
                error={model.error}
                helperText={model.helperText}
                autoFocus={true}
                value={model.value}
              />
              <FormControl fullWidth>
                <InputLabel id='vehicle_type_label' error={vehicleType.error}>
                  Vehicle Type
                </InputLabel>
                <Select
                  labelId='demo-simple-select-label'
                  id='demo-simple-select'
                  value={vehicleType.value}
                  label='Vehicle Type'
                  onChange={(e) => {
                    setVehicleType({ ...vehicleType, value: e.target.value });
                  }}
                  error={vehicleType.error}
                >
                  {vehicleTypes.map((vt) => {
                    return (
                      <MenuItem key={`vehicle-type-${vt.id}`} value={vt.value}>
                        {vt.value}
                      </MenuItem>
                    );
                  })}
                </Select>
                <FormHelperText error>{vehicleType.helperText}</FormHelperText>
              </FormControl>
              <TextField
                name='brand'
                type='text'
                label='Brand'
                fullWidth={true}
                onChange={(e) => {
                  setBrand({ ...brand, value: e.target.value });
                }}
                required
                error={brand.error}
                helperText={brand.helperText}
                value={brand.value}
              />
              <TextField
                name='number_plate_no'
                type='text'
                label='Number Plate No'
                fullWidth={true}
                onChange={(e) => {
                  setNumberPlateNo({
                    ...numberPlateNo,
                    value: e.target.value,
                  });
                }}
                required
                error={numberPlateNo.error}
                helperText={numberPlateNo.helperText}
                value={numberPlateNo.value}
              />
              <FormControl fullWidth>
                <InputLabel
                  id='driver_assigned_label'
                  error={driverAssigned.error}
                >
                  Driver Assigned
                </InputLabel>
                <Select
                  labelId='driver_assigned_label'
                  value={driverAssigned.value}
                  label='Vehicle Type'
                  onChange={(e) => {
                    setDriverAssigned({
                      ...driverAssigned,
                      value: e.target.value,
                    });
                  }}
                  error={driverAssigned.error}
                >
                  <MenuItem key={-998} value=''>
                    None
                  </MenuItem>
                  {drivers.map((dr) => {
                    return (
                      <MenuItem key={`driver-${dr.userId}`} value={dr.driverId}>
                        {dr.firstName}
                      </MenuItem>
                    );
                  })}
                </Select>
                <FormHelperText error>
                  {driverAssigned.helperText}
                </FormHelperText>
              </FormControl>
              <FormControl fullWidth>
                <InputLabel
                  id='branch_assigned_label'
                  error={branchAssigned.error}
                >
                  Branch Assigned
                </InputLabel>
                <Select
                  labelId='branch_assigned_label'
                  value={branchAssigned.value}
                  label='Branch Assigned'
                  onChange={(e) => {
                    setBranchAssigned({
                      ...branchAssigned,
                      value: e.target.value,
                    });
                  }}
                  error={branchAssigned.error}
                >
                  <MenuItem key={-998} value=''>
                    None
                  </MenuItem>
                  {branches.map((br) => {
                    return (
                      <MenuItem key={`branch-assigned-${br.id}`} value={br.id}>
                        {br.name}
                      </MenuItem>
                    );
                  })}
                </Select>
                <FormHelperText error>{vehicleType.helperText}</FormHelperText>
              </FormControl>
              {/* <FormControl fullWidth>
                <InputLabel id='vehicle_type_label' error={vehicleType.error}>
                  Vehicle Type
                </InputLabel>
                <Select
                  labelId='demo-simple-select-label'
                  id='demo-simple-select'
                  value={vehicleType.value}
                  label='Vehicle Type'
                  onChange={(e) => {
                    setVehicleType({ ...vehicleType, value: e.target.value });
                  }}
                  error={vehicleType.error}
                >
                  {vehicleTypes.map((vt) => {
                    return (
                      <MenuItem key={`vehicle-type-${vt.id}`} value={vt.value}>
                        {vt.value}
                      </MenuItem>
                    );
                  })}
                </Select>
                <FormHelperText error>{vehicleType.helperText}</FormHelperText>
              </FormControl> */}
            </div>
          </Grid>
        </DialogContent>
        <DialogActions sx={{ marginRight: 2 }}>
          <Button onClick={handleClose}>Cancel</Button>
          <Button variant='contained' onClick={registerVehicle}>
            Update Vehicle
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
