package src.ihm;
//#region import
//import JXMapViewer
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.WaypointRenderer;
import org.jxmapviewer.JXMapViewer;
//import awt
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
//#endregion

/**
 * Un waypoint painter en forme d'explosion pour les collisions
 */
public class CollisionWaypointRenderer implements WaypointRenderer<DefaultWaypoint>
{
    //#region methodes
    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer carte, DefaultWaypoint waypoint) {
        // Convertir les coordonnées du waypoint en coordonnées de pixel
        Point2D point = carte.getTileFactory().geoToPixel(waypoint.getPosition(), carte.getZoom());

        // Traduire le système de coordonnées graphiques à la position du waypoint
        g.translate((int)point.getX(), (int)point.getY());

        // Crée un carré
        Polygon carre1 = new Polygon();
        carre1.addPoint(-5, -5); // Coin supérieur gauche
        carre1.addPoint(5, -5); // Coin supérieur droit
        carre1.addPoint(5, 5); // Coin inférieur droit
        carre1.addPoint(-5, 5); // Coin inférieur gauche

        // Crée un deuxième carré et le tourne de 45 degrés
        Polygon carre2 = new Polygon();
        carre2.addPoint(-5, -5); // Coin supérieur gauche
        carre2.addPoint(5, -5); // Coin supérieur droit
        carre2.addPoint(5, 5); // Coin inférieur droit
        carre2.addPoint(-5, 5); // Coin inférieur gauche
        AffineTransform transformation = new AffineTransform();
        transformation.rotate(Math.toRadians(45), 0, 0);
        Path2D carreTourne = (Path2D) transformation.createTransformedShape(carre2);
        PathIterator iterateur = carreTourne.getPathIterator(null);
        float[] coords = new float[5];
        while (!iterateur.isDone()) {
            int type = iterateur.currentSegment(coords);
            if (type != PathIterator.SEG_CLOSE) {
                carre2.addPoint((int)coords[0], (int)coords[1]);
            }
            iterateur.next();
        }

        // Dessine les carrés
        g.setColor(Color.RED);
        g.fill(carre1);
        g.fill(carre2);

        // Réalise la translation
        g.translate(-(int)point.getX(), -(int)point.getY());
    }
    //#endregion
}
