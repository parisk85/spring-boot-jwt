package gr.parisk85.springbootjwt.event.listener;

import gr.parisk85.springbootjwt.model.ConfirmationEmail;
import gr.parisk85.springbootjwt.event.OnRegistrationCompleteEvent;
import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener {
    private final EmailService emailService;
    private final ConfirmationEmail confirmationEmail;

    public RegistrationListener(final EmailService emailService, final ConfirmationEmail confirmationEmail) {
        this.emailService = emailService;
        this.confirmationEmail = confirmationEmail;
    }

    public void onApplicationEvent(final OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.dispatchConfirmationEmail(onRegistrationCompleteEvent);
    }

    @EventListener
    private void dispatchConfirmationEmail(final OnRegistrationCompleteEvent event) {
        final ApplicationUser user = event.getUser();
        final String text = new StringBuilder(confirmationEmail.getMessage())
                .append(confirmationEmail.getAppUrl())
                .append(event.getUuid())
                .toString();
        emailService.sendConfirmationEmail(user.getEmail(), confirmationEmail.getSubject(), text);
    }
}
