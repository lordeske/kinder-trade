import React, { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom";
import { UserContext } from "./KorisnikContext";
import { getFiguriceZaKorisnickoIme } from "../api calls/figurice_api";
import { kreirajTrgovinu } from "../api calls/trgovina_api";
import { useDrag, useDrop } from "react-dnd";


const ItemType = {
    FIGURICA: "FIGURICA",
};

const KreirajTrgovinu = () => {
    const { user } = useContext(UserContext);
    const { korisnickoIme } = useParams();

    const [njegoveFigurice, setNjegoveFigurice] = useState([]);
    const [mojeFigurice, setMojeFigurice] = useState([]);
    const [ponudjeneFigurice, setPonudjeneFigurice] = useState([]);
    const [trazeneFigurice, setTrazeneFigurice] = useState([]);
    const [greska, setGreska] = useState(null);

    useEffect(() => {
        if (user && user.korisnickoIme) {
            getFiguriceZaKorisnickoIme(user.korisnickoIme)
                .then(setMojeFigurice)
                .catch((err) => setGreska("Greška pri učitavanju figurica"));
        }
        if (korisnickoIme) {
            getFiguriceZaKorisnickoIme(korisnickoIme)
                .then(setNjegoveFigurice)
                .catch((err) => setGreska("Greška pri učitavanju figurica"));
        }
    }, [korisnickoIme, user]);

    const handleKreirajTrgovinu = async () => {
        try {
            const trgovinaDto = {
                posiljalac: user.korisnickoIme,
                primalac: korisnickoIme,
                ponudjeneFigurice: ponudjeneFigurice.map((f) => f.idFigurice),
                trazeneFigurice: trazeneFigurice.map((f) => f.idFigurice),
                
            };

            await kreirajTrgovinu(trgovinaDto);
            alert("Trgovina uspešno kreirana!");
        } catch (error) {
            console.error("Greška pri kreiranju trgovine:", error);
            setGreska("Greška pri kreiranju trgovine");
        }
    };

    return (
        <div className="container">
            <h2>Kreiraj Trgovinu sa {korisnickoIme}</h2>

            <div className="trade-section">
                {/* Moje figurice */}
                <FiguriceLista
                    figurice={mojeFigurice}
                    title="Moje Figurice"
                    onDropFigurica={(fig) =>
                        setPonudjeneFigurice([...ponudjeneFigurice, fig])
                    }
                />

                {/* Box za ponudjene figurice */}
                <FiguriceBox
                    title="Ponudjene Figurice"
                    figurice={ponudjeneFigurice}
                    setFigurice={setPonudjeneFigurice}
                />

                {/* Box za trazene figurice */}
                <FiguriceBox
                    title="Tražene Figurice"
                    figurice={trazeneFigurice}
                    setFigurice={setTrazeneFigurice}
                />

                {/* Njegove figurice */}
                <FiguriceLista
                    figurice={njegoveFigurice}
                    title="Njegove Figurice"
                    onDropFigurica={(fig) =>
                        setTrazeneFigurice([...trazeneFigurice, fig])
                    }
                />
            </div>

            <button onClick={handleKreirajTrgovinu} className="trade-button">
                Kreiraj Trgovinu
            </button>
        </div>
    );
};

// Komponenta za prikaz figurica
const FiguriceLista = ({ figurice, title, onDropFigurica }) => {
    return (
        <div className="figurice-container">
            <h3>{title}</h3>
            {figurice.map((fig) => (
                <FiguricaMap key={fig.idFigurice} figurica={fig} onDrop={onDropFigurica} />
            ))}
        </div>
    );
};


const FiguricaMap = ({ figurica, onDrop }) => {
    const [{ isDragging }, drag] = useDrag(() => ({
        type: ItemType.FIGURICA,
        item: figurica,
        collect: (monitor) => ({
            isDragging: !!monitor.isDragging(),
        }),
    }));

    return (
        <div
            ref={drag}
            className="figurica"
            style={{ opacity: isDragging ? 0.5 : 1 }}
        >
            <img src={figurica.slikaUrl} alt={figurica.naslov} />
            <p>{figurica.naslov}</p>
        </div>
    );
};


const FiguriceBox = ({ title, figurice, setFigurice }) => {
    const [{ isOver }, drop] = useDrop({
        accept: ItemType.FIGURICA,
        drop: (item) => {
            setFigurice((prev) => [...prev, item]);
        },
        collect: (monitor) => ({
            isOver: !!monitor.isOver(),
        }),
    });

    return (
        <div ref={drop} className={`figurice-box ${isOver ? "highlight" : ""}`}>
            <h3>{title}</h3>
            {figurice.map((fig) => (
                <FiguricaMap key={fig.id} figurica={fig} />
            ))}
        </div>
    );
};

export default KreirajTrgovinu;
