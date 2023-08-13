package project.otb.repositiry;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation findByUsername(String username);
//    List<Reservation> findByBusNumberPlate(String busNumberPlate);
}