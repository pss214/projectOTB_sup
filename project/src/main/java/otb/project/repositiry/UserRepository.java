package otb.project.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import otb.project.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
