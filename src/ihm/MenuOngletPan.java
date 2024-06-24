package src.ihm;
//#region imports
//swing imports
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

//awt imports
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.BorderLayout;
//#endregion

/**
 * <h3>Cette classe crée un panneau pour les onglets du menu.</h3>
 * <p>Le panneau contient des boutons pour retourner au menu principal, afficher la carte, importer un graphe et construire un graphe.</p>
 * <p>Chaque bouton est représenté par une image et un texte.</p>
 * @author NOUVEL Armand
 */
public class MenuOngletPan extends JPanel
{
    //#region attributs
    private FenetreSuperpose fen;
    //#endregion

    //#region constructeur
    public MenuOngletPan(FenetreSuperpose fen) {
        super();
        this.fen=fen;
        this.constrPan();
    }
    //#endregion

    //#region methodes
    private void constrPan() {
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(false);

        JPanel pan = new JPanel();
        pan.setOpaque(false);
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        pan.add(Box.createVerticalGlue());
        pan.add(this.constrButtonPan());

        this.add(pan, BorderLayout.WEST);
    }

    private RoundedPanel constrButtonPan() {
        RoundedPanel pan = new RoundedPanel(12);
        pan.setBackground(new Color(45, 40, 63));
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));

        //button retour
        JPanel retourPan = new JPanel();
        retourPan.setOpaque(false);
        retourPan.setLayout(new BoxLayout(retourPan, BoxLayout.LINE_AXIS));
        Image retourImage = new ImageIcon("image/botArrow.png").getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
        JButton retour = new JButton(new ImageIcon(retourImage));
        retour.setBorderPainted(false);
        retour.setContentAreaFilled(false);
        retour.setFocusPainted(false);
        retour.addActionListener((ActionListener) -> {
            fen.getSuperposePan().remove(this);
            fen.menuButton.setVisible(true);
            fen.getSuperposePan().revalidate();
            fen.getSuperposePan().repaint();
        });
        retourPan.add(Box.createRigidArea(new Dimension(25, 0)));
        retourPan.add(new JLabel("<html><font color='white'>MENU</font></html>"));
        retourPan.add(retour);
        pan.add(retourPan);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        //button home
        JPanel homePan = new JPanel();
        homePan.setOpaque(false);
        homePan.setLayout(new BoxLayout(homePan, BoxLayout.LINE_AXIS));
        Image imageHome = new ImageIcon("image/homeLogo.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        RoundedButton home = new RoundedButton(new ImageIcon(imageHome));
        home.setToolTipText("Retour au menu principal");
        home.addActionListener((ActionListener) -> {
            FenetrePrincipale fenetre = new FenetrePrincipale();
            fenetre.setVisible(true);
            fen.dispose();
        });
        homePan.add(Box.createRigidArea(new Dimension(20,0)));
        homePan.add(home);
        homePan.add(Box.createRigidArea(new Dimension(20,0)));
        pan.add(homePan);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        //button map
        JPanel mapPan = new JPanel();
        mapPan.setOpaque(false);
        mapPan.setLayout(new BoxLayout(mapPan, BoxLayout.LINE_AXIS));
        Image imageMap = new ImageIcon("image/map.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        RoundedButton map = new RoundedButton(new ImageIcon(imageMap));
        map.setToolTipText("Ouvrir la carte de la France avec les vols");
        map.addActionListener((ActionListener) -> {
            FenetreMap fenetre = new FenetreMap();
            fenetre.setVisible(true);
            fen.dispose();
        });
        mapPan.add(Box.createRigidArea(new Dimension(20,0)));
        mapPan.add(map);
        mapPan.add(Box.createRigidArea(new Dimension(20,0)));
        pan.add(mapPan);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        //button graph
        JPanel importGraphPan = new JPanel();
        importGraphPan.setOpaque(false);
        importGraphPan.setLayout(new BoxLayout(importGraphPan, BoxLayout.LINE_AXIS));
        Image imageGraph = new ImageIcon("image/graph.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        RoundedButton importGraph = new RoundedButton(new ImageIcon(imageGraph));
        importGraph.setToolTipText("Ouvrir le menu pour charger les graphes");
        importGraph.addActionListener((ActionListener) -> {
            FenetreImportGraph fenetre = new FenetreImportGraph();
            fenetre.setVisible(true);
            fen.dispose();
        });
        importGraphPan.add(Box.createRigidArea(new Dimension(20,0)));
        importGraphPan.add(importGraph);
        importGraphPan.add(Box.createRigidArea(new Dimension(20,0)));
        pan.add(importGraphPan);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        //bouton coloration d'un graphe
        JPanel graphePan = new JPanel();
        graphePan.setOpaque(false);
        graphePan.setLayout(new BoxLayout(graphePan, BoxLayout.LINE_AXIS));
        Image imageColor = new ImageIcon("image/plan.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        RoundedButton entreeGraphe = new RoundedButton(new ImageIcon(imageColor));
        entreeGraphe.setToolTipText("Ouvrir le menu pour colorier plusieurs graphes");
        entreeGraphe.addActionListener((ActionListener) -> {
            FenetreImportFolder fenetre = new FenetreImportFolder();
            fenetre.setVisible(true);
            fen.dispose();
        });
        graphePan.add(Box.createRigidArea(new Dimension(20,0)));
        graphePan.add(entreeGraphe);
        graphePan.add(Box.createRigidArea(new Dimension(20,0)));
        pan.add(graphePan);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        return pan;
    }
}
