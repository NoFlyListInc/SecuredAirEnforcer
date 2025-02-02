package src.ihm;
//#region import
//import JXMapViewer objects
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.painter.Painter;
//import awt objects
import java.awt.geom.Point2D;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.BasicStroke;
//#endregion

/**
 * Un LineOverlayPainter pour dessiner une ligne sur la carte
 * @see Painter
 * @author NOUVEL Armand
 * @version 1.0
 */
public class LineOverlayPainter implements Painter<JXMapViewer> {
    //#region attributs
    private GeoPosition point1;
    private GeoPosition point2;
    private Color color = Color.GRAY;
    //#endregion

    //#region constructeurs
    /**
     * Constructeur de LineOverlayPainter
     * @param point1 le premier point de la ligne
     * @param point2 le deuxième point de la ligne
     */
    public LineOverlayPainter(GeoPosition point1, GeoPosition point2) {
        this.point1 = point1;
        this.point2 = point2;
    }
    //#endregion

    //#region methodes
    /**
     * Defini la couleur de la ligne
     * @param color couleur
     */
    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    /**
     * Dessine la ligne sur la carte
     * @param g Graphics2D
     * @param map JXMapViewer
     * @param w int
     * @param h int
     */
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
        // Convertir les coordonnées géographiques en coordonnées de pixel
        Point2D pt1 = map.convertGeoPositionToPoint(point1);
        Point2D pt2 = map.convertGeoPositionToPoint(point2);

        // Dessiner la ligne
        g.setColor(this.color);
        g.setStroke(new BasicStroke(2));
        g.drawLine((int)pt1.getX(), (int)pt1.getY(), (int)pt2.getX(), (int)pt2.getY());
    }
    //#endregion
}
