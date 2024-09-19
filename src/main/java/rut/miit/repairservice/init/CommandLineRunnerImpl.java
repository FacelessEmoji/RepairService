package rut.miit.repairservice.init;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerImpl {

    @Autowired
    private ModelMapper modelMapper;

}
