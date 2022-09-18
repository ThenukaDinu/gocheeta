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
import UpdateVehicle from './UpdateVehicle';

export default function VehicleCard({
  vehicle,
  setVehicles,
  branches,
  drivers,
}) {
  const deleteSuccess = () => toast.success('Vehicle deleted successfully.');
  const otherError = () =>
    toast.error('Something went wrong, Please contact support service.');
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
  const [showUpdateVehicle, setShowUpdateVehicle] = useState(false);
  const renderUpdateVehicle = () => {
    return showUpdateVehicle ? (
      <UpdateVehicle
        open={showUpdateVehicle}
        handleClose={() => {
          setShowUpdateVehicle(!showUpdateVehicle);
        }}
        vehicle={vehicle}
        setVehicles={setVehicles}
        driver={getDriver()}
        branch={getBranch()}
        branches={branches}
        drivers={drivers}
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
          'Please confirm you would like to delete this selected vehicle permanently!'
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
      const response = await axios.delete(`/vehicles/${vehicle.id}`);
      if (response.status === 200) {
        setVehicles((i) => i.filter((v) => v.id !== vehicle.id));
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
  const getBranch = () => {
    if (!vehicle || (vehicle && !vehicle.branchId) || !branches) return null;
    const assignedBranch = branches.find((b) => b.id === vehicle.branchId);
    return assignedBranch;
  };
  const getDriver = () => {
    if (!vehicle || (vehicle && !vehicle.driverId) || !drivers) return null;
    const assignedDriver = drivers.find((d) => d.driverId === vehicle.driverId);
    return assignedDriver;
  };
  return (
    <>
      {renderDeleteConfirmation()}
      {renderUpdateVehicle()}
      <Grid item className='vehicle-card' xs={true} md={4} lg={3}>
        <Card style={{ height: '100%' }}>
          <CardMedia
            component='img'
            height={200}
            image='https://picsum.photos/500'
            alt='green iguana'
          />
          <CardContent>
            <Typography gutterBottom variant='h5' component='div'>
              {vehicle.model}
            </Typography>
            <div style={{ marginBottom: '1rem' }}>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginTop: '1rem',
                }}
              >
                <span>Type: </span>
                <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                  {vehicle.vehicleType}
                </span>
              </div>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginTop: '1rem',
                }}
              >
                <span>Brand: </span>
                <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                  {vehicle.brand}
                </span>
              </div>
              <div
                style={{
                  display: 'flex',
                  alignItems: 'center',
                  marginTop: '1rem',
                }}
              >
                <span>Number Plate No: </span>
                <span style={{ marginLeft: '0.7rem', fontWeight: 'bold' }}>
                  {vehicle.numberPlateNo}{' '}
                </span>
              </div>
            </div>
            {vehicle.branchId && getBranch() ? (
              <Chip
                sx={{ marginTop: 2 }}
                label={getBranch().name}
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
              {vehicle.driverId && getDriver() ? (
                <Chip
                  sx={{ marginTop: 2, marginRight: 1 }}
                  label={getDriver().firstName}
                  color='success'
                />
              ) : (
                <Chip
                  sx={{ marginTop: 2 }}
                  label='No Driver Assigned'
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
              onClick={() => setShowUpdateVehicle(!showUpdateVehicle)}
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
