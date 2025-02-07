import axios from 'axios';

const URL = 'http://localhost:8080/api/recenzije';

const api = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export const kreirajRecenziju = async (recenzijaPodaci) => {
  try {
    const token = localStorage.getItem("refreshToken");
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    const response = await api.post("", recenzijaPodaci, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data;
  } catch (error) {
    console.error("Greška prilikom kreiranja recenzije:", error);
    throw error;
  }
};;


  export async function getSveRecenzijeKorisnika(idRecenziranog) {
    try {
      const response = await api.get(`/${idRecenziranog}`);
      return response.data;
    } catch (error) {
      console.error(`Greška prilikom dohvatanja recenzija za korisnika sa ID ${idRecenziranog}:`, error);
      throw error;
    }
  }

  export async function getSveRecenzije() {
    try {
      const response = await api.get('/sve');
      return response.data;
    } catch (error) {
      console.error('Greška prilikom dohvatanja svih recenzija:', error);
      throw error;
    }
  }
  

  export async function obrisiRecenziju(id) {
    try {
      const response = await api.delete(`/obrisi/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Greška prilikom brisanja recenzije sa ID ${id}:`, error);
      throw error;
    }
  }
  
  

  
