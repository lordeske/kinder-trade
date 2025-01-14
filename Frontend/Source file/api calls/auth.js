import axios from 'axios';


const URL = 'http://localhost:8080/api/autentifikacija';

const api = axios.create({
    baseURL: URL,
    timeout: 5000,
    headers: {
        'Content-Type': 'application/json',
    },
});

const registracija = async (podaciRegistracije) => {
    try {
        const response = await api.post('/registracija', podaciRegistracije);
        return response.data;
    } catch (error) {
        console.error('Greška prilikom registracije:', error);
        throw error;
    }
};





const login = async (loginPodaci) => {


    try {
        const response = await api.post('/login', loginPodaci);
        const { accessToken, refreshToken } = response.data;


        localStorage.setItem('accessToken', accessToken);
        localStorage.setItem('refreshToken', refreshToken);

        console.log(response.data)
        return response.data;

    }
    catch (error) {

        console.error('Greška prilikom logina:', error);
        throw error;

    }


}


const logout = async () => {


    try {
 

        localStorage.removeItem('accessToken');
        localStorage.removeItem('refreshToken');

        
    }
    catch (error) {

        console.error('Greška prilikom odjave:', error);
        throw error;

    }


}













export { registracija , login, logout}




