package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.repository.ApplicationUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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
        final String confirmationToken = UUID.randomUUID().toString();
        applicationUser.setConfirmationToken(confirmationToken);
        applicationUser.setConfirmationTokenExpiration(getConfirmationTokenExpirationDate());
        if (applicationUser.getRoles().isEmpty()) {
            applicationUser.setRoles(Set.of(roleService.getByName("ROLE_USER").get()));
        }
        applicationUserRepository.save(applicationUser);
    }

    @Transactional
    public void enableUser(final String username, final String confirmationToken) {
        //TODO: custom exception
        ApplicationUser user = findByUsername(username).orElseThrow(RuntimeException::new);
        if (!user.getConfirmationToken().equals(confirmationToken)) {
            //TODO: custom exception
            throw new RuntimeException();
        }
        if (user.getConfirmationTokenExpiration().before(new Date())) {
            //TODO: custom exception
            throw new RuntimeException();
        }
        user.setConfirmationToken(null);
        user.setConfirmationTokenExpiration(null);
        user.setEnabled(true);
        applicationUserRepository.save(user);
    }

    private Date getConfirmationTokenExpirationDate() {
        final Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.MINUTE, 30);
        return Date.from(expirationTime.toInstant());
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
