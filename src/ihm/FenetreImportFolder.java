package src.ihm;
//#region imports
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
//#endregion
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;


public class FenetreImportFolder extends SuperposedFenetre {

    public FenetreImportFolder() {
        // Base de la fenêtre
        this.setTitle("Construction Window");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

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

        // Bouton pour parcourir le dossier
        RoundedButton boutonFold = new RoundedButton("Parcourir...");
        boutonFold.setBackground(new Color(176, 226, 255));
        boutonFold.setContentAreaFilled(false);
        boutonFold.setFont(boutonFold.getFont().deriveFont(Font.PLAIN, 25));
        boutonFold.setAlignmentX(Component.CENTER_ALIGNMENT);

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
                fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    File[] files = selectedFile.listFiles();
                        for (File file : files) {
                            if (file.getName().matches("graph-test\\d+.txt")) {
                                System.out.println(file.getName());
                                try {Scanner scanner = new Scanner(file);
                                    while (scanner.hasNextLine()) {
                                        String line = scanner.nextLine();
                                        System.out.println(line);
                                    }
                                    scanner.close();
                                    panFold.setBackground(new Color(21, 105, 19));
                                } catch (FileNotFoundException ex) {
                                    System.out.println("An error occurred.");
                                    ex.printStackTrace();
                                }
                        }
                    }                        
                }
            }
        });

        
        panneau.add(panFold, gbc);
        this.add(panneau);

        
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