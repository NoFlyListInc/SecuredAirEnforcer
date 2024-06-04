package src.ihm;
//graphstream objects
import org.graphstream.ui.swingViewer.Viewer;

import src.core.Graph;
import src.core.ListAeroport;
import src.core.ListVol;

//swing objects
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.JFrame;
//awt objects
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Color;
//#endregion

/**
 * Classe FenetreGraphe
 * Fenêtre pour afficher un graph
 * @extends SuperposedFenetre
 * @autor NOUVEL Armand et Thomas FERNANDES
 */
public class FenetreGraphe extends SuperposedFenetre {

    JPanel settingMenuPosition = new JPanel();
    JButton settingButton;
    Graph graph = new Graph("graph");

    /**
     * Constructeur de la classe FenetreGraphe
     * @param cheminFichier String
     * @param format String, {"txt", "csv"}
     */
    public FenetreGraphe(String cheminFichier, String format) {
        // Base de la fenêtre
        this.setTitle("Graphe");
        this.setMinimumSize(new Dimension(400,300));  
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        
        // creation du graph
        if (format.equals("txt")) {
            this.graph.fillFile(cheminFichier);
        } else if (format.equals("csv")) {
            ListVol listVol = new ListVol();
            ListAeroport listAeroport = new ListAeroport();
            listAeroport.fill("./data/aeroports.txt");
            listVol.fill(cheminFichier, listAeroport);
            this.graph.fillVol(listVol);
        }
        Viewer viewerGraphe = new Viewer(this.graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewerGraphe.enableAutoLayout();

        // superposePan
        superposePan.setPreferredSize(new Dimension(800, 600));

        // JPanel pricipale
        JPanel panPrincipale = new JPanel();
        panPrincipale.setLayout(new BorderLayout());
        panPrincipale.setOpaque(true);
        panPrincipale.setBounds(0, 0, 800, 600);
        JLabel labelCheminFichier = new JLabel("Chemin du fichier TXT sélectionné : " + cheminFichier);
        labelCheminFichier.setHorizontalAlignment(SwingConstants.CENTER);
        panPrincipale.add(labelCheminFichier, BorderLayout.NORTH);
        panPrincipale.add(viewerGraphe.addDefaultView(false), BorderLayout.CENTER);
        this.superposePan.add(panPrincipale, JLayeredPane.DEFAULT_LAYER);

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
        buttonPan.add(settingPan, BorderLayout.EAST);

        JPanel menuPan = new JPanel();
        menuPan.setOpaque(false);
        menuPan.setLayout(new BoxLayout(menuPan, BoxLayout.PAGE_AXIS));
        menuPan.add(Box.createVerticalGlue());
        menuPan.add(this.menuButton);
        buttonPan.add(menuPan, BorderLayout.WEST);

        superposePan.add(buttonPan, JLayeredPane.PALETTE_LAYER);

        this.constrSettingMenu();

        // Ajout du superposePan
        this.setContentPane(superposePan);

        // ajustement de la taille
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getContentPane().getSize();
                panPrincipale.setBounds(0, 0, size.width, size.height);
                buttonPan.setBounds(0, 0, size.width, size.height);
                menu.setBounds(0, 0, size.width, size.height);
            }
        });
    }

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
        importVolButton.addActionListener((ActionListener) -> {
            JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers CSV", "csv"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String cheminFichier = fileChooser.getSelectedFile().getPath();
                    // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier CSV
                    FenetreGraphe fen = new FenetreGraphe(cheminFichier, "csv");
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
        importPrefaitButton.addActionListener((ActionListener) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers TXT", "txt"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String cheminFichier = fileChooser.getSelectedFile().getPath();
                // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier TXT
                FenetreGraphe fen = new FenetreGraphe(cheminFichier, "txt");
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


        // hauteur setting (hmax)
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
        //button
        JPanel couleurPan = new JPanel();
        couleurPan.setOpaque(false);
        couleurPan.setLayout(new BoxLayout(couleurPan, BoxLayout.LINE_AXIS));
        RoundedButton couleurButton = new RoundedButton("Appliquer la coloration");
        couleurButton.setBackground(new Color(176, 226, 255));
        couleurButton.addActionListener((ActionListener) -> {
            graph.dSature(sliderCouleur.getValue());
            retour.doClick();
        });
        couleurPan.add(Box.createHorizontalGlue());
        couleurPan.add(couleurButton);
        couleurPan.add(Box.createHorizontalGlue());
        //ajout du button dans le settingPopUp
        settingPopUp.add(couleurPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,15)));


        //panel du settingPopUp
        JPanel settingMenuPan = new JPanel();
        settingMenuPan.setOpaque(false);
        settingMenuPan.setLayout(new BoxLayout(settingMenuPan, BoxLayout.PAGE_AXIS));
        settingMenuPan.add(settingPopUp);
        settingMenuPan.add(Box.createVerticalGlue());

        this.settingMenuPosition.add(settingMenuPan, BorderLayout.EAST);
    }
}