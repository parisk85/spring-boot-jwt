package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUserService applicationUserService;

    public UserDetailsServiceImpl(final ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final ApplicationUser applicationUser = applicationUserService.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        applicationUser.getRoles().stream().forEach(
                role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()))
        );

        //TODO: investigate need for creating the additional fields in ApplicationUser
        return new User(applicationUser.getUsername(),
                applicationUser.getPassword(),
                applicationUser.isEnabled(), true, true, true,
                grantedAuthorities);
    }
}
