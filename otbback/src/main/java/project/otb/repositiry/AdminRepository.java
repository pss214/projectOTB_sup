package project.otb.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.otb.entity.Admin;
import project.otb.entity.Bus;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAdminname(String username);
}
