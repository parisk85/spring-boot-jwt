package gr.parisk85.springbootjwt.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "CONFIRMATION_TOKENS")
public class ConfirmationToken {
    @Id
    private String confirmationToken;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationDate;

    @OneToOne(targetEntity = ApplicationUser.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private ApplicationUser user;

    public ConfirmationToken() {}

    public ConfirmationToken(ApplicationUser user) {
        this.user = user;
        this.confirmationToken = UUID.randomUUID().toString();
        final Calendar expirationTime = Calendar.getInstance();
        expirationTime.add(Calendar.MINUTE, 30);
        this.expirationDate = Date.from(expirationTime.toInstant());
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
    }
}
