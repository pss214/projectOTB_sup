package project.otb.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.Bus;

@Repository
public interface BusRepository extends JpaRepository<Bus,String> {
    Bus findByBusUniNumber(String BusUniNumber);
}
