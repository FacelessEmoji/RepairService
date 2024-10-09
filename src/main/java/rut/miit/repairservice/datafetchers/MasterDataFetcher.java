package rut.miit.repairservice.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import rut.miit.repairservice.dtos.main.MasterDTO;
import rut.miit.repairservice.models.enums.SpecializationType;
import rut.miit.repairservice.services.implementations.ClientServiceImpl;
import rut.miit.repairservice.services.implementations.MasterServiceImpl;

import java.util.List;

@DgsComponent
public class MasterDataFetcher {
    private MasterServiceImpl masterService;
    private ModelMapper modelMapper;

    @Autowired
    public void setMasterService(MasterServiceImpl masterService) {
        this.masterService = masterService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @DgsQuery
    public List<MasterDTO> masters(@InputArgument String masterId) {
        if (masterId != null) {
            MasterDTO master = modelMapper.map(masterService.getMasterById(masterId), MasterDTO.class);
            return master != null ? List.of(master) : List.of();
        }
        return masterService.getAllMasters();
    }

    @DgsMutation
    public MasterDTO addMaster(
            @InputArgument String firstName,
            @InputArgument String phoneNumber,
            @InputArgument SpecializationType specialization) {

        MasterDTO newMaster = new MasterDTO(firstName, phoneNumber, specialization);
        MasterDTO createdMaster = masterService.createMaster(newMaster);

        return createdMaster;
    }

    @DgsMutation
    public MasterDTO updateMasterName(
            @InputArgument String id,
            @InputArgument String name) {

        return masterService.updateMasterName(id, name);
    }

    @DgsMutation
    public MasterDTO updateMasterPhone(
            @InputArgument String id,
            @InputArgument String phone) {

        return masterService.updateMasterPhone(id, phone);
    }

    @DgsMutation
    public MasterDTO updateMasterSpecialization(
            @InputArgument String id,
            @InputArgument SpecializationType specialization) {

        return masterService.updateMasterSpecialization(id, specialization);
    }

    @DgsMutation
    public String deleteMaster(@InputArgument String id) {
        try {
            masterService.deleteMaster(id);
            return "Master deleted successfully";
        } catch (EntityNotFoundException e) {
            return "Master not found";
        } catch (Exception e) {
            return "Error deleting master";
        }
    }
}
