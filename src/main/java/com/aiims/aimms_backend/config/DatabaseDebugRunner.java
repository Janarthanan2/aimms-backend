package main.java.com.aiims.aimms_backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseDebugRunner implements CommandLineRunner {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String datasourceUser;

    private final com.aiims.aimms_backend.repository.UserRepository userRepository;

    public DatabaseDebugRunner(com.aiims.aimms_backend.repository.UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("==================================================");
        System.out.println("DEBUG: Database Configuration Check");
        System.out.println("Datasource URL: " + datasourceUrl);
        System.out.println("Datasource User: " + datasourceUser);

        seedUsers();

        System.out.println("==================================================");
    }

    private void seedUsers() {
        String email = "admin@example.com";
        if (userRepository.findByEmail(email).isEmpty()) {
            com.aiims.aimms_backend.model.User admin = new com.aiims.aimms_backend.model.User();
            admin.setName("Admin User");
            admin.setEmail(email);
            admin.setPhone("1234567890");
            admin.setPasswordHash("admin123"); // Storing plain text as per current NoOp config
            admin.setRole(com.aiims.aimms_backend.model.Role.ADMIN);

            userRepository.save(admin);
            System.out.println("DEBUG: Created default admin user.");
            System.out.println("   Email: " + email);
            System.out.println("   Password: admin123");
        } else {
            System.out.println("DEBUG: Default admin user already exists (" + email + ")");
        }
    }
}
