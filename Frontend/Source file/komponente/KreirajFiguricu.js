import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { kreirajFiguricu } from '../api calls/figurice_api';
import '../css folder/KreirajFiguricu.css';

const KreirajFiguricu = () => {
  const [naslov, setNaslov] = useState('');
  const [opis, setOpis] = useState('');
  const [cena, setCena] = useState('');
  const [kategorija, setKategorija] = useState('');
  const [stanje, setStanje] = useState('');
  const [slikaUrl, setSlikaUrl] = useState('');
  const [greska, setGreska] = useState('');
  const [poruka, setPoruka] = useState('');
  const [loading, setLoading] = useState(false);

  const navigacija = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();
    setGreska('');
    setPoruka('');
    setLoading(true);

    if (!naslov || !cena || !kategorija || !stanje) {
      setGreska('Sva obavezna polja moraju biti popunjena.');
      setLoading(false);
      return;
    }

    const figuricaData = {
      naslov,
      opis,
      cena: parseFloat(cena),
      kategorija,
      stanje,
      slikaUrl,
    };

    try {
      await kreirajFiguricu(figuricaData);
      
      setPoruka('Figurica uspešno kreirana!');
      navigacija('/moj-profil');
    } catch (error) {
      console.error('Greška prilikom kreiranja figurice:', error);
      setGreska('Desila se greška. Pokušajte ponovo.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="kreiraj-figuricu-page">
      <h1>Kreiraj Figuricu</h1>
      <form onSubmit={handleSubmit} className="kreiraj-figuricu-form">
        <input
          type="text"
          placeholder="Naslov figurice *"
          value={naslov}
          onChange={(e) => setNaslov(e.target.value)}
          required
        />
        <textarea
          placeholder="Opis figurice"
          value={opis}
          onChange={(e) => setOpis(e.target.value)}
        ></textarea>
        <input
          type="number"
          placeholder="Cena figurice (RSD) *"
          value={cena}
          onChange={(e) => setCena(e.target.value)}
          required
        />
        <select
          value={kategorija}
          onChange={(e) => setKategorija(e.target.value)}
          required
        >
          <option value="">Izaberite kategoriju *</option>
          <option value="akcione">Akcione</option>
          <option value="kolekcionarske">Kolekcionarske</option>
          <option value="animirane">Animirane</option>
        </select>
        <select
          value={stanje}
          onChange={(e) => setStanje(e.target.value)}
          required
        >
          <option value="">Izaberite stanje *</option>
          <option value="novo">Novo</option>
          <option value="korisceno">Korišćeno</option>
        </select>
        <input
          type="text"
          placeholder="URL slike figurice"
          value={slikaUrl}
          onChange={(e) => setSlikaUrl(e.target.value)}
        />
        <button type="submit" disabled={loading}>
          {loading ? 'Kreiranje...' : 'Kreiraj Figuricu'}
        </button>
      </form>
      {greska && <p className="error-message">{greska}</p>}
      {poruka && <p className="success-message">{poruka}</p>}
    </div>
  );
};

export default KreirajFiguricu;
