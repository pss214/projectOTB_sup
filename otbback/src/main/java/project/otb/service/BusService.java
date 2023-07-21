package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.DTO.BusDTO;
import project.otb.DTO.LoginDto;
import project.otb.entity.Bus;
import project.otb.repositiry.BusRepository;

@Slf4j
@Service
public class BusService {
    private final BusRepository busRepository;
    private final PasswordEncoder passwordEncoder;

    public BusService(BusRepository busRepository, PasswordEncoder passwordEncoder) {
        this.busRepository = busRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Bus create(final BusDTO entity) {
        Bus bus = Bus.builder()
                .BusUniNumber(entity.getBusUniNumber())
                .Password(passwordEncoder.encode(entity.getPassword()))
                .Personnel(entity.getPersonnel())
                .build();

        return busRepository.save(bus);
    }
    public BusDTO getLogin(final LoginDto dto){
        Bus bus = busRepository.findByBusUniNumber(dto.getUsername());
        if(passwordEncoder.matches(dto.getPassword(), bus.getPassword())){
            final BusDTO response = BusDTO.builder()
                    .BusUniNumber(bus.getBusUniNumber())
                    .build();
            return response;
        }else{
            return null;
        }
    }
}
