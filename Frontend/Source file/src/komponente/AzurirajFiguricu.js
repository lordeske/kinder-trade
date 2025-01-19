import React, { useState, useEffect, useContext } from "react";
import { useParams, useNavigate } from "react-router-dom";
import { azurirajFiguricu, getFiguricaById } from "../api calls/figurice_api";
import "../css folder/AzurirajProfil.css";
import { UserContext } from "./KorisnikContext";

const AzurirajFiguricu = () => {
  const { idFigurice } = useParams();
  const navigacija = useNavigate();
  const { user } = useContext(UserContext);
  const [pristup, setPristup] = useState(false);

  const [naslov, setNaslov] = useState("");
  const [opis, setOpis] = useState("");
  const [cena, setCena] = useState("");
  const [kategorija, setKategorija] = useState("");
  const [stanje, setStanje] = useState("");
  const [slikaUrl, setSlikaUrl] = useState("");

  const [poruka, setPoruka] = useState("");
  const [greska, setGreska] = useState("");
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!user) {
      navigacija("/login");
    } else {
      setPristup(true);
    }
  }, [user, navigacija]);

  useEffect(() => {
    const dobijFiguricu = async () => {
      try {
        const dobijenaFigurica = await getFiguricaById(idFigurice);
        setNaslov(dobijenaFigurica.naslov);
        setOpis(dobijenaFigurica.opis);
        setCena(dobijenaFigurica.cena);
        setKategorija(dobijenaFigurica.kategorija);
        setStanje(dobijenaFigurica.stanje);
        setSlikaUrl(dobijenaFigurica.slikaUrl);
      } catch (error) {
        console.error("Greška prilikom dobijanja figurice:", error);
        setGreska("Došlo je do greške prilikom dobijanja figurice.");
      }
    };

    if (pristup) {
      dobijFiguricu();
    }
  }, [idFigurice, pristup]);

  const handleAzurirajFiguricu = async (e) => {
    e.preventDefault();
    setPoruka("");
    setGreska("");
    setLoading(true);

    try {
      const azuriraniPodaci = { naslov, opis, cena, kategorija, stanje, slikaUrl };
      console.log("Priprema za slanje podataka:", azuriraniPodaci);

      await azurirajFiguricu(idFigurice, azuriraniPodaci);

      setPoruka("Figurica je uspešno ažurirana!");
    } catch (error) {
      console.error("Greška prilikom ažuriranja figurice:", error);
      setGreska("Došlo je do greške prilikom ažuriranja figurice. Pokušajte ponovo.");
    } finally {
      setLoading(false);
    }
  };

  if (!pristup) {
    return null;
  }

  return (
    <div className="azuriraj-profil-page">
      <h1>Ažuriraj Figuricu</h1>
      <form onSubmit={handleAzurirajFiguricu} className="kreiraj-figuricu-form">
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
          {loading ? "Ažuriranje..." : "Ažuriraj Figuricu"}
        </button>
      </form>
      {poruka && <p className="success-message">{poruka}</p>}
      {greska && <p className="error-message">{greska}</p>}
    </div>
  );
};

export default AzurirajFiguricu;
