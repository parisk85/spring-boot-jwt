package gr.parisk85.springbootjwt.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @GetMapping
    public String test() {
        return "This is not a drill";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String adminTest() {
        return "This should be visible only with admin rights";
    }
}
