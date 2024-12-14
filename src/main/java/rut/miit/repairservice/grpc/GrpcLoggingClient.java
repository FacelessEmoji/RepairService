package rut.miit.repairservice.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import rut.miit.repairservice.LoggingServiceGrpc;
import rut.miit.repairservice.LoggingServiceGrpc.LoggingServiceBlockingStub;
import rut.miit.repairservice.LoggingServiceProto.LogRequest;
import rut.miit.repairservice.LoggingServiceProto.LogResponse;

@Component
public class GrpcLoggingClient {

    @Value("${grpc.server.host}")
    private String host;

    @Value("${grpc.server.port}")
    private int port;

    private final ManagedChannel channel;
    private final LoggingServiceBlockingStub blockingStub;

    public GrpcLoggingClient(@Value("${grpc.server.host}") String host, @Value("${grpc.server.port}") int port) {
        // Использование значений из application.properties
        this.channel = ManagedChannelBuilder.forAddress(host, port)
                .usePlaintext()  // Для простоты — используем незащищённое соединение
                .build();
        this.blockingStub = LoggingServiceGrpc.newBlockingStub(channel);
    }

    public void logAction(String action, String entityType, String entityId, String description, String timestamp) {
        // Создаем запрос с использованием description вместо userId
        LogRequest request = LogRequest.newBuilder()
                .setAction(action)
                .setEntityType(entityType)
                .setEntityId(entityId)
                .setDescription(description)  // Теперь описание действия
                .setTimestamp(timestamp)
                .build();

        // Отправляем запрос
        LogResponse response = blockingStub.logAction(request);

        // Выводим результат
        System.out.println("Response from server: " + response.getMessage());
    }

    public void shutdown() {
        channel.shutdown();
    }
}
