package gr.parisk85.springbootjwt.event;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final ApplicationUser user;

    public OnRegistrationCompleteEvent(final ApplicationUser user) {
        super(user);
        this.user = user;
    }

    public ApplicationUser getUser() {
        return user;
    }
}
