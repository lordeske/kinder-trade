import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import { prikaziKorisnikaDrugima } from '../api calls/korisnik_api';
import '../css folder/ProfilKorisnika.css';






const ProfilKorisnika = () => {


    const { imeKorisnika } = useParams();
    const [korisnik, setKorisnik] = useState({});
    const [loading, setLoading] = useState(true);
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
            setLoading(false)

        }

    }

    const formatirajDatum = (datum) => {
        return new Intl.DateTimeFormat('eng', {
            day: 'numeric',
            month: 'long',
            year: 'numeric',



        }).format(new Date(datum));
    }



    useEffect(() => {

        dobijKorisnika(imeKorisnika)



    }, [imeKorisnika])
















    return (
        <div className="profile-page">

            <div className="profile-info">
                <img src="profile.jpg" alt="Profile" />
                <h2>Ime Korisnika</h2>
                <p>Datum kreiranja: 01.01.2020</p>
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
        </div>

    );
}

export default ProfilKorisnika
