package gr.parisk85.springbootjwt.event.listener;

import gr.parisk85.springbootjwt.event.OnRegistrationCompleteEvent;
import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.model.ConfirmationEmail;
import gr.parisk85.springbootjwt.model.ConfirmationToken;
import gr.parisk85.springbootjwt.service.ConfirmationTokenService;
import gr.parisk85.springbootjwt.service.EmailService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener {
    private final EmailService emailService;
    private final ConfirmationEmail confirmationEmail;
    private final ConfirmationTokenService confirmationTokenService;

    public RegistrationListener(final EmailService emailService, final ConfirmationEmail confirmationEmail, final ConfirmationTokenService confirmationTokenService) {
        this.emailService = emailService;
        this.confirmationEmail = confirmationEmail;
        this.confirmationTokenService = confirmationTokenService;
    }

    @EventListener
    public void onApplicationEvent(final OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.dispatchConfirmationEmail(onRegistrationCompleteEvent);
    }

    protected void dispatchConfirmationEmail(final OnRegistrationCompleteEvent event) {
        final ApplicationUser user = event.getUser();
        final ConfirmationToken confirmationToken = confirmationTokenService.createConfirmationToken(user);
        final String text = new StringBuilder(confirmationEmail.getMessage())
                .append(confirmationEmail.getAppUrl())
                .append(user.getUsername())
                .append("/")
                .append(confirmationToken.getToken())
                .toString();
        emailService.sendConfirmationEmail(user.getEmail(), confirmationEmail.getSubject(), text);
    }
}
