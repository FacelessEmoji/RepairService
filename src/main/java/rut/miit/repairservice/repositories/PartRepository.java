package rut.miit.repairservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rut.miit.repairservice.models.entities.Part;

import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, String> {
    Part findByName(String name);
}
