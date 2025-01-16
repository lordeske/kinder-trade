import React, { useState, useContext } from "react";
import { UserContext } from "./KorisnikContext";
import { azurirajKorisnika } from "../api calls/korisnik_api"; 
import "../css folder/AzurirajProfil.css";

const AzurirajProfil = () => {
  const { user } = useContext(UserContext);
  const [korisnickoIme, setKorisnickoIme] = useState(user.korisnickoIme);
  const [email, setEmail] = useState(user.email);
  const [slika, setSlika] = useState(user.slika || "");
  const [poruka, setPoruka] = useState("");
  const [greska, setGreska] = useState("");
  const [loading, setLoading] = useState(false);

  const handleAzurirajProfil = async (e) => {
    e.preventDefault();
    setPoruka("");
    setGreska("");
    setLoading(true);

    try {
      const azuriraniPodaci = { korisnickoIme, email, slika };
      await azurirajKorisnika(azuriraniPodaci); 
      setPoruka("Profil je uspešno ažuriran!");
    } catch (error) {
      console.error("Greška prilikom ažuriranja profila:", error);
      setGreska("Došlo je do greške prilikom ažuriranja profila. Pokušajte ponovo.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="azuriraj-profil-page">
      <h1>Ažuriraj Profil</h1>
      <form className="azuriraj-profil-form" onSubmit={handleAzurirajProfil}>
        <label>
          Korisničko ime:
          <input
            type="text"
            value={korisnickoIme}
            onChange={(e) => setKorisnickoIme(e.target.value)}
            required
          />
        </label>
        <label>
          Email:
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </label>
        <label>
          URL slike:
          <input
            type="text"
            value={slika}
            onChange={(e) => setSlika(e.target.value)}
          />
        </label>
        <button type="submit" disabled={loading}>
          {loading ? "Ažuriranje..." : "Ažuriraj profil"}
        </button>
      </form>
      {poruka && <p className="success-message">{poruka}</p>}
      {greska && <p className="error-message">{greska}</p>}
    </div>
  );
};

export default AzurirajProfil;
