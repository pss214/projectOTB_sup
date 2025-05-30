package project.otb.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByUsername(String username);
    User findByEmail(String email);
    User findByUsernameAndPassword(String username, String password);
    Boolean existsByUsername(String username);
}
