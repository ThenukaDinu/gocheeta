import {
  Button,
  Card,
  CardContent,
  CardMedia,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
  Tab,
  Tabs,
  Typography,
} from '@mui/material';
import { Box } from '@mui/system';
import axios from 'axios';
import React, { useEffect, useState } from 'react';
import './PlaceBookingModal.scss';

export default function PlaceBookingModal({
  open,
  handleClose,
  placeBookingFinal,
  selectedVehicle,
  setSelectedVehicle,
}) {
  const [value, setValue] = useState(3);
  const [categories, setCategories] = useState([]);
  const [vehicles, setVehicles] = useState([]);

  function TabPanel(props) {
    const { children, value, index, ...other } = props;

    return (
      <div
        role='tabpanel'
        hidden={value !== index}
        id={`vertical-tabpanel-${index}`}
        aria-labelledby={`vertical-tab-${index}`}
        {...other}
      >
        {value === index && (
          <div>
            <div>{children}</div>
          </div>
        )}
      </div>
    );
  }

  function a11yProps(index) {
    return {
      id: `vertical-tab-${index}`,
      'aria-controls': `vertical-tabpanel-${index}`,
    };
  }
  const handleChange = (event, newValue) => {
    setValue(newValue);
  };
  useEffect(() => {
    const getCategories = async () => {
      const response = await axios.get('/categories');
      if (response.status === 200)
        setCategories([
          ...response.data,
          {
            id: -999,
            name: 'All',
            description: 'All Vehicles',
          },
        ]);
    };
    const getVehicles = async () => {
      const response = await axios.get('/vehicles');
      if (response.status === 200)
        setVehicles(
          response.data.filter(
            (v) =>
              v.driverId !== null &&
              v.driverId !== 0 &&
              v.driverId !== undefined
          )
        );
    };
    const run = async () => {
      await getCategories();
      await getVehicles();
    };
    run();
  }, []);
  const renderCategoryTabs = () => {
    return categories ? (
      categories.map((c, index) => {
        return <Tab label={c.name} {...a11yProps(index)} key={c.id} />;
      })
    ) : (
      <></>
    );
  };
  const selectVehicle = (vehicle) => {
    if (
      selectVehicle &&
      selectVehicle.value &&
      selectVehicle.value.id === vehicle.id
    ) {
      setSelectedVehicle({
        ...selectedVehicle,
        value: '',
      });
    } else {
      setSelectedVehicle({
        ...selectedVehicle,
        value: vehicle,
      });
    }
  };
  const renderTabPanels = () => {
    return categories && vehicles ? (
      categories.map((c, index) => {
        return (
          <TabPanel value={value} index={index} className='tab-content-booking'>
            <Grid container spacing={4}>
              {vehicles
                .filter((v) => (c.id !== -999 ? v.categoryId === c.id : true))
                .map((vehicle) => {
                  return (
                    <Grid item>
                      <div
                        className={`vehicle-card ${
                          selectedVehicle &&
                          selectedVehicle.value &&
                          selectedVehicle.value.id === vehicle.id
                            ? 'active'
                            : ''
                        }`}
                        onClick={() => {
                          selectVehicle(vehicle);
                        }}
                      >
                        <Card style={{ height: '100%', cursor: 'pointer' }}>
                          <CardMedia
                            component='img'
                            height={200}
                            image='https://picsum.photos/500'
                            alt='green iguana'
                          />
                          <CardContent>
                            <Typography
                              gutterBottom
                              variant='h5'
                              component='div'
                            >
                              {vehicle.model}
                            </Typography>
                            <Typography
                              variant='subtitle1'
                              color='text.secondary'
                            >
                              <Box sx={{ marginBottom: 1 }}>
                                <b>Type: </b> {vehicle.vehicleType}
                              </Box>
                              <Box sx={{ marginBottom: 1 }}>
                                <b>Brand: </b>
                                {vehicle.brand}
                              </Box>
                              <Box sx={{ marginBottom: 1 }}>
                                <b>Number Plate No: </b>
                                {vehicle.numberPlateNo}{' '}
                              </Box>
                            </Typography>
                          </CardContent>
                        </Card>
                      </div>
                    </Grid>
                  );
                })}
            </Grid>
          </TabPanel>
        );
      })
    ) : (
      <></>
    );
  };

  return (
    <div className='place-booking-modal'>
      <Dialog fullWidth maxWidth='xl' open={open} onClose={handleClose}>
        <DialogTitle>Place Booking</DialogTitle>
        <DialogContent>
          <Tabs
            variant='scrollable'
            value={value}
            onChange={handleChange}
            aria-label='Vertical tabs example'
            sx={{ borderRight: 1, borderColor: 'divider' }}
          >
            {renderCategoryTabs()}
          </Tabs>
          <div style={{ marginTop: '2rem' }}>{renderTabPanels()}</div>
        </DialogContent>
        <DialogActions sx={{ marginRight: 2, marginBottom: 3 }}>
          <Button
            variant='outlined'
            color='info'
            onClick={handleClose}
            sx={{ marginRight: 1 }}
          >
            Cancel Booking
          </Button>
          <Button
            sx={{ marginRight: 1 }}
            variant='contained'
            onClick={placeBookingFinal}
            color='success'
            disabled={!selectedVehicle.value || selectedVehicle.error}
          >
            Place Booking
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
