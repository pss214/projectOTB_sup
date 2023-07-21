package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.otb.DTO.BusDTO;
import project.otb.entity.Bus;
import project.otb.repositiry.BusRepository;

@Slf4j
@Service
public class BusService {
    private final BusRepository busRepository;

    public BusService(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    public Bus create(final BusDTO entity) {
        Bus bus = Bus.builder()
                .BusNumber(entity.getBusNumber())
                .Password(entity.getPassword())
                .Personnel(entity.getPersonnel())
    }
}
