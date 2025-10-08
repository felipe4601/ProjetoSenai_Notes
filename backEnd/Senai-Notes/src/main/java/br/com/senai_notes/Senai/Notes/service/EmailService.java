package br.com.senai_notes.Senai.Notes.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${SMTP_EMAIL}")
    private String origem;

    public EmailService(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }
    public void enviarEmail(String email, String novaSenha){
        SimpleMailMessage message = new SimpleMailMessage();
        // de quem vai mandar o email
        message.setFrom(origem);
        // destinatario do email
        message.setTo(email);
        // Título do email
        message.setSubject("SENAI NOTES - Senha Redefinida");
        // mensagem do email
        message.setText("Olá, \n\nsua senha foi definida com sucesso!" +
                "Sua nova senha é: " + novaSenha);
        mailSender.send(message);
    }
}
