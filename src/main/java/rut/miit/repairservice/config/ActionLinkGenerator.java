package rut.miit.repairservice.config;

import java.util.HashMap;
import java.util.Map;

public class ActionLinkGenerator {

    public static Map<String, String> createAction(String baseUrl, String actionPath, String id, String method) {
        Map<String, String> action = new HashMap<>();

        action.put("href", baseUrl + actionPath + "?id=" + id);
        action.put("method", method);

        // Если метод PUT, добавляем заголовок accept
        if ("PUT".equals(method) || "POST".equals(method)) {
            action.put("accept", "application/json");
        }

        return action;
    }

    public static Map<String, String> addAction(String baseUrl, String id) {
        return createAction(baseUrl, "/add", id, "POST");
    }

    public static Map<String, String> deleteAction(String baseUrl, String id) {
        return createAction(baseUrl, "/delete", id, "DELETE");
    }

    public static Map<String, String> getAction(String baseUrl, String id) {
        return createAction(baseUrl, "/{id}", id, "GET"); // Используем шаблон для id
    }

    public static Map<String, String> updateAction(String baseUrl, String id) {
        return createAction(baseUrl, "/update", id, "PUT");
    }
}

