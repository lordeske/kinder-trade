import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { prikaziKorisnikaDrugima, getPredlozeniKorisnici } from '../api calls/korisnik_api';
import { getFiguriceZaKorisnickoIme } from '../api calls/figurice_api';
import '../css folder/ProfilKorisnika.css';
import { useNavigate } from 'react-router-dom';






const ProfilKorisnika = () => {


    const navigacija = useNavigate();

    const { korisnickoIme } = useParams();
    const [korisnik, setKorisnik] = useState({});
    const [figurice, setFigurice] = useState({});
    const [predlozeniKorisnici, setPredlozeniKorisnici] = useState({});
    const [loadingKorisnik, setLoadingKorisnik] = useState(true);
    const [loadingFigurice, setLoadingFigurice] = useState(true);
    const [loadingPredlozeniKorisnici, setLoadingPredlozeni] = useState(true);

    const [greska, setGreska] = useState(null);


    const dobijKorisnika = async (imeKorisnika) => {

        try {
            const dobijeniKorisnik = await prikaziKorisnikaDrugima(imeKorisnika)
            setKorisnik(dobijeniKorisnik);
            console.log(korisnik)

        } catch (error) {
            console.log("Desila se greska prilikom prikaza korisnika", error)
            setGreska("Desila se greska prilikom prikaza korisnika")
        }
        finally {
            setLoadingKorisnik(false)

        }

    }


    const dobijFiguriceKorisnika = async (imeKorisnika) => {

        try {
            const dobijeneFigurice = await getFiguriceZaKorisnickoIme(imeKorisnika)
            setFigurice(dobijeneFigurice);
            console.log(figurice)

        } catch (error) {
            console.log("Desila se greska prilikom prikaza korisnika", error)
            setGreska("Desila se greska prilikom prikaza korisnika")
        }
        finally {
            setLoadingFigurice(false)

        }

    }


    const dobijPredlozeneKorisnike = async () => {

        try {
            const dobijeniPredlozeniKorisnici = await getPredlozeniKorisnici(korisnickoIme)
            setPredlozeniKorisnici(dobijeniPredlozeniKorisnici);
            console.log(predlozeniKorisnici)

        } catch (error) {
            console.log("Desila se greska prilikom prikaza predlozenih korisnika", error)
            setGreska("Desila se greska prilikom prikaza predlozenih korisnika")
        }
        finally {
            setLoadingPredlozeni(false)

        }

    }

    const formatirajDatum = (datum) => {
        const parsedDate = new Date(datum);
        if (isNaN(parsedDate)) {
            return 'Nevalidan datum';
        }
        return new Intl.DateTimeFormat('en', {
            day: 'numeric',
            month: 'long',
            year: 'numeric',

        }).format(parsedDate);
    };




    useEffect(() => {

        dobijKorisnika(korisnickoIme)
        dobijFiguriceKorisnika(korisnickoIme)
        dobijPredlozeneKorisnike()



    }, [korisnickoIme])
















    return (

        <div className="profile-page">
            {/*trenutni korisnik*/}
            <div className="profile-info">
                {loadingKorisnik ? (
                    <>
                        <div className="spinner"></div>
                        <p>Učitavanje korisničkih podataka...</p>
                    </>
                ) : (
                    <>
                        <img
                            src="/publicslike/avatar.jpeg"
                            alt="Profilna slika"
                            className="profile-avatar"
                        />
                        <h2>{korisnik.korisnickoIme || "Učitavanje..."}</h2>
                        <p>
                            {`Datum kreiranja: ${korisnik.datumKreiranja ? formatirajDatum(korisnik.datumKreiranja) : "Učitavanje..."
                                }`}
                        </p>
                    </>
                )}
            </div>

            {/* figurice korisnika */}
            <div className="posts">
                {loadingFigurice ? (
                    <>
                        <div className="spinner"></div>
                        <p>Učitavanje figurica...</p>
                    </>
                ) : (
                    <>
                        {figurice.length > 0 ? (
                            figurice.map((figurica, index) => (
                                <div key={index} className="post-box">
                                    <img
                                        src={`/publicslike/${figurica.naslov}.jpg`}
                                        alt={figurica.naslov || "Figurica"}
                                        className="figurica-slika"
                                    />

                                    <p>{figurica.naslov || "Nepoznata figurica"}</p>
                                    <p>{figurica.cena || "Opis nije dostupan."}</p>
                                </div>
                            ))
                        ) : (
                            <p>Nema dostupnih figurica za prikaz.</p>
                        )}
                    </>
                )}
            </div>

            {/* predlozeni profili */}
            <div className="suggested-profiles">
                {loadingPredlozeniKorisnici ? (
                    <>
                        <div className="spinner"></div>
                        <p>Učitavanje predloženih profila...</p>
                    </>
                ) : (
                    <>
                        <h3>Predloženi Profili</h3>
                        {predlozeniKorisnici.length > 0 ? (
                            predlozeniKorisnici.map((predlozeniKorisnik, index) => (
                                <div key={index} className="suggested-profile">
                                    <img
                                        src={`user${index + 1}.jpg`}
                                        alt={`Slika korisnika`}
                                        className="suggested-profile-avatar"
                                    />
                                    <p onClick={() => navigacija(`/profil/${predlozeniKorisnik.korisnickoIme}`)}>
                                        {predlozeniKorisnik.korisnickoIme}
                                    </p>
                                </div>
                            ))
                        ) : (
                            <p>Nema predloženih profila za prikaz.</p>
                        )}
                    </>
                )}
            </div>
        </div>

    );
}

export default ProfilKorisnika
