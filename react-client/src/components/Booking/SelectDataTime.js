import {
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogTitle,
  TextField,
} from '@mui/material';
import {
  DesktopDateTimePicker,
  LocalizationProvider,
} from '@mui/x-date-pickers';
import { AdapterDateFns } from '@mui/x-date-pickers/AdapterDateFns';
import React from 'react';

export default function SelectDataTime({
  handleClose,
  open,
  nextStep,
  tripScheduledTime,
  setTripScheduledTime,
}) {
  return (
    <>
      <Dialog fullWidth maxWidth='sm' open={open} onClose={handleClose}>
        <DialogTitle>Select Date And Time</DialogTitle>
        <DialogContent>
          <br />
          <LocalizationProvider dateAdapter={AdapterDateFns}>
            <DesktopDateTimePicker
              label='Trip Scheduled Time'
              onChange={(val) =>
                setTripScheduledTime({
                  ...tripScheduledTime,
                  value: val,
                })
              }
              value={tripScheduledTime.value}
              inputFormat='yyyy/MM/dd KK:mm aa'
              renderInput={(params) => (
                <TextField
                  {...params}
                  fullWidth={true}
                  name='trip_scheduled_time_field'
                  id='trip_scheduled_time'
                  error={tripScheduledTime.error}
                  helperText={tripScheduledTime.helperText}
                />
              )}
            />
          </LocalizationProvider>
          <br />
          <br />
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
            onClick={nextStep}
            color='success'
            disabled={tripScheduledTime.error || !tripScheduledTime.value}
          >
            Proceed
          </Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
