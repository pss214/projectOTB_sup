package otb.project.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otb.project.entity.Reservation;
@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
