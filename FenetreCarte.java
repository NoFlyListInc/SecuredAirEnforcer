import javax.swing.JFrame;

public class FenetreCarte extends JFrame{
        
        public FenetreCarte() {
            // Base de la fenêtre
            setTitle("Carte de l'aéroport");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            
            // TO DO
            
            setVisible(true);
        }
        
        public static void main(String[] args) {
            new FenetreCarte();
        }
    
}
