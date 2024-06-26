package src.ihm;

//#region imports
//swing imports
import javax.swing.*;


import java.awt.*;
//#endregion

/**
 * Panneau arrondi, objet graphique
 * @see JPanel
 * @author NOUVEL Armand
 * @version 1.0
 */
public class RoundedPanel extends JPanel {
    private Color backgroundColor;
    private int cornerRadius = 15;

    /**
     * Constructeur du panneau arrondi
     * @param layout Layout du panneau
     * @param radius Rayon des coins
     * @param bgColor Couleur de fond
     */
    public RoundedPanel(LayoutManager layout, int radius, Color bgColor) {
        super(layout);
        cornerRadius = radius;
        backgroundColor = bgColor;
        setOpaque(false);
    }

    /**
     * Constructeur du panneau arrondi
     * @param layout Layout du panneau
     * @param radius Rayon des coins
     */
    public RoundedPanel(LayoutManager layout, int radius) {
        super(layout);
        cornerRadius = radius;
        setOpaque(false);
    }

    /** 
     * Constructeur du panneau arrondi
     * @param radius Rayon des coins
    */
    public RoundedPanel(int radius) {
        super();
        cornerRadius = radius;
        setOpaque(false);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draws the rounded panel with borders.
        if (backgroundColor != null) {
            graphics.setColor(backgroundColor);
        } else {
            graphics.setColor(getBackground());
        }
        graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
        graphics.setColor(getForeground());
        graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border
    }
}
