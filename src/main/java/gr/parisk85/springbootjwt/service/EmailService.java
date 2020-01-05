package gr.parisk85.springbootjwt.service;

public interface EmailService {
    void sendConfirmationMail(String to, String subject, String text);
}
