package src.ihm;
//#region imports
//swing imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import src.core.Graph;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;

//graphstream imports
import org.graphstream.graph.Node;

//util imports
import java.util.ArrayList;

//awt imports
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Component;
import java.awt.BorderLayout;

//awt event imports
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
//io imports
import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;

//nio imports
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.FileAlreadyExistsException;
//util imports

//#endregion

/**
 * <h3>Cette classe crée une fenêtre pour importer un dossier.</h3>
 * <p>La fenêtre contient un panneau pour charger un dossier contenant des fichiers .txt.</p>
 * <p>Le panneau contient un titre, une instruction et un bouton pour parcourir les fichiers.</p>
 * <p>Les fichiers doivent être en format .txt.</p>
 * @autor FERNANDES Thomas
 */
public class FenetreImportFolder extends FenetreSuperpose {

    /**
     * Constructeur de la fenêtre d'importation de dossier.
     * <p>Crée une fenêtre pour importer un dossier contenant des fichiers .txt.</p>
     * <p>Le panneau contient un titre, une instruction et un bouton pour parcourir les fichiers.</p>
     */
    public FenetreImportFolder() {

    //#region Fenetre
        // Base de la fenêtre
        this.setTitle("Coloration à la chaine");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
    //#endregion

    //#region Panneau
        // Création du panneau principal avec GridBagLayout
        JPanel panneau = new JPanel(new GridBagLayout());
        panneau.setBackground(new Color(98, 142, 255));

        // Contraintes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Panneau d'importation de dossier
        JPanel panFold = new JPanel();
        panFold.setLayout(new BoxLayout(panFold, BoxLayout.Y_AXIS));
        panFold.setBackground(new Color(45, 40, 63));
        panFold.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panFold.setPreferredSize(new Dimension(220, 280));
    //#endregion

    //#region Labels
        // Titre de l'importation de dossier
        JLabel titreFold = new JLabel("<html><center>Charger plusieurs fichiers</center></html>");
        titreFold.setForeground(Color.WHITE);
        titreFold.setFont(titreFold.getFont().deriveFont(Font.PLAIN, 22));
        titreFold.setHorizontalAlignment(SwingConstants.CENTER);
        titreFold.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Instruction pour l'importation de dossier
        JLabel instructionFold = new JLabel("<html><center>Veuillez sélectionner un ou plusieurs fichiers .txt</center></html>");
        instructionFold.setForeground(Color.WHITE);
        instructionFold.setFont(instructionFold.getFont().deriveFont(Font.PLAIN, 15));
        instructionFold.setHorizontalAlignment(SwingConstants.CENTER);
        instructionFold.setAlignmentX(Component.CENTER_ALIGNMENT);
    //#endregion

    //#region Bouton
        // Bouton pour parcourir le dossier
        RoundedButton boutonFold = new RoundedButton("Parcourir...");
        boutonFold.setBackground(new Color(176, 226, 255));
        boutonFold.setContentAreaFilled(false);
        boutonFold.setFont(boutonFold.getFont().deriveFont(Font.PLAIN, 25));
        boutonFold.setAlignmentX(Component.CENTER_ALIGNMENT);
        boutonFold.setToolTipText("<html>Charger un ou des fichier(s) TXT<br>Exemple : <br> kmax <br> nbNoeuds<br> arretes</html>");

        // Ajouter les composants au panneau d'importation de dossier
        panFold.add(titreFold);
        panFold.add(Box.createVerticalStrut(60));
        panFold.add(instructionFold);
        panFold.add(Box.createRigidArea(new Dimension(0, 10)));
        panFold.add(boutonFold);
        panFold.add(Box.createVerticalGlue());

        boutonFold.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION && fileChooser.getSelectedFiles().length > 0) {
                    try {
                        //creation du dossier resultat
                        String resultatPath = fileChooser.getSelectedFiles()[0].getParentFile().getAbsolutePath()+"/resulat";
                        Path dossier = Paths.get(resultatPath);
                        try {
                            Files.createDirectory(dossier);
                        } catch (FileAlreadyExistsException ex) {
                            // si il existe deja on fait rien
                        }
                        //fichier coloration-groupe
                        BufferedWriter writerColorationFile = new BufferedWriter(new FileWriter(resultatPath+"/"+"coloration-groupeLacroixNouvelFernandes.csv"));

                        //on parcours chaque fichier
                        for (File files : fileChooser.getSelectedFiles()) {
                            //creation du graph
                            String Path = files.getAbsolutePath();
                            Graph graph = new Graph("graph");
                            try {
                                graph.fillFile(Path);
                            } catch (Exception ex) {
                                JOptionPane.showMessageDialog(FenetreImportFolder.this, ex.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
                            }
                            // coloration                            
                            graph.dSature();

                            //creation du fichier reponse
                            BufferedWriter writer = new BufferedWriter(new FileWriter(resultatPath+"/"+files.getName().replace("graph","colo")));
                            ArrayList<String> couleurList = new ArrayList<String>();
                            for (Node noeud : graph.getEachNode()) {
                                int couleur = couleurList.indexOf(noeud.getAttribute("ui.style"));
                                if (couleur == -1) {
                                    couleur=couleurList.size();
                                    couleurList.add(noeud.getAttribute("ui.style"));
                                }
                                writer.write(Integer.toString(noeud.getAttribute("ui.label"))+" ; "+(couleur+1));
                                writer.newLine();
                            }
                            writer.close();

                            //nouvelle ligne dans le fichier coloration-groupe
                            writerColorationFile.write(files.getName().replace("graph","colo")+";"+graph.getVolsMemesNiveaux().size());
                            writerColorationFile.newLine();
                        }
                        writerColorationFile.close();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(FenetreImportFolder.this, ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    panFold.setBackground(new Color(21, 105, 19));
                }
            }
        });
    //#endregion


        // Ajouter les panneaux à la grille
        panneau.add(panFold, gbc);
        this.add(panneau);

        
        // Ajouter le panneau à la fenêtre
        this.superposePan.add(panneau, JLayeredPane.DEFAULT_LAYER);

    //#region Menu
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
    //#endregion
    }
}