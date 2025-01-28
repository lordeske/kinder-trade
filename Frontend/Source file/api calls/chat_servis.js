import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

const WEBSOCKET_URL = "http://localhost:8080/ws";
let stompClient = null;

export const connectToPublicChat = (onMessageReceived) => {
  const socket = new SockJS(WEBSOCKET_URL);
  stompClient = Stomp.over(socket);

  stompClient.connect({}, () => {
    console.log("Povezan na public chat.");
    stompClient.subscribe("/topic/public", (message) => {
      const parsedMessage = JSON.parse(message.body);
      onMessageReceived(parsedMessage);
    });
  });
};

export const sendMessageToPublicChat = (message) => {
  if (stompClient && stompClient.connected) {
    stompClient.send("/app/sendMessage", {}, JSON.stringify(message));
  } else {
    console.error("Nije povezan na WebSocket server. Poruka nije poslata.");
  }
};
