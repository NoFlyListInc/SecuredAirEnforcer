package src.ihm;
// Purpose: Créer une fenêtre principale pour l'application Secured Air Enforcer.
import javax.swing.*;
import java.awt.*;

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
        this.setMinimumSize(new Dimension(400,300)); 
        this.setSize(800, 600);
        this.setTitle("home sweat home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);


        // Création du panneau bleu
        JPanel panneauBleu = new JPanel();
        panneauBleu.setBackground(new Color(84, 172, 238));
        panneauBleu.setLayout(new BoxLayout(panneauBleu, BoxLayout.PAGE_AXIS));
        panneauBleu.add(Box.createVerticalGlue());

        JPanel panneauTitle = new JPanel();
        panneauTitle.setOpaque(false);
        panneauTitle.setLayout(new BoxLayout(panneauTitle, BoxLayout.LINE_AXIS));

        // Création du titre
        JLabel titre = new JLabel("<html><h1 style='font-size:24px'><font color='black'>S</font><font color='white'>ecured</font><br><font color='black'>A</font><font color='white'>ir</font><br><font color='black'>E</font><font color='white'>nforcer</font></h1></html>");
        titre.setHorizontalAlignment(SwingConstants.CENTER);
        titre.setSize(titre.getWidth()*2, titre.getHeight()*2);
        panneauTitle.add(titre);
        panneauBleu.add(panneauTitle);

        JPanel panneauImage = new JPanel();
        panneauImage.setOpaque(false);
        panneauImage.setLayout(new BoxLayout(panneauImage, BoxLayout.LINE_AXIS));
        //creation de l'image
        Image image = new ImageIcon("image/avion.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon imageIcon = new ImageIcon(image);
        panneauImage.add(new JLabel(imageIcon));
        panneauBleu.add(panneauImage);
        panneauBleu.add(Box.createVerticalGlue());
        

        JPanel panneauBleuBas = new JPanel();
        panneauBleuBas.setOpaque(false);
        panneauBleuBas.setLayout(new BoxLayout(panneauBleuBas, BoxLayout.LINE_AXIS));
        panneauBleuBas.add(Box.createHorizontalGlue());
        panneauBleuBas.add(new JLabel("Un projet de NFL Group, sous license Apache 2.0"));
        panneauBleu.add(panneauBleuBas);

        
        // Création du panneau blanc
        JPanel panneauBlanc = new JPanel(new GridBagLayout());
        panneauBlanc.setBackground(Color.WHITE);
        
        // Charger les images pour les boutons et les redimensionner
        ImageIcon imageCarteIcon = new ImageIcon("image/map.png");
        Image imageCarte = imageCarteIcon.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        imageCarteIcon = new ImageIcon(imageCarte);

        ImageIcon imageGraphe = new ImageIcon("image/graph.png");
        Image imageGrapheImage = imageGraphe.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        imageGraphe = new ImageIcon(imageGrapheImage);

        ImageIcon imageConstruire = new ImageIcon("image/plan.png");
        Image imageConstruireImage = imageConstruire.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        imageConstruire = new ImageIcon(imageConstruireImage);
        
        // Créer les boutons
        RoundedButton ouvrirCarte = new RoundedButton(imageCarteIcon, "Afficher la carte");
        ouvrirCarte.setBackground(new Color(176, 226, 255));
        ouvrirCarte.setVerticalTextPosition(SwingConstants.BOTTOM);
        ouvrirCarte.setHorizontalTextPosition(SwingConstants.CENTER);

        ouvrirCarte.addActionListener(e -> {
            FenetreMap fen = new FenetreMap();
            fen.setVisible(true);
            this.dispose();
        });

        RoundedButton ouvrirGraphe = new RoundedButton(imageGraphe, "Générer un graphe");
        ouvrirGraphe.setBackground(new Color(176, 226, 255));
        ouvrirGraphe.setVerticalTextPosition(SwingConstants.BOTTOM);
        ouvrirGraphe.setHorizontalTextPosition(SwingConstants.CENTER);

        ouvrirGraphe.addActionListener(e -> {
            FenetreImportGraph fen = new FenetreImportGraph();
            fen.setVisible(true);
            this.dispose();
        });

        RoundedButton construireGraphe = new RoundedButton(imageConstruire, "Construire un graphe");
        construireGraphe.setBackground(new Color(176, 226, 255));
        construireGraphe.setVerticalTextPosition(SwingConstants.BOTTOM);
        construireGraphe.setHorizontalTextPosition(SwingConstants.CENTER);

        construireGraphe.addActionListener(e -> {
            FenetreConstr fen = new FenetreConstr();
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
