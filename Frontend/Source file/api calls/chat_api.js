import axios from "axios";

const BASE_URL = "http://localhost:8080/api/chat";

export const getRazgovori = async () => {
  try {
    const token = localStorage.getItem("refreshToken");
    if (!token) {
      throw new Error("Token nije pronađen. Prijavite se ponovo.");
    }

    const response = await axios.get(`${BASE_URL}/razgovori`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    return response.data; 
  } catch (error) {
    console.error("Greška prilikom dobijanja razgovora:", error);
    throw error;
  }
};
