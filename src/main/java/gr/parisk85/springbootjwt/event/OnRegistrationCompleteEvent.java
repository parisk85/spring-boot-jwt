package gr.parisk85.springbootjwt.event;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    public OnRegistrationCompleteEvent(ApplicationUser user) {
        super(user);
    }
}
