package src.ihm;
//#region import
import src.core.Graphe;
import src.core.ListeAeroport;

import src.core.ListVol;
//graphstream
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.util.Camera;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;
//swing
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.Box;
import javax.swing.JFrame;
//awt
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.Cursor;
//io
import java.io.File;
//#endregion


/**
 * Class FenetreGraphe
 * Fenêtre pour afficher un Graph
 * @extends SuperposedFenetre
 * @autor NOUVEL Armand et FERNANDES Thomas
 */
public class FenetreGraphe extends FenetreSuperpose {

    //#region Attributs

    /**
     * chemin du fichier
     */
    private String cheminFichier;

    /**
     * JPanel du parametre menu
     */
    private JPanel parametreMenuPosition = new JPanel();

    /**
     * JButton du parametre menu
     */
    private JButton boutonParametre;

    /**
     * le graph qui sera affiché
     */
    private Graphe graphe;

    /**
     * JLabel des infos du graph
     */
    private JLabel infoLabel = new JLabel();

    /**
     * JPanel du graph
     */
    private JPanel panGraphe;

    /**
     * JPanel des boutons
     */
    private JPanel panBouton;

    //#endregion

    /**
     * Constructeur de la classe FenetreGraphe
     * @param cheminFichier String
     */
    public FenetreGraphe(String cheminFichier) {

        this.cheminFichier = cheminFichier;
        // Base de la fenêtre

    //#region Fenetre

        this.setTitle("Graphe");
        this.setMinimumSize(new Dimension(400,300));  
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.constrFen();

        // ajustement de la taille
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension taille = getContentPane().getSize();
                panGraphe.setBounds(0, 0, taille.width, taille.height);
                panBouton.setBounds(0, 0, taille.width, taille.height);
                menu.setBounds(0, 0, taille.width, taille.height);
            }
        });
    }

    /**
     * Construit la fenêtre
     */
    private void constrFen() {

        this.setLayout(new BorderLayout());
        this.superposePan.setPreferredSize(new Dimension(800, 600));

        this.panGraphe = this.constrGraphPan();
        this.superposePan.add(this.panGraphe, JLayeredPane.DEFAULT_LAYER);

        this.panBouton = this.constrBoutonPan();
        this.superposePan.add(panBouton, JLayeredPane.PALETTE_LAYER);

        this.constrParametreMenu();

        this.setContentPane(superposePan);
    }


    /**
     * Construit le JPanel du graph
     * @return JPanel
     */
    private JPanel constrGraphPan() {
        // creation du graph
        File file = new File(this.cheminFichier);

        this.graphe = new Graphe(file.getName());
        if (file.getName().endsWith(".txt")) {
            try {

                this.graphe.remplirAvecFichier(this.cheminFichier);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        } else if (file.getName().endsWith(".csv")) {
            ListeAeroport listeAeroport = new ListeAeroport();
            try {
                listeAeroport.remplir("data/aeroports.txt");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
            ListVol listeVol = new ListVol();
            try {
                listeVol.remplir(this.cheminFichier, listeAeroport);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
            }

            this.graphe.remplirAvecListeVol(listeVol, 15);
        }

        // Viewer pour zoomer et deplacer
        Viewer visualisation = new Viewer(this.graphe, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD); // Créer un viewer
        visualisation.enableAutoLayout();
        View vue = visualisation.addDefaultView(false);
        vue.setMouseManager(new DefaultMouseManager() {
                private Point dernierePosition;

                //deplacement
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (curElement != null) {
                        elementMoving(curElement, e);
                    } else {
                        Point positionActuelle = e.getPoint();
                        Camera camera = view.getCamera();
                        double dx, dy;

                        if (dernierePosition != null) {
                            dx = dernierePosition.getX() - positionActuelle.getX();
                            dy = dernierePosition.getY() - positionActuelle.getY();
                        }
                        else {
                            dx = -positionActuelle.getX();
                            dy = -positionActuelle.getY();
                        }

                        camera.setViewCenter(
                                camera.getViewCenter().x + dx * camera.getViewPercent() * 0.0075,
                                camera.getViewCenter().y - dy * camera.getViewPercent() * 0.0075,
                                camera.getViewCenter().z
                                
                        );
                        dernierePosition = positionActuelle;
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    dernierePosition = e.getPoint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    dernierePosition = null;
                }

            });

            // ecouteur pour zoomer
            vue.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    Camera camera = vue.getCamera();
                    double zoomFacteur = 1.1;
                    if (e.getWheelRotation() < 0) {
                        camera.setViewPercent(camera.getViewPercent() / zoomFacteur);
                    }
                    else {
                        camera.setViewPercent(camera.getViewPercent() * zoomFacteur);
                    }
                }
            });

        // JPanel
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.setOpaque(true);
        pan.setBounds(0, 0, 800, 600);
        pan.add(vue, BorderLayout.CENTER);

        return pan;
    }

    /**
     * Construit le JPanel des boutons
     * @return JPanel
    */
    private JPanel constrBoutonPan() {
        // JPanel des boutons
        JPanel boutonPan = new JPanel();
        boutonPan.setBounds(0, 0, 800, 600);
        boutonPan.setOpaque(false);
        boutonPan.setLayout(new BorderLayout());

        JPanel parametrePan = new JPanel();
        parametrePan.setOpaque(false);
        parametrePan.setLayout(new BoxLayout(parametrePan, BoxLayout.PAGE_AXIS));
        Image parametreImage = new ImageIcon("image/parametre.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        this.boutonParametre = new JButton(new ImageIcon(parametreImage));
        this.boutonParametre.setBorderPainted(false);
        this.boutonParametre.setContentAreaFilled(false);
        this.boutonParametre.setFocusPainted(false);
        this.boutonParametre.addActionListener((ActionListener) -> {
            Dimension taille = getContentPane().getSize();
            parametreMenuPosition.setBounds(0, 0, taille.width, taille.height);
            getSuperposePan().add(parametreMenuPosition, JLayeredPane.MODAL_LAYER);
            boutonParametre.setVisible(false);
            getSuperposePan().revalidate();
            getSuperposePan().repaint();
        });
        parametrePan.add(boutonParametre);
        parametrePan.add(Box.createVerticalGlue());
        boutonParametre.setToolTipText("Ouvrir les paramètres");
        boutonPan.add(parametrePan, BorderLayout.EAST);

        JPanel menuPan = new JPanel();
        menuPan.setOpaque(false);
        menuPan.setLayout(new BorderLayout());

        //Panel info
        this.infoLabel.setText(createInfoString());
        JPanel infoPan = new JPanel();
        infoPan.setOpaque(false);
        infoPan.setLayout(new BorderLayout());
        infoPan.add(this.infoLabel, BorderLayout.WEST);
        menuPan.add(infoLabel, BorderLayout.NORTH);

        JPanel menuBoutonPan = new JPanel();
        menuBoutonPan.setOpaque(false);
        menuBoutonPan.setLayout(new BoxLayout(menuBoutonPan, BoxLayout.LINE_AXIS));
        this.menuButton.setMaximumSize(new Dimension(121, 30));
        menuBoutonPan.add(this.menuButton, BorderLayout.WEST);
        menuPan.add(menuBoutonPan, BorderLayout.SOUTH);

        boutonPan.add(menuPan, BorderLayout.WEST);

        return boutonPan;
    }

    /**
     * Construit le JPanel du parametre menu
     * @return  JPanel
     */
    private void constrParametreMenu() {
        //parametre le panel border pour positionner le parametreMenu
        this.parametreMenuPosition.setLayout(new BorderLayout());
        this.parametreMenuPosition.setOpaque(false);

        //panel du popUp parametre
        RoundedPanel parametrePopUp = new RoundedPanel(12);
        parametrePopUp.setBackground(Color.WHITE);
        parametrePopUp.setLayout(new BoxLayout(parametrePopUp, BoxLayout.PAGE_AXIS));

        //panel du retour et du label parametre
        JPanel retourPan = new JPanel();
        retourPan.setOpaque(false);
        retourPan.setLayout(new BoxLayout(retourPan, BoxLayout.LINE_AXIS));
        //button retour
        Image retourImage = new ImageIcon("image/rightArrow.png").getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
        JButton retour = new JButton(new ImageIcon(retourImage));
        retour.setToolTipText("Fermer le menu paramètres");
        retour.setBorderPainted(false);
        retour.setContentAreaFilled(false);
        retour.setFocusPainted(false);
        retour.addActionListener((ActionListener) -> {
            getSuperposePan().remove(parametreMenuPosition);
            boutonParametre.setVisible(true);
            getSuperposePan().revalidate();
            getSuperposePan().repaint();
        });
        retourPan.add(Box.createRigidArea(new Dimension(10,0)));
        retourPan.add(retour);
        retourPan.add(Box.createHorizontalGlue());
        //label parametre
        JLabel parametreLabel = new JLabel("parametre");
        parametreLabel.setForeground(Color.BLACK);
        retourPan.add(parametreLabel);
        retourPan.add(Box.createHorizontalGlue());
        retourPan.add(Box.createHorizontalGlue());
        //ajout du retour et du label dans le parametrePopUp
        parametrePopUp.add(Box.createRigidArea(new Dimension(0,10)));
        parametrePopUp.add(retourPan);
        parametrePopUp.add(Box.createRigidArea(new Dimension(0,10)));


        //button import vol
        JPanel importVolPan = new JPanel();
        importVolPan.setOpaque(false);
        importVolPan.setLayout(new BoxLayout(importVolPan, BoxLayout.LINE_AXIS));
        RoundedButton importVolBouton = new RoundedButton("importer une liste de vols");
        importVolBouton.setToolTipText("<html>Charger un fichier CSV <br> Exemple : <br> ID_vol ; ID_aéro_départ ; ID_aéro_départ ; Heure_départ ; Min_départ ; duration</html>");
        importVolBouton.addActionListener((ActionListener) -> {
            JFileChooser selectionFichier = new JFileChooser();
                selectionFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
                selectionFichier.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers CSV", "csv"));
                int valeurRetourne = selectionFichier.showOpenDialog(null);
                if (valeurRetourne == JFileChooser.APPROVE_OPTION) {
                    String cheminFichier = selectionFichier.getSelectedFile().getPath();
                    // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier CSV
                    FenetreGraphe fen = new FenetreGraphe(cheminFichier);
                    fen.setVisible(true);
                    dispose();
                }
        });
        importVolBouton.setBackground(new Color(176, 226, 255));
        importVolPan.add(Box.createHorizontalGlue());
        importVolPan.add(importVolBouton);
        importVolPan.add(Box.createHorizontalGlue());
        //ajout du button import dans le parametrePopUp
        parametrePopUp.add(importVolPan);
        parametrePopUp.add(Box.createRigidArea(new Dimension(0,5)));

        //button import prefait
        JPanel importPrefaitPan = new JPanel();
        importPrefaitPan.setOpaque(false);
        importPrefaitPan.setLayout(new BoxLayout(importPrefaitPan, BoxLayout.LINE_AXIS));
        RoundedButton importPrefaitBouton = new RoundedButton("importer un graphe préfait");
        importPrefaitBouton.setToolTipText("<html>Charger un fichier TXT <br> Exemple : <br> kmax <br> nbNoeuds<br> arretes</html>");
        importPrefaitBouton.addActionListener((ActionListener) -> {
            JFileChooser selectionFichier = new JFileChooser();
            selectionFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
            selectionFichier.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers TXT", "txt"));
            int valeurRetourne = selectionFichier.showOpenDialog(null);
            if (valeurRetourne == JFileChooser.APPROVE_OPTION) {
                String cheminFichier = selectionFichier.getSelectedFile().getPath();
                // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier TXT
                FenetreGraphe fen = new FenetreGraphe(cheminFichier);
                fen.setVisible(true);
                dispose();
            }
        });
        importPrefaitBouton.setBackground(new Color(176, 226, 255));
        importPrefaitPan.add(Box.createHorizontalGlue());
        importPrefaitPan.add(importPrefaitBouton);
        importPrefaitPan.add(Box.createHorizontalGlue());
        //ajout du button import dans le parametrePopUp
        parametrePopUp.add(importPrefaitPan);
        parametrePopUp.add(Box.createRigidArea(new Dimension(0,15)));


        // coloration (kmax)
        //barre Pan
        JPanel barreCouleurPan = new JPanel();
        barreCouleurPan.setMaximumSize(new Dimension(200, 50));
        barreCouleurPan.setOpaque(false);
        barreCouleurPan.setLayout(new BoxLayout(barreCouleurPan, BoxLayout.LINE_AXIS));
        //JLabel
        barreCouleurPan.add(Box.createHorizontalGlue());
        barreCouleurPan.add(new JLabel("kmax : "));
        barreCouleurPan.add(Box.createRigidArea(new Dimension(10,0)));
        //barre
        JSlider barreCouleur = new JSlider(JSlider.HORIZONTAL, 1, 20, 12);
        barreCouleurPan.add(barreCouleur);
        barreCouleurPan.add(Box.createRigidArea(new Dimension(10,0)));
        //JLabel int
        JLabel intLabel = new JLabel("12");
        barreCouleur.addChangeListener((ChangeEvent) -> {
            intLabel.setText(Integer.toString(barreCouleur.getValue()));
        });
        //ajout de la barre et du label dans le parametrePopUp
        barreCouleurPan.add(intLabel);
        barreCouleurPan.add(Box.createHorizontalGlue());
        parametrePopUp.add(barreCouleurPan);

        // Dsature bouton
        JPanel dsaturePan = new JPanel();
        dsaturePan.setOpaque(false);
        dsaturePan.setLayout(new BoxLayout(dsaturePan, BoxLayout.LINE_AXIS));
        RoundedButton dsatureButton = new RoundedButton("colorier avec DSature");
        dsatureButton.setToolTipText("Appliquer la méthode DSature sur le graphe");
        dsatureButton.setBackground(new Color(176, 226, 255));
        dsatureButton.addActionListener((ActionListener) -> {
            graphe.setKdonne(barreCouleur.getValue());
            graphe.dSature();

            retour.doClick();
            infoLabel.setText(createInfoString());
        });
        dsaturePan.add(Box.createHorizontalGlue());
        dsaturePan.add(dsatureButton);
        dsaturePan.add(Box.createHorizontalGlue());
        //ajout du buouton dans le parametrePopUp
        parametrePopUp.add(dsaturePan);
        parametrePopUp.add(Box.createRigidArea(new Dimension(0,5)));
        // WelshPowell bouton
        JPanel welshPowellPan = new JPanel();
        welshPowellPan.setOpaque(false);
        welshPowellPan.setLayout(new BoxLayout(welshPowellPan, BoxLayout.LINE_AXIS));
        RoundedButton welshPowellButton = new RoundedButton("Colorier avec WelshPowell");
        welshPowellButton.setToolTipText("Appliquer la méthode WelshPowell sur le graphe");
        welshPowellButton.setBackground(new Color(176, 226, 255));
        welshPowellButton.addActionListener((ActionListener) -> {
            graphe.setKdonne(barreCouleur.getValue());
            graphe.welshPowell();
            retour.doClick();
            infoLabel.setText(createInfoString());
        });
        welshPowellPan.add(Box.createHorizontalGlue());
        welshPowellPan.add(welshPowellButton);
        welshPowellPan.add(Box.createHorizontalGlue());
        //ajout du bouton dans le parametrePopUp
        parametrePopUp.add(welshPowellPan);
        parametrePopUp.add(Box.createRigidArea(new Dimension(0,15)));


        //panel du parametrePopUp
        JPanel parametreMenuPan = new JPanel();
        parametreMenuPan.setOpaque(false);
        parametreMenuPan.setLayout(new BoxLayout(parametreMenuPan, BoxLayout.PAGE_AXIS));
        parametreMenuPan.add(parametrePopUp);
        parametreMenuPan.add(Box.createVerticalGlue());

        parametreMenuPosition.add(parametreMenuPan, BorderLayout.EAST);
    }

    private String createInfoString() {
        StringBuilder info = new StringBuilder();
        info.append("<html>");
        info.append("file : ").append(this.graphe.getId()).append("<br>");
        info.append("nbr noeuds : ").append(this.graphe.getNodeCount()).append("<br>");
        info.append("nbr arêtes : ").append(this.graphe.getEdgeCount()).append("<br>");
        info.append("nbr couleurs : ").append(this.graphe.getKoptimal()).append("<br>");
        info.append("</html>");
        return info.toString();
    }
}