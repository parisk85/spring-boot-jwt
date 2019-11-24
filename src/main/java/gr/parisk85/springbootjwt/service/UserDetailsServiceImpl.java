package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUserService applicationUserService;

    public UserDetailsServiceImpl(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<ApplicationUser> applicationUserOptional = applicationUserService.findByUsername(username);

        applicationUserOptional.orElseThrow(() -> new UsernameNotFoundException(username));

        return new User(applicationUserOptional.get().getUsername(),
                applicationUserOptional.get().getPassword(), new ArrayList<>());
    }
}
