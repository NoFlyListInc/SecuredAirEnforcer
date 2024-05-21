import javax.swing.JFrame;

public class FenetreConstruction extends JFrame {
    
    public FenetreConstruction() {
        // Base de la fenêtre
        setTitle("Construction Window");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // TO DO
        
        setVisible(true);
    }
    
    public static void main(String[] args) {
        new FenetreConstruction();
    }
}
