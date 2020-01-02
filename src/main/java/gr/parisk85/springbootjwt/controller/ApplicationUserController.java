package gr.parisk85.springbootjwt.controller;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.service.ApplicationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class ApplicationUserController {
    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PreAuthorize("#username == authentication.principal.username")
    @GetMapping("/{username}")
    public ResponseEntity<ApplicationUser> getUser(@PathVariable String username) {
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        final Optional<ApplicationUser> applicationUserOptional = applicationUserService.findByUsername(username);

        //TODO: create custom exception
        applicationUserOptional.orElseThrow(RuntimeException::new);

        return ResponseEntity
                .ok(applicationUserOptional.get());
    }
}
