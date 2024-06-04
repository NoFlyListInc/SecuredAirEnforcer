package src.ihm;

//#region import
//swing imports
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

//awt imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
//#endregion

/**
 * <h3>Cette classe crée une fenêtre principale pour l'application Secured Air Enforcer.</h3>
 * <p>La fenêtre contient un panneau bleu à gauche et un panneau blanc à droite.</p>
 * <p>Le panneau bleu contient le titre de l'application et une image d'un avion.</p>
 * <p>Le panneau blanc contient trois boutons: "Afficher la carte", "Entrer un graphe préfait" et "Colorisation d'un graphe".</p>
 * <p>Lorsque l'utilisateur clique sur le bouton "Afficher la carte", une carte de l'aéroport s'affiche.</p>
 * <p>Lorsque l'utilisateur clique sur le bouton "Entrer un graphe préfait", un graphe préfait s'affiche.</p>
 * <p>Lorsque l'utilisateur clique sur le bouton "Colorisation d'un graphe", une fenêtre s'ouvre pour coloriser un graphe.</p>
 * <p>La fenêtre est de taille 800x600 pixels.</p>
 */
public class FenetrePrincipale extends JFrame
{
    /**
     * Constructeur de la fenêtre principale.
     * <p>Crée une fenêtre principale avec un panneau bleu à gauche et un panneau blanc à droite.</p>
     * <p>Le panneau bleu contient le titre de l'application et une image d'un avion.</p>
     * <p>Le panneau blanc contient trois boutons: "Afficher la carte", "Entrer un graphe préfait" et "Colorisation d'un graphe".</p>
     * @autor FERNANDES Thomas
     */
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
        panneauBleu.setBackground(new Color(98, 142, 255));
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

        // Création de l'image
        JPanel panneauImage = new JPanel();
        panneauImage.setOpaque(false);
        panneauImage.setLayout(new BoxLayout(panneauImage, BoxLayout.LINE_AXIS));

        // Charger l'image et la redimensionner
        Image image = new ImageIcon("Image/avion.png").getImage().getScaledInstance(150, 150, Image.SCALE_DEFAULT);
        ImageIcon imageIcon = new ImageIcon(image);
        panneauImage.add(new JLabel(imageIcon));
        panneauBleu.add(panneauImage);
        panneauBleu.add(Box.createVerticalGlue());
        
        // Création du texte en bas
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

        ImageIcon imagefold = new ImageIcon("image/plan.png");
        Image imagefoldImage = imagefold.getImage().getScaledInstance(100, 80, Image.SCALE_SMOOTH);
        imagefold = new ImageIcon(imagefoldImage);
        
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

        RoundedButton foldGraphe = new RoundedButton(imagefold, "Colorisation d'un graphe");
        foldGraphe.setBackground(new Color(176, 226, 255));
        foldGraphe.setVerticalTextPosition(SwingConstants.BOTTOM);
        foldGraphe.setHorizontalTextPosition(SwingConstants.CENTER);

        foldGraphe.addActionListener(e -> {
            FenetreImportFolder fen = new FenetreImportFolder();
            fen.setVisible(true);
            this.dispose();
        });



        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weighty = 1;

        panneauBlanc.add(ouvrirCarte, gbc);
        panneauBlanc.add(ouvrirGraphe, gbc);
        panneauBlanc.add(foldGraphe, gbc);

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
