import React, { useContext, useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { prikaziKorisnikaDrugima, getPredlozeniKorisnici } from '../api calls/korisnik_api';
import { getFiguriceZaKorisnickoIme } from '../api calls/figurice_api';
import { UserContext } from './KorisnikContext'; 
import '../css folder/ProfilKorisnika.css';

const ProfilKorisnika = () => {
  const { korisnickoIme } = useParams();
  const navigacija = useNavigate();
  const { user, loading } = useContext(UserContext);

  const [korisnik, setKorisnik] = useState({});
  const [figurice, setFigurice] = useState([]);
  const [predlozeniKorisnici, setPredlozeniKorisnici] = useState([]);
  const [loadingKorisnik, setLoadingKorisnik] = useState(true);
  const [loadingFigurice, setLoadingFigurice] = useState(true);
  const [loadingPredlozeniKorisnici, setLoadingPredlozeniKorisnici] = useState(true);
  const [greska, setGreska] = useState(null);


  useEffect(() => {
    if (!loading && user && user.korisnickoIme === korisnickoIme) {
      navigacija('/moj-profil');
    }
  }, [loading, user, korisnickoIme, navigacija]);

  const dobijKorisnika = async (imeKorisnika) => {
    try {
      const dobijeniKorisnik = await prikaziKorisnikaDrugima(imeKorisnika);
      setKorisnik(dobijeniKorisnik);
    } catch (error) {
      console.error('Greška prilikom dobijanja korisnika:', error);
      setGreska('Desila se greška prilikom prikaza korisnika.');
    } finally {
      setLoadingKorisnik(false);
    }
  };

  const dobijFiguriceKorisnika = async (imeKorisnika) => {
    try {
      const dobijeneFigurice = await getFiguriceZaKorisnickoIme(imeKorisnika);
      setFigurice(dobijeneFigurice);
    } catch (error) {
      console.error('Greška prilikom dobijanja figurica korisnika:', error);
      setGreska('Desila se greška prilikom prikaza figurica.');
    } finally {
      setLoadingFigurice(false);
    }
  };

  const dobijPredlozeneKorisnike = async () => {
    try {
      const dobijeniPredlozeniKorisnici = await getPredlozeniKorisnici(korisnickoIme);
      setPredlozeniKorisnici(dobijeniPredlozeniKorisnici);
    } catch (error) {
      console.error('Greška prilikom dobijanja predloženih korisnika:', error);
      setGreska('Desila se greška prilikom prikaza predloženih korisnika.');
    } finally {
      setLoadingPredlozeniKorisnici(false);
    }
  };

  const pogledajFiguricu = (id) => {
    if (id) {
      navigacija(`/figurica/${id}`);
    } else {
      console.log('ID figurice nije dostupan.');
    }
  };

 
  useEffect(() => {
    dobijKorisnika(korisnickoIme);
    dobijFiguriceKorisnika(korisnickoIme);
    dobijPredlozeneKorisnike();
  }, [korisnickoIme]);

  return (
    <div className="profile-page">
      {/* Informacije o korisniku */}
      <div className="profile-info">
        {loadingKorisnik ? (
          <>
            <div className="spinner"></div>
            <p>Učitavanje korisničkih podataka...</p>
          </>
        ) : (
          <>
            <img
              src={korisnik.slika || '/publicslike/avatar.jpeg'}
              alt="Profilna slika"
              className="profile-avatar"
            />
            <h2>{korisnik.korisnickoIme || 'Nepoznato korisničko ime'}</h2>
            <p>{`Datum kreiranja: ${korisnik.datumKreiranja ? korisnik.datumKreiranja : 'Nepoznato'}`}</p>
          </>
        )}
      </div>

      {/* Figurice korisnika */}
      <div className="posts">
        {loadingFigurice ? (
          <>
            <div className="spinner"></div>
            <p>Učitavanje figurica...</p>
          </>
        ) : (
          <>
            {figurice.length > 0 ? (
              figurice.map((figurica) => (
                <div
                  key={figurica.idFigurice}
                  className="post-box"
                  onClick={() => pogledajFiguricu(figurica.idFigurice)}
                >
                  <img
                    src={`/publicslike/${figurica.naslov}.jpg`}
                    alt={figurica.naslov || 'Figurica'}
                    className="figurica-slika"
                  />
                  <p>{figurica.naslov}</p>
                  <p>{figurica.cena ? `${figurica.cena} RSD` : 'Cena nije dostupna'}</p>
                </div>
              ))
            ) : (
              <p>Korisnik nema dostupnih figurica za prikaz.</p>
            )}
          </>
        )}
      </div>


      {/* Predloženi korisnici */}
      <div className="suggested-profiles">
        {loadingPredlozeniKorisnici ? (
          <>
            <div className="spinner"></div>
            <p>Učitavanje predloženih profila...</p>
          </>
        ) : (
          <>
            <h3>Predloženi Profili</h3>
            {predlozeniKorisnici.length > 0 ? (
              predlozeniKorisnici.map((predlozeniKorisnik) => (
                <div
                  key={predlozeniKorisnik.korisnickoIme}
                  className="suggested-profile"
                  onClick={() => navigacija(`/profil/${predlozeniKorisnik.korisnickoIme}`)}
                >
                  <img
                    src={predlozeniKorisnik.slika || '/publicslike/avatar.jpeg'}
                    alt={`Slika korisnika ${predlozeniKorisnik.korisnickoIme}`}
                    className="suggested-profile-avatar"
                  />
                  <p>{predlozeniKorisnik.korisnickoIme}</p>
                </div>
              ))
            ) : (
              <p>Nema predloženih profila za prikaz.</p>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default ProfilKorisnika;
