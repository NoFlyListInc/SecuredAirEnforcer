package src.ihm;
//#region imports
//swing imports
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLayeredPane;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import src.core.Graph;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;

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
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
//util imports

//#endregion

/**
 * <h3>Cette classe crée une fenêtre pour importer un dossier.</h3>
 * <p>La fenêtre contient un panneau pour charger un dossier contenant des fichiers .txt.</p>
 * <p>Le panneau contient un titre, une instruction et un bouton pour parcourir les fichiers.</p>
 * <p>Les fichiers doivent être en format .txt.</p>
 * @autor FERNANDES Thomas
 */
public class FenetreImportFolder extends SuperposedFenetre {

    /**
     * Constructeur de la fenêtre d'importation de dossier.
     * <p>Crée une fenêtre pour importer un dossier contenant des fichiers .txt.</p>
     * <p>Le panneau contient un titre, une instruction et un bouton pour parcourir les fichiers.</p>
     */
    public FenetreImportFolder() {

    //#region Fenetre
        // Base de la fenêtre
        this.setTitle("Construction Window");
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
        JLabel titreFold = new JLabel("<html><center>Charger un dossier</center></html>");
        titreFold.setForeground(Color.WHITE);
        titreFold.setFont(titreFold.getFont().deriveFont(Font.PLAIN, 25));
        titreFold.setHorizontalAlignment(SwingConstants.CENTER);
        titreFold.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Instruction pour l'importation de dossier
        JLabel instructionFold = new JLabel("<html><center>Veuillez sélectionner un dossier contenant les fichiers .txt</center></html>");
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
        boutonFold.setToolTipText("<html>Charger un dossier contenant des fichiers TXT ou un/des fichier(s) TXT<br>Exemple : <br> kmax <br> nbNoeuds<br> arretes</html>");

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
                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Text files", "txt"));
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    if (fileChooser.getSelectedFile().isDirectory()) {
                        File[] files = fileChooser.getSelectedFile().listFiles();
                        for (File file : files) {
                            if (!file.getName().endsWith(".txt")) {
                                continue;
                            }
                            try {
                                String Path = file.getAbsolutePath();
                                Graph graph = new Graph("test");
                                graph.fillFile(Path);

                                BufferedReader reader = new BufferedReader(new FileReader(Path));
                                String line = reader.readLine();
                                int kMAX = Integer.parseInt(line);
                                reader.close();
                                graph.dSature(kMAX);


                                String graphInfo = graph.getColoredGraph();
                                System.out.println(graphInfo);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                    } else {
                        
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    for (File files : selectedFiles) {
                        try {
                            String Path = files.getAbsolutePath();
                            Graph graph = new Graph("test");
                            graph.fillFile(Path);

                            BufferedReader reader = new BufferedReader(new FileReader(Path));
                            String line = reader.readLine();
                            int kMAX = Integer.parseInt(line);
                            reader.close();
                            graph.dSature(kMAX);
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
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