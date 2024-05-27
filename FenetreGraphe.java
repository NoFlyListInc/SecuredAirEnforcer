//#region imports
import javax.swing.*;
import java.awt.*;
import java.io.File;
//#endregion


/**
 * Fenêtre pour afficher le graphe chargé à partir d'un fichier texte
 * @param cheminFichierCSV Chemin du fichier CSV contenant les données du graphe
 * @return Fenêtre affichant le graphe
 * @author FERNANDES Thomas & NOUVEL Armand
 */
//#region Base de la fenêtre
public class FenetreGraphe extends JFrame {
    public FenetreGraphe(String cheminFichierCSV) {
        // Base de la fenêtre
        this.setTitle("Graphe");
        this.setSize(800, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
//#endregion

//#region Affichage du chemin du fichier CSV
        JLabel labelCheminFichier = new JLabel("Chemin du fichier CSV sélectionné : " + cheminFichierCSV);
        labelCheminFichier.setHorizontalAlignment(SwingConstants.CENTER);
        add(labelCheminFichier, BorderLayout.NORTH);
//#endregion

//#region Panneau pour afficher le graphe
        JPanel panneauGraphe = new JPanel();
        panneauGraphe.setBackground(Color.WHITE);
        add(panneauGraphe, BorderLayout.CENTER);
//#endregion
    }
}