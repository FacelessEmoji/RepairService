package rut.miit.repairservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.MasterDTO;
import rut.miit.repairservice.grpc.GrpcLoggingClient;
import rut.miit.repairservice.models.entities.Master;
import rut.miit.repairservice.models.enums.SpecializationType;
import rut.miit.repairservice.repositories.MasterRepository;
import rut.miit.repairservice.services.interfaces.MasterService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MasterServiceImpl implements MasterService<String> {
    private MasterRepository masterRepository;
    private ModelMapper modelMapper;
    private GrpcLoggingClient grpcLoggingClient;

    @Autowired
    public void setMasterRepository(MasterRepository masterRepository) {
        this.masterRepository = masterRepository;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setGrpcLoggingClient(GrpcLoggingClient grpcLoggingClient) {
        this.grpcLoggingClient = grpcLoggingClient;
    }

    @Override
    public List<MasterDTO> getAllMasters() {
        return masterRepository.findAll().stream()
                .map(m -> modelMapper.map(m, MasterDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Master getMasterById(String s) {
        return masterRepository.findById(s).orElse(null);
    }

    @Override
    public MasterDTO createMaster(MasterDTO masterDTO) {
        Master master = modelMapper.map(masterDTO, Master.class);
        master = masterRepository.saveAndFlush(master);
        MasterDTO result = modelMapper.map(master, MasterDTO.class);

        grpcLoggingClient.logAction(
                "CREATE",
                "Master",
                master.getId(),
                "System",
                java.time.ZonedDateTime.now().toString()
        );

        return result;
    }

    @Override
    public MasterDTO updateMaster(String s, MasterDTO masterDTO) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setFirstName(masterDTO.getFirstName());
        master.setPhoneNumber(masterDTO.getPhoneNumber());
        master.setSpecialization(masterDTO.getSpecialization());
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }

    @Override
    public void deleteMaster(String s) {
        masterRepository.deleteById(s);

        grpcLoggingClient.logAction(
                "DELETE",
                "Master",
                s,
                "System",
                java.time.ZonedDateTime.now().toString()
        );
    }
    @Override
    public MasterDTO updateMasterName(String s, String name) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setFirstName(name);
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }

    @Override
    public MasterDTO updateMasterPhone(String s, String phone) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setPhoneNumber(phone);
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }

    @Override
    public MasterDTO updateMasterSpecialization(String s, SpecializationType specialization) {
        Master master = masterRepository.findById(s).orElseThrow();
        master.setSpecialization(specialization);
        return modelMapper.map(masterRepository.saveAndFlush(master), MasterDTO.class);
    }
}
