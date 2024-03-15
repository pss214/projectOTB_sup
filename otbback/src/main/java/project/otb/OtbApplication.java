package project.otb;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.otb.entity.Admin;
import project.otb.repository.AdminRepository;

@SpringBootApplication
public class OtbApplication implements CommandLineRunner {
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	public OtbApplication(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
		this.adminRepository = adminRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public static void main(String[] args) {
		SpringApplication.run(OtbApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Admin admin = adminRepository.findByAdminname("admin");
		if(admin==null){
			adminRepository.save(Admin.builder().adminname("admin").password(passwordEncoder.encode("12341234")).email("admin@gmail.com").build());
		}
	}
}
