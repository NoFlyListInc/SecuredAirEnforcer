import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FenetreGraphChoose extends JFrame {
    public FenetreGraphChoose() {
        // Base de la fenêtre
        setTitle("Choose Graph");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau
        JPanel panneau = new JPanel(new GridBagLayout());
        panneau.setBackground(new Color(84, 172, 238));
        GridBagConstraints gbc = new GridBagConstraints();

        // Image de l'avion
        ImageIcon imageIcon = new ImageIcon("images/avion.png");
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(image);
        JLabel imageAvion = new JLabel(imageIcon);

        // Contrainte pour l'image
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.insets = new Insets(10, 10, 10, 10);
        panneau.add(imageAvion, gbc);

        // Contrainte pour le titre
        JLabel titre = new JLabel("Charger un graphe pré-enregistré");
        titre.setFont(titre.getFont().deriveFont(Font.PLAIN, 24)); 
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        panneau.add(titre, gbc);

        // Contrainte pour l'instruction
        JLabel instruction = new JLabel("Veuillez sélectionner un fichier en format .csv");
        instruction.setFont(instruction.getFont().deriveFont(Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2; // Spanning two columns
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 10, 20, 10);
        panneau.add(instruction, gbc);

        // Contrainte pour le bouton
        JButton boutonCharger = new JButton("Parcourir...");
        boutonCharger.setFont(boutonCharger.getFont().deriveFont(Font.PLAIN, 20));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Spanning two columns
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);
        panneau.add(boutonCharger, gbc);

        // Action du bouton
        boutonCharger.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers CSV", "csv"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String cheminFichier = fileChooser.getSelectedFile().getPath();
                    // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier CSV
                    new FenetreGraphe(cheminFichier);
                }
            }
        });

        // Ajouter le panneau à la fenêtre
        add(panneau);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FenetreGraphChoose());
    }
}