package gr.parisk85.springbootjwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import gr.parisk85.springbootjwt.model.ConfirmationEmail;
import gr.parisk85.springbootjwt.model.MailSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Properties;

@Configuration
public class BeanConfig {
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JavaMailSender javaMailSender(final @Autowired MailSettings mailSettings) {
        final JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost(mailSettings.getHost());
        javaMailSender.setPort(mailSettings.getPort());
        javaMailSender.setUsername(mailSettings.getUsername());
        javaMailSender.setPassword(mailSettings.getPassword());
        final Properties mailProps = javaMailSender.getJavaMailProperties();
        mailProps.put("mail.transport.protocol", "smtp");
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.smtp.starttls.enable", "true");
        mailProps.put("mail.debug", "true");
        return javaMailSender;
    }

    @Bean
    @ConfigurationProperties(prefix = "mail.settings")
    public MailSettings mailSettings() {
        return new MailSettings();
    }

    @Bean
    @ConfigurationProperties(prefix = "mail.confirm")
    public ConfirmationEmail confirmationEmail() {
        return new ConfirmationEmail();
    }

    @Bean(name = "applicationEventMulticaster")
    public ApplicationEventMulticaster simpleApplicationEventMulticaster() {
        SimpleApplicationEventMulticaster eventMulticaster = new SimpleApplicationEventMulticaster();
        eventMulticaster.setTaskExecutor(new SimpleAsyncTaskExecutor());
        return eventMulticaster;
    }

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
