package rut.miit.repairservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rut.miit.repairservice.models.entities.Master;

@Repository
public interface MasterRepository extends JpaRepository<Master, String> {

}
