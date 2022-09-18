import React from 'react';
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Typography from '@mui/material/Typography';
import { Button, Chip, Grid } from '@mui/material';
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
        setDrivers={setDrivers}
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
        <Card style={{ height: '100%' }}>
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
            <div style={{ marginBottom: '1rem' }}>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginTop: '1rem',
                }}
              >
                <span>email: </span>
                <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                  {driver.email}
                </span>
              </div>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginTop: '1rem',
                }}
              >
                <span>DOB: </span>
                <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                  {driver.dateOfBirth}
                </span>
              </div>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginTop: '1rem',
                }}
              >
                <span>mobile: </span>
                <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                  {driver.mobile}
                </span>
              </div>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginTop: '1rem',
                }}
              >
                <span>NIC: </span>
                <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                  {driver.nic}
                </span>
              </div>
            </div>
            {driver.branch ? (
              <Chip
                sx={{ marginTop: 2 }}
                label={driver.branch.name}
                color='success'
              />
            ) : (
              <Chip
                sx={{ marginTop: 2 }}
                label='No Branch Assigned'
                color='primary'
              />
            )}
            <div>
              {driver.vehicles && driver.vehicles.length ? (
                driver.vehicles.map((v) => (
                  <Chip
                    sx={{ marginTop: 2, marginRight: 1 }}
                    label={v.model}
                    color='success'
                  />
                ))
              ) : (
                <Chip
                  sx={{ marginTop: 2 }}
                  label='No Vehicles Assigned'
                  color='info'
                />
              )}
            </div>
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
