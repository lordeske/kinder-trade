import React, { useState, useEffect, useContext } from "react";
import { fetchPrivateMessages, sendPrivateMessage, connectToPrivateChat } from "../api calls/privatni_servis";
import { UserContext } from "./KorisnikContext";
import { useParams } from "react-router-dom";
import '../css folder/PrivateChat.css';

const PrivatniChat = () => {
    const { user } = useContext(UserContext);
    const { sagovornik } = useParams();
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");

    
    useEffect(() => {
        const loadMessages = async () => {
            if (user) {
                const oldMessages = await fetchPrivateMessages(user.korisnickoIme, sagovornik);
                setMessages(oldMessages);
            }
        };

        loadMessages();
        connectToPrivateChat(sagovornik, (message) => {
            setMessages((prevMessages) => [...prevMessages, message]);
        });

    }, [user, sagovornik]);

    const handleSendMessage = () => {
        if (newMessage.trim() !== "") {
            const message = {
                posiljalac: user.korisnickoIme,
                primalac: sagovornik,
                sadrzajPoruke: newMessage,
            };

            sendPrivateMessage(message);
            setMessages((prevMessages) => [...prevMessages, message]);
            setNewMessage("");
        }
    };

    return (
        <div className="privatni-chat-container">
            <h1 className="privatni-chat-naslov">Razgovor sa {sagovornik}</h1>
            <div className="privatni-chat-poruke">
                {messages.map((msg, index) => (
                    <div 
                        key={index} 
                        className={`privatna-poruka ${msg.posiljalac === user.korisnickoIme ? "privatna-moja" : "privatna-njihova"}`}
                    >
                        <span className="privatna-poruka-ime">{msg.posiljalac}</span>
                        <p>{msg.sadrzajPoruke}</p>
                        <small className="privatna-poruka-vreme">{msg.timestamp}</small>
                    </div>
                ))}
            </div>
            <div className="privatni-chat-input-container">
                <input
                    type="text"
                    className="privatni-chat-input"
                    placeholder="Unesite poruku..."
                    value={newMessage}
                    onChange={(e) => setNewMessage(e.target.value)}
                />
                <button className="privatni-chat-dugme" onClick={handleSendMessage}>Pošalji</button>
            </div>
        </div>
    );
    
};

export default PrivatniChat;
