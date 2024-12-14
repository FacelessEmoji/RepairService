package rut.miit.repairservice.ws;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService extends TextWebSocketHandler {

    private WebSocketSession currentSession;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.currentSession = session;
    }

    public void sendMessage(String message) {
        if (currentSession != null) {
            try {
                currentSession.sendMessage(new TextMessage(message));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void sendDownloadLink(String fileName) {
        if (currentSession != null) {
            try {
                String downloadLink = "http://localhost:8080/download-report/" + fileName;
                currentSession.sendMessage(new TextMessage("Отчет готов! Скачать: " + downloadLink));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
