import javax.swing.*;
import java.awt.*;
import java.io.File;

public class FenetreGraphe extends JFrame {
    public FenetreGraphe(String cheminFichierCSV) {
        // Base de la fenêtre
        setTitle("Graphe");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Affichage du chemin du fichier CSV dans la fenêtre
        JLabel labelCheminFichier = new JLabel("Chemin du fichier CSV sélectionné : " + cheminFichierCSV);
        labelCheminFichier.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelCheminFichier, BorderLayout.NORTH);

        // Panneau pour afficher le graphe (vous pouvez ajouter ici le code pour afficher le graphe à partir du fichier CSV)
        JPanel panneauGraphe = new JPanel();
        panneauGraphe.setBackground(Color.WHITE);
        add(panneauGraphe, BorderLayout.CENTER);

        setVisible(true);
    }

    public static void main(String[] args) {
        // L'appel à FenetreGraphe est désormais géré dans FenetreGraphChoose
    }
}