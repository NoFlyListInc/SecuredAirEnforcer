package src.ihm;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/**
 * <h3>Cette classe crée une fenêtre pour importer un graphe.</h3>
 * <p>La fenêtre contient deux panneaux: un pour charger une liste de vol et un pour charger une liste d'aéroports.</p>
 * <p>Chaque panneau contient un titre, une instruction et un bouton pour parcourir les fichiers.</p>
 * <p>Les fichiers doivent être en format .csv pour la liste de vol et en format .txt pour la liste d'aéroports.</p>
 * @author FERNANDES Thomas & NOUVEL Armand
 */
public class FenetreImportGraph extends SuperposedFenetre {
    public FenetreImportGraph() {
        // Base de la fenêtre
        this.setTitle("Import a graph");
        this.setMinimumSize(new Dimension(400,300));  
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Panneau principal
        JPanel panneau = new JPanel(new GridBagLayout());
        panneau.setBackground(new Color(98, 142, 255));
        GridBagConstraints gbc = new GridBagConstraints();

        // Contraintes
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;

        // Panneau Liste de vol
        JPanel panVol = new JPanel();
        panVol.setLayout(new BoxLayout(panVol, BoxLayout.Y_AXIS));
        panVol.setBackground(new Color(45, 40, 63));
        panVol.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panVol.setPreferredSize(new Dimension(220, 280)); // Taille préférée du panneau de vol


        JLabel titreVol = new JLabel("<html><center>Charger une<br>liste de vol</center></html>");
        titreVol.setForeground(Color.WHITE);
        titreVol.setFont(titreVol.getFont().deriveFont(Font.PLAIN, 25));
        titreVol.setHorizontalAlignment(SwingConstants.CENTER);
        titreVol.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionVol = new JLabel("<html>Veuillez sélectionner un<br><center>fichier en format .csv</center></html>");
        instructionVol.setForeground(Color.WHITE);
        instructionVol.setFont(instructionVol.getFont().deriveFont(Font.PLAIN, 15));
        instructionVol.setHorizontalAlignment(SwingConstants.CENTER);
        instructionVol.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton boutonVol = new RoundedButton("Parcourir...");
        boutonVol.setBackground(new Color(176, 226, 255));
        boutonVol.setContentAreaFilled(false);
        boutonVol.setFont(boutonVol.getFont().deriveFont(Font.PLAIN, 25));
        boutonVol.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonVol.setToolTipText("<html>Charger un fichier CSV <br> Exemple : <br> ID_vol ; ID_aéro_départ ; ID_aéro_départ ; Heure_départ ; Min_départ ; duration</html>");

        // Ajouter les composants au panneau de vol
        panVol.add(titreVol);
        panVol.add(Box.createVerticalStrut(70));
        panVol.add(instructionVol);
        panVol.add(Box.createRigidArea(new Dimension(0, 10)));
        panVol.add(boutonVol);
        panVol.add(Box.createVerticalGlue());

        // Panneau Liste d'aéroports
        JPanel panPrefait = new JPanel();
        panPrefait.setLayout(new BoxLayout(panPrefait, BoxLayout.Y_AXIS));
        panPrefait.setBackground(new Color(45, 40, 63));
        panPrefait.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panPrefait.setPreferredSize(new Dimension(220, 280)); // Taille préférée du panneau des aéroports

        JLabel titrePrefait = new JLabel("<html><center>Charger un graphe préfait</center></html>");
        titrePrefait.setForeground(Color.WHITE);
        titrePrefait.setFont(titrePrefait.getFont().deriveFont(Font.PLAIN, 25));
        titrePrefait.setHorizontalAlignment(SwingConstants.CENTER);
        titrePrefait.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel instructionPrefait = new JLabel("<html>Veuillez sélectionner un<br><center> fichier en format .txt</center></html>");
        instructionPrefait.setForeground(Color.WHITE);
        instructionPrefait.setFont(instructionPrefait.getFont().deriveFont(Font.PLAIN, 15));
        instructionPrefait.setHorizontalAlignment(SwingConstants.CENTER);
        instructionPrefait.setAlignmentX(Component.CENTER_ALIGNMENT);

        RoundedButton boutonPrefait = new RoundedButton("Parcourir...");
        boutonPrefait.setBackground(new Color(176, 226, 255));
        boutonPrefait.setContentAreaFilled(false);
        boutonPrefait.setFont(boutonPrefait.getFont().deriveFont(Font.PLAIN, 25));
        boutonPrefait.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonPrefait.setToolTipText("<html>Charger un fichier TXT <br> Exemple : <br> kmax <br> nbNoeuds<br> arretes</html>");

        // Ajouter les composants au panneau des aéroports
        panPrefait.add(titrePrefait);
        panPrefait.add(Box.createVerticalStrut(70));
        panPrefait.add(instructionPrefait);
        panPrefait.add(Box.createRigidArea(new Dimension(0, 10)));
        panPrefait.add(boutonPrefait);
        panPrefait.add(Box.createVerticalGlue());

        // Ajouter les panneaux à la grille
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 10, 5, 50); // Augmenter l'espace à droite du premier panneau
        panneau.add(panVol, gbc);

        gbc.gridx = 3;
        gbc.insets = new Insets(5, 50, 5, 10); // Augmenter l'espace à gauche du second panneau
        panneau.add(panPrefait, gbc);

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
                    FenetreGraphe fen = new FenetreGraphe(cheminFichier);
                    fen.setVisible(true);
                    dispose();
                }
            }
        });

        // Action bouton graph-test.txt
        boutonPrefait.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
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