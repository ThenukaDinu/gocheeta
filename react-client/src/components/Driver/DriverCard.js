import React from 'react';
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';
import CardContent from '@mui/material/CardContent';
import CardActions from '@mui/material/CardActions';
import Typography from '@mui/material/Typography';
import { Button, Grid } from '@mui/material';

export default function DriverCard({ driver }) {
  return (
    <Grid item className='driver-card'>
      <Card sx={{ minWidth: 300, maxWidth: 345 }}>
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
          <Button size='small'>Share</Button>
          <Button size='small'>Learn More</Button>
        </CardActions>
      </Card>
    </Grid>
  );
}
