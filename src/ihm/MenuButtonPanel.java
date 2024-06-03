package src.ihm;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;

public class MenuButtonPanel extends RoundedPanel {
    
    public MenuButtonPanel(int radius, SuperposedFenetre fen) {
        super(radius);
        this.setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        this.setBackground(new Color(45, 40, 63));
        this.add(Box.createRigidArea(new Dimension(25,0)));
        this.add(new JLabel("<html><font color='white'>MENU</font></html>"));
        Image buttonImage = new ImageIcon("Image/topArrow.png").getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
        JButton button = new JButton(new ImageIcon(buttonImage));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.addActionListener((ActionListner) -> {
            this.setVisible(false);
            Dimension size = fen.getContentPane().getSize();
            fen.menu.setBounds(0, 0, size.width, size.height);
            fen.superposePan.add(fen.menu, JLayeredPane.MODAL_LAYER);
        });
        this.add(button);
    }
}
