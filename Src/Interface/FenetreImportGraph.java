package Src.Interface;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FenetreImportGraph extends SuperposedFenetre {
    public FenetreImportGraph() {
        // Base de la fenêtre
        this.setTitle("Import a graph");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Panneau
        JPanel panneau = new JPanel();
        panneau.setBounds(0, 0, 800, 600);
        panneau.setLayout(new BoxLayout(panneau, BoxLayout.PAGE_AXIS));
        panneau.setBackground(new Color(84, 172, 238));

        panneau.add(Box.createVerticalGlue());

        // pan image + titre
        JPanel panTitre = new JPanel();
        panTitre.setLayout(new BoxLayout(panTitre, BoxLayout.LINE_AXIS));
        panTitre.setOpaque(false);

        // Image de l'avion
        ImageIcon imageIcon = new ImageIcon("Image/avion.png");
        Image image = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        imageIcon = new ImageIcon(image);
        JLabel imageAvion = new JLabel(imageIcon);
        panTitre.add(imageAvion);

        panTitre.add(Box.createRigidArea(new Dimension(20, 0)));

        // titre
        JLabel titre = new JLabel("Importer un graphe à partir d'un fichier");
        titre.setFont(titre.getFont().deriveFont(Font.PLAIN, 24)); 
        panTitre.add(titre);

        // Ajouter le panneau titre à la fenêtre
        panneau.add(panTitre);
        panneau.add(Box.createRigidArea(new Dimension(0, 30)));

        // pan boutons
        JPanel panBoutons = new JPanel();
        panBoutons.setLayout(new BoxLayout(panBoutons, BoxLayout.LINE_AXIS));
        panBoutons.setOpaque(false);

        // bouton graph-test.txt
        JButton boutonGraph = new JButton("Importer graph-test.txt");
        boutonGraph.setFont(boutonGraph.getFont().deriveFont(Font.PLAIN, 20));
        panBoutons.add(boutonGraph);

        panBoutons.add(Box.createRigidArea(new Dimension(30, 0)));

        // bouton vol-test.csv
        JButton boutonVol = new JButton("Importer vol-test.csv");
        boutonVol.setFont(boutonVol.getFont().deriveFont(Font.PLAIN, 20));
        panBoutons.add(boutonVol);

        // Ajouter le panneau boutons à la fenêtre
        panneau.add(panBoutons);
        panneau.add(Box.createVerticalGlue());

        // Action bouton graph-test.txt
        boutonGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers TXT", "txt"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    String cheminFichier = fileChooser.getSelectedFile().getPath();
                    // Créer une nouvelle fenêtre FenetreGraphe en passant le chemin du fichier CSV
                    FenetreGraphe fen = new FenetreGraphe(cheminFichier, "txt");
                    fen.setVisible(true);
                    dispose();
                }
            }
        });

        // Action bouton vol-test.csv
        boutonVol.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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
            }
        });

        // Ajouter le panneau à la fenêtre
        this.superposePan.add(panneau, JLayeredPane.DEFAULT_LAYER);

        //menu
        JPanel buttonPan = new JPanel();
        buttonPan.setBounds(0, 0, 800, 600);
        buttonPan.setOpaque(false);
        buttonPan.setLayout(new BorderLayout());

        JPanel menuPan = new JPanel();
        menuPan.setOpaque(false);
        menuPan.setLayout(new BoxLayout(menuPan, BoxLayout.PAGE_AXIS));
        menuPan.add(Box.createVerticalGlue());
        menuPan.add(this.menuButton);
        buttonPan.add(menuPan, BorderLayout.WEST);

        superposePan.add(buttonPan, JLayeredPane.PALETTE_LAYER);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getContentPane().getSize();
                panneau.setBounds(0, 0, size.width, size.height);
                buttonPan.setBounds(0, 0, size.width, size.height);
            }
        });

        this.setContentPane(superposePan);
    }
}