import React from 'react';
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Typography from '@mui/material/Typography';
import { Button, Grid } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import ReadMoreIcon from '@mui/icons-material/ReadMore';
import ConfirmationDialog from '../Common/ConfirmationDialog';
import { useState } from 'react';
import axios from 'axios';
import { toast } from 'react-toastify';
import UpdateDriver from './UpdateDriver';

export default function DriverCard({ driver, setDrivers }) {
  const deleteSuccess = () => toast.success('Driver deleted successfully.');
  const otherError = () =>
    toast.error('Something went wrong, Please contact support service.');
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [showUpdateDriver, setShowUpdateDriver] = useState(false);
  const renderUpdateDriver = () => {
    return showUpdateDriver ? (
      <UpdateDriver
        open={showUpdateDriver}
        handleClose={() => {
          setShowUpdateDriver(!showUpdateDriver);
        }}
        driver={driver}
      />
    ) : (
      <></>
    );
  };
  const renderDeleteConfirmation = () => {
    return showDeleteConfirmation ? (
      <ConfirmationDialog
        handleClose={handleClose}
        open={showDeleteConfirmation}
        confirmBtnText={'Confirm'}
        cancelBtnText={'Cancel'}
        description={
          'Please confirm you would like to delete this selected driver permanently!'
        }
        title={'Confirmation'}
        handleConfirm={handleConfirm}
      />
    ) : (
      <></>
    );
  };
  const handleClose = () => {
    setShowDeleteConfirmation(!showDeleteConfirmation);
  };
  const handleConfirm = async () => {
    try {
      const response = await axios.delete(`/drivers/${driver.driverId}`);
      if (response.status === 200) {
        setDrivers((d) => d.filter((dr) => dr.driverId !== driver.driverId));
        deleteSuccess();
        return;
      } else {
        otherError();
      }
    } catch (error) {
      otherError();
      console.error(error);
    }
    setShowDeleteConfirmation(!showDeleteConfirmation);
  };
  return (
    <>
      {renderDeleteConfirmation()}
      {renderUpdateDriver()}
      <Grid item className='driver-card' xs={true} md={4} lg={3}>
        <Card>
          <CardMedia
            component='img'
            height={200}
            image='https://picsum.photos/500'
            alt='green iguana'
          />
          <CardContent>
            <Typography gutterBottom variant='h5' component='div'>
              {driver.firstName}
            </Typography>
            <Typography variant='body2' color='text.secondary'>
              {driver.email} <br /> <br />
              {driver.dateOfBirth} <br /> <br />
              {driver.mobile} <br /> <br />
              {driver.nic}
            </Typography>
          </CardContent>
          <CardActions>
            <Button
              variant='text'
              color='error'
              size='small'
              sx={{ marginRight: 2, marginLeft: 0.6 }}
              onClick={() => {
                setShowDeleteConfirmation(!showDeleteConfirmation);
              }}
            >
              Delete
              <DeleteIcon
                sx={{ marginLeft: 1, marginBottom: 0.3 }}
                fontSize='small'
              />
            </Button>
            <Button
              variant='text'
              size='small'
              color='warning'
              onClick={() => setShowUpdateDriver(!showUpdateDriver)}
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
    </>
  );
}
