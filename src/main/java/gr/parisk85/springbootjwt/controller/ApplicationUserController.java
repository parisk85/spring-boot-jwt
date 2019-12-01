package gr.parisk85.springbootjwt.controller;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.service.ApplicationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/user")
public class ApplicationUserController {
    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationUser> getUser(@PathVariable Long id) {
        final Optional<ApplicationUser> applicationUserOptional = applicationUserService.findById(id);

        applicationUserOptional.orElseThrow(RuntimeException::new);

        return ResponseEntity
                .ok(applicationUserOptional.get());
    }
}
