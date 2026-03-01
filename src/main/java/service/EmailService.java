package service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;
import javax.mail.Session;

public class EmailService {
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final String SMTP_PORT = "587";
    private static final String EMAIL_FROM = "hafsabelahnch@gmail.com"; // Remplacez par votre email
    private static final String EMAIL_PASSWORD = "zykq hhha uwpc jwcg"; // Mot de passe d'application



    //Envoie un email de vérification avec code

    public static boolean sendVerificationEmail(String toEmail, String code, String nom) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                  return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);

            }
        }); session.setDebug(true);

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Vérification de compte - Hotel Management");

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                    <div style="background-color: white; padding: 30px; border-radius: 10px; max-width: 600px; margin: auto;">
                        <h2 style="color: #2c3e50;">Bienvenue sur Hotel Management!</h2>
                        <p>Bonjour <strong>%s</strong>,</p>
                        <p>Merci de vous être inscrit sur notre plateforme de gestion hôtelière.</p>
                        <p>Voici votre code de vérification :</p>
                        <div style="background-color: #3498db; color: white; padding: 20px; text-align: center; font-size: 32px; font-weight: bold; letter-spacing: 5px; border-radius: 5px; margin: 20px 0;">
                            %s
                        </div>
                        <p>Ce code est valable pendant 15 minutes.</p>
                        <p>Si vous n'avez pas demandé cette inscription, ignorez cet email.</p>
                        <hr style="border: none; border-top: 1px solid #eee; margin: 20px 0;">
                        <p style="color: #7f8c8d; font-size: 12px;">© 2025 Hotel Management System</p>
                    </div>
                </body>
                </html>
                """.formatted(nom, code);

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

            System.out.println("✓ Email envoyé à: " + toEmail);
            return true;

        } catch (MessagingException e) {
            System.err.println("✗ Erreur d'envoi d'email: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Envoie un email de bienvenue après vérification
     */
    public static boolean sendWelcomeEmail(String toEmail, String nom, String prenom) {


        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, EMAIL_PASSWORD);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EMAIL_FROM));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Compte vérifié - Bienvenue!");

            String htmlContent = """
                <html>
                <body style="font-family: Arial, sans-serif;">
                    <div style="background-color: #27ae60; color: white; padding: 20px; text-align: center;">
                        <h1>✓ Compte Vérifié!</h1>
                    </div>
                    <div style="padding: 30px; background-color: white;">
                        <p>Bonjour <strong>%s %s</strong>,</p>
                        <p>Votre compte a été vérifié avec succès.</p>
                        <p>Vous pouvez maintenant vous connecter et accéder à toutes les fonctionnalités.</p>
                        <a href="#" style="background-color: #3498db; color: white; padding: 15px 30px; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 20px;">
                            Se connecter
                        </a>
                    </div>
                </body>
                </html>
                """.formatted(prenom, nom);

            message.setContent(htmlContent, "text/html; charset=utf-8");
            Transport.send(message);

            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
