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
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.JFrame;
//awt objects
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
//#endregion

/**
 * Classe FenetreGraphe
 * Fenêtre pour afficher un graph
 * @extends SuperposedFenetre
 * @autor NOUVEL Armand et Thomas FERNANDES
 */
public class FenetreGraphe extends SuperposedFenetre {

    /**
     * Constructeur de la classe FenetreGraphe
     * @param cheminFichier String
     * @param format String, {"txt", "csv"}
     */
    public FenetreGraphe(String cheminFichier, String format) {
        // Base de la fenêtre
        this.setTitle("Graphe");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        
        // creation du graph
        Graph graph = new Graph("graph");
        if (format.equals("txt")) {
            graph.fillFile(cheminFichier);
        } else if (format.equals("csv")) {
            ListVol listVol = new ListVol();
            ListAeroport listAeroport = new ListAeroport();
            listAeroport.fill("./Data/aeroports.txt");
            listVol.fill(cheminFichier, listAeroport);
            graph.fillVol(listVol);
        }
        Viewer viewerGraphe = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
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
        Image settingImage = new ImageIcon("Image/parametre.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        JButton settingButton = new JButton(new ImageIcon(settingImage));
        settingButton.setBorderPainted(false);
        settingButton.setContentAreaFilled(false);
        settingButton.setFocusPainted(false);
        settingButton.addActionListener((ActionListener) -> {
            
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

        // Ajout du superposePan
        this.add(superposePan);

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
}