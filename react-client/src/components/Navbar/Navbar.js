import React from 'react';
import Button from '@mui/material/Button';
import './Navbar.scss';
import logo from './../../assets/images/fake-logo.svg';
import { Link } from 'react-router-dom';

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
          <Link to='/'>Home</Link>
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
          <Link to='/signIn'>
            <Button variant='contained' color='custom'>
              Sign In
            </Button>
          </Link>
          <Link to='/signUp'>
            <Button variant='contained' color='custom'>
              Sign Up
            </Button>
          </Link>
          {/* <Button variant='outlined'>Logout</Button> */}
        </li>
      </ul>
    </div>
  );
}
