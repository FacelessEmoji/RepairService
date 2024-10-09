package rut.miit.repairservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rut.miit.repairservice.models.entities.Order;
import rut.miit.repairservice.models.enums.StatusType;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
    List<Order> findAllByClient_Id(String clientId);
    List<Order> findAllByMaster_Id(String masterId);
    List<Order> findAllByClient_IdAndMaster_Id(String clientId, String masterId);
    List<Order> findAllByStatus(StatusType status);

}
