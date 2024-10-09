package rut.miit.repairservice.datafetchers;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import rut.miit.repairservice.dtos.main.PartDTO;
import rut.miit.repairservice.services.implementations.PartServiceImpl;

import java.math.BigDecimal;
import java.util.List;

@DgsComponent
public class PartDataFetcher {
    private PartServiceImpl partService;
    private ModelMapper modelMapper;

    @Autowired
    public void setPartService(PartServiceImpl partService) {
        this.partService = partService;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @DgsQuery
    public List<PartDTO> parts(@InputArgument String partId) {
        if (partId != null) {
            // Если указан partId, возвращаем только одну деталь
            PartDTO part = modelMapper.map(partService.getPartById(partId), PartDTO.class);
            return part != null ? List.of(part) : List.of();
        }
        // Иначе возвращаем все детали
        return partService.getAllParts();
    }

    @DgsMutation
    public PartDTO addPart(
            @InputArgument String name,
            @InputArgument Integer quantity,
            @InputArgument BigDecimal price) {

        PartDTO newPart = new PartDTO(name, quantity, price);
        PartDTO createdPart = partService.createPart(newPart);

        return createdPart;
    }

    @DgsMutation
    public PartDTO updatePartName(
            @InputArgument String id,
            @InputArgument String name) {

        return partService.updatePartName(id, name);
    }

    @DgsMutation
    public PartDTO updatePartQuantity(
            @InputArgument String id,
            @InputArgument Integer quantity) {

        return partService.updatePartQuantity(id, quantity);
    }

    @DgsMutation
    public PartDTO updatePartPrice(
            @InputArgument String id,
            @InputArgument BigDecimal price) {

        return partService.updatePartPrice(id, price);
    }

    @DgsMutation
    public String deletePart(@InputArgument String id) {
        try {
            partService.deletePart(id);
            return "Part deleted successfully";
        } catch (EntityNotFoundException e) {
            return "Part not found";
        } catch (Exception e) {
            return "Error deleting part";
        }
    }
}

