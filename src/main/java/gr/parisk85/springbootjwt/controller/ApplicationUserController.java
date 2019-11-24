package gr.parisk85.springbootjwt.controller;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.service.ApplicationUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping(value = "/user")
public class ApplicationUserController {
    private final ApplicationUserService applicationUserService;

    public ApplicationUserController(ApplicationUserService applicationUserService) {
        this.applicationUserService = applicationUserService;
    }

    @PostMapping
    public ResponseEntity<ApplicationUser> saveUser(@RequestBody ApplicationUser applicationUser) {
        applicationUserService.createNew(applicationUser);

        final URI location = ServletUriComponentsBuilder.fromCurrentServletMapping()
                .path("/user/{id}")
                .build().expand(applicationUser.getId()).toUri();

        return ResponseEntity.created(location)
               .body(applicationUser);
    }
}
