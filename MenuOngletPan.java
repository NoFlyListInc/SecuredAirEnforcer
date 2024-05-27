//#region imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
//#endregion
import javax.swing.border.Border;

public class MenuOngletPan extends JPanel
{
    //#region attributs
    private JLayeredPane superposePan;
    //#endregion

    //#region constructeur
    public MenuOngletPan(JLayeredPane superposePan) {
        super();
        this.superposePan=superposePan;
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
            superposePan.remove(this);
            superposePan.revalidate();
            superposePan.repaint();
        });
        pan.add(retour);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton home = new JButton("home");
        pan.add(home);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton map = new JButton("map");
        pan.add(map);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton constrGraphe = new JButton("constr");
        pan.add(constrGraphe);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        JButton entreeGraphe = new JButton("entree");
        pan.add(entreeGraphe);
        pan.add(Box.createRigidArea(new Dimension(0,10)));

        return pan;
    }
}
