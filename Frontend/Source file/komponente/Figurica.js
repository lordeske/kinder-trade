import '../css folder/Figurica.css';
import { useNavigate } from 'react-router-dom';

import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import { getFiguricaById } from '../api calls/figurice_api';
import { dodajFiguricuUOmiljene, daLiJeFiguricaUOmiljenim, obrisiFiguricuIzOmiljenih } from '../api calls/omiljeni_api';

const Figurica = () => {
  const [figurica, setFigurica] = useState({});
  const [loading, setLoading] = useState(true);
  const [greska, setGreska] = useState(false);
  const [uOmiljenima, setUOmiljenima] = useState(false);
  const { idFigurice } = useParams();
  const navigacija = useNavigate();


  const pogledajVlasnika = (vlasnik) => {
    if (vlasnik) {
      navigacija(`/profil/${vlasnik}`);
    } else {
      console.log("Vlasnik nije dostupan.");
    }
  };



  useEffect(() => {

    const proveriOmiljeno = async () => {


      try {


        const rezultat = await daLiJeFiguricaUOmiljenim(idFigurice);
        setUOmiljenima(rezultat)


      }
      catch (error) {

        console.error("Greška prilikom provere omiljenih:", error);

      }


    }


    proveriOmiljeno();

  }, [idFigurice])


  const handleOmiljeno = async () => {


    try {


      if (uOmiljenima) {

        await obrisiFiguricuIzOmiljenih(idFigurice);
        console.log("obirsna iz omiljenih")

      }
      else {


        await dodajFiguricuUOmiljene(idFigurice);
        console.log("dodata u omiljeno")


      }
      setUOmiljenima(!uOmiljenima);

    }
    catch (error) {

      console.error("Greška prilikom upravljanja omiljenom figuricom:", error);

    }






  }



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
        <button
          className="pocetna-button pocetna-button-primary"
          onClick={(e) => {
            e.stopPropagation();
            handleOmiljeno();
          }}
        >
          {uOmiljenima ? "Izbrisi iz omiljenih" : "Dodaj u omiljeno"}
        </button>
      </div>


      <div className="figurica-korisnik">
        <h2>Informacije o korisniku</h2>
        <p>Vlasnik figurice: {figurica.vlasnikFigurice || "Nije dostupno"}</p>
        <button
          className="pocetna-button pocetna-button-primary"
          onClick={(e) => {
            e.stopPropagation();
            pogledajVlasnika(figurica.vlasnikFigurice);
          }}
        >
          Pogledaj vlasnika
        </button>
      </div>
    </div>
  );
};

export default Figurica;
