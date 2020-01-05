package gr.parisk85.springbootjwt.event;

import gr.parisk85.springbootjwt.service.EmailService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    private final EmailService emailService;

    public RegistrationListener(final EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        this.dispatchConfirmationEmail(onRegistrationCompleteEvent);
    }

    private void dispatchConfirmationEmail(final OnRegistrationCompleteEvent onRegistrationCompleteEvent) {
        System.out.println("EMAIL DISPATCHED");
    }
}
