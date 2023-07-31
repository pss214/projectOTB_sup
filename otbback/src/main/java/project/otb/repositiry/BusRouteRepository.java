package project.otb.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import project.otb.entity.BusRoute;

public interface BusRouteRepository extends JpaRepository<BusRoute,Long> {
    BusRoute findByroute(String Route_Id);
    BusRoute findByrouteid(String Route);
}
