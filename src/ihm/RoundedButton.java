package src.ihm;

//#region imports
//swing imports
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

//awt imports
import java.awt.Shape;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

//#endregion

/**
 * <h3>Cette classe crée un bouton arrondi.</h3>
 * <p>Le bouton est de forme arrondie et contient un texte ou une image.</p>
 * <p>Le bouton est de taille 20x20 pixels.</p>
 * @author NOUVEL Armand
 */
public class RoundedButton extends JButton {
    private Shape shape;

    /**
     * Constructeur du bouton arrondi avec un texte.
     * @param text Texte du bouton
     */
    public RoundedButton(String text) {
        super(text);
        this.setOpaque(false);
        this.setFocusPainted(false);
    }

    /**
     * Constructeur du bouton arrondi avec une image.
     * @param icon Image du bouton
     */
    public RoundedButton(ImageIcon icon) {
        super(icon);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
    }

    /**
     * Constructeur du bouton arrondi avec une image et un texte.
     * @param icon Image du bouton
     * @param text Texte du bouton
     */
    public RoundedButton(ImageIcon icon, String text) {
        super(text, icon);
        this.setOpaque(false);
        this.setContentAreaFilled(false);
        this.setBorderPainted(false);
        this.setFocusPainted(false);
    }

    /**
     * Dessine le bouton.
     * @param g Graphics
     */
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
    
    
        
        
        g2d.setColor(getBackground());
    
        // Dessiner le bouton par-dessus l'ombre
        boolean isMouseOver = getModel().isRollover();
        if (isMouseOver) {
            g2d.setColor(new Color(156,206,235));
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        } else {
            g2d.setColor(getBackground());
            g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }

        super.paintComponent(g);
        g2d.dispose();
    }

    /**
     * Dessine la bordure du bouton.
     * @param g Graphics
     */
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        boolean isMouseOver = getModel().isRollover();
        if (isMouseOver) {
            g2d.setColor(getForeground());
            g2d.setStroke(new BasicStroke(2));
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        } else {
            g2d.setColor(getForeground());
            g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
    }

    /**
     * Vérifie si le bouton contient les coordonnées x et y.
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return Vrai si le bouton contient les coordonnées x et y
     */
    public boolean contains(int x, int y) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
        }
        return shape.contains(x, y);
    }

}
