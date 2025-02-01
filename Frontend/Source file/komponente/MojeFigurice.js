import React, { useEffect, useState } from 'react';
import { mojeFigurice } from '../api calls/figurice_api';
import { useNavigate } from 'react-router-dom';
import '../css folder/Pocetna.css';

const MojeFigurice = () => {
  const [figurice, setFigurice] = useState([]);
  const [greska, setGreska] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigacija = useNavigate();

  const dohvatiMojeFigurice = async () => {
    try {
      const dobijeneFigurice = await mojeFigurice();
      setFigurice(dobijeneFigurice); // Ažurira stanje
    } catch (error) {
      console.error("Desila se greška prilikom prikaza figurica:", error);
      setGreska("Desila se greška prilikom prikaza figurica.");
    } finally {
      setLoading(false); // Postavlja učitavanje na false
    }
  };

  const pogledajFiguricu = (id) => {
    if (id) {
      navigacija(`/figurica/${id}`);
    } else {
      console.error("ID figurice nije dostupan.");
    }
  };

  useEffect(() => {
    dohvatiMojeFigurice();
  }, []);

  return (
    <div className="pocetna-page">
      {greska && <p className="pocetna-error-message">{greska}</p>}
      <h1>Moje figurice</h1>
      <button
        className="profil-btn"
        onClick={() => navigacija('/kreiraj-figuricu')}
      >
        Kreiraj Figuricu
      </button>
      <div className="pocetna-posts">
        {loading ? (
          <>
            <div className="pocetna-spinner"></div>
            <p>Učitavanje figurica...</p>
          </>
        ) : (
          <>
            {figurice.length > 0 ? (
              figurice.map((figurica) => (
                <div
                  key={figurica.id}
                  className="pocetna-post-box"
                  onClick={() => pogledajFiguricu(figurica.id)}
                >
                  <img
                    src={`/publicslike/${figurica.slikaUrl}`}
                    alt={figurica.naslov || "Figurica"}
                    className="pocetna-slika"
                  />
                  <p>{`${figurica.naslov} - ${figurica.cena} RSD`}</p>
                </div>
              ))
            ) : (
              <div className="empty-state">
                <p>Trenutno nemate figurica za prikaz.</p>
              </div>
            )}
          </>
        )}
      </div>
    </div>
  );
};

export default MojeFigurice;
