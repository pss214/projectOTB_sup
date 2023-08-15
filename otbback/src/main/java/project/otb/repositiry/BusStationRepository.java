package project.otb.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import project.otb.entity.BusStation;

public interface BusStationRepository extends JpaRepository<BusStation, Long> {
    BusStation findBystationid(String stationid);
    BusStation findBystationuniid(String stationuniid);
}
