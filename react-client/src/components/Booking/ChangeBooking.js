import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  FormControl,
  InputLabel,
  MenuItem,
  Select,
} from '@mui/material';
import axios from 'axios';
import { useEffect, useState } from 'react';
import { toast } from 'react-toastify';

const statusList = [
  { id: 1, value: 'Pending' },
  { id: 2, value: 'Accepted' },
  { id: 3, value: 'TripStarted' },
  { id: 4, value: 'TripEnded' },
  { id: 5, value: 'PaidAndCompleted' },
  { id: 6, value: 'Canceled' },
];

export default function ChangeBooking({ handleClose, open, booking }) {
  const [status, setStatus] = useState({
    value: '',
    error: false,
    helperText: '',
  });
  const updateStatus = async () => {
    const data = {
      status: status.value,
    };
    try {
      const response = await axios.put(`/bookings/${booking.id}`, data);
      if (response.status === 200) {
        booking.status = status.value;
        const updateSuccess = () =>
          toast.success('Trip status updated successfully.');
        updateSuccess();
        handleClose();
      }
    } catch (error) {
      console.error(error);
    }
  };
  useEffect(() => {
    if (booking) {
      setStatus((s) => ({ ...s, value: booking.status }));
    }
  }, [booking]);

  return (
    <>
      <Dialog fullWidth maxWidth='sm' open={open} onClose={handleClose}>
        <DialogTitle>Update Booking</DialogTitle>
        <DialogContent>
          <br />
          <FormControl fullWidth>
            <InputLabel id='demo-simple-select-label'>Trip Status</InputLabel>
            <Select
              labelId='demo-simple-select-label'
              id='demo-simple-select'
              value={status.value}
              label='Trip Status'
              onChange={(e) => {
                setStatus({ ...status, value: e.target.value });
              }}
            >
              {statusList.map((s) => {
                return (
                  <MenuItem key={`status-${s.id}`} value={s.value}>
                    {s.value}
                  </MenuItem>
                );
              })}
            </Select>
          </FormControl>
        </DialogContent>
        <DialogActions sx={{ marginRight: 2, marginBottom: 3 }}>
          <Button
            variant='outlined'
            color='info'
            onClick={handleClose}
            sx={{ marginRight: 1 }}
          >
            Cancel
          </Button>
          <Button
            variant='contained'
            onClick={updateStatus}
            color='success'
            disabled={status.value === '' || status.value === booking.status}
          >
            Change Status
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
