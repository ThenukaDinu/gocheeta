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

export default function DriverCard({ driver }) {
  const [showDeleteConfirmation, setShowDeleteConfirmation] = useState(false);
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
  const handleConfirm = () => {
    // api call
    setShowDeleteConfirmation(!showDeleteConfirmation);
  };
  return (
    <>
      {renderDeleteConfirmation()}
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
            <Button variant='text' size='small' color='warning'>
              Show More
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
