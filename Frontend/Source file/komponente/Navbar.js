import React from 'react';
import { Link } from 'react-router-dom';
import '../css folder/Navbar.css';


const Navbar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <Link to="/pocetna">Logo</Link>
      </div>
      <ul className="navbar-links">
        <li><Link to="/pocetna">Početna</Link></li>
        <li><Link to="/profil">Profil</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;
