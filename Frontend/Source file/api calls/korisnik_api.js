import axios from "axios";

const URL = "http://localhost:8080/api/korisnici";

const api = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});



export async function azurirajKorisnika(azurirnaiKorisnik, slika) {
  try {
    const token = localStorage.getItem("refreshToken");
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    const formData = new FormData();
    Object.keys(azurirnaiKorisnik).forEach(key => formData.append(key, azurirnaiKorisnik[key]));
    if (slika) {
      formData.append("slika", slika);
    }

    const response = await api.put(
      `/azuriraj`,
      formData,
      {
        headers: {
          Authorization: `Bearer ${token}`,
          "Content-Type": "multipart/form-data",
        },
      }
    );
    return response.data;
  } catch (error) {
    console.error(`Greška prilikom ažuriranja korisnika `, error , error.message);
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
