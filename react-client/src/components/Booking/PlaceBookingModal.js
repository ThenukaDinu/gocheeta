import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  Grid,
} from '@mui/material';
import React from 'react';

export default function PlaceBookingModal({ open, handleClose }) {
  const placeBooking = () => {
    console.log('place booking');
  };
  return (
    <div>
      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Place Booking</DialogTitle>
        <DialogContent>
          <Grid item md={12} marginTop={2} className='update-modal'>
            <h1>Place booking modal</h1>
          </Grid>
        </DialogContent>
        <DialogActions sx={{ marginRight: 2 }}>
          <Button variant='outlined' color='info' onClick={handleClose}>
            Cancel Booking
          </Button>
          <Button variant='contained' onClick={placeBooking} color='success'>
            Place Booking
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
