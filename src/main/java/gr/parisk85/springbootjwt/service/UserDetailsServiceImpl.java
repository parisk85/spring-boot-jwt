package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.model.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ApplicationUserService applicationUserService;
    private final RoleService roleService;

    public UserDetailsServiceImpl(final ApplicationUserService applicationUserService, final RoleService roleService) {
        this.applicationUserService = applicationUserService;
        this.roleService = roleService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final Optional<ApplicationUser> applicationUserOptional = applicationUserService.findByUsername(username);

        applicationUserOptional.orElseThrow(() -> new UsernameNotFoundException(username));

        final Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        for (final Role role : applicationUserOptional.get().getRoles()) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(applicationUserOptional.get().getUsername(),
                applicationUserOptional.get().getPassword(), grantedAuthorities);
    }
}
