import axios from 'axios';

const URL = 'http://localhost:8080/api/trgovina';

const api = axios.create({
    baseURL: URL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

// Automatsko dodavanje tokena 
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem("refreshToken");
        if (token) {
            console.log("Kreiram trgovinu sa tokenom", token);
            config.headers.Authorization = `Bearer ${token}`;
        } else {
            console.warn("Token nije pronađen. Korisnik možda nije prijavljen.");
        }
        return config;
    },
    (error) => Promise.reject(error)
);



export async function kreirajTrgovinu(trgovina) {
    try {
        const response = await api.post(`${URL}`, trgovina);
        console.log(" Trgovina uspešno kreirana:", response.data);
        return response.data;
    } catch (error) {
        console.error("Greška prilikom kreiranja trgovine:", error);
        throw error;
    }
}


export async function prihvatiTrgovinu(trgovinaID) {
    try {
        const response = await api.put(`/prihvati/${trgovinaID}`);
        console.log(" Trgovina prihvaćena:", response.data);
        return response.data;
    } catch (error) {
        console.error(`Greška prilikom prihvatanja trgovine ${trgovinaID}:`, error);
        throw error;
    }
}

export async function odbijTrgovinu(trgovinaID) {
    try {
        const response = await api.put(`/odbij/${trgovinaID}`);
        console.log(" Trgovina odbijena:", response.data);
        return response.data;
    } catch (error) {
        console.error(`Greška prilikom odbijanja trgovine ${trgovinaID}:`, error);
        throw error;
    }
}


export async function povuci(trgovinaID) {
    try {
        const response = await api.put(`/povuci/${trgovinaID}`);
        console.log(" Trgovina povučena:", response.data);
        return response.data;
    } catch (error) {
        console.error(`Greška prilikom povlačenja trgovine ${trgovinaID}:`, error);
        throw error;
    }
}

export async function counterPonuda(trgovinaID, trgovinaDtoFigurice) {
    try {
        const response = await api.put(`/counter/${trgovinaID}`, trgovinaDtoFigurice);
        console.log(" Counter trgovina uspešno kreirana:", response.data);
        return response.data;
    } catch (error) {
        console.error(`Greška prilikom counter kreiranja trgovine ${trgovinaID}:`, error);
        throw error;
    }
}


export async function sveMojeTrgovine() {
    try {
        const response = await api.get("/moje");
        console.log("Sve moje trgovine:", response.data);
        return response.data;
    } catch (error) {
        console.error("Greška prilikom prikaza svih trgovina:", error);
        throw error;
    }
}

// 📌 Prikaz svih PENDING trgovina korisnika
export async function sveMojePending() {
    try {
        const response = await api.get("/pending");
        console.log("Sve moje pending trgovine:", response.data);
        return response.data;
    } catch (error) {
        console.error("Greška prilikom prikaza pending trgovina:", error);
        throw error;
    }
}


export async function sveMojeCounter() {
    try {
        const response = await api.get("/counter");
        console.log("Sve moje counter ponude:", response.data);
        return response.data;
    } catch (error) {
        console.error("Greška prilikom prikaza counter trgovina:", error);
        throw error;
    }
}
