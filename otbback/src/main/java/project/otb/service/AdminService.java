package project.otb.service;

import org.springframework.stereotype.Service;
import project.otb.repositiry.AdminRepository;

@Service
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

}
