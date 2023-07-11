package otb.project.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import otb.project.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
