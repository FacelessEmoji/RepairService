package rut.miit.repairservice.services.interfaces;

import rut.miit.repairservice.dtos.main.MasterDTO;
import rut.miit.repairservice.models.enums.SpecializationType;

import java.util.List;

public interface MasterService<ID> {
    List<MasterDTO> getAllMasters();
    MasterDTO getMasterById(ID id);
    MasterDTO createMaster(MasterDTO masterDTO);
    MasterDTO updateMaster(ID id, MasterDTO masterDTO);
    void deleteMaster(ID id);

    MasterDTO updateMasterName(ID id, String name);
    MasterDTO updateMasterPhone(ID id, String phone);
    MasterDTO updateMasterSpecialization(ID id, SpecializationType specialization);
}

