import React, { useEffect, useState } from 'react';
import { getRandomFigurice } from '../api calls/figurice_api';
import { useNavigate } from 'react-router-dom';
import '../css folder/Pocetna.css';

const Pocetna = () => {
  const [figurice, setFigurice] = useState([]);
  const [greska, setGreska] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigacija = useNavigate();

  const dobijRandomFigurice = async () => {
    try {
      const dobijeneFigurice = await getRandomFigurice(15);
      setFigurice(dobijeneFigurice);
    } catch (error) {
      setGreska("Greška prilikom dobijanja figurica.");
      console.error("Greška prilikom dobijanja figurica: ", error);
    } finally {
      setLoading(false);
    }
  };

  const pogledajVlasnika = (vlasnik) => {
    if (vlasnik) {
      navigacija(`/profil/${vlasnik}`);
    } else {
      console.log("Vlasnik nije dostupan.");
    }
  };


  const pogledajFiguricu = (id) => {
    if (id) {
      navigacija(`/figurica/${id}`);
    } else {
      console.log("ID figurice nije dostupan.");
    }
  };




  useEffect(() => {
    dobijRandomFigurice();
  }, []);

  return (
    <div className="pocetna-page">
      {greska && <p className="pocetna-error-message">{greska}</p>}

      <div className="pocetna-posts">
        {loading ? (
          <>
            <div className="pocetna-spinner"></div>
            <p>Učitavanje figurica...</p>
          </>
        ) : (
          <>
            {figurice.length > 0 ? (
              figurice.map((figurica, index) => (
                <div
                  key={index}
                  className="pocetna-post-box"
                  onClick={() => pogledajFiguricu(figurica.idFigurice)}
                >
                  <img
                    src={`/publicslike/${figurica.naslov}.jpg`}
                    alt={figurica.naslov || "Figurica"}
                    className="pocetna-slika"
                  />
                  <p>{figurica.naslov} {figurica.cena} RSD</p>

                  <div className="pocetna-button-container">
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
              ))
            ) : (
              <p>Nema dostupnih figurica za prikaz.</p>
            )}
          </>

        )}
      </div>
    </div>
  );
};

export default Pocetna;


