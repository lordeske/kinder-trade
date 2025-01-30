import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

const WEBSOCKET_URL = "http://localhost:8080/ws";
let stompClient = null;

export const connectToPrivateChat = (korisnickoIme, onMessageReceived) => {
  const socket = new SockJS(WEBSOCKET_URL);
  stompClient = new Client({
    webSocketFactory: () => socket,
    debug: (str) => console.log(str),
    reconnectDelay: 5000,
  });

  stompClient.onConnect = () => {
    console.log("Povezan na WebSocket server.");

   
    stompClient.subscribe(`/user/${korisnickoIme}/privatne`, (message) => {
      const parsedMessage = JSON.parse(message.body);
      onMessageReceived(parsedMessage);
    });
  };

  stompClient.activate();
};

export const sendPrivateMessage = (message) => {
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: "/app/privateMessage",
      body: JSON.stringify(message),
    });
  } else {
    console.error("Nije povezan na WebSocket server. Poruka nije poslata.");
  }
};


export const fetchPrivateMessages = async (korisnik1, korisnik2) => {
  try {
      const response = await fetch(`http://localhost:8080/api/poruke/izmedju?korisnik1=${korisnik1}&korisnik2=${korisnik2}`);
      if (!response.ok) {
          throw new Error("Greška pri dohvatanju poruka");
      }
      return await response.json();
  } catch (error) {
      console.error("Greška pri dohvatanju privatnih poruka:", error);
      return [];
  }
};

