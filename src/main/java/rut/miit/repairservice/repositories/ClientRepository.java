package rut.miit.repairservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import rut.miit.repairservice.models.entities.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
}
