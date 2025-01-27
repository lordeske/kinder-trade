import React, { useContext, useState } from 'react';
import { UserContext } from './KorisnikContext'; 
import { Link, useNavigate } from 'react-router-dom';
import '../css folder/Navbar.css';
import { logout } from '../api calls/auth';

const Navbar = () => {
  const { user, loading } = useContext(UserContext);
  const [isDropdownOpen, setIsDropdownOpen] = useState(false);
  const navigate = useNavigate();

  const toggleDropdown = () => {
    setIsDropdownOpen((prev) => !prev);
  };

  const handleLogout = async () => {
  
    await logout();
    window.location.href = '/login';
  };

  return (
    <nav className="navbar">
      <div className="navbar-logo">
        <Link to="/pocetna">Logo</Link>
      </div>
      <ul className="navbar-links">
        <li><Link to="/pocetna">Početna</Link></li>
        {!loading && user ? (
          <>
            
            <li><Link to="/moje-figurice">Figurice</Link></li>
            <li><Link to="/moje-omiljene">Omiljene</Link></li>
            <li><Link to="/razgovori">Mes</Link></li>
            <li className="navbar-user-dropdown">
              <button onClick={toggleDropdown} className="user-button">
                {user.korisnickoIme} ▼
              </button>
              {isDropdownOpen && (
                <ul className="dropdown-menu">
                  <li>
                    <button onClick={() => navigate('/moj-profil')}>Pogledaj profil</button>
                  </li>
                  <li>
                    <button onClick={handleLogout}>Odjavi se</button>
                  </li>
                </ul>
              )}
            </li>
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
