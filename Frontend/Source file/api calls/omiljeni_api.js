import axios from 'axios';

const URL = 'http://localhost:8080/api/omiljeni';

const api = axios.create({
  baseURL: URL,
  timeout: 5000,
  headers: {
    'Content-Type': 'application/json',
  },
});



export async function dodajFiguricuUOmiljene(figuricaId) {
  try {
  
    const token = localStorage.getItem("refreshToken");
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    console.log("Šaljem zahtev sa tokenom:", token);
    console.log("ID figurice za omiljene:", figuricaId);

 
    const response = await api.post(
      `/${figuricaId}`, 
      {}, 
      {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      }
    );

    console.log("Figurica uspešno dodata u omiljene:", response.data);
    return response.data;
  } catch (error) {
    console.error(
      `Greška prilikom dodavanja figurice sa ID ${figuricaId} u omiljene:`,
      error
    );
    throw error;
  }
}








export async function daLiJeFiguricaUOmiljenim(figuricaId) {
  try {
    const token = localStorage.getItem("refreshToken");
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    const response = await api.get(`/omiljeno/${figuricaId}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data; 
  } catch (error) {
    console.error(`Greška prilikom provere figurice sa ID ${figuricaId}:`, error);
    throw error;
  }
}



export async function getOmiljeneFigurice() {
  try {
    const token = localStorage.getItem("refreshToken");
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    const response = await api.get("/moje-omiljene",
       {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });

    return response.data; 
  } catch (error) {
    console.error(`Greška prilikom prikaza omiljenih figurica`, error);
    throw error;
  }
}
  






export async function obrisiFiguricuIzOmiljenih(figuricaId) {
  try {
  
    const token = localStorage.getItem("refreshToken");
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    console.log("Šaljem zahtev sa tokenom:", token);
    console.log("ID figurice za omiljene:", figuricaId);

 
    const response = await api.delete(
      `/${figuricaId}`, 
       
      {
        headers: {
          Authorization: `Bearer ${token}`, 
        },
      }
    );

    console.log("Figurica uspešno obrisana iz omiljene:", response.data);
    return response.data;
  } catch (error) {
    console.error(
      `Greška prilikom brisanja  figurice sa ID ${figuricaId} iz omiljenih:`,
      error
    );
    throw error;
  }
}

