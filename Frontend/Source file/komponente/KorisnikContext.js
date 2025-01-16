import React, { createContext, useState, useEffect } from 'react';
import { dohvatiMojProfil } from '../api calls/korisnik_api';

export const UserContext = createContext();

export const UserProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchUserProfile = async () => {
      const token = localStorage.getItem('accessToken'); 
      if (token) {
        try {
          const userData = await dohvatiMojProfil(token);
          setUser(userData);
        } catch (error) {
          console.error('Greška prilikom dobijanja korisničkog profila:', error);
          setUser(null); 
        }
      }
      setLoading(false);
    };

    fetchUserProfile();
  }, []);

  return (
    <UserContext.Provider value={{ user, setUser, loading }}>
      {children}
    </UserContext.Provider>
  );
};
