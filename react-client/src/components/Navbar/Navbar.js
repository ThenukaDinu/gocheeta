import React from 'react';
import Button from '@mui/material/Button';
import Avatar from '@mui/material/Avatar';
import './Navbar.scss';
import logo from './../../assets/images/fake-logo.svg';
import { Link } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { removeUserDetails } from '../../store/userSlice';
import avatar from '../../assets/images/avatar.png';

export default function Navbar() {
  const dispatch = useDispatch();
  const userDetails = useSelector((state) => state.user.userDetails);
  const logoutUser = () => {
    dispatch(removeUserDetails());
  };
  const isAdmin = () => {
    return (
      userDetails !== null &&
      userDetails.roles.some((role) => role.roleName === 'ADMIN')
    );
  };
  return (
    <div className={'Navbar ' + (isAdmin ? 'admin' : '')}>
      <ul className='navbar-parent'>
        <li>
          <Link to='/'>
            <img src={logo} alt='logo-img' />
          </Link>
        </li>
        {/* {process.env.REACT_APP_BASE_URL + userDetails.avatarUrl} */}
        <li>
          <Link to='/'>Home</Link>
        </li>
        {isAdmin === true ? (
          <>
            <li>
              <Link to='/drivers'>Drivers</Link>
            </li>
            <li>
              <Link to='/branches'>Branches</Link>
            </li>
            <li>
              <Link to='reports'>Reports</Link>
            </li>
          </>
        ) : (
          <></>
        )}
        {userDetails !== null ? (
          <>
            <li>
              <Link to='/bookings'>Bookings</Link>
            </li>
          </>
        ) : (
          <></>
        )}
        {!isAdmin || userDetails === null ? (
          <>
            <li>
              <a href='contact.asp'>Contact</a>
            </li>
            <li>
              <a href='about.asp'>About</a>
            </li>
          </>
        ) : (
          <></>
        )}
        <li>
          {userDetails === null ? (
            <>
              <Link to='/signIn'>
                <Button variant='contained' color='primary'>
                  Sign In
                </Button>
              </Link>
              <Link to='/signUp'>
                <Button variant='contained' color='primary'>
                  Sign Up
                </Button>
              </Link>
            </>
          ) : (
            <>
              <Button onClick={logoutUser} variant='outlined'>
                Logout
              </Button>
              {/* 'https://i.pravatar.cc/300' */}
              {/* <Avatar
                alt='profile picture'
                src={
                  userDetails && userDetails.avatarUrl
                    ? `${process.env.REACT_APP_BASE_URL}{userDetails.avatarUrl}`
                    : avatar
                }
              /> */}
              <Avatar alt='profile picture' src={avatar} />
            </>
          )}
        </li>
      </ul>
    </div>
  );
}
