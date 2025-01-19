import '../css folder/Figurica.css';
import React, { useContext, useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getFiguricaById } from '../api calls/figurice_api';
import { dodajFiguricuUOmiljene, daLiJeFiguricaUOmiljenim, obrisiFiguricuIzOmiljenih } from '../api calls/omiljeni_api';
import { UserContext } from './KorisnikContext';

const Figurica = () => {
  const [figurica, setFigurica] = useState({});
  const [loading, setLoading] = useState(true);
  const [greska, setGreska] = useState(false);
  const [uOmiljenima, setUOmiljenima] = useState(false);
  const [jesiVlasnik, setJesiVlasnik] = useState(false); 
  const { idFigurice } = useParams();
  const navigacija = useNavigate();
  const { user } = useContext(UserContext);

  const pogledajVlasnika = (vlasnik) => {
    if (vlasnik) {
      navigacija(`/profil/${vlasnik}`);
    } else {
      console.log('Vlasnik nije dostupan.');
    }
  };

  const provjeriVlasnistvo = () => {
    if (user && figurica.vlasnikFigurice) {
      setJesiVlasnik(user.korisnickoIme === figurica.vlasnikFigurice);
    }
  };

  const handleOmiljeno = async () => {
    try {
      if (uOmiljenima) {
        await obrisiFiguricuIzOmiljenih(idFigurice);
      } else {
        await dodajFiguricuUOmiljene(idFigurice);
      }
      setUOmiljenima(!uOmiljenima);
    } catch (error) {
      console.error('Greška prilikom upravljanja omiljenom figuricom:', error);
    }
  };

  const dobijFiguricu = async () => {
    try {
      const dobijenaFigurica = await getFiguricaById(idFigurice);
      setFigurica(dobijenaFigurica);

      const uOmiljeno = await daLiJeFiguricaUOmiljenim(idFigurice);
      setUOmiljenima(uOmiljeno);
    } catch (error) {
      setGreska('Greška prilikom dobijanja figurice.');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    dobijFiguricu();
  }, [idFigurice]);

  useEffect(() => {
    provjeriVlasnistvo();
  }, [figurica, user]);

  if (loading) {
    return (
      <div className="figurica-loading">
        <div className="spinner"></div>
        <p>Učitavanje figurice...</p>
      </div>
    );
  }

  if (greska) {
    return <div className="figurica-error">{greska}</div>;
  }

  return (
    <div className="figurica-page">
      <div className="figurica-slika">
        <img
          src={`/publicslike/${figurica.slikaUrl}.jpg`}
          alt={figurica.naslov || 'Figurica'}
        />
      </div>

      <div className="figurica-opis">
        <h1>{figurica.naslov || 'Nepoznata figurica'}</h1>
        <p>{figurica.opis || 'Opis nije dostupan.'}</p>
        <p>Kategorija: {figurica.kategorija || 'Kategorija nije dostupna.'}</p>
        <p>Stanje: {figurica.stanje || 'Stanje nije dostupno.'}</p>
        <p>Cena: {figurica.cena ? `${figurica.cena} RSD` : 'Cena nije dostupna.'}</p>

        {jesiVlasnik ? (
          <button
            className="pocetna-button pocetna-button-primary"
            onClick={() => navigacija(`/azuriraj-figuricu/${idFigurice}`)}
          >
            Ažuriraj figuricu
          </button>
        ) : (
          <button
            className="pocetna-button pocetna-button-primary"
            onClick={handleOmiljeno}
          >
            {uOmiljenima ? 'Izbriši iz omiljenih' : 'Dodaj u omiljeno'}
          </button>
        )}
      </div>

      {!jesiVlasnik && (
        <div className="figurica-korisnik">
          <h2>Informacije o korisniku</h2>
          <p>Vlasnik figurice: {figurica.vlasnikFigurice || 'Nije dostupno'}</p>
          <button
            className="pocetna-button pocetna-button-primary"
            onClick={() => pogledajVlasnika(figurica.vlasnikFigurice)}
          >
            Pogledaj vlasnika
          </button>
        </div>
      )}
    </div>
  );
};

export default Figurica;
