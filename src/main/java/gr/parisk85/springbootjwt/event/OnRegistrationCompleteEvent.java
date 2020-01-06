package gr.parisk85.springbootjwt.event;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import org.springframework.context.ApplicationEvent;

public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private final ApplicationUser user;
    private final String uuid;

    public OnRegistrationCompleteEvent(final ApplicationUser user, final String uuid) {
        super(user);
        this.user = user;
        this.uuid = uuid;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public String getUuid() {
        return uuid;
    }
}
