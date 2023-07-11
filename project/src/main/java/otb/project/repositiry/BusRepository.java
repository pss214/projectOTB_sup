package otb.project.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import otb.project.entity.Bus;

public interface BusRepository extends JpaRepository<Bus,String> {
}
