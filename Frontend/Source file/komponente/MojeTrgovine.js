import React, { useState, useEffect, useContext } from "react";

import { UserContext } from "./KorisnikContext";

import { sveMojeTrgovine } from "../api calls/trgovina_api";


const MojeTrgovine = () => {
    const { user } = useContext(UserContext);
    
    
    const [trgovine, setTrgovine] = useState([]);
    

    useEffect(() => {
        if (user && user.korisnickoIme) {
            setTrgovine(sveMojeTrgovine())
            console.log(trgovine);
                
        }
        
    }, [user]);

    
    

   

    return (
        <div>

        </div>
    );
};

export default MojeTrgovine;
