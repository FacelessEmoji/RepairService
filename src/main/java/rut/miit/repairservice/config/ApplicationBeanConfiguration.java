package rut.miit.repairservice.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rut.miit.repairservice.dtos.main.OrderDTO;
import rut.miit.repairservice.dtos.main.OrderPartDTO;
import rut.miit.repairservice.models.entities.Order;
import rut.miit.repairservice.models.entities.OrderPart;

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration()
                .setFieldMatchingEnabled(true)
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE);

        TypeMap<Order, OrderDTO> orderToDTO = modelMapper.createTypeMap(Order.class, OrderDTO.class);
        orderToDTO.addMappings(m -> m.map(src -> src.getClient().getId(), OrderDTO::setClient));
        orderToDTO.addMappings(m -> m.map(src -> src.getMaster().getId(), OrderDTO::setMaster));


        TypeMap<OrderPart, OrderPartDTO> orderPartToDTO = modelMapper.createTypeMap(OrderPart.class, OrderPartDTO.class);
        orderPartToDTO.addMappings(m -> m.map(src -> src.getOrder().getId(), OrderPartDTO::setOrder));
        orderPartToDTO.addMappings(m -> m.map(src -> src.getPart().getId(), OrderPartDTO::setPart));

        return modelMapper;
    }
}
