import React, { useState, useEffect, useContext } from "react";
import { UserContext } from "./KorisnikContext";
import { odbijTrgovinu, povuci, prihvatiTrgovinu, sveMojeTrgovine } from "../api calls/trgovina_api";
import '../css folder/MojeTrgovine.css';
import { useNavigate } from "react-router-dom";

const MojeTrgovine = () => {
    const { user } = useContext(UserContext);
    const [sveTrgovine, setSveTrgovine] = useState([]);
    const [filtriraneTrgovine, setFiltriraneTrgovine] = useState([]);
    const [loading, setLoading] = useState(true);
    const [greska, setGreska] = useState(null);

    const navigacija = useNavigate();

    useEffect(() => {
        if (user && user.korisnickoIme) {
            sveMojeTrgovine()
                .then((res) => {
                    setSveTrgovine(res);
                    setFiltriraneTrgovine(res);
                    setLoading(false);
                })
                .catch(() => {
                    setGreska("Greška pri učitavanju trgovina.");
                    setLoading(false);
                });
        }
    }, [user]);

   
    const handleFiltrirajTrgovine = (status) => {
        if (status === "ALL") {
            setFiltriraneTrgovine(sveTrgovine); 
        } else {
            setFiltriraneTrgovine(sveTrgovine.filter(t => t.status === status));
        }
    };

    const handlePovuciTrgovinu = async (idTrgovine) => {
        try {
            await povuci(idTrgovine);
            setSveTrgovine(prevTrgovine => 
                prevTrgovine.map(trgovina => 
                    trgovina.id === idTrgovine ? { ...trgovina, status: "DECLINED" } : trgovina
                )
            );
    
            setFiltriraneTrgovine(prevTrgovine => 
                prevTrgovine.map(trgovina => 
                    trgovina.id === idTrgovine ? { ...trgovina, status: "DECLINED" } : trgovina
                )
            );
            alert("Trgovina uspešno povučena!");
        } catch (error) {
            console.error("Greška pri povlačenju trgovine:", error);
            alert("Došlo je do greške pri povlačenju trgovine.");
        }
    };

    const handleOdbijPonudu = async (idTrgovine) => {
        try {
            await odbijTrgovinu(idTrgovine);
            setSveTrgovine(prevTrgovine => 
                prevTrgovine.map(trgovina => 
                    trgovina.id === idTrgovine ? { ...trgovina, status: "DECLINED" } : trgovina
                )
            );
    
            setFiltriraneTrgovine(prevTrgovine => 
                prevTrgovine.map(trgovina => 
                    trgovina.id === idTrgovine ? { ...trgovina, status: "DECLINED" } : trgovina
                )
            );
            alert("Ponuda odbijena!");
        } catch (error) {
            console.error("Greška pri odbijanju trgovine:", error);
            alert("Došlo je do greške pri odbijanju trgovine.");
        }
    };


    const prihvatiPonudu = async (idTrgovine) => {
        try {
            await prihvatiTrgovinu(idTrgovine);
    
            
            setSveTrgovine(prevTrgovine => 
                prevTrgovine.map(trgovina => 
                    trgovina.id === idTrgovine ? { ...trgovina, status: "ACCEPTED" } : trgovina
                )
            );
    
            setFiltriraneTrgovine(prevTrgovine => 
                prevTrgovine.map(trgovina => 
                    trgovina.id === idTrgovine ? { ...trgovina, status: "ACCEPTED" } : trgovina
                )
            );
    
            alert("Trgovina uspešno prihvaćena!");
        } catch (error) {
            console.error("Greška pri prihvatanju trgovine:", error);
            alert("Došlo je do greške pri prihvatanju trgovine.");
        }
    };


   

    return (
        <div className="moje-trgovine-container">
            <h2>Moje Trgovine</h2>

            {/* Dugmad za filtriranje */}
            <div className="filter-buttons">
                <button className="trgovina-btn" onClick={() => handleFiltrirajTrgovine("ALL")}>Prikaži Sve</button>
                <button className="trgovina-btn" onClick={() => handleFiltrirajTrgovine("PENDING")}>Pending</button>
                <button className="trgovina-btn" onClick={() => handleFiltrirajTrgovine("DECLINED")}>Declined</button>
                <button className="trgovina-btn" onClick={() => handleFiltrirajTrgovine("ACCEPTED")}>Accepted</button>
            </div>

            {loading && <p>Učitavanje...</p>}
            {greska && <p className="error">{greska}</p>}

            <div className="trgovine-lista">
                {filtriraneTrgovine.length === 0 ? (
                    <p className="no-trades">Nema trgovina za prikaz.</p>
                ) : (
                    filtriraneTrgovine.map((trgovina) => (
                        <div key={trgovina.id} className="trgovina-kartica">
                            <h3>Trgovina #{trgovina.id}</h3>
                            <p><strong>Pošiljalac:</strong> {trgovina.posiljalac}</p>
                            <p><strong>Primalac:</strong> {trgovina.primalac}</p>
                            <p><strong>Status:</strong> <span className={`status-${trgovina.status.toLowerCase()}`}>{trgovina.status == "COUNTER_OFFER" ? "COUNTER OFFER" : trgovina.status }</span></p>

                            {/* Akcije za povlačenje i odbijanje */}
                            {trgovina.status === "PENDING" && user.korisnickoIme === trgovina.posiljalac && (
                                <button className="trgovina-btn-povuci" onClick={() => handlePovuciTrgovinu(trgovina.id)}>
                                    Povuci Trgovinu
                                </button>
                            )}
                            {trgovina.status === "PENDING" && user.korisnickoIme === trgovina.primalac && (
                                <button className="trgovina-btn" onClick={() => handleOdbijPonudu(trgovina.id)}>
                                    Odbij Ponudu
                                </button>
                            )}
                            {trgovina.status === "PENDING" && user.korisnickoIme === trgovina.primalac && (
                                <button className="trgovina-btn" onClick={() => prihvatiPonudu(trgovina.id)}>
                                    Prihvati Ponudu
                                </button>
                            )}
                            {trgovina.status === "PENDING" && user.korisnickoIme === trgovina.primalac && (
                                <button className="trgovina-btn" onClick={() => navigacija(`/kreiraj-kontra-trgovinu/${trgovina.id}/${trgovina.posiljalac}`) }>
                                    PosaljiKontraPonudu
                                </button>
                            )}
                            {trgovina.status === "COUNTER_OFFER" && user.korisnickoIme === trgovina.posiljalac && (
                                <button className="trgovina-btn" onClick={() => handlePovuciTrgovinu(trgovina.id) }>
                                    Povuci Trgovinu
                                </button>
                            )}
                             {trgovina.status === "COUNTER_OFFER" && user.korisnickoIme === trgovina.primalac && (
                                <button className="trgovina-btn" onClick={() => handleOdbijPonudu(trgovina.id) }>
                                Odbij Ponudu
                                </button>
                            )}
                             {trgovina.status === "COUNTER_OFFER" && user.korisnickoIme === trgovina.primalac && (
                                <button className="trgovina-btn" onClick={() => prihvatiPonudu(trgovina.id) }>
                               Prihvati Ponudu
                                </button>
                            )}

                            {/* Prikaz figurica */}
                            <div className="figurice-container">
                                <div className="figurice-box">
                                    <h4>Ponuđene Figurice</h4>
                                    {trgovina.ponudjeneFigurice.length > 0 ? (
                                        <div className="figurice-lista">
                                            {trgovina.ponudjeneFigurice.map(figurica => (
                                                <div key={figurica.id} className="figurica">
                                                    <img src={`/publicslike/${figurica.slikaUrl}`} alt={figurica.naslov} />
                                                    <p>{figurica.naslov}</p>
                                                    <p>Cena: {figurica.cena} RSD</p>
                                                </div>
                                            ))}
                                        </div>
                                    ) : <p className="prazna">Nema ponuđenih figurica.</p>}
                                </div>

                                <div className="figurice-box">
                                    <h4>Tražene Figurice</h4>
                                    {trgovina.trazeneFigurice.length > 0 ? (
                                        <div className="figurice-lista">
                                            {trgovina.trazeneFigurice.map(figurica => (
                                                <div key={figurica.id} className="figurica">
                                                    <img src={`/publicslike/${figurica.slikaUrl}`} alt={figurica.naslov} />
                                                    <p>{figurica.naslov}</p>
                                                    <p>Cena: {figurica.cena} RSD</p>
                                                </div>
                                            ))}
                                        </div>
                                    ) : <p className="prazna">Nema traženih figurica.</p>}
                                </div>
                            </div>
                        </div>
                    ))
                )}
            </div>
        </div>
    );
};

export default MojeTrgovine;
