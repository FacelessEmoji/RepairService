package rut.miit.repairservice.services.implementations;

import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.MasterDTO;
import rut.miit.repairservice.models.enums.SpecializationType;
import rut.miit.repairservice.services.interfaces.MasterService;

import java.util.List;

@Service
public class MasterServiceImpl implements MasterService<String>{
    @Override
    public List<MasterDTO> getAllMasters() {
        return List.of();
    }

    @Override
    public MasterDTO getMasterById(String s) {
        return null;
    }

    @Override
    public MasterDTO createMaster(MasterDTO masterDTO) {
        return null;
    }

    @Override
    public MasterDTO updateMaster(String s, MasterDTO masterDTO) {
        return null;
    }

    @Override
    public void deleteMaster(String s) {

    }

    @Override
    public MasterDTO updateMasterName(String s, String name) {
        return null;
    }

    @Override
    public MasterDTO updateMasterPhone(String s, String phone) {
        return null;
    }

    @Override
    public MasterDTO updateMasterSpecialization(String s, SpecializationType specialization) {
        return null;
    }
}
