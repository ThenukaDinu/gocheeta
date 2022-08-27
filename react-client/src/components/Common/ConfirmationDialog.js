import * as React from 'react';
import Button from '@mui/material/Button';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import useMediaQuery from '@mui/material/useMediaQuery';
import { useTheme } from '@mui/material/styles';

export default function ConfirmationDialog(props) {
  const theme = useTheme();
  const fullScreen = useMediaQuery(theme.breakpoints.down('md'));
  return (
    <div>
      <Dialog
        fullScreen={fullScreen}
        open={props.open}
        onClose={props.handleClose}
        aria-labelledby='responsive-dialog-title'
      >
        <DialogTitle id='responsive-dialog-title'>{props.title}</DialogTitle>
        <DialogContent>
          <DialogContentText>{props.description}</DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button
            variant='outlined'
            color='info'
            autoFocus
            onClick={props.handleClose}
          >
            {props.cancelBtnText}
          </Button>
          <Button
            variant='contained'
            color='error'
            onClick={props.handleConfirm}
            autoFocus
          >
            {props.confirmBtnText}
          </Button>
        </DialogActions>
      </Dialog>
    </div>
  );
}
