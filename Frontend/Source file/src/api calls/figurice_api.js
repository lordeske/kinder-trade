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
    console.log("DOBIO SI FIGURICU: :", response.data)
    return response.data;

  } catch (error) {
    console.error(`Greška prilikom dohvatanja figurice sa ID ${id}:`, error);
    throw error;
  }
}


export async function kreirajFiguricu(figurica) {
  try {
    const token = localStorage.getItem("refreshToken"); 
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    console.log('Šaljem zahtev sa tokenom:', token);
    console.log(figurica)

    const response = await api.post(
      `/kreiraj`, 
      figurica,   
      {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      }
    );

    return response.data; 
  } catch (error) {
    console.error('Greška prilikom kreiranja figurice:', error.response || error.message);
    throw error;
  }
}



export async function azurirajFiguricu(id, azuriranaFigurica) {
  try {
    const token = localStorage.getItem("refreshToken"); 
    if (!token)
    {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    const response = await api.put(
      `/azuriraj/${id}`, 
      azuriranaFigurica,   
      {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      }
    );
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
    console.log("Dobijene figurice korisnika: ", response.data);
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


export async function mojeFigurice() {
  try {
    const token = localStorage.getItem('accessToken');
    if (!token) {
      throw new Error('Token nije pronađen. Prijavite se ponovo.');
    }

    console.log('Šaljem zahtev sa tokenom:', token);

    const response = await api.get('/moje-figurice', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    console.log('Dobijene figurice:', response.data);
    return response.data;
  } catch (error) {
    console.error('Greška prilikom dobijanja figurica korisnika:', error);
    throw error;
  }
}