import {
  useJsApiLoader,
  GoogleMap,
  Marker,
  Autocomplete,
  DirectionsRenderer,
} from '@react-google-maps/api';
import { useRef, useState } from 'react';
import './Booking.scss';
import { FaLocationArrow, FaTimes } from 'react-icons/fa';
import { Box } from '@mui/system';
import { CircularProgress, Grid, TextField } from '@mui/material';
import { LoadingButton } from '@mui/lab';

const center = { lat: 48.8584, lng: 2.2945 };

export default function Booking() {
  const { isLoaded } = useJsApiLoader({
    googleMapsApiKey: process.env.REACT_APP_GOOGLE_MAPS_API_KEY,
    libraries: ['places'],
  });

  const [map, setMap] = useState(/** @type google.maps.Map */ (null));
  const [directionsResponse, setDirectionsResponse] = useState(null);
  const [distance, setDistance] = useState('');
  const [duration, setDuration] = useState('');

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

  return (
    <Grid container className='Booking'>
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
          <TextField
            variant='filled'
            name='origin'
            type='text'
            label='From'
            fullWidth={false}
            sx={{ marginRight: '2rem' }}
            size='small'
          />
          <TextField
            variant='filled'
            name='origin'
            type='text'
            label='To'
            fullWidth={false}
            sx={{ marginRight: '2rem' }}
            size='small'
          />
          <LoadingButton
            variant='contained'
            color='primary'
            size='medium'
            fullWidth={false}
            onClick={null}
            loading={false}
          >
            Calculate Distance
          </LoadingButton>
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
          <Marker position={center} />
          {directionsResponse && (
            <DirectionsRenderer directions={directionsResponse} />
          )}
          //{' '}
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