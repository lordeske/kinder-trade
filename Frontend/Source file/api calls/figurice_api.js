import axios from 'axios';


const URL = 'http://localhost:8080/api/figurice';

const api = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});


export async function getFiguricaById(id) {
  try {
    const response = await api.get(`/id/${id}`);
    console.log(response.data)
    return response.data;
  
  } catch (error) {
    console.error(`Greška prilikom dohvatanja figurice sa ID ${id}:`, error);
    throw error;
  }
}


export async function kreirajFiguricu(figurica, idKorisnika) {
  try {
    const response = await api.post(`/korisnik/${idKorisnika}`, figurica);
    return response.data;
  } catch (error) {
    console.error('Greška prilikom kreiranja figurice:', error);
    throw error;
  }
}


export async function azurirajFiguricu(id, azuriranaFigurica) {
  try {
    const response = await api.put(`/id/${id}`, azuriranaFigurica);
    return response.data;
  } catch (error) {
    console.error(`Greška prilikom ažuriranja figurice sa ID ${id}:`, error);
    throw error;
  }
}


export async function obrisiFiguricu(id) {
  try {
    const response = await api.delete(`/id/${id}`);
    return response.data;
  } catch (error) {
    console.error(`Greška prilikom brisanja figurice sa ID ${id}:`, error);
    throw error;
  }
}

export async function nadjiFiguricePoNazivu(naziv) {
  try {
    const response = await api.get(`/naziv/${naziv}`);
    return response.data;
  } catch (error) {
    console.error(`Greška prilikom dohvatanja figurica sa nazivom ${naziv}:`, error);
    throw error;
  }
}


export async function getFiguriceZaKorisnika(idKorisnika) {
  try {
    const response = await api.get(`/korisnik/${idKorisnika}`);
    return response.data;
  } catch (error) {
    console.error(`Greška prilikom dohvatanja figurica za korisnika sa ID ${idKorisnika}:`, error);
    throw error;
  }
}


export async function getFiguriceZaKorisnickoIme(korisnickoIme) {
  try {
    const response = await api.get(`/profil/${korisnickoIme}`);
    console.log("Dobijene figurice: ", response.data);
    return response.data;
  } catch (error) {
    console.error(`Greška prilikom dohvatanja figurica za korisnika sa imenom ${korisnickoIme}:`, error);
    throw error;
  }
}


export async function getRandomFigurice(limit) {
  try {
    const response = await api.get(`/random?limit=${limit}`);
    console.log("Dobijene random figurice: ", response.data);
    return response.data;
  } catch (error) {
    console.error('Greška prilikom dohvatanja random figurica:', error);
    throw error;
  }
}
