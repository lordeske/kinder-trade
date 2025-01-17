import axios from "axios";

const URL = "http://localhost:8080/api/korisnici";

const api = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});




export async function sviKorisnici() {
  try {
    const response = await api.get("/");
    return response.data;
  } catch (error) {
    console.error("Greška prilikom prikaza svih korisnika:", error);
    throw error;
  }
}




export async function azurirajKorisnika(azuriraniInfoKorisnika) {
  try {
    const token = localStorage.getItem("refreshToken"); 
    if (!token) {
      throw new Error("Nema validnog tokena. Korisnik nije prijavljen.");
    }

    console.log("Šaljem zahtev sa payload-om:", azuriraniInfoKorisnika);

    const response = await api.put(`/azuriraj`, azuriraniInfoKorisnika, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log("Korisnik uspešno ažuriran:", response.data);
    return response.data; 
  } catch (error) {
    console.error("Greška prilikom ažuriranja korisnika:", error.message);
    throw error;
  }
}



export async function obrisiKorisnika(idKorisnika) {
  try {
    const response = await api.delete(`/${idKorisnika}`);
    return response.data;
  } catch (error) {
    console.error("Greška prilikom brisanja korisnika:", error);
    throw error;
  }
}

export async function prikaziKorisnikaDrugima(imeKorisnika) {
  try {
    const response = await api.get(`/profil/${imeKorisnika}`);
    console.log("Dobijeni podaci:",response.data)
    return response.data;
  } catch (error) {
    console.error("Greška prilikom prikaza korisnika:", imeKorisnika);
    throw error;
  }
}


export async function getPredlozeniKorisnici(korisnickoIme) {
  try {
    const response = await api.get(`/predlozeni/${korisnickoIme} `);
    console.log("Dobijeni predlozeni korisnici:",response.data)
    return response.data;
  } catch (error) {
    console.error("Greška prilikom prikaza predlozenih korisnika:", );
    throw error;
  }
}



export async function dohvatiMojProfil () {
  try {
    const token = localStorage.getItem("refreshToken")
    console.log('Šaljem zahtev sa tokenom:', token);

    const response = await axios.get('http://localhost:8080/api/korisnici/loginprofile/me', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    
    console.log('Uspešan odgovor:', response.data);
    return response.data
  } catch (error) {
    console.error('Greška:', error.response ? error.response.data : error.message);
  }
};

dohvatiMojProfil();
