import { useState } from "react";
import React from "react";
import { useNavigate } from 'react-router-dom';
import { login } from "../api calls/auth";
import '../css folder/Login.css';






const LoginStrana = () => {
    const [korisnickoIme, setKorisnickoIme] = useState("");
    const [lozinka, setLozinka] = useState("");
    const [poruka, setPoruka] = useState("");
    const [greska, setGreska] = useState("");
    const [loading, setLoading] = useState(false);

    

    const navigacija = useNavigate();


    const handleInputCiscenje = (postavljac) => (e) => {
        postavljac(e.target.value)
        setGreska("");
        setPoruka("")


    }

    const loginProfil = async (e) => {
        e.preventDefault();

        setGreska("");
        setPoruka("");
        setLoading(true);

       


        const korisnik = {
            korisnickoIme,
            lozinka
        };

        try {
            await login(korisnik);
            setKorisnickoIme("");
            setLozinka("");
            setPoruka("Uspesno logovan");
            
            navigacija("/pocetna")

        } catch (error) {
            console.error("Greška prilikom logovanja korisnika:", error);
            setGreska("Greška prilikom logovanja profila. Pokušajte ponovo.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="login-page">
      <div className="container">
        <div className="login-box">
          <div className="circle circle-one"></div>
          <div className="form-box">
            <h1 className="title">Login</h1>
            <form onSubmit={loginProfil}>
              <input
                type="text"
                placeholder="Vaše korisničko ime:"
                id="name"
                value={korisnickoIme}
                onChange={handleInputCiscenje(setKorisnickoIme)}
                required
              />
              
              <input
                type="password"
                placeholder="Lozinka:"
                id="password"
                value={lozinka}
                onChange={handleInputCiscenje(setLozinka)}
                required
              />
              <button className="submit-btn" type="submit" disabled={loading}>
                {loading ? "Login..." : "Loginovanje na profil"}
              </button>
              <a  onClick={ () => navigacija("/login")}>Registruj se</a>
            </form>
            {loading && <div className="spinner"></div>}
            {!loading && poruka && <p className="message-success">{poruka}</p>}
            {!loading && greska && <p className="message-error">{greska}</p>}
          </div>
          <div className="circle circle-two"></div>
        </div>
        <div className="theme-btn-container"></div>
      </div>
    </div>

    );
};

export default LoginStrana;
