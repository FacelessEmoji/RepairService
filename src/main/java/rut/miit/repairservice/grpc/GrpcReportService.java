package rut.miit.repairservice.grpc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.OrderDTO;
import rut.miit.repairservice.dtos.main.OrderPartDTO;
import rut.miit.repairservice.models.entities.Master;
import rut.miit.repairservice.models.entities.Part;
import rut.miit.repairservice.services.implementations.MasterServiceImpl;
import rut.miit.repairservice.services.implementations.OrderPartServiceImpl;
import rut.miit.repairservice.services.implementations.OrderServiceImpl;
import rut.miit.repairservice.services.implementations.PartServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GrpcReportService {

    private OrderServiceImpl orderService;
    private MasterServiceImpl masterService;
    private OrderPartServiceImpl orderPartService;
    private PartServiceImpl partService;
    private GrpcLoggingClient grpcLoggingClient;

    @Autowired
    public GrpcReportService(OrderServiceImpl orderService, MasterServiceImpl masterService, OrderPartServiceImpl orderPartService, GrpcLoggingClient grpcLoggingClient, PartServiceImpl partService) {
        this.orderService = orderService;
        this.masterService = masterService;
        this.orderPartService = orderPartService;
        this.grpcLoggingClient = grpcLoggingClient;
        this.partService = partService;
    }

    public void generateReport() {
        // 1. Orders grouped by master
        List<OrderDTO> orders = orderService.getAllOrders();
        Map<String, List<OrderDTO>> ordersByMaster = orders.stream()
                .collect(Collectors.groupingBy(OrderDTO::getMaster));

        // 2. Calculate revenue per master
        Map<String, BigDecimal> masterRevenues = ordersByMaster.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .map(this::calculateOrderRevenue)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ));

        // 3. Best and worst masters
        String bestMasterId = masterRevenues.entrySet().stream()
                .max(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        String worstMasterId = masterRevenues.entrySet().stream()
                .min(Comparator.comparing(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse("Unknown");

        Master bestMaster = masterService.getMasterById(bestMasterId);
        Master worstMaster = masterService.getMasterById(worstMasterId);

        BigDecimal bestMasterRevenue = masterRevenues.getOrDefault(bestMasterId, BigDecimal.ZERO);
        BigDecimal worstMasterRevenue = masterRevenues.getOrDefault(worstMasterId, BigDecimal.ZERO);

        // 4. Turnover for the day
        LocalDateTime today = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        BigDecimal dailyTurnover = orders.stream()
                .map(this::calculateOrderRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // 5. Total completed orders
        long completedOrdersCount = orders.size();

        // 6. Total used parts
        List<OrderPartDTO> allOrderParts = orderPartService.getAllOrderParts();
        int totalUsedParts = allOrderParts.stream()
                .mapToInt(OrderPartDTO::getQuantity)
                .sum();

        // 7. Average order cost
        BigDecimal totalRevenue = orders.stream()
                .map(this::calculateOrderRevenue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal averageOrderCost = completedOrdersCount > 0
                ? totalRevenue.divide(BigDecimal.valueOf(completedOrdersCount), BigDecimal.ROUND_HALF_UP)
                : BigDecimal.ZERO;

        // Combine statistics into one log message
        String statsLogMessage = String.format(
                "Best Master: %s (Revenue: %s), Worst Master: %s (Revenue: %s), Daily Turnover: %s, " +
                        "Completed Orders: %d, Total Used Parts: %d, Average Order Cost: %s",
                bestMaster.getFirstName(), bestMasterRevenue,
                worstMaster.getFirstName(), worstMasterRevenue,
                dailyTurnover, completedOrdersCount, totalUsedParts, averageOrderCost
        );

        // Send the log through gRPC client
        grpcLoggingClient.logAction("Stats", "SystemStats", "DailyStats", statsLogMessage, LocalDateTime.now().toString());
    }

    private BigDecimal calculateOrderRevenue(OrderDTO order) {
        List<OrderPartDTO> orderParts = orderPartService.getAllOrderParts().stream()
                .filter(op -> op.getOrder().equals(order.getId()))
                .toList();

        return orderParts.stream()
                .map(orderPart -> {
                    Part part = partService.getPartById(orderPart.getPart());
                    return part.getPrice().multiply(BigDecimal.valueOf(orderPart.getQuantity()));
                })
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

