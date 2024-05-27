// Purpose: Créer une fenêtre principale pour l'application Secured Air Enforcer.
import javax.swing.*;

import java.awt.*;
import java.awt.Color;

/**
 * <h3>Cette classe crée une fenêtre principale pour l'application Secured Air Enforcer.</h3>
 * <p>La fenêtre contient un panneau bleu à gauche et un panneau blanc à droite.</p>
 * <p>Le panneau bleu contient le titre de l'application et une image d'un avion.</p>
 * <p>Le panneau blanc contient trois boutons: "Afficher la carte", "Entrer un graphe préfait" et "Construire un graphe".</p>
 * <p>Lorsque l'utilisateur clique sur le bouton "Afficher la carte", une carte de l'aéroport s'affiche.</p>
 * <p>Lorsque l'utilisateur clique sur le bouton "Entrer un graphe préfait", un graphe préfait s'affiche.</p>
 * <p>Lorsque l'utilisateur clique sur le bouton "Construire un graphe", il peut construire un graphe en entrant les données.</p>
 * <p>La fenêtre est de taille 800x600 pixels.</p>
 */
public class FenetrePrincipale extends JFrame
{
    public FenetrePrincipale()
    {
        // Création de la fenêtre
        this.setSize(800, 600);
        this.setTitle("home sweat home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        JPanel panneauBleu = new JPanel(new GridBagLayout());
        panneauBleu.setBackground(new Color(84, 172, 238));

        GridBagConstraints gbcBleu = new GridBagConstraints();
        gbcBleu.gridwidth = GridBagConstraints.REMAINDER;
        gbcBleu.fill = GridBagConstraints.HORIZONTAL;

        // Création du titre
        JLabel titre = new JLabel("<html><h1>Secured<br>Air<br>Enforcer</h1></html>");

        
        panneauBleu.add(titre, gbcBleu);

        // Charger l'image de l'avion et la redimensionner
        ImageIcon imageIcon = new ImageIcon("images/avion.png");
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        
        imageIcon = new ImageIcon(image);

        // Création de l'image de l'avion
        JLabel imageAvion = new JLabel(imageIcon);
        panneauBleu.add(imageAvion, gbcBleu);

        // Création de la description
        JLabel description = new JLabel("Un projet de NFL Group, sous license Apache 2.0");
        panneauBleu.add(description, gbcBleu);

        this.add(panneauBleu);
        
        // Création du panneau blanc
        JPanel panneauBlanc = new JPanel(new GridBagLayout());
        panneauBlanc.setBackground(Color.WHITE);
        
        // Charger les images pour les boutons et les redimensionner
        ImageIcon imageCarteIcon = new ImageIcon("images/carte.png");
        Image imageCarte = imageCarteIcon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        imageCarteIcon = new ImageIcon(imageCarte);

        ImageIcon imageGraphe = new ImageIcon("images/graph.png");
        Image imageGrapheImage = imageGraphe.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        imageGraphe = new ImageIcon(imageGrapheImage);

        ImageIcon imageConstruire = new ImageIcon("images/plan.png");
        Image imageConstruireImage = imageConstruire.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        imageConstruire = new ImageIcon(imageConstruireImage);
        
        // Créer les boutons
        JButton ouvrirCarte = new JButton("Afficher la carte", imageCarteIcon);
        ouvrirCarte.setVerticalTextPosition(SwingConstants.BOTTOM);
        ouvrirCarte.setHorizontalTextPosition(SwingConstants.CENTER);

        ouvrirCarte.addActionListener(e -> {
            FenetreMap fen = new FenetreMap();
            fen.setVisible(true);
            this.dispose();
        });

        JButton ouvrirGraphe = new JButton("Entrer un graphe préfait", imageGraphe);
        ouvrirGraphe.setVerticalTextPosition(SwingConstants.BOTTOM);
        ouvrirGraphe.setHorizontalTextPosition(SwingConstants.CENTER);

        ouvrirGraphe.addActionListener(e -> {
            FenetreGraphChoose fen = new FenetreGraphChoose();
            fen.setVisible(true);
            this.dispose();
        });

        JButton construireGraphe = new JButton("Construire un graphe", imageConstruire);
        construireGraphe.setVerticalTextPosition(SwingConstants.BOTTOM);
        construireGraphe.setHorizontalTextPosition(SwingConstants.CENTER);

        construireGraphe.addActionListener(e -> {
            FenetreConstruction fen = new FenetreConstruction();
            fen.setVisible(true);
            this.dispose();
        });



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        panneauBlanc.add(ouvrirCarte, gbc);
        panneauBlanc.add(ouvrirGraphe, gbc);
        panneauBlanc.add(construireGraphe, gbc);

        // Créer un panneau principal et y ajouter les deux panneaux
        JPanel panneauPrincipal = new JPanel();
        panneauPrincipal.setLayout(new BoxLayout(panneauPrincipal, BoxLayout.X_AXIS));
        panneauBleu.setPreferredSize(new Dimension((int) (this.getWidth() * 0.7), (int) (this.getHeight())));
        panneauBlanc.setPreferredSize(new Dimension((int) (this.getWidth() * 0.3), (int) (this.getHeight())));
        panneauPrincipal.add(panneauBleu);
        panneauPrincipal.add(panneauBlanc);

        this.add(panneauPrincipal);
    }
}
