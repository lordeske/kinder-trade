import React, { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom";
import { UserContext } from "./KorisnikContext";
import {
  sendPrivateMessage,
  connectToWebSocket,
  disconnectFromWebSocket,
  subscribeToMessages,
} from "../api calls/chat_servis";
import "../css folder/Chat.css";

const Chat = () => {
  const { user } = useContext(UserContext); 
  const { sagovornik } = useParams(); 
  const [poruke, setPoruke] = useState([]); 
  const [novaPoruka, setNovaPoruka] = useState(""); 

  
  useEffect(() => {
    const subscribe = async () => {
      connectToWebSocket(
        () => {
          console.log("WebSocket povezan.");
          subscribeToMessages(user.korisnickoIme, (primljenaPoruka) => {
            setPoruke((prev) => [...prev, primljenaPoruka]);
          });
        },
        (error) => {
          console.error("Greška prilikom povezivanja na WebSocket:", error);
        }
      );
    };

    if (user) {
      subscribe();
    }

  
    return () => {
      disconnectFromWebSocket();
    };
  }, [user]);


  const posaljiPoruku = async () => {
    if (!novaPoruka.trim()) return; 

    try {
      const poruka = {
        posiljalac: user.korisnickoIme,
        primalac: sagovornik,
        sadrzajPoruke: novaPoruka,
      };
      await sendPrivateMessage(poruka); 
      setPoruke((prev) => [...prev, poruka]);
      setNovaPoruka(""); 
    } catch (error) {
      console.error("Greška prilikom slanja poruke:", error);
    }
  };

  return (
    <div className="chat-container">
      <h2>Razgovor sa {sagovornik}</h2>
      <div className="poruke-container">
        {poruke.map((poruka, index) => (
          <div
            key={index}
            className={`poruka ${
              poruka.posiljalac === user.korisnickoIme ? "moja" : "njihova"
            }`}
          >
            <p>{poruka.sadrzajPoruke}</p>
          </div>
        ))}
      </div>
      <div className="input-container">
        <input
          type="text"
          value={novaPoruka}
          onChange={(e) => setNovaPoruka(e.target.value)}
          placeholder="Unesite poruku..."
        />
        <button onClick={posaljiPoruku}>Pošalji</button>
      </div>
    </div>
  );
};

export default Chat;
