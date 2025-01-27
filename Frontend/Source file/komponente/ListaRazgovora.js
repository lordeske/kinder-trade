import React, { useEffect, useState, useContext } from "react";
import { UserContext } from "./KorisnikContext";
import { getRazgovori } from "../api calls/chat_api";
import { useNavigate } from "react-router-dom";




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
        <div>
            <h1>Razgovori</h1>
            {razgovori.length > 0 ? (
                <ul>
                    {razgovori.map((sagovornik, index) => (
                        <li key={index}>
                            <button onClick={() => otvoriChat(sagovornik)}>
                                Razgovor sa: {sagovornik}
                            </button>
                        </li>
                    ))}
                </ul>
            ) : (
                <p>Nema razgovora.</p>
            )}
        </div>
    )
}

export default ListaRazgovora
