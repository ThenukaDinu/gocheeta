import React from 'react';
import Button from '@mui/material/Button';
import './Navbar.scss';
import logo from './../../assets/images/fake-logo.svg';

export default function Navbar() {
  return (
    <div className='Navbar'>
      <ul className='navbar-parent'>
        <li>
          <a href='default.asp'>
            <img src={logo} alt='logo-img' />
          </a>
        </li>
        <li>
          <a href='default.asp'>Home</a>
        </li>
        <li>
          <a href='news.asp'>News</a>
        </li>
        <li>
          <a href='contact.asp'>Contact</a>
        </li>
        <li>
          <a href='about.asp'>About</a>
        </li>
        <li>
          <Button variant='contained' color='custom'>
            Sign In
          </Button>
          <Button variant='contained' color='custom'>
            Sign Up
          </Button>
          {/* <Button variant='outlined'>Logout</Button> */}
        </li>
      </ul>
    </div>
  );
}
