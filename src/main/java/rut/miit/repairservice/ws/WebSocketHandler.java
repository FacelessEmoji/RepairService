package rut.miit.repairservice.ws;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private WebSocketSession currentSession = null;
    private boolean connectionEstablished = false;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("WebSocket connection established: " + session.getId());
        currentSession = session;
        connectionEstablished = true;
    }

    public void sendMessageToAll(String message) {
        if (currentSession != null) {
            try {
                currentSession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isConnectionEstablished() {
        return connectionEstablished;
    }
}
