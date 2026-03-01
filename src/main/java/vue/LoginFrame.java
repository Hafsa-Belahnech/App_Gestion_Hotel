package vue;

import dao.UtilisateurDao;
import modele.Utilisateur;
import service.PasswordHasher;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private UtilisateurDao utilisateurDAO = new UtilisateurDao();

    public LoginFrame() {
        setTitle("🏨 Gestion Hôtel - Connexion");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal avec gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(41, 128, 185), 0, getHeight(), new Color(52, 152, 219));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Panel de contenu
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        contentPanel.setOpaque(false);

        // Logo/Titre
        JLabel titleLabel = new JLabel("🏨 GESTION HÔTEL", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Système de réservation", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 200, 200));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        contentPanel.add(titleLabel);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(subtitleLabel);
        contentPanel.add(Box.createVerticalStrut(40));

        // Email
        JPanel emailPanel = createInputPanel("Email:", emailField = new JTextField(25));
        contentPanel.add(emailPanel);
        contentPanel.add(Box.createVerticalStrut(15));

        // Mot de passe
        JPanel passwordPanel = createInputPanel("Mot de passe:", passwordField = new JPasswordField(25));
        contentPanel.add(passwordPanel);
        contentPanel.add(Box.createVerticalStrut(30));

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);

        loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBackground(new Color(39, 174, 96));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFocusPainted(false);
        loginButton.setBorderPainted(false);
        loginButton.setPreferredSize(new Dimension(130, 40));

        registerButton = new JButton("S'inscrire");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 13));
        registerButton.setBackground(new Color(52, 73, 94));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.setBorderPainted(false);
        registerButton.setPreferredSize(new Dimension(130, 40));

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        contentPanel.add(buttonPanel);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        add(mainPanel);

        // Événements
        loginButton.addActionListener(e -> effectuerConnexion());
        registerButton.addActionListener(e -> ouvrirInscription());

        // Enter pour se connecter
        passwordField.addActionListener(e -> effectuerConnexion());
    }

    private JPanel createInputPanel(String labelText, JComponent field) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.PLAIN, 14));
        label.setForeground(Color.WHITE);

        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(250, 35));

        panel.add(label, BorderLayout.WEST);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private void effectuerConnexion() {
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez remplir tous les champs",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Utilisateur utilisateur = utilisateurDAO.authenticate(email, password);

            if (utilisateur != null) {
                service.Session.ouvrir(utilisateur); //ajoutée!!
                JOptionPane.showMessageDialog(this,
                        "Bienvenue " + utilisateur.getPrenom() + " " + utilisateur.getNom() + "!",
                        "Connexion réussie",
                        JOptionPane.INFORMATION_MESSAGE);

                MainFrame mainFrame = new MainFrame(utilisateur);
                mainFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Email ou mot de passe incorrect",
                        "Échec de connexion",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(this,
                    e.getMessage(),
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,
                    "Erreur de connexion: " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ouvrirInscription() {
        RegisterFrame registerFrame = new RegisterFrame();
        registerFrame.setVisible(true);
    }

    public static void main(String[] args) {
        // Look and Feel moderne
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            LoginFrame login = new LoginFrame();
            login.setVisible(true);
        });
    }
}