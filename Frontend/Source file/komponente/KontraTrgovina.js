import React, { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom";
import { UserContext } from "./KorisnikContext";
import { getFiguriceZaKorisnickoIme } from "../api calls/figurice_api";
import { counterPonuda } from "../api calls/trgovina_api";
import '../css folder/KreirajTrgovinu.css';

const KontraTrgovina = () => {
    const { user } = useContext(UserContext);
    const { korisnickoIme } = useParams();
    const { idTrgovine } = useParams();

    const [njegoveFigurice, setNjegoveFigurice] = useState([]);
    const [mojeFigurice, setMojeFigurice] = useState([]);
    const [ponudjeneFigurice, setPonudjeneFigurice] = useState([]);
    const [trazeneFigurice, setTrazeneFigurice] = useState([]);
    const [greska, setGreska] = useState(null);

    useEffect(() => {
        if (user && user.korisnickoIme) {
            getFiguriceZaKorisnickoIme(user.korisnickoIme)
                .then(setMojeFigurice)
                .catch(() => setGreska("Greška pri učitavanju figurica"));
        }
        if (korisnickoIme) {
            getFiguriceZaKorisnickoIme(korisnickoIme)
                .then(setNjegoveFigurice)
                .catch(() => setGreska("Greška pri učitavanju figurica"));
        }
    }, [korisnickoIme, user]);

    const handleDodajUPonudjene = (figurica) => {
        if (!ponudjeneFigurice.some(f => f.idFigurice === figurica.idFigurice)) {
            setPonudjeneFigurice([...ponudjeneFigurice, figurica]);
        }
    };

    const handleDodajUTrazene = (figurica) => {
        if (!trazeneFigurice.some(f => f.idFigurice === figurica.idFigurice)) {
            setTrazeneFigurice([...trazeneFigurice, figurica]);
        }
    };

    const handleUkloniIzPonudjenih = (idFigurice) => {

        setPonudjeneFigurice(prev => prev.filter(f => f.idFigurice !== idFigurice));
    };

    const handleUkloniIzTrazenih = (idFigurice) => {

        setTrazeneFigurice(prev => prev.filter(f => f.idFigurice !== idFigurice));
    };


    if(!user)
    {
        return;
    }

    const handleKreirajTrgovinu = async () => {
        try {
            const trgovinaDto = {
                

                ponudjeneFigurice: ponudjeneFigurice.map(f => f.idFigurice),
                trazeneFigurice: trazeneFigurice.map(f => f.idFigurice)

            };
            console.log(trgovinaDto, trgovinaDto)

            await counterPonuda(idTrgovine,trgovinaDto);
            alert("Trgovina uspešno kreirana!");
            setPonudjeneFigurice([]);
            setTrazeneFigurice([]);
        } catch (error) {
            console.error("Greška pri kreiranju trgovine:", error);
            setGreska("Greška pri kreiranju trgovine");
        }
    };

    return (
        <div className="kreiraj-trgovinu-container">
            <h2>Kreiranje Trgovine sa {korisnickoIme}</h2>

            {greska && <p style={{ color: "red" }}>{greska}</p>}

            <div className="figurice-sekcija">
                <h3>Njegove figurice</h3>
                <div className="figurice-lista">
                    {njegoveFigurice.map(figurica => (
                        <div key={figurica.idFigurice} className="figurica">
                            <img src={`/publicslike/${figurica.slikaUrl}`} alt={figurica.naslov} />
                            <p>{figurica.naslov}</p>
                            <button className="trgovina-btn" onClick={() => handleDodajUTrazene(figurica)}>Dodaj u tražene</button>
                        </div>
                    ))}
                </div>
            </div>

            <div className="figurice-sekcija">
                <h3>Moje figurice</h3>
                <div className="figurice-lista">
                    {mojeFigurice.map(figurica => (
                        <div key={figurica.idFigurice} className="figurica">
                            <img src={`/publicslike/${figurica.slikaUrl}`} alt={figurica.naslov} />
                            <p>{figurica.naslov}</p>
                            <button className="trgovina-btn" onClick={() => handleDodajUPonudjene(figurica)}>Dodaj u ponuđene</button>
                        </div>
                    ))}
                </div>
            </div>
            <div className="figurice-sekcija">
                <h3>Ponudjene figurica</h3>
                <div className="figurice-lista">
                    {ponudjeneFigurice.map(figurica => (
                        <div key={figurica.idFigurice} className="figurica">
                            <img src={`/publicslike/${figurica.slikaUrl}`} alt={figurica.naslov} />
                            <p>{figurica.naslov}</p>
                            <button className="trgovina-btn" onClick={() => handleUkloniIzPonudjenih(figurica.idFigurice)}>Ukloni</button>
                        </div>
                    ))}
                </div>
            </div>
            <div className="figurice-sekcija">
                <h3>Trazene figurica</h3>
                <div className="figurice-lista">
                    {trazeneFigurice.map(figurica => (
                        <div key={figurica.idFigurice} className="figurica">
                            <img src={`/publicslike/${figurica.slikaUrl}`} alt={figurica.naslov} />
                            <p>{figurica.naslov}</p>
                            <button className="trgovina-btn" onClick={() => handleUkloniIzTrazenih(figurica.idFigurice)}>Ukloni</button>
                        </div>
                    ))}
                </div>
            </div>


            <button className="trgovina-btn-kreiraj" onClick={handleKreirajTrgovinu} disabled={ponudjeneFigurice.length === 0 || trazeneFigurice.length === 0}>
                Kreiraj Trgovinu
            </button>
        </div>
    );
};

export default KontraTrgovina;
