package project.otb.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import project.otb.DTO.BusDTO;
import project.otb.DTO.LoginDto;
import project.otb.DTO.ResponseDTO;
import project.otb.entity.Bus;
import project.otb.security.TokenProvider;
import project.otb.repositiry.BusRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
public class BusService {
    private final BusRepository busRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public BusService(BusRepository busRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.busRepository = busRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    public ResponseDTO create(final BusDTO entity) {
        if(busRepository.existsBybusNumberPlate(entity.getBusnumberplate())){
            throw new RuntimeException("아이디가 존재합니다!");
        }else {
            Bus bus = Bus.builder()
                    .BusNumber(entity.getBusnumber())
                    .busNumberPlate(entity.getBusnumberplate())
                    .Password(passwordEncoder.encode(entity.getPassword()))
                    .Personnel(entity.getPersonnel())
                    .Created_Date(LocalDateTime.now())
                    .build();
            busRepository.save(bus);
            return ResponseDTO.builder()
                    .message("회원가입을 성공했습니다")
                    .build();
        }
    }
    public BusDTO getLogin(final LoginDto dto) {
        Bus bus = busRepository.findBybusNumberPlate(dto.getUsername());
        if (passwordEncoder.matches(dto.getPassword(), bus.getPassword())) {
            String token = tokenProvider.createToken(String.format("%s:%s", bus.getId(), "USER"));
            return BusDTO.builder()
                    .busnumberplate(bus.getBusNumberPlate())
                    .token("otb " + token)
                    .busnumber(bus.getBusNumber())
                    .personnel(bus.getPersonnel())
                    .build();
        } else {
            return null;
        }
    }
}
