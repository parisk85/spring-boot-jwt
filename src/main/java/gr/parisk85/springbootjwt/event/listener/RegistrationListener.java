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
        //TODO: custom exception
        final ConfirmationToken confirmationToken = confirmationTokenService.getByUser(user).orElseThrow(RuntimeException::new);
        final String text = new StringBuilder(confirmationEmail.getMessage())
                .append(confirmationEmail.getAppUrl())
                .append(confirmationToken.getUser().getUsername())
                .append("/")
                .append(confirmationToken.getConfirmationToken())
                .toString();
        emailService.sendConfirmationEmail(user.getEmail(), confirmationEmail.getSubject(), text);
    }
}
