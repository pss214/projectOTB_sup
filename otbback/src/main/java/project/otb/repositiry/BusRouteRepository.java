package project.otb.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.BusRoute;
@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
    BusRoute findByrouteidEquals(String Route_Id);
    BusRoute findByrouteEquals(String Route);
}
