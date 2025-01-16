import React, { useContext } from 'react';
import { UserContext } from './KorisnikContext'; 
import { Link } from 'react-router-dom';
import '../css folder/Navbar.css';

const Navbar = () => {
  const { user, loading } = useContext(UserContext);

  console.log('Loading:', loading);
  console.log('User:', user);

  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <Link to="/pocetna">Logo</Link>
      </div>
      <ul className="navbar-links">
        <li><Link to="/pocetna">Početna</Link></li>
        {!loading && user ? (
          <>
            <li><Link to="/moj-profil">{user.korisnickoIme}</Link></li>
            <li><button onClick={() => {
              localStorage.removeItem('accessToken');
              window.location.href = '/login';
            }}>Odjavi se</button></li>
          </>
        ) : (
          <>
            <li><Link to="/login">Login</Link></li>
            <li><Link to="/register">Registracija</Link></li>
          </>
        )}
      </ul>
    </nav>
  );
};

export default Navbar;
