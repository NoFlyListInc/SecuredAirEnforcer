import javax.swing.*;
import org.graphstream.ui.swingViewer.Viewer;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FenetreGraphe extends JFrame {

    //#region attributes
    private JLayeredPane superposePan = new JLayeredPane();
    private MenuOngletPan menu = new MenuOngletPan(superposePan);
    //#endregion

    public FenetreGraphe(String cheminFichierCSV) {
        // Base de la fenêtre
        this.setTitle("Graphe");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        
        // creation du graph
        Graph graph = new Graph("graph");
        graph.fillFile(cheminFichierCSV);
        Viewer viewerGraphe = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewerGraphe.enableAutoLayout();

        // superposePan
        superposePan.setPreferredSize(new Dimension(800, 600));

        // JPanel pricipale
        JPanel panPrincipale = new JPanel();
        panPrincipale.setLayout(new BorderLayout());
        panPrincipale.setOpaque(true);
        panPrincipale.setBounds(0, 0, 800, 600);
        JLabel labelCheminFichier = new JLabel("Chemin du fichier TXT sélectionné : " + cheminFichierCSV);
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