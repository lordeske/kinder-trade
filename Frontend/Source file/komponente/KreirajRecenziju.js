import React, { useState, useContext } from "react";
import { useParams } from "react-router-dom";
import "../css folder/KreirajRecenziju.css";
import { kreirajRecenziju } from "../api calls/recenzije_api";

const KreirajRecenziju = () => {
    const { korisnickoIme } = useParams();
    

    const [ocena, setOcena] = useState(0);
    const [komentar, setKomentar] = useState("");
    const [poruka, setPoruka] = useState("");
    const [greska, setGreska] = useState("");
    const [loading, setLoading] = useState(false);

    const handleOcena = (value) => {
        setOcena(value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setPoruka("");
        setGreska("");
        setLoading(true);

        const recenzijaPodaci = {

            imeRecenziranog: korisnickoIme,
            ocena,
            komentar,
        };

        try {
            await kreirajRecenziju(recenzijaPodaci);
            setPoruka("Recenzija je uspešno dodata!");
            setOcena(0);
            setKomentar("");
        } catch (error) {
            console.error("Greška prilikom kreiranja recenzije:", error);
            setGreska("Došlo je do greške prilikom kreiranja recenzije. Pokušajte ponovo.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="kreiraj-recenziju-page">
            <h1>Ostavi recenziju za {korisnickoIme}</h1>
            <form onSubmit={handleSubmit} className="recenzija-form">
                <div className="rating-container">
                    <p>Ocena:</p>
                    {[1, 2, 3, 4, 5].map((value) => (
                        <button
                            type="button"
                            key={value}
                            className={`rating-star ${value <= ocena ? "selected" : ""}`}
                            onClick={() => handleOcena(value)}
                        >
                            ★
                        </button>
                    ))}
                </div>
                <textarea
                    placeholder="Unesite komentar..."
                    value={komentar}
                    onChange={(e) => setKomentar(e.target.value)}
                    required
                ></textarea>
                <button type="submit" disabled={loading} className="submit-btn">
                    {loading ? "Dodavanje..." : "Dodaj recenziju"}
                </button>
            </form>
            {poruka && <p className="success-message">{poruka}</p>}
            {greska && <p className="error-message">{greska}</p>}
        </div>
    );
};

export default KreirajRecenziju;
