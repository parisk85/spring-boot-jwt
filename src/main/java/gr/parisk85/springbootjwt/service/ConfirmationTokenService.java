package gr.parisk85.springbootjwt.service;

import gr.parisk85.springbootjwt.model.ApplicationUser;
import gr.parisk85.springbootjwt.model.ConfirmationToken;
import gr.parisk85.springbootjwt.repository.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    @Transactional
    public ConfirmationToken createConfirmationToken(final ApplicationUser user) {
        return confirmationTokenRepository.save(new ConfirmationToken(user));
    }

    public Optional<ConfirmationToken> getByUser(final ApplicationUser user) {
        return confirmationTokenRepository.findByUser(user);
    }

    public Optional<ConfirmationToken> getByToken(final String token) {
        return confirmationTokenRepository.findByToken(token);
    }
}
