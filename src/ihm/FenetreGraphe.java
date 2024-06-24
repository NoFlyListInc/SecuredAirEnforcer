package src.ihm;
//#region import
import src.core.Graphe;
import src.core.ListAeroport;
import src.core.ListVol;
//graphstream objects
import org.graphstream.ui.swingViewer.Viewer;
import org.graphstream.ui.swingViewer.View;
import org.graphstream.ui.swingViewer.util.Camera;
import org.graphstream.ui.swingViewer.util.DefaultMouseManager;
import org.graphstream.ui.geom.Point3;
//swing objects
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
//awt objects
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.Cursor;
//io objects
import java.io.File;
//#endregion


/**
 * Class FenetreGraphe
 * Fenêtre pour afficher un Graph
 * @extends SuperposedFenetre
 * @autor NOUVEL Armand et FERNANDES Thomas
 */
public class FenetreGraphe extends SuperposedFenetre {

    //#region Attributs

    /**
     * chemin du fichier
     */
    private String cheminFichier;

    /**
     * JPanel du setting menu
     */
    private JPanel settingMenuPosition = new JPanel();

    /**
     * JButton du setting menu
     */
    private JButton settingButton;

    /**
     * le graph qui sera affiché
     */
    private Graphe graph;

    /**
     * JLabel des infos du graph
     */
    private JLabel infoLabel = new JLabel();

    /**
     * JPanel du graph
     */
    private JPanel panGraph;

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
                Dimension size = getContentPane().getSize();
                panGraph.setBounds(0, 0, size.width, size.height);
                panBouton.setBounds(0, 0, size.width, size.height);
                menu.setBounds(0, 0, size.width, size.height);
            }
        });
    }

    /**
     * Construit la fenêtre
     */
    private void constrFen() {

        this.setLayout(new BorderLayout());
        this.superposePan.setPreferredSize(new Dimension(800, 600));

        this.panGraph = this.constrGraphPan();
        this.superposePan.add(this.panGraph, JLayeredPane.DEFAULT_LAYER);

        this.panBouton = this.constrBoutonPan();
        this.superposePan.add(panBouton, JLayeredPane.PALETTE_LAYER);

        this.constrSettingMenu();

        this.setContentPane(superposePan);
    }


    /**
     * Construit le JPanel du graph
     * @return JPanel
     */
    private JPanel constrGraphPan() {
        // creation du graph
        File file = new File(this.cheminFichier);

        this.graph = new Graphe(file.getName());
        if (file.getName().endsWith(".txt")) {
            try {
                this.graph.remplirAvecFichier(this.cheminFichier);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        } else if (file.getName().endsWith(".csv")) {
            ListAeroport listAeroport = new ListAeroport();
            try {
                listAeroport.fill("data/aeroports.txt");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
            ListVol listVol = new ListVol();
            try {
                listVol.fill(this.cheminFichier, listAeroport);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
            this.graph.remplirAvecListeVol(listVol, 15);
        }

        // Viewer pour zoomer et deplacer
        Viewer vue = new Viewer(this.graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD); // Créer un viewer
        vue.enableAutoLayout();
        View view = vue.addDefaultView(false);
        view.setMouseManager(new DefaultMouseManager() {
                private Point lastMousePosition;

                //deplacement
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (curElement != null) {
                        elementMoving(curElement, e);
                    } else {
                        Point currentMousePosition = e.getPoint();
                        Camera camera = view.getCamera();
                        double dx, dy;

                        if (lastMousePosition != null) {
                            dx = lastMousePosition.getX() - currentMousePosition.getX();
                            dy = lastMousePosition.getY() - currentMousePosition.getY();
                        }
                        else {
                            dx = -currentMousePosition.getX();
                            dy = -currentMousePosition.getY();
                        }

                        camera.setViewCenter(
                                camera.getViewCenter().x + dx * camera.getViewPercent() * 0.0075,
                                camera.getViewCenter().y - dy * camera.getViewPercent() * 0.0075,
                                camera.getViewCenter().z
                                
                        );
                        lastMousePosition = currentMousePosition;
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                    lastMousePosition = e.getPoint();
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    lastMousePosition = null;
                }

            });

            // ecouteur pour zoomer
            view.addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    Camera camera = view.getCamera();
                    double zoomFactor = 1.1;
                    if (e.getWheelRotation() < 0) {
                        camera.setViewPercent(camera.getViewPercent() / zoomFactor);
                    }
                    else {
                        camera.setViewPercent(camera.getViewPercent() * zoomFactor);
                    }
                }
            });

        // JPanel
        JPanel pan = new JPanel();
        pan.setLayout(new BorderLayout());
        pan.setOpaque(true);
        pan.setBounds(0, 0, 800, 600);
        pan.add(view, BorderLayout.CENTER);

        return pan;
    }

    /**
     * Construit le JPanel des boutons
     * @return JPanel
    */
    private JPanel constrBoutonPan() {
        // JPanel des boutons
        JPanel buttonPan = new JPanel();
        buttonPan.setBounds(0, 0, 800, 600);
        buttonPan.setOpaque(false);
        buttonPan.setLayout(new BorderLayout());

        JPanel settingPan = new JPanel();
        settingPan.setOpaque(false);
        settingPan.setLayout(new BoxLayout(settingPan, BoxLayout.PAGE_AXIS));
        Image settingImage = new ImageIcon("image/parametre.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        this.settingButton = new JButton(new ImageIcon(settingImage));
        this.settingButton.setBorderPainted(false);
        this.settingButton.setContentAreaFilled(false);
        this.settingButton.setFocusPainted(false);
        this.settingButton.addActionListener((ActionListener) -> {
            Dimension size = getContentPane().getSize();
            settingMenuPosition.setBounds(0, 0, size.width, size.height);
            getSuperposePan().add(settingMenuPosition, JLayeredPane.MODAL_LAYER);
            settingButton.setVisible(false);
            getSuperposePan().revalidate();
            getSuperposePan().repaint();
        });
        settingPan.add(settingButton);
        settingPan.add(Box.createVerticalGlue());
        settingButton.setToolTipText("Ouvrir les paramètres");
        buttonPan.add(settingPan, BorderLayout.EAST);

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

        JPanel menuButtonPan = new JPanel();
        menuButtonPan.setOpaque(false);
        menuButtonPan.setLayout(new BoxLayout(menuButtonPan, BoxLayout.LINE_AXIS));
        this.menuButton.setMaximumSize(new Dimension(121, 30));
        menuButtonPan.add(this.menuButton, BorderLayout.WEST);
        menuPan.add(menuButtonPan, BorderLayout.SOUTH);

        buttonPan.add(menuPan, BorderLayout.WEST);

        return buttonPan;
    }

    /**
     * Construit le JPanel du setting menu
     * @return  JPanel
     */
    private void constrSettingMenu() {
        //parametre le panel border pour positionner le settingMenu
        this.settingMenuPosition.setLayout(new BorderLayout());
        this.settingMenuPosition.setOpaque(false);

        //panel du popUp setting
        RoundedPanel settingPopUp = new RoundedPanel(12);
        settingPopUp.setBackground(Color.WHITE);
        settingPopUp.setLayout(new BoxLayout(settingPopUp, BoxLayout.PAGE_AXIS));

        //panel du retour et du label SETTING
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
            getSuperposePan().remove(settingMenuPosition);
            settingButton.setVisible(true);
            getSuperposePan().revalidate();
            getSuperposePan().repaint();
        });
        retourPan.add(Box.createRigidArea(new Dimension(10,0)));
        retourPan.add(retour);
        retourPan.add(Box.createHorizontalGlue());
        //label SETTING
        JLabel settingLabel = new JLabel("SETTING");
        settingLabel.setForeground(Color.BLACK);
        retourPan.add(settingLabel);
        retourPan.add(Box.createHorizontalGlue());
        retourPan.add(Box.createHorizontalGlue());
        //ajout du retour et du label dans le settingPopUp
        settingPopUp.add(Box.createRigidArea(new Dimension(0,10)));
        settingPopUp.add(retourPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,10)));


        //button import vol
        JPanel importVolPan = new JPanel();
        importVolPan.setOpaque(false);
        importVolPan.setLayout(new BoxLayout(importVolPan, BoxLayout.LINE_AXIS));
        RoundedButton importVolButton = new RoundedButton("importer une liste de vols");
        importVolButton.setToolTipText("<html>Charger un fichier CSV <br> Exemple : <br> ID_vol ; ID_aéro_départ ; ID_aéro_départ ; Heure_départ ; Min_départ ; duration</html>");
        importVolButton.addActionListener((ActionListener) -> {
            JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers CSV", "csv"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String cheminFichier = fileChooser.getSelectedFile().getPath();
                    // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier CSV
                    FenetreGraphe fen = new FenetreGraphe(cheminFichier);
                    fen.setVisible(true);
                    dispose();
                }
        });
        importVolButton.setBackground(new Color(176, 226, 255));
        importVolPan.add(Box.createHorizontalGlue());
        importVolPan.add(importVolButton);
        importVolPan.add(Box.createHorizontalGlue());
        //ajout du button import dans le settingPopUp
        settingPopUp.add(importVolPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,5)));

        //button import prefait
        JPanel importPrefaitPan = new JPanel();
        importPrefaitPan.setOpaque(false);
        importPrefaitPan.setLayout(new BoxLayout(importPrefaitPan, BoxLayout.LINE_AXIS));
        RoundedButton importPrefaitButton = new RoundedButton("importer un graphe préfait");
        importPrefaitButton.setToolTipText("<html>Charger un fichier TXT <br> Exemple : <br> kmax <br> nbNoeuds<br> arretes</html>");
        importPrefaitButton.addActionListener((ActionListener) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers TXT", "txt"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String cheminFichier = fileChooser.getSelectedFile().getPath();
                // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier TXT
                FenetreGraphe fen = new FenetreGraphe(cheminFichier);
                fen.setVisible(true);
                dispose();
            }
        });
        importPrefaitButton.setBackground(new Color(176, 226, 255));
        importPrefaitPan.add(Box.createHorizontalGlue());
        importPrefaitPan.add(importPrefaitButton);
        importPrefaitPan.add(Box.createHorizontalGlue());
        //ajout du button import dans le settingPopUp
        settingPopUp.add(importPrefaitPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,15)));


        // coloration (kmax)
        //slider Pan
        JPanel sliderCouleurPan = new JPanel();
        sliderCouleurPan.setMaximumSize(new Dimension(200, 50));
        sliderCouleurPan.setOpaque(false);
        sliderCouleurPan.setLayout(new BoxLayout(sliderCouleurPan, BoxLayout.LINE_AXIS));
        //JLabel
        sliderCouleurPan.add(Box.createHorizontalGlue());
        sliderCouleurPan.add(new JLabel("kmax : "));
        sliderCouleurPan.add(Box.createRigidArea(new Dimension(10,0)));
        //slider
        JSlider sliderCouleur = new JSlider(JSlider.HORIZONTAL, 1, 20, 12);
        sliderCouleurPan.add(sliderCouleur);
        sliderCouleurPan.add(Box.createRigidArea(new Dimension(10,0)));
        //JLabel int
        JLabel intLabel = new JLabel("12");
        sliderCouleur.addChangeListener((ChangeEvent) -> {
            intLabel.setText(Integer.toString(sliderCouleur.getValue()));
        });
        //ajout du slider et du label dans le settingPopUp
        sliderCouleurPan.add(intLabel);
        sliderCouleurPan.add(Box.createHorizontalGlue());
        settingPopUp.add(sliderCouleurPan);

        // Dsature button
        JPanel dsaturePan = new JPanel();
        dsaturePan.setOpaque(false);
        dsaturePan.setLayout(new BoxLayout(dsaturePan, BoxLayout.LINE_AXIS));
        RoundedButton dsatureButton = new RoundedButton("colorier avec DSature");
        dsatureButton.setToolTipText("Appliquer la méthode DSature sur le graphe");
        dsatureButton.setBackground(new Color(176, 226, 255));
        dsatureButton.addActionListener((ActionListener) -> {
            graph.setKdonne(sliderCouleur.getValue());
            graph.dSature();

            retour.doClick();
            infoLabel.setText(createInfoString());
        });
        dsaturePan.add(Box.createHorizontalGlue());
        dsaturePan.add(dsatureButton);
        dsaturePan.add(Box.createHorizontalGlue());
        //ajout du button dans le settingPopUp
        settingPopUp.add(dsaturePan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,5)));
        // WelshPowell button
        JPanel welshPowellPan = new JPanel();
        welshPowellPan.setOpaque(false);
        welshPowellPan.setLayout(new BoxLayout(welshPowellPan, BoxLayout.LINE_AXIS));
        RoundedButton welshPowellButton = new RoundedButton("Colorier avec WelshPowell");
        welshPowellButton.setToolTipText("Appliquer la méthode WelshPowell sur le graphe");
        welshPowellButton.setBackground(new Color(176, 226, 255));
        welshPowellButton.addActionListener((ActionListener) -> {
            graph.setKdonne(sliderCouleur.getValue());
            graph.welshPowell();
            retour.doClick();
            infoLabel.setText(createInfoString());
        });
        welshPowellPan.add(Box.createHorizontalGlue());
        welshPowellPan.add(welshPowellButton);
        welshPowellPan.add(Box.createHorizontalGlue());
        //ajout du button dans le settingPopUp
        settingPopUp.add(welshPowellPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,15)));


        //panel du settingPopUp
        JPanel settingMenuPan = new JPanel();
        settingMenuPan.setOpaque(false);
        settingMenuPan.setLayout(new BoxLayout(settingMenuPan, BoxLayout.PAGE_AXIS));
        settingMenuPan.add(settingPopUp);
        settingMenuPan.add(Box.createVerticalGlue());

        settingMenuPosition.add(settingMenuPan, BorderLayout.EAST);
    }

    private String createInfoString() {
        StringBuilder info = new StringBuilder();
        info.append("<html>");
        info.append("file : ").append(this.graph.getId()).append("<br>");
        info.append("nbr noeuds : ").append(this.graph.getNodeCount()).append("<br>");
        info.append("nbr arêtes : ").append(this.graph.getEdgeCount()).append("<br>");
        info.append("nbr couleurs : ").append(this.graph.getKoptimal()).append("<br>");
        info.append("</html>");
        return info.toString();
    }
}