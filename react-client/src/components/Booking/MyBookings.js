import { Button, Card, CardActions, CardContent, Grid } from '@mui/material';
import axios from 'axios';
import React, { useEffect } from 'react';
import { useState } from 'react';
import { useSelector } from 'react-redux';
import ReadMoreIcon from '@mui/icons-material/ReadMore';
import ChangeBooking from './ChangeBooking';

export default function MyBookings() {
  const [bookings, setBookings] = useState([]);
  const [drivers, setDrivers] = useState([]);
  const userDetails = useSelector((state) => state.user.userDetails);
  const [isStatusModalOpen, setIsStatusModalOpen] = useState(false);
  const updateStatus = () => {
    setIsStatusModalOpen(true);
  };
  const renderBookingStatus = (booking) => {
    return (
      <ChangeBooking
        open={isStatusModalOpen}
        handleClose={handleCloseStatusModal}
        booking={booking}
      />
    );
  };
  const handleCloseStatusModal = () => {
    setIsStatusModalOpen(false);
  };
  const renderBookingCards = () => {
    return bookings ? (
      bookings.map((b) => {
        return (
          <Grid key={b.id} item style={{ padding: '1rem' }}>
            {renderBookingStatus(b)}
            <Card style={{ height: '100%' }}>
              <CardContent>
                <div style={{ display: 'flex', alignItems: 'center' }}>
                  <span>Status: </span>
                  <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                    {b.status}
                  </span>
                </div>
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginTop: '1rem',
                  }}
                >
                  <span>Pick Up Location: </span>
                  <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                    {b.pickUpLocation}
                  </span>
                </div>
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginTop: '1rem',
                  }}
                >
                  <span>Drop Off Location: </span>
                  <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                    {b.dropOffLocation}
                  </span>
                </div>
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginTop: '1rem',
                  }}
                >
                  <span>Distance: </span>
                  <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                    {b.distance} KM
                  </span>
                </div>
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginTop: '1rem',
                  }}
                >
                  <span>Duration: </span>
                  <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                    {b.duration} min
                  </span>
                </div>
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginTop: '1rem',
                  }}
                >
                  <span>Price: </span>
                  <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                    LKR. {b.price}
                  </span>
                </div>
                <div
                  style={{
                    display: 'flex',
                    alignItems: 'center',
                    marginTop: '1rem',
                  }}
                >
                  <span>Trip Scheduled Time: </span>
                  <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                    {b.tripScheduledTime
                      ? b.tripScheduledTime.replace('T', ' ')
                      : ''}
                  </span>
                </div>
              </CardContent>
              <CardActions>
                <Button
                  variant='text'
                  size='small'
                  color='warning'
                  onClick={() => updateStatus()}
                >
                  Update
                  <ReadMoreIcon
                    sx={{ marginLeft: 1, marginBottom: 0.3 }}
                    fontSize='small'
                  />
                </Button>
              </CardActions>
            </Card>
          </Grid>
        );
      })
    ) : (
      <></>
    );
  };
  useEffect(() => {
    const getAllDrivers = async () => {
      try {
        const response = await axios.get('drivers');
        if (response.status === 200) {
          setDrivers(response.data);
        }
      } catch (error) {
        console.error(error);
      }
    };
    const getAllBookings = async () => {
      try {
        if (drivers) {
          const response = await axios.get('/bookings');
          const driver = drivers.find((d) => d.userId === userDetails.userId);
          if (response.status === 200) {
            const relevantBookings = response.data.filter(
              (b) =>
                userDetails.userId === b.userId ||
                (driver && b.driverId === driver.driverId)
            );
            setBookings(relevantBookings);
          }
        }
      } catch (error) {
        console.error(error);
      }
    };
    const getAsync = async () => {
      await getAllDrivers();
      await getAllBookings();
    };
    getAsync();
    // eslint-disable-next-line
  }, [userDetails.userId]);
  return (
    <Grid container sx={{ paddingTop: '2rem', paddingLeft: '2rem' }}>
      {renderBookingCards()}
    </Grid>
  );
}
