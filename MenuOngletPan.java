//#region imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//#endregion

public class MenuOngletPan extends JPanel
{
    //#region attributs
    private SuperposedFenetre fen;
    //#endregion

    //#region constructeur
    public MenuOngletPan(SuperposedFenetre fen) {
        super();
        this.fen=fen;
        this.constrPan();
    }
    //#endregion

    //#region methodes
    private void constrPan() {
        this.setLayout(new BorderLayout(0, 0));
        this.setOpaque(false);

        JPanel pan = new JPanel();
        pan.setOpaque(false);
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));
        pan.add(Box.createVerticalGlue());
        pan.add(this.constrButtonPan());

        this.add(pan, BorderLayout.WEST);
    }

    private JPanel constrButtonPan() {
        JPanel pan = new JPanel();
        pan.setOpaque(true);
        pan.setBackground(Color.CYAN);
        pan.setLayout(new BoxLayout(pan, BoxLayout.PAGE_AXIS));

        pan.add(Box.createRigidArea(new Dimension(60,10)));

        JButton retour = new JButton("retour");
        retour.addActionListener((ActionListener) -> {
            fen.getSuperposePan().remove(this);
            fen.getSuperposePan().revalidate();
            fen.getSuperposePan().repaint();
        });
        pan.add(retour);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton home = new JButton("home");
        home.addActionListener((ActionListener) -> {
            FenetrePrincipale fenetre = new FenetrePrincipale();
            fenetre.setVisible(true);
            fen.dispose();
        });
        pan.add(home);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton map = new JButton("map");
        map.addActionListener((ActionListener) -> {
            FenetreMap fenetre = new FenetreMap();
            fenetre.setVisible(true);
            fen.dispose();
        });
        pan.add(map);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton constrGraphe = new JButton("entree");
        constrGraphe.addActionListener((ActionListener) -> {
            FenetreImportGraph fenetre = new FenetreImportGraph();
            fenetre.setVisible(true);
            fen.dispose();
        });
        pan.add(constrGraphe);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton entreeGraphe = new JButton("constr");
        pan.add(entreeGraphe);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        return pan;
    }
}
