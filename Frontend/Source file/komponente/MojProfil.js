import React, { useContext } from 'react';
import { UserContext } from './KorisnikContext';
import { useNavigate } from 'react-router-dom';
import '../css folder/MojProfil.css';

const MojProfil = () => {
  const { user, loading } = useContext(UserContext);
  const navigacija = useNavigate();


  

  if (loading) {
    return <div>Učitavanje...</div>;
  }

  if (!user) {
    return <div>Morate se prijaviti da biste videli ovu stranicu.</div>;
  }

  const formatirajDatum = (datum) => {
    const parsedDate = new Date(datum);
    return isNaN(parsedDate)
      ? 'Nevalidan datum'
      : new Intl.DateTimeFormat('en', {
          day: 'numeric',
          month: 'long',
          year: 'numeric',
        }).format(parsedDate);
  };

  return (
    <div className="moj-profil-page">
      <div className="moj-profil-header">
        <img
          src={user?.slika ? `/publicslike/${user.slika}` : '/publicslike/avatar.jpeg'}
          alt="Profilna slika"
          className="profil-slika"
        />
        <h1>{user.korisnickoIme}</h1>
        <p>Email: {user.email}</p>
        <p>Uloga: {user.uloga}</p>
        <p>Datum kreiranja: {formatirajDatum(user.datumKreiranja)}</p>
      </div>

      <div className="moj-profil-buttons">
        
        <button
          className="profil-btn secondary"
          onClick={() => navigacija('/azuriraj-profil')}
        >
          Ažuriraj Profil
        </button>
      </div>
    </div>
  );
};

export default MojProfil;
