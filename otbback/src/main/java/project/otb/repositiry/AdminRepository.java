package project.otb.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import project.otb.entity.Admin;
import project.otb.entity.Bus;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByAdminname(String username);
}
