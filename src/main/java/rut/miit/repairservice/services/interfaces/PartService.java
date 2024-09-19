package rut.miit.repairservice.services.interfaces;

import rut.miit.repairservice.dtos.main.PartDTO;

import java.util.List;
import java.math.BigDecimal;

public interface PartService<ID> {
    List<PartDTO> getAllParts();
    PartDTO getPartById(ID id);
    PartDTO createPart(PartDTO partDTO);
    PartDTO updatePart(ID id, PartDTO partDTO);
    void deletePart(ID id);

    PartDTO updatePartName(ID id, String name);
    PartDTO updatePartQuantity(ID id, Integer quantity);
    PartDTO updatePartPrice(ID id, BigDecimal price);
}

