import React, { useEffect, useState } from 'react';
import {  } from '../api calls/figurice_api';
import { useNavigate } from 'react-router-dom';
import '../css folder/Pocetna.css';
import { getOmiljeneFigurice } from '../api calls/omiljeni_api';

const MojeOmiljene = () => {
  const [figurice, setFigurice] = useState([]);
  const [greska, setGreska] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigacija = useNavigate();

  

  


  const pogledajFiguricu = (id) => {
    if (id) {
      navigacija(`/figurica/${id}`);
    } else {
      console.log("ID figurice nije dostupan.");
    }
  };


  const dobijOmiljeneFigurice = async () => {
      try {
        const omiljeneFigrice = await getOmiljeneFigurice();
        setFigurice(omiljeneFigrice);
      } catch (error) {
        setGreska("Greška prilikom dobijanja figurica.");
        console.error("Greška prilikom dobijanja figurica: ", error);
      } finally {
        setLoading(false);
      }
    };




  useEffect(() => {
   
    dobijOmiljeneFigurice()
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
                    src={`/publicslike/${figurica.slikaUrl}`}
                    alt={figurica.naslov || "Figurica"}
                    className="pocetna-slika"
                  />
                  <p>{figurica.naslov} {figurica.cena} RSD</p>

                  
                </div>
              ))
            ) : (
              <p>Dodaj figuricu da bi je imao u omiljenim</p>
            )}
          </>

        )}
      </div>
    </div>
  );
};

export default MojeOmiljene;


