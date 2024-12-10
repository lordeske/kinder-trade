import axios from "axios";


const URL = "http://localhost:8080/api/korisnici"


const api = axios.create(
    {
        baseURL : URL, 
        timeout : 5000,
        headers : {
            'Content-Type': 'application/json',
        }

    }
)





/// Ovo ce biti pocetni appi call koji cemo da unaprijedimo


export async function kreirajKorisnikaV1(korisnik) {

    try
    {   
        const response  = await api.post("/" , korisnik);
        return response.data;

    }
    catch(error)
    {

        console.log("Greska prilikom kreiranja korisnika,",error)
        throw error;


    }
    
}


export async function sviKorisnici() {

    try
    {
        const response = await api.get("/");
        return response.data;


    }
    catch(error)
    {

        console.log(`Greska prilikom prikaza svih korisnika korisnika:`,error)
        throw error;
        
    }
    
}


export async function nadjiKorisnika(idKorisnika) {


    try
    {
        const response = await api.get(`/${idKorisnika}`);
        return response.data;


    }
    catch(error)
    {
        console.log(`Greska prilikom prikaza korisnika:`, error)



    }

    return axios.get(`${URL}/${idKorisnika}`)
    
}

export async function azurirajKorisnika(idKorisnika , azuriraniInfoKorisnika) {


    try
    {
        const response = await api.patch(`/${idKorisnika}`, azurirajKorisnika);
        return response.data

    }
    catch(error)
    {
        console.log(`Greska prilikom azuriranja korisnika: `, idKorisnika, `podaci:` , azurirajKorisnika );
        throw error


    }

    
    
}

export async function obrisiKorisnika(idKorisnika) {

    try {
        const response = await api.delete(`/${idKorisnika}`);
        return response.data;
      } catch (error) {
        console.error('Greška prilikom brisanja korisnika:', error);
        throw error;
      }
    
}


export async function prikaziKorisnikaDrugima(imeKorisnika) {


    try
    {
        const response = await api.get(`/${imeKorisnika}`)
        return response.data;
    }

    catch(error)
    {
        console.log(`Greska prilikom prikaza korisnika : ` , imeKorisnika);
        throw error;

    }
    

    
}


