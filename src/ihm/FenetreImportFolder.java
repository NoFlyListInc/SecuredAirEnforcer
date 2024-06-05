package src.ihm;
//#region imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
//#endregion
import java.awt.event.ActionListener;

public class FenetreImportFolder extends JFrame {

    public FenetreImportFolder() {
        // Base de la fenêtre
        this.setTitle("Construction Window");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Création du panneau principal avec GridBagLayout
        JPanel panneau = new JPanel(new GridBagLayout());
        panneau.setBackground(new Color(98, 142, 255));

        // Contraintes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Panneau d'importation de dossier
        JPanel panFold = new JPanel();
        panFold.setLayout(new BoxLayout(panFold, BoxLayout.Y_AXIS));
        panFold.setBackground(new Color(45, 40, 63));
        panFold.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panFold.setPreferredSize(new Dimension(220, 280));

        // Titre de l'importation de dossier
        JLabel titreFold = new JLabel("<html><center>Charger un dossier</center></html>");
        titreFold.setForeground(Color.WHITE);
        titreFold.setFont(titreFold.getFont().deriveFont(Font.PLAIN, 25));
        titreFold.setHorizontalAlignment(SwingConstants.CENTER);
        titreFold.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Instruction pour l'importation de dossier
        JLabel instructionFold = new JLabel("<html><center>Veuillez sélectionner un dossier contenant les fichiers .txt</center></html>");
        instructionFold.setForeground(Color.WHITE);
        instructionFold.setFont(instructionFold.getFont().deriveFont(Font.PLAIN, 15));
        instructionFold.setHorizontalAlignment(SwingConstants.CENTER);
        instructionFold.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Bouton pour parcourir le dossier
        RoundedButton boutonFold = new RoundedButton("Parcourir...");
        boutonFold.setBackground(new Color(176, 226, 255));
        boutonFold.setContentAreaFilled(false);
        boutonFold.setFont(boutonFold.getFont().deriveFont(Font.PLAIN, 25));
        boutonFold.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Ajouter les composants au panneau d'importation de dossier
        panFold.add(titreFold);
        panFold.add(Box.createVerticalStrut(60));
        panFold.add(instructionFold);
        panFold.add(Box.createRigidArea(new Dimension(0, 10)));
        panFold.add(boutonFold);
        panFold.add(Box.createVerticalGlue());

        boutonFold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String cheminFichier = fileChooser.getSelectedFile().getPath();
                    FenetreGraphe fen = new FenetreGraphe(cheminFichier);
                    fen.setVisible(true);
                    dispose();
                }
            }
        });

        
        panneau.add(panFold, gbc);
        this.add(panneau);
    }
}