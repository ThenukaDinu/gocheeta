import CloseOutlinedIcon from '@mui/icons-material/CloseOutlined';
import {
  useJsApiLoader,
  GoogleMap,
  Autocomplete,
  DirectionsRenderer,
} from '@react-google-maps/api';
import { useRef, useState } from 'react';
import './Booking.scss';
import { CircularProgress, Grid, Fab } from '@mui/material';
import { LoadingButton } from '@mui/lab';
import PlaceBookingModal from './PlaceBookingModal';

const center = { lat: 6.876258, lng: 79.858159 };

export default function Booking() {
  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    libraries: ['places'],
  });

  // eslint-disable-next-line
  const [map, setMap] = useState(/** @type google.maps.Map */ (null));
  const [directionsResponse, setDirectionsResponse] = useState(null);
  const [distance, setDistance] = useState('');
  const [duration, setDuration] = useState('');
  const [isModalOpen, setIsModalOpen] = useState(false);

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
    setIsModalOpen(true);
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
      <PlaceBookingModal open={isModalOpen} handleClose={handleClose} />
    ) : (
      <></>
    );
  };

  const handleClose = () => {
    setIsModalOpen(false);
  };

  return (
    <Grid container className='Booking'>
      {renderPlaceBooking()}
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

  // return (
  //   <Flex
  //     position='relative'
  //     flexDirection='column'
  //     alignItems='center'
  //     h='100vh'
  //     w='100vw'
  //   >
  //     <Box position='absolute' left={0} top={0} h='100%' w='100%'>
  //       {/* Google Map Box */}
  //       <GoogleMap
  //         center={center}
  //         zoom={15}
  //         mapContainerStyle={{ width: '100%', height: '100%' }}
  //         options={{
  //           zoomControl: false,
  //           streetViewControl: false,
  //           mapTypeControl: false,
  //           fullscreenControl: false,
  //         }}
  //         onLoad={(map) => setMap(map)}
  //       >
  //         <Marker position={center} />
  //         {directionsResponse && (
  //           <DirectionsRenderer directions={directionsResponse} />
  //         )}
  //       </GoogleMap>
  //     </Box>
  //     <Box
  //       p={4}
  //       borderRadius='lg'
  //       m={4}
  //       bgColor='white'
  //       shadow='base'
  //       minW='container.md'
  //       zIndex='1'
  //     >
  //       <HStack spacing={2} justifyContent='space-between'>
  //         <Box flexGrow={1}>
  //           <Autocomplete>
  //             <Input type='text' placeholder='Origin' ref={originRef} />
  //           </Autocomplete>
  //         </Box>
  //         <Box flexGrow={1}>
  //           <Autocomplete>
  //             <Input
  //               type='text'
  //               placeholder='Destination'
  //               ref={destiantionRef}
  //             />
  //           </Autocomplete>
  //         </Box>

  //         <ButtonGroup>
  //           <Button colorScheme='pink' type='submit' onClick={calculateRoute}>
  //             Calculate Route
  //           </Button>
  //           <IconButton
  //             aria-label='center back'
  //             icon={<FaTimes />}
  //             onClick={clearRoute}
  //           />
  //         </ButtonGroup>
  //       </HStack>
  //       <HStack spacing={4} mt={4} justifyContent='space-between'>
  //         <Text>Distance: {distance} </Text>
  //         <Text>Duration: {duration} </Text>
  //         <IconButton
  //           aria-label='center back'
  //           icon={<FaLocationArrow />}
  //           isRound
  //           onClick={() => {
  //             map.panTo(center);
  //             map.setZoom(15);
  //           }}
  //         />
  //       </HStack>
  //     </Box>
  //   </Flex>
  // );
}
