import '../css folder/Figurica.css';

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getFiguricaById } from '../api calls/figurice_api';

const Figurica = () => {
  const [figurica, setFigurica] = useState({});
  const [loading, setLoading] = useState(true);
  const [greska, setGreska] = useState(false);
  const { idFigurice } = useParams();

  const dobijFiguricu = async () => {
    try {
      const dobijenaFigurica = await getFiguricaById(idFigurice);
      setFigurica(dobijenaFigurica);
    } catch (error) {
      setGreska("Greška prilikom dobijanja figurice.");
      console.error("Greška prilikom dobijanja figurice: ", error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    dobijFiguricu();
  }, []);

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
          alt={figurica.naslov || "Figurica"}
        />
      </div>

      
      <div className="figurica-opis">
        <h1>{figurica.naslov || "Nepoznata figurica"}</h1>
        <p>{figurica.opis || "Opis nije dostupan."}</p>
        <p>Kategorija: {figurica.kategorija || "Kategorija nije dostupna."}</p>
        <p>Stanje: {figurica.stanje || "Stanje nije dostupno."}</p>
        <p>Cena: {figurica.cena ? `${figurica.cena} RSD` : "Cena nije dostupna."}</p>
        <p>Datum kreiranja: {figurica.datumKreiranja || "Datum nije dostupan."}</p>
      </div>

      
      <div className="figurica-korisnik">
        <h2>Informacije o korisniku</h2>
        <p>Korisničko ime: {figurica.korisnik?.korisnickoIme || "Nije dostupno"}</p>
        <p>Email: {figurica.korisnik?.email || "Nije dostupan"}</p>
        <p>Uloga: {figurica.korisnik?.uloga || "Nije dostupna"}</p>
        <p>Datum registracije: {figurica.korisnik?.datumKreiranja || "Datum nije dostupan"}</p>
      </div>
    </div>
  );
};

export default Figurica;
