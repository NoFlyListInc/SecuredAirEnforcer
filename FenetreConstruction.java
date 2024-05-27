//#region imports
import javax.swing.*;
import java.awt.*;
//#endregion

/**
 * Fenêtre pour construire un graphe avec 12 fonctionnalités
 * @return Fenêtre pour la construction d'un graphe
 * @author FERNANDES Thomas
 */
//#region Base de la fenêtre
public class FenetreConstruction extends JFrame {

    public FenetreConstruction() {
        this.setTitle("Construction Window");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        // Création du panneau principal avec GridBagLayout
        JPanel panneau = new JPanel(new GridBagLayout());
        panneau.setBackground(new Color(84, 172, 238));
//#endregion

//#region Bouton paramètre
        // Chargement de l'image
        ImageIcon icon = new ImageIcon("images/parametre.png");
        Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        icon = new ImageIcon(image);

        // Création du bouton avec une image
        JButton bouton = new JButton(icon);
        bouton.setOpaque(false); // Rend le bouton transparent
        bouton.setContentAreaFilled(false); // Assure que la zone de contenu du bouton est transparente
        bouton.setBorder(null);

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
//#endregion

//#region Panneau blanc
        // Création du panneau blanc
        JPanel panneauBlanc = new JPanel(new BoxLayout(bouton, MAXIMIZED_BOTH));
        panneauBlanc.setBackground(Color.WHITE);
        panneauBlanc.setPreferredSize(new Dimension(250, 600));

       


        // Ajout du panneau à la fenêtre
        this.add(panneau);
        this.add(panneauBlanc, BorderLayout.EAST);
        

    }
}