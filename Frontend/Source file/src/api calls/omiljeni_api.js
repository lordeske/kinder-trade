import axios from 'axios';

const URL = 'http://localhost:8080/api/omiljeni';

const api = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});

export async function getOmiljeneFigurice(korisnikId) {
    try {
      const response = await api.get(`/${korisnikId}`);
      return response.data;
    } catch (error) {
      console.error(`Greška prilikom dohvatanja omiljenih figurica za korisnika sa ID ${korisnikId}:`, error);
      throw error;
    }
  }
  

  export async function dodajFiguricuUOmiljene(korisnikId, figuricaId) {
    try {
      const response = await api.post(`/${korisnikId}/${figuricaId}`);
      return response.data;
    } catch (error) {
      console.error(
        `Greška prilikom dodavanja figurice sa ID ${figuricaId} u omiljene za korisnika sa ID ${korisnikId}:`,
        error
      );
      throw error;
    }
  }
  

  export async function obrisiFiguricuIzOmiljenih(id) {
    try {
      const response = await api.delete(`/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Greška prilikom brisanja omiljene figurice sa ID ${id}:`, error);
      throw error;
    }
  }
  
