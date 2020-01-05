package gr.parisk85.springbootjwt.service;

public interface EmailService {
    void sendConfirmationEmail(String to, String subject, String text);
}
