import axios from "axios";

const URL = "http://localhost:8080/api/korisnici";

const api = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});


export async function kreirajKorisnikaV1(korisnik) {
  try {
    const response = await api.post("/", korisnik);
    return response.data;
  } catch (error) {
    console.error("Greška prilikom kreiranja korisnika:", error);
    throw error;
  }
}


export async function sviKorisnici() {
  try {
    const response = await api.get("/");
    return response.data;
  } catch (error) {
    console.error("Greška prilikom prikaza svih korisnika:", error);
    throw error;
  }
}


export async function nadjiKorisnika(idKorisnika) {
  try {
    const response = await api.get(`/${idKorisnika}`);
    return response.data;
  } catch (error) {
    console.error("Greška prilikom prikaza korisnika:", error);
    throw error;
  }
}


export async function azurirajKorisnika(idKorisnika, azuriraniInfoKorisnika) {
  try {
    const response = await api.patch(`/${idKorisnika}`, azuriraniInfoKorisnika);
    return response.data;
  } catch (error) {
    console.error("Greška prilikom ažuriranja korisnika:", idKorisnika, "podaci:", azuriraniInfoKorisnika);
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



