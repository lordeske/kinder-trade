import React, { useEffect, useState, useContext } from "react";
import { UserContext } from "./KorisnikContext";
import { getRazgovori } from "../api calls/chat_api";
import { useNavigate } from "react-router-dom";
import '../css folder/ListaRazgovora.css';





const ListaRazgovora = () => {


    const { user } = useContext(UserContext);
    const [razgovori, setRazgovori] = useState([]);
    const [loading, setLoading] = useState(true);
    const [greska, setGreska] = useState(null);
    const navigacija = useNavigate();




    useEffect(() => {
        const dobijRazgovore = async () => {
            try {
                const data = await getRazgovori(user.korisnickoIme);
                setRazgovori(data);
            } catch (error) {
                setGreska("Neuspjesno dobijanje razgovora.");
            } finally {
                setLoading(false);
            }
        };

        if (user) {
            dobijRazgovore();
        }
    }, [user]);




    const otvoriChat = (sagovornik) => {


        navigacija(`/chat/${sagovornik}`)  //Otvaranje druge stranice sa sa sa chatom korisnika 2
    }

    if (loading) return <p>Učitavanje razgovora...</p>;
    if (greska) return <p>{greska}</p>;




    return (
        <div className="lista-razgovora-container">
            <h1 className="lista-razgovora-naslov">Razgovori</h1>
            {razgovori.length > 0 ? (
                <ul className="lista-razgovora">
                    {razgovori.map((sagovornik, index) => (
                        <li key={index} className="lista-razgovora-item">
                            <button className="lista-razgovora-dugme" onClick={() => otvoriChat(sagovornik)}>
                                Razgovor sa: {sagovornik}
                                <span className="lista-razgovora-ikonica">➝</span>
                            </button>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Nema razgovora.</p>
            )}
        </div>
    );
    
}

export default ListaRazgovora
