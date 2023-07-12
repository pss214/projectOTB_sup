package otb.project.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import otb.project.entity.Bus;
@Repository
public interface BusRepository extends JpaRepository<Bus,String> {
}
