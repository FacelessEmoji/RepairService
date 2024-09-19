package rut.miit.repairservice.services.implementations;

import org.springframework.stereotype.Service;
import rut.miit.repairservice.dtos.main.PartDTO;
import rut.miit.repairservice.services.interfaces.PartService;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PartServiceImpl implements PartService<String> {
    @Override
    public List<PartDTO> getAllParts() {
        return List.of();
    }

    @Override
    public PartDTO getPartById(String s) {
        return null;
    }

    @Override
    public PartDTO createPart(PartDTO partDTO) {
        return null;
    }

    @Override
    public PartDTO updatePart(String s, PartDTO partDTO) {
        return null;
    }

    @Override
    public void deletePart(String s) {

    }

    @Override
    public PartDTO updatePartName(String s, String name) {
        return null;
    }

    @Override
    public PartDTO updatePartQuantity(String s, Integer quantity) {
        return null;
    }

    @Override
    public PartDTO updatePartPrice(String s, BigDecimal price) {
        return null;
    }
}
