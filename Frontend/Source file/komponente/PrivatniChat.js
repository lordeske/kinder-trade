import React, { useState, useEffect, useContext } from "react";
import { connectToPrivateChat, sendPrivateMessage } from "../api calls/privatni_servis";
import { UserContext } from "./KorisnikContext";
import { useParams } from "react-router-dom";

const PrivatniChat = () => {
  const { user } = useContext(UserContext);
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");
  const {sagovornik} = useParams()

  useEffect(() => {
    connectToPrivateChat(user.korisnickoIme, (message) => {
      setMessages((prevMessages) => [...prevMessages, message]);
    });
  }, [user.korisnickoIme]);

  const sendMessage = () => {
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
    <div>
      <h2>Chat sa {sagovornik}</h2>
      <div className="chat-box">
        {messages.map((msg, index) => (
          <div key={index} className={`message ${msg.posiljalac === user.korisnickoIme ? "my-message" : "their-message"}`}>
            <strong>{msg.posiljalac}</strong>: {msg.sadrzajPoruke}
          </div>
        ))}
      </div>
      <div>
        <input
          type="text"
          placeholder="Unesite poruku..."
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
        />
        <button onClick={sendMessage}>Pošalji</button>
      </div>
    </div>
  );
};

export default PrivatniChat;
