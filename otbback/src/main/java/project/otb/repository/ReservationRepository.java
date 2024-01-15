package project.otb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.Reservation;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findByUsername(String username);
    Reservation findByrtuinum(String rtuinum);
    List<Reservation> findBybusnumberplate(String busnumberplate);
}