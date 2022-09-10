import {
  Fab,
  FormControl,
  FormHelperText,
  Grid,
  InputLabel,
  MenuItem,
  Select,
  TextField,
} from '@mui/material';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { toast } from 'react-toastify';
import './Vehicle.scss';
import VehicleCard from './VehicleCard';
import validator from 'validator';
import { Box } from '@mui/system';
import AddIcon from '@mui/icons-material/Add';
import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';
import { LoadingButton } from '@mui/lab';
import vehicleTypes from './vehicleTypes';

export default function Vehicle() {
  const [branches, setBranches] = useState([]);
  const [drivers, setDrivers] = useState([]);
  const invalidDetails = () =>
    toast.error('Invalid details provided!, Please validate and try again.');
  const otherError = () =>
    toast.error('Something went wrong, Please contact support service.');
  const [vehicles, setVehicles] = useState([]);
  const [loading, setLoading] = useState(false);
  const [showAddIcon, setShowAddIcon] = useState(true);

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

  const renderVehiclesList = () => {
    return (
      <Grid
        container
        className={`vehicles-list ${showAddIcon ? '' : 'showScroll'}`}
        spacing={3}
        padding={3}
        marginTop={showAddIcon ? -2 : 3.5}
      >
        {vehicles.map((v) => {
          return (
            <VehicleCard
              key={v.id}
              vehicle={v}
              setVehicles={setVehicles}
              branches={branches}
              drivers={drivers}
            ></VehicleCard>
          );
        })}
      </Grid>
    );
  };
  const registerVehicle = async () => {
    setLoading(true);
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
      setLoading(false);
      return;
    }

    let response = null;
    try {
      response = await axios.post('/vehicles', {
        model: model.value,
        vehicleType: vehicleType.value,
        brand: brand.value,
        numberPlateNo: numberPlateNo.value,
      });
      setLoading(false);
    } catch (error) {
      setLoading(false);
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
      setVehicles((vehicles) => [...vehicles, response.data]);
    }
  };
  useEffect(() => {
    const getAllVehicles = async () => {
      try {
        const response = await axios.get('/vehicles');
        setVehicles(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    const getAllBranches = async () => {
      try {
        const response = await axios.get('/branches');
        setBranches(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    const getAllDrivers = async () => {
      try {
        const response = await axios.get('/drivers');
        setDrivers(response.data);
      } catch (error) {
        console.error(error);
      }
    };
    getAllDrivers();
    getAllBranches();
    getAllVehicles();
  }, []);
  return (
    <div className='Vehicle'>
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
            {renderVehiclesList()}
          </Grid>
          {showAddIcon === true ? (
            <></>
          ) : (
            <Grid item md={3.6} className='right' marginTop={2}>
              <Grid spacing={2} container justifyContent={'flex-end'}>
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
                        <MenuItem
                          key={`vehicle-type-${vt.id}`}
                          value={vt.value}
                        >
                          {vt.value}
                        </MenuItem>
                      );
                    })}
                  </Select>
                  <FormHelperText error>
                    {vehicleType.helperText}
                  </FormHelperText>
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
                />
                <LoadingButton
                  variant='contained'
                  color='primary'
                  size='large'
                  fullWidth={true}
                  onClick={registerVehicle}
                  loading={loading}
                >
                  Add Vehicle
                </LoadingButton>
              </div>
            </Grid>
          )}
        </Grid>
      </Box>
    </div>
  );
}
