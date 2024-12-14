package rut.miit.repairservice.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rut.miit.repairservice.grpc.GrpcReportService;
import rut.miit.repairservice.ws.WebSocketHandler;
import java.io.File;

@Controller
public class ReportController {

    private final GrpcReportService grpcReportService;
    private final WebSocketHandler webSocketHandler;

    @Autowired
    public ReportController(GrpcReportService grpcReportService, WebSocketHandler webSocketHandler) {
        this.grpcReportService = grpcReportService;
        this.webSocketHandler = webSocketHandler;
    }

    @GetMapping("/")
    public String getReportPage() {
        return "report";
    }

    @PostMapping("/generate-report")
    @ResponseBody
    public String generateReport() {
        webSocketHandler.sendMessageToAll("Отчет в процессе создания...");

        grpcReportService.generateReport();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        webSocketHandler.sendMessageToAll("Отчет готов! Вы можете скачать его.");

        return "Отчет в процессе создания, ожидайте...";
    }

    @GetMapping("/download-report")
    @ResponseBody
    public ResponseEntity<Resource> downloadReport() {
        File reportFile = new File("reports/DailyReport_2024-12-12.pdf");

        if (!reportFile.exists()) {
            throw new RuntimeException("Отчет не найден!");
        }

        Resource resource = new FileSystemResource(reportFile);

        if (!resource.exists()) {
            throw new RuntimeException("Файл не существует.");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + reportFile.getName() + "\"")
                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")
                .body(resource);
    }

}
