package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.model.Role;
import gr.parisk85.springbootjwt.repository.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public ApplicationUserService(final ApplicationUserRepository applicationUserRepository, final RoleService roleService, final BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.applicationUserRepository = applicationUserRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public Optional<ApplicationUser> findById(final Long id) {
        return applicationUserRepository.findById(id);
    }

    public Optional<ApplicationUser> findByUsername(final String username) {
        return applicationUserRepository.getByUsername(username);
    }

    public void createNew(final ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUser.setCreatedAt(new Date());
        applicationUser.setLastLoginAt(null);
        if (applicationUser.getRoles().isEmpty()) {
            final List<Role> userRole = Arrays.asList(roleService.getByName("ROLE_USER").get());
            applicationUser.setRoles(new HashSet<>(userRole));
        }
        applicationUserRepository.save(applicationUser);
    }

    public void updateLastLoginDate(final String username) {
        Optional<ApplicationUser> applicationUserOptional = findByUsername(username);
        applicationUserOptional.get().setLastLoginAt(new Date());
        update(applicationUserOptional.get());
    }

    public void update(final ApplicationUser applicationUser) {
        applicationUserRepository.save(applicationUser);
    }
}
