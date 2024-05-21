import javax.swing.JFrame;

public class FenetreGraphe extends JFrame{
            
        public FenetreGraphe() {
            // Base de la fenêtre
            setTitle("Graphe");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
         
            // TO DO
            
            setVisible(true);
        }
            
        public static void main(String[] args) {
            new FenetreGraphe();
        }

}
