package project.otb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.BusStation;
@Repository
public interface BusStationRepository extends JpaRepository<BusStation, Long> {
    BusStation findBystationid(String stationid);
    BusStation findBystationuniid(String stationuniid);
}
