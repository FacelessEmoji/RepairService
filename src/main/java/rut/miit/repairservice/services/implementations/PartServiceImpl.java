package rut.miit.repairservice.services.implementations;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.PartDTO;
import rut.miit.repairservice.grpc.GrpcLoggingClient;
import rut.miit.repairservice.models.entities.Part;
import rut.miit.repairservice.repositories.PartRepository;
import rut.miit.repairservice.services.interfaces.PartService;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartServiceImpl implements PartService<String> {
    private PartRepository partRepository;
    private ModelMapper modelMapper;
    private GrpcLoggingClient grpcLoggingClient;

    @Autowired
    public void setPartRepository(PartRepository partRepository) {
        this.partRepository = partRepository;
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
    public List<PartDTO> getAllParts() {
        return partRepository.findAll().stream()
                .map(p -> modelMapper.map(p, PartDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Part getPartById(String s) {
        return partRepository.findById(s).orElse(null);
    }

    @Override
    public PartDTO createPart(PartDTO partDTO) {
        Part part = modelMapper.map(partDTO, Part.class);
        part = partRepository.saveAndFlush(part);
        PartDTO result = modelMapper.map(part, PartDTO.class);

        grpcLoggingClient.logAction(
                "CREATE",
                "Part",
                part.getId(),
                "System",
                java.time.ZonedDateTime.now().toString()
        );

        return result;
    }

    @Override
    public PartDTO updatePart(String s, PartDTO partDTO) {
        Part part = partRepository.findById(s).orElseThrow();
        part.setName(partDTO.getName());
        part.setQuantity(partDTO.getQuantity());
        part.setPrice(partDTO.getPrice());
        Part updatedPart = partRepository.saveAndFlush(part);

        grpcLoggingClient.logAction(
                "UPDATE",
                "Part",
                updatedPart.getId(),
                "System",
                java.time.ZonedDateTime.now().toString()
        );

        return modelMapper.map(updatedPart, PartDTO.class);
    }

    @Override
    public void deletePart(String s) {
        partRepository.deleteById(s);

        grpcLoggingClient.logAction(
                "DELETE",
                "Part",
                s,
                "System",
                java.time.ZonedDateTime.now().toString()
        );
    }

    @Override
    public PartDTO updatePartName(String s, String name) {
        Part part = partRepository.findById(s).orElseThrow();
        part.setName(name);
        return modelMapper.map(partRepository.saveAndFlush(part), PartDTO.class);
    }

    @Override
    public PartDTO updatePartQuantity(String s, Integer quantity) {
        Part part = partRepository.findById(s).orElseThrow();
        part.setQuantity(quantity);
        return modelMapper.map(partRepository.saveAndFlush(part), PartDTO.class);
    }

    @Override
    public PartDTO updatePartPrice(String s, BigDecimal price) {
        Part part = partRepository.findById(s).orElseThrow();
        part.setPrice(price);
        return modelMapper.map(partRepository.saveAndFlush(part), PartDTO.class);
    }

    @Override
    public PartDTO getPartByName(String name) {
        return modelMapper.map(partRepository.findByName(name), PartDTO.class);
    }
}