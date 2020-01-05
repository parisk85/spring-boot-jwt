package gr.parisk85.springbootjwt.controller;

import gr.parisk85.springbootjwt.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    @GetMapping
    public String test() {
        return "This is not a drill";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/admin")
    public String adminTest() {
        return "This should be visible only with admin rights";
    }

    @GetMapping("/mail")
    public void testMail() {
        emailService.sendConfirmationEmail("parisk85@gmail.com", "test", "asdf");
    }
}
