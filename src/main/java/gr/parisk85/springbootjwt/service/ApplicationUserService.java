package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.repository.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserService(ApplicationUserRepository applicationUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<ApplicationUser> findByUsername(String username) {
        return applicationUserRepository.getByUsername(username);
    }

    public void createNew(ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUserRepository.save(applicationUser);
    }
}
