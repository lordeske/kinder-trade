import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { prikaziKorisnikaDrugima } from '../api calls/korisnik_api';
import { getFiguriceZaKorisnickoIme } from '../api calls/figurice_api';
import '../css folder/ProfilKorisnika.css';






const ProfilKorisnika = () => {


    const { korisnickoIme } = useParams();
    const [korisnik, setKorisnik] = useState({});
    const [figurice, setFigurice] = useState({});
    const [loadingKorisnik, setLoadingKorisnik] = useState(true);
    const [lodaingFigurice, setLoadingFigurice] = useState(true);
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



    }, [korisnickoIme])
















    return (

        <div className="profile-page">
            {loadingKorisnik ? (
                <div className="loading-container">
                    <div className="spinner"></div>
                    <p>Učitavanje korisničkih podataka...</p>
                </div>
            ) : (
                <>
                    
                    <div className="profile-info">
                        <img src="/publicslike/avatar.jpeg" alt="Profile" />
                        <h2>{korisnik.korisnickoIme || "Učitavanje..."}</h2>
                        <p>{`Datum kreiranja: ${korisnik.datumKreiranja ? formatirajDatum(korisnik.datumKreiranja) : "Učitavanje..."}`}</p>
                    </div>


                    <div className="posts">
                        {[...Array(12)].map((_, index) => (
                            <div key={index} className="post-box"></div>
                        ))}
                    </div>


                    <div className="suggested-profiles">
                        <h3>Predloženi Profili</h3>
                        {['Korisnik 1', 'Korisnik 2', 'Korisnik 3'].map((name, index) => (
                            <div key={index} className="suggested-profile">
                                <img src={`user${index + 1}.jpg`} alt={name} />
                                <p>{name}</p>
                            </div>
                        ))}
                    </div>
                </>
            )}
        </div>

    );
}

export default ProfilKorisnika
