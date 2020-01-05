package gr.parisk85.springbootjwt.event;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener {
    private final EmailService emailService;

    public RegistrationListener(final EmailService emailService) {
        this.emailService = emailService;
    }

    public void onApplicationEvent(final OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.dispatchConfirmationEmail(onRegistrationCompleteEvent);
    }

    @EventListener
    private void dispatchConfirmationEmail(final OnRegistrationCompleteEvent event) {
        ApplicationUser user = event.getUser();
        emailService.sendConfirmationEmail(user.getEmail(), "Confirmation E-mail", "confirm your e-mail!");
    }
}
