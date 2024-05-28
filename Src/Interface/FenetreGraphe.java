package Src.Interface;
import org.graphstream.ui.swingViewer.Viewer;

import Src.Core.Graph;
import Src.Core.ListAeroport;
import Src.Core.ListVol;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Box;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Dimension;
//#endregion


public class FenetreGraphe extends SuperposedFenetre {

    //#region attributes
    private JLayeredPane superposePan = new JLayeredPane();
    //#endregion

    //#region accesseurs
    public JLayeredPane getSuperposePan() {
        return superposePan;
    }
    //#endregion

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
        JButton settingButton = new JButton("setting");
        settingButton.addActionListener((ActionListener) -> {
            
        });
        settingPan.add(settingButton);
        settingPan.add(Box.createVerticalGlue());
        buttonPan.add(settingPan, BorderLayout.EAST);

        JPanel menuPan = new JPanel();
        menuPan.setOpaque(false);
        menuPan.setLayout(new BoxLayout(menuPan, BoxLayout.PAGE_AXIS));
        JButton menuButton = new JButton("menu");
        menuButton.addActionListener((ActionListner) -> {
            Dimension size = getContentPane().getSize();
            menu.setBounds(0, 0, size.width, size.height);
            superposePan.add(menu, JLayeredPane.MODAL_LAYER);
        });
        menuPan.add(Box.createVerticalGlue());
        menuPan.add(menuButton);
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