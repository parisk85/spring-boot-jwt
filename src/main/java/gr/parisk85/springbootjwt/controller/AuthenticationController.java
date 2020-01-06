package gr.parisk85.springbootjwt.controller;

import gr.parisk85.springbootjwt.event.OnRegistrationCompleteEvent;
import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.model.AuthenticationRequest;
import gr.parisk85.springbootjwt.model.AuthenticationResponse;
import gr.parisk85.springbootjwt.service.ApplicationUserService;
import gr.parisk85.springbootjwt.service.JwtTokenService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
    private final AuthenticationManager authenticationManager;
    private final ApplicationUserService applicationUserService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenService jwtTokenService;
    private final ApplicationEventPublisher eventPublisher;

    public AuthenticationController(final AuthenticationManager authenticationManager,
                                    final ApplicationUserService applicationUserService,
                                    final UserDetailsService userDetailsService, JwtTokenService jwtTokenService,
                                    final ApplicationEventPublisher eventPublisher) {
        this.authenticationManager = authenticationManager;
        this.applicationUserService = applicationUserService;
        this.userDetailsService = userDetailsService;
        this.jwtTokenService = jwtTokenService;
        this.eventPublisher = eventPublisher;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        //TODO: create controller advice to catch BadCredentialsException
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                        authenticationRequest.getPassword()));

        //TODO: add password to userDetails for token generation
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final String token = jwtTokenService.generateToken(userDetails);

        //TODO: investigate if this is placed properly or a new endpoint should contain the update
        applicationUserService.updateLastLoginDate(authenticationRequest.getUsername());

        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<ApplicationUser> register(@RequestBody ApplicationUser applicationUser) {
        if (!applicationUser.getPassword().equals(applicationUser.getPasswordConfirm())) {
            //TODO: custom exception
            throw new RuntimeException("Passwords do not match");
        }

        applicationUserService.createNew(applicationUser);

        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/users/{id}")
                .build().expand(applicationUser.getId()).toUri();

        final String emailVerificaitonToken = UUID.randomUUID().toString();

        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(applicationUser, emailVerificaitonToken));

        return ResponseEntity.created(location)
                .body(applicationUser);
    }

    @PostMapping("/confirm")
    public ResponseEntity<Void> confirmEmail(final @RequestBody String uuid) {
        //TODO: handle mail confirmation
        return ResponseEntity.ok().build();
    }
}
