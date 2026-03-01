package vue;

import dao.UtilisateurDao;
import modele.Utilisateur;
import service.EmailService;
import service.PasswordHasher;
import utils.Validator;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField nomField, prenomField, emailField, telephoneField;
    private JPasswordField passwordField, confirmPasswordField;
    private JComboBox<String> roleComboBox;
    private JButton registerButton, cancelButton;
    private UtilisateurDao utilisateurDAO = new UtilisateurDao();

    public RegisterFrame() {
        setTitle("🏨 Créer un compte");
        setSize(500, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(46, 204, 113), 0, getHeight(), new Color(39, 174, 96));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        contentPanel.setOpaque(false);

        // Titre
        JLabel titleLabel = new JLabel("CRÉER UN COMPTE", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(30));

        // Champs
        contentPanel.add(createInputPanel("Nom:", nomField = new JTextField(25)));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createInputPanel("Prénom:", prenomField = new JTextField(25)));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createInputPanel("Email:", emailField = new JTextField(25)));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createInputPanel("Téléphone:", telephoneField = new JTextField(25)));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createInputPanel("Mot de passe:", passwordField = new JPasswordField(25)));
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(createInputPanel("Confirmer:", confirmPasswordField = new JPasswordField(25)));
        contentPanel.add(Box.createVerticalStrut(10));

        // Rôle
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rolePanel.setOpaque(false);
        rolePanel.add(new JLabel("Rôle: "));
        String[] roles = {"client", "receptionniste", "admin"};
        roleComboBox = new JComboBox<>(roles);
        roleComboBox.setFont(new Font("Arial", Font.PLAIN, 14));
        rolePanel.add(roleComboBox);
        contentPanel.add(rolePanel);

        contentPanel.add(Box.createVerticalStrut(25));

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        registerButton = new JButton("S'inscrire");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(new Color(39, 174, 96));
        registerButton.setFocusPainted(false);
        registerButton.setPreferredSize(new Dimension(130, 40));

        cancelButton = new JButton("Annuler");
        cancelButton.setFont(new Font("Arial", Font.PLAIN, 13));
        cancelButton.setBackground(new Color(52, 73, 94));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);
        cancelButton.setPreferredSize(new Dimension(130, 40));

        buttonPanel.add(registerButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(buttonPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        registerButton.addActionListener(e -> inscrireUtilisateur());
        cancelButton.addActionListener(e -> dispose());
    }

    private JPanel createInputPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(280, 35));

        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void inscrireUtilisateur() {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        // Validation avec Validator
        if (!Validator.isNotEmpty(nom)) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            nomField.requestFocus();
            return;
        }

        if (!Validator.isNotEmpty(prenom)) {
            JOptionPane.showMessageDialog(this, "Le prénom est obligatoire", "Erreur", JOptionPane.ERROR_MESSAGE);
            prenomField.requestFocus();
            return;
        }

        if (!Validator.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, Validator.getEmailErrorMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return;
        }

        if (!Validator.isValidPhone(telephone)) {
            JOptionPane.showMessageDialog(this, "Le téléphone doit contenir 10 chiffres", "Erreur", JOptionPane.ERROR_MESSAGE);
            telephoneField.requestFocus();
            return;
        }

        if (!Validator.isValidPassword(password)) {
            JOptionPane.showMessageDialog(this, Validator.getPasswordErrorMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            passwordField.requestFocus();
            return;
        }

        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Les mots de passe ne correspondent pas", "Erreur", JOptionPane.ERROR_MESSAGE);
            confirmPasswordField.requestFocus();
            return;
        }

        // Vérifier si email existe déjà
        if (utilisateurDAO.findByEmail(email) != null) {
            JOptionPane.showMessageDialog(this, "Cet email est déjà utilisé", "Erreur", JOptionPane.ERROR_MESSAGE);
            emailField.requestFocus();
            return;
        }

        // Générer code de vérification
        String verificationCode = PasswordHasher.generateVerificationCode();

        // Créer l'utilisateur
        Utilisateur utilisateur = new Utilisateur(nom, prenom, email, telephone, password, (String) roleComboBox.getSelectedItem());
        utilisateur.setCodeVerification(verificationCode);

        if (utilisateurDAO.create(utilisateur)) {
            // Envoyer email de vérification
            boolean emailSent = EmailService.sendVerificationEmail(email, verificationCode, prenom);

            if (emailSent) {
                // Demander le code de vérification
                String code = JOptionPane.showInputDialog(this,
                        "Un code de vérification a été envoyé à " + email + "\n\nEntrez le code:",
                        "Vérification Email",
                        JOptionPane.QUESTION_MESSAGE);

                if (code != null && code.trim().equals(verificationCode)) {
                    if (utilisateurDAO.verifyCode(email, code)) {
                        JOptionPane.showMessageDialog(this,
                                "✓ Compte créé et vérifié avec succès!\nVous pouvez maintenant vous connecter.",
                                "Succès",
                                JOptionPane.INFORMATION_MESSAGE);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Code de vérification invalide", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Vérification annulée. Vérifiez votre email plus tard.", "Information", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Compte créé mais email non envoyé.\nVeuillez contacter l'administrateur.", "Attention", JOptionPane.WARNING_MESSAGE);
                dispose();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la création du compte", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}