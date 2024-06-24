package src.ihm;
//#region import
//import JXMapViwer
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.WaypointRenderer;
import org.jxmapviewer.JXMapViewer;
//import awt
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.Point2D;
//#endregion

/**
 * Un waypoint painter en forme de triangle pour les aéroports         
 */
public class AeroportWaypointRenderer implements WaypointRenderer<DefaultWaypoint>
{
    //#region methodes
    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer carte, DefaultWaypoint waypoint) {
        // Convertir les coordonnées du waypoint en coordonnées de pixel
        Point2D point = carte.getTileFactory().geoToPixel(waypoint.getPosition(), carte.getZoom());

        // Traduire le système de coordonnées graphiques à la position du waypoint
        g.translate((int)point.getX(), (int)point.getY());

        // Crée un triangle avec la pointe vers le bas
        Polygon triangle = new Polygon();
        triangle.addPoint(0, 7); // Pointe du triangle
        triangle.addPoint(-7, -7); // Coin supérieur gauche
        triangle.addPoint(7, -7); // Coin supérieur droit

        // Dessine le triangle
        g.setColor(Color.BLACK);
        g.fill(triangle);

        // Réalise la translation
        g.translate(-(int)point.getX(), -(int)point.getY());
    }
    //#endregion
}
