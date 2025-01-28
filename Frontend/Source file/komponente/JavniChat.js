import React, { useState, useEffect, useContext } from "react";
import { connectToPublicChat, sendMessageToPublicChat } from "../api calls/chat_servis";
import { UserContext } from "./KorisnikContext";
import "../css folder/JavniChat.css"; 

const JavniChat = () => {
  const [messages, setMessages] = useState([]);
  const [newMessage, setNewMessage] = useState("");

  const { user } = useContext(UserContext); 

  useEffect(() => {
    connectToPublicChat((message) => {
      setMessages((prevMessages) => [...prevMessages, message]);
    });
  }, []);

  const sendMessage = () => {
    if (newMessage.trim() !== "") {
      const message = {
        posiljalac: user.korisnickoIme,
        content: newMessage,
        timestamp: new Date().toLocaleString(),
      };
      sendMessageToPublicChat(message);
      setNewMessage("");
    }
  };

  if (!user)
  {
    return false;
  }

  return (
    <div className="javni-chat-container">
      <h1 className="javni-chat-title">Javni Chat</h1>
      <div className="javni-chat-box">
        {messages.map((msg, index) => (
          <div key={index} className="javni-chat-message">
            <strong className="sender">{msg.posiljalac || "Nepoznati korisnik"}</strong>:{" "}
            <span className="content">{msg.content}</span>{" "}
            <small className="timestamp">{msg.timestamp}</small>
          </div>
        ))}
      </div>
      <div className="javni-chat-input-container">
        <input
          type="text"
          className="javni-chat-input"
          placeholder="Unesite poruku..."
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
        />
        <button className="javni-chat-send-button" onClick={sendMessage} disabled={!newMessage.trim()}>
          Pošalji
        </button>
      </div>
    </div>
  );
};

export default JavniChat;
