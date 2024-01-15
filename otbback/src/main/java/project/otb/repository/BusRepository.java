package project.otb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus,Long> {
    Bus findBybusNumberPlate(String BusUniNumber);
    Boolean existsBybusNumberPlate(String username);
}
