import { useState } from "react";
import React from "react";
import { useNavigate } from 'react-router-dom';
import { registracija } from "../api calls/auth";
import '../css folder/Login.css';






const Login = () => {
    const [korisnickoIme, setKorisnickoIme] = useState("");
    const [email, setEmail] = useState("");
    const [lozinka, setLozinka] = useState("");
    const [poruka, setPoruka] = useState("");
    const [greska, setGreska] = useState("");
    const [loading, setLoading] = useState(false);

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    const passwordRegex = /^(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{6,}$/;

    const navigacija = useNavigate();


    const handleInputCiscenje = (postavljac) => (e) => {
        postavljac(e.target.value)
        setGreska("");
        setPoruka("")


    }

    const kreirajProfil = async (e) => {
        e.preventDefault();

        setGreska("");
        setPoruka("");
        setLoading(true);

        if (!emailRegex.test(email)) {
            setGreska("Unesite validan email.");
            setLoading(false);
            return;
        }


        if (!passwordRegex.test(lozinka)) {
            setGreska("Lozinka mora imati najmanje 6 karaktera, jedno veliko slovo, broj i specijalni karakter.");
            setLoading(false);
            return;
        }


        const korisnik = {
            korisnickoIme,
            email,
            lozinka,
        };

        try {
            await registracija(korisnik);
            setKorisnickoIme("");
            setLozinka("");
            setEmail("");
            setPoruka("Profil uspešno kreiran!");
            navigacija("/loginStrana")

        } catch (error) {
            console.error("Greška prilikom kreiranja korisnika:", error);
            setGreska("Greška prilikom kreiranja profila. Pokušajte ponovo.");
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
            <h1 className="title">Kreiraj profil</h1>
            <form onSubmit={kreirajProfil}>
              <input
                type="text"
                placeholder="Vaše korisničko ime:"
                id="name"
                value={korisnickoIme}
                onChange={handleInputCiscenje(setKorisnickoIme)}
                required
              />
              <input
                type="email"
                placeholder="Email:"
                id="email"
                value={email}
                onChange={handleInputCiscenje(setEmail)}
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
                {loading ? "Kreiranje..." : "Kreiraj profil"}
              </button>
              
              <a  onClick={ () => navigacija("/loginStrana")}>Imas nalog? Loguj se!</a>
             
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

export default Login;
