import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.WaypointRenderer;
import java.awt.Color;
import java.awt.Graphics2D;
import org.jxmapviewer.JXMapViewer;
import java.awt.Polygon;
import java.awt.geom.Point2D;

public class AeroportWaypointRenderer implements WaypointRenderer<DefaultWaypoint>
{
    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer map, DefaultWaypoint waypoint) {
        // Convertir les coordonnées du waypoint en coordonnées de pixel
        Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());

        // Traduire le système de coordonnées graphiques à la position du waypoint
        g.translate((int)point.getX(), (int)point.getY());

        // Crée un triangle avec la pointe vers le bas
        Polygon triangle = new Polygon();
        triangle.addPoint(0, 7); // Pointe du triangle
        triangle.addPoint(-7, -7); // Coin supérieur gauche
        triangle.addPoint(7, -7); // Coin supérieur droit

        // Dessinez le triangle
        g.setColor(Color.BLACK);
        g.fill(triangle);

        // Réinitialiser la translation
        g.translate(-(int)point.getX(), -(int)point.getY());
    }
}
