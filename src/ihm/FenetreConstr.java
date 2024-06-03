package src.ihm;
//#region imports
import javax.swing.*;
import java.awt.*;
//#endregion

public class FenetreConstr extends JFrame {

    public FenetreConstr() {
        // Base de la fenêtre
        this.setTitle("Construction Window");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Création du panneau principal avec GridBagLayout
        JPanel panneau = new JPanel(new GridBagLayout());
        panneau.setBackground(new Color(84, 172, 238));

        // Chargement de l'image depuis le fichier
        ImageIcon icon = new ImageIcon("image/parametre.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);

        // Création du bouton avec une image
        JButton bouton = new JButton(icon);
        bouton.setOpaque(false); // Rend le bouton transparent
        bouton.setContentAreaFilled(false); // Assure que la zone de contenu du bouton est transparente
        bouton.setBorderPainted(false); // Supprime la bordure du bouton

        // Création des contraintes pour placer le bouton en haut à droite et le monter vers le haut
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.NORTHEAST; 
        gbc.gridx = 1; 
        gbc.gridy = 0; 
        gbc.weightx = 1; 
        gbc.weighty = 1; 
        gbc.insets = new Insets(10, 0, 0, 10); 

        // Ajout du bouton avec les contraintes spécifiées
        panneau.add(bouton, gbc);

        // Création du panneau blanc
        JPanel panneauBlanc = new JPanel(new GridBagLayout());
        panneauBlanc.setBackground(Color.WHITE);
        panneauBlanc.setPreferredSize(new Dimension(250, 600));

        // Création des contraintes pour placer le panneau blanc à droite
        GridBagConstraints gbcBlanc = new GridBagConstraints();
        gbcBlanc.gridx = 1;
        gbcBlanc.gridy = 0;

        // Retour au panneau principal
        JButton retour = new JButton("Retour");
        gbcBlanc.gridx = 0;
        gbcBlanc.gridy = 0;
        gbcBlanc.anchor = GridBagConstraints.NORTHWEST;
        panneauBlanc.add(retour, gbcBlanc);

        // Entrée pour kmax
        JTextArea kmax = new JTextArea("Nombre de couleurs maximales : ");
        kmax.setEditable(false);
        kmax.setBackground(Color.WHITE);
        kmax.setFont(new Font("Arial", Font.PLAIN, 20));
        GridBagConstraints gbcKmax = new GridBagConstraints();
        gbcKmax.gridx = 1;
        gbcKmax.gridy = 0;

        // Bouton pour modifier kmax
        JButton boutonKmax = new JButton("Modifier le nombre de couleurs maximales");
        gbcBlanc.gridx = 1;
        gbcBlanc.gridy = 0;
        gbcBlanc.fill = GridBagConstraints.HORIZONTAL;
        gbcBlanc.insets = new Insets(10, 10, 10, 10);
        panneauBlanc.add(boutonKmax, gbcBlanc);


        // Ajout du panneau à la fenêtre
        this.add(panneau);
        this.add(panneauBlanc, BorderLayout.EAST);
        

    }
}