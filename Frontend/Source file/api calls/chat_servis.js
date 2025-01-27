import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";

const WEBSOCKET_URL = "http://localhost:8080/ws"; 
let stompClient = null;

export const connectToWebSocket = (onConnected, onError) => {
  const socket = new SockJS(WEBSOCKET_URL);
  stompClient = new Client({
    webSocketFactory: () => socket,
    debug: (str) => console.log(str),
    reconnectDelay: 5000, 
  });

  stompClient.onConnect = () => {
    console.log("Povezan na WebSocket server.");
    if (onConnected) onConnected();
  };

  stompClient.onStompError = (frame) => {
    console.error("Greška STOMP protokola:", frame.headers["message"]);
  };

  stompClient.onWebSocketClose = () => {
    console.error("WebSocket konekcija prekinuta.");
  };

  stompClient.activate(); // Aktivacija konekcije
};

export const disconnectFromWebSocket = () => {
  if (stompClient) {
    stompClient.deactivate();
    console.log("Prekinuta WebSocket konekcija.");
  }
};

export const sendPrivateMessage = (poruka) => {
  if (stompClient && stompClient.connected) {
    stompClient.publish({
      destination: "/app/privateMessage", 
      body: JSON.stringify(poruka), // Telo poruke
    });
    console.log("Privatna poruka poslata:", poruka);
  } else {
    console.error("Nije povezan na WebSocket server. Poruka nije poslata.");
  }
};

export const subscribeToMessages = (username, onMessage) => {
  if (stompClient && stompClient.connected) {
    stompClient.subscribe(`/user/${username}/queue/messages`, (message) => {
      const parsedMessage = JSON.parse(message.body);
      console.log("Primljena privatna poruka:", parsedMessage);
      if (onMessage) onMessage(parsedMessage);
    });
  } else {
    console.error("Nije povezan na WebSocket server. Pretplata nije moguća.");
  }
};
