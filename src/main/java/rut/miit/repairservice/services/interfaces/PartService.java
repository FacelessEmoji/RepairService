package rut.miit.repairservice.services.interfaces;

import rut.miit.repairservice.dtos.main.PartDTO;
import rut.miit.repairservice.models.entities.Part;

import java.util.List;
import java.math.BigDecimal;

public interface PartService<ID> {
    List<PartDTO> getAllParts();
    Part getPartById(ID id);
    PartDTO createPart(PartDTO partDTO);
    PartDTO updatePart(ID id, PartDTO partDTO);
    void deletePart(ID id);

    PartDTO updatePartName(ID id, String name);
    PartDTO updatePartQuantity(ID id, Integer quantity);
    PartDTO updatePartPrice(ID id, BigDecimal price);

    PartDTO getPartByName(String name);
}

