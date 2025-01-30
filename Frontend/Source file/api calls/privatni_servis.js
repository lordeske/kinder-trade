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
