import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';
import {
  useJsApiLoader,
  GoogleMap,
  Autocomplete,
  DirectionsRenderer,
} from '@react-google-maps/api';
import { useEffect, useRef, useState } from 'react';
import './Booking.scss';
import { CircularProgress, Grid, Fab } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import PlaceBookingModal from './PlaceBookingModal';
import SelectDataTime from './SelectDataTime';
import { format } from 'date-fns';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { toast } from 'react-toastify';

const center = { lat: 6.876258, lng: 79.858159 };
const libs = ['places'];

export default function Booking() {
  useEffect(() => {
    const checkLoggedIn = async () => {
      if (localStorage.getItem('token')) {
        await axios.get('/secured');
      }
    };
    checkLoggedIn();
  }, []);

  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    // googleMapsApiKey: '',
    libraries: libs,
  });

  const userDetails = useSelector((state) => state.user.userDetails);
  // eslint-disable-next-line
  const [map, setMap] = useState(/** @type google.maps.Map */ (null));
  const [directionsResponse, setDirectionsResponse] = useState(null);
  const [distance, setDistance] = useState('');
  const [duration, setDuration] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [tripScheduledTime, setTripScheduledTime] = useState({
    error: false,
    value: null,
  });
  const [selectedVehicle, setSelectedVehicle] = useState({
    error: false,
    value: null,
  });
  const [isDateModalOpen, setIsDateModalOpen] = useState(false);

  /** @type React.MutableRefObject<HTMLInputElement> */
  const originRef = useRef();
  /** @type React.MutableRefObject<HTMLInputElement> */
  const destiantionRef = useRef();

  if (!isLoaded) {
    return <CircularProgress />;
  }

  async function calculateRoute() {
    if (originRef.current.value === '' || destiantionRef.current.value === '') {
      return;
    }
    // eslint-disable-next-line no-undef
    const directionsService = new google.maps.DirectionsService();
    const results = await directionsService.route({
      origin: originRef.current.value,
      destination: destiantionRef.current.value,
      // eslint-disable-next-line no-undef
      travelMode: google.maps.TravelMode.DRIVING,
    });
    setDirectionsResponse(results);
    setDistance(results.routes[0].legs[0].distance.text);
    setDuration(results.routes[0].legs[0].duration.text);
  }

  function clearRoute() {
    setDirectionsResponse(null);
    setDistance('');
    setDuration('');
    originRef.current.value = '';
    destiantionRef.current.value = '';
  }

  const placeBooking = () => {
    setIsDateModalOpen(true);
  };

  const placeBookingFinal = async () => {
    const formattedDate = format(
      tripScheduledTime.value,
      "yyyy-MM-dd'T'kk:mm:00"
    );
    const data = {
      tripScheduledTime: formattedDate,
      pickUpLocation: originRef.current.value,
      dropOffLocation: destiantionRef.current.value,
      status: 'Pending',
      driverId: selectedVehicle.value.driverId,
      vehicleId: selectedVehicle.value.id,
      userId: userDetails.userId,
      // eslint-disable-next-line
      distance: parseFloat(distance.replace(/[^\d\.]*/g, '')),
      // eslint-disable-next-line
      duration: parseFloat(duration.replace(/[^\d\.]*/g, '')),
    };
    const response = await axios.post('/bookings', data);
    console.log(response);
    if (response.status === 200) {
      clearRoute();
      const updateSuccess = () => toast.success('Booking placed successfully.');
      updateSuccess();
      handleClose();
    }
  };

  const myStyle = {
    maxHeight: '20px',
    minHeight: '20px',
    minWidth: '20px',
    maxWidth: '20px',
    marginTop: '5px',
    marginLeft: '2rem',
  };

  const renderPlaceBooking = () => {
    return isModalOpen ? (
      <PlaceBookingModal
        open={isModalOpen}
        handleClose={handleClose}
        placeBookingFinal={placeBookingFinal}
        setSelectedVehicle={setSelectedVehicle}
        selectedVehicle={selectedVehicle}
      />
    ) : (
      <></>
    );
  };

  const handleClose = () => {
    setIsModalOpen(false);
  };
  const handleDateModalClose = () => {
    setIsDateModalOpen(false);
  };
  const nextStep = () => {
    handleDateModalClose();
    setIsModalOpen(true);
  };

  const renderSelectDateTime = () => {
    return (
      <SelectDataTime
        open={isDateModalOpen}
        handleClose={handleDateModalClose}
        tripScheduledTime={tripScheduledTime}
        setTripScheduledTime={setTripScheduledTime}
        nextStep={nextStep}
      />
    );
  };

  return (
    <Grid container className='Booking'>
      {renderPlaceBooking()}
      {renderSelectDateTime()}
      <Grid
        item
        container
        xs={12}
        justifyContent={'space-between'}
        sx={{
          textAlign: 'center',
        }}
      >
        <Grid
          item
          container
          className='search-box-container'
          spacing={2}
          alignItems={'center'}
          justifyContent={'center'}
          sx={{ maxWidth: '37vw' }}
        >
          <Autocomplete>
            <input
              className='fromInputText'
              type='text'
              name='from'
              ref={originRef}
            />
          </Autocomplete>
          <Autocomplete>
            <input
              className='toInputText'
              type='text'
              name='to'
              ref={destiantionRef}
            />
          </Autocomplete>
          <div style={{ display: 'flex', flexDirection: 'column' }}>
            <LoadingButton
              variant='contained'
              color='info'
              size='medium'
              fullWidth={false}
              onClick={calculateRoute}
              loading={false}
              style={{ marginBottom: '1.4rem' }}
            >
              Calculate Distance
            </LoadingButton>
            <LoadingButton
              variant='contained'
              color='success'
              size='medium'
              fullWidth={false}
              onClick={placeBooking}
              loading={false}
              disabled={duration === '' || distance === ''}
            >
              Place Booking
            </LoadingButton>
          </div>
          <Grid item xs={12}>
            <div style={{ display: 'flex', justifyContent: 'flex-start' }}>
              <h4 style={{ marginRight: '2rem' }}>distance: {distance}</h4>
              <h4>duration: {duration}</h4>
              <Fab
                sx={{ marginLeft: 2 }}
                className='icon'
                color='primary'
                aria-label='add'
                size='small'
                onClick={clearRoute}
                style={myStyle}
                disabled={duration === '' && distance === ''}
              >
                <CloseOutlinedIcon fontSize='small' style={{ width: '15px' }} />
              </Fab>
            </div>
          </Grid>
        </Grid>
      </Grid>
      <Grid
        item
        xs={12}
        sx={{
          height: '91vh',
        }}
      >
        <GoogleMap
          center={center}
          zoom={15}
          mapContainerStyle={{ width: '100%', height: '100%' }}
          options={{
            zoomControl: true,
            streetViewControl: false,
            mapTypeControl: false,
            fullscreenControl: false,
          }}
          onLoad={(map) => setMap(map)}
        >
          {directionsResponse && (
            <DirectionsRenderer directions={directionsResponse} />
          )}
        </GoogleMap>
      </Grid>
    </Grid>
  );
}
