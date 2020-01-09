package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.model.ConfirmationToken;
import gr.parisk85.springbootjwt.repository.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class ApplicationUserService {
    private final ApplicationUserRepository applicationUserRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;

    public ApplicationUserService(final ApplicationUserRepository applicationUserRepository, final RoleService roleService, final BCryptPasswordEncoder bCryptPasswordEncoder, final ConfirmationTokenService confirmationTokenService) {
        this.applicationUserRepository = applicationUserRepository;
        this.roleService = roleService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public Optional<ApplicationUser> findById(final Long id) {
        return applicationUserRepository.findById(id);
    }

    public Optional<ApplicationUser> findByUsername(final String username) {
        return applicationUserRepository.getByUsername(username);
    }

    @Transactional
    public void createNew(final ApplicationUser applicationUser) {
        applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
        applicationUser.setCreatedAt(new Date());
        applicationUser.setLastLoginAt(null);
        if (applicationUser.getRoles().isEmpty()) {
            applicationUser.setRoles(Set.of(roleService.getByName("ROLE_USER").get()));
        }
        applicationUserRepository.save(applicationUser);
        confirmationTokenService.createConfirmationToken(applicationUser);
    }

    @Transactional
    public void enableUser(final String username, final String confirmationToken) {
        //TODO: custom exception
        final ApplicationUser user = findByUsername(username).orElseThrow(RuntimeException::new);
        final ConfirmationToken storedToken = confirmationTokenService.getByUser(user).orElseThrow(RuntimeException::new);
        if (!storedToken.getConfirmationToken().equals(confirmationToken)) {
            //TODO: custom exception
            throw new RuntimeException();
        }
        if (storedToken.getExpirationDate().before(new Date())) {
            //TODO: custom exception
            throw new RuntimeException();
        }
        user.setEnabled(true);
        applicationUserRepository.save(user);
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
