import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.WaypointRenderer;
import java.awt.Color;
import java.awt.Graphics2D;
import org.jxmapviewer.JXMapViewer;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;

public class CollisionWaypointRenderer implements WaypointRenderer<DefaultWaypoint>
{
    @Override
    public void paintWaypoint(Graphics2D g, JXMapViewer map, DefaultWaypoint waypoint) {
        // Convertir les coordonnées du waypoint en coordonnées de pixel
        Point2D point = map.getTileFactory().geoToPixel(waypoint.getPosition(), map.getZoom());

        // Traduire le système de coordonnées graphiques à la position du waypoint
        g.translate((int)point.getX(), (int)point.getY());

        // Crée un carré
        Polygon square1 = new Polygon();
        square1.addPoint(-5, -5);
        square1.addPoint(5, -5);
        square1.addPoint(5, 5);
        square1.addPoint(-5, 5);

        // Crée un deuxième carré et le tourne de 45 degrés
        Polygon square2 = new Polygon();
        square2.addPoint(-5, -5);
        square2.addPoint(5, -5);
        square2.addPoint(5, 5);
        square2.addPoint(-5, 5);
        AffineTransform transform = new AffineTransform();
        transform.rotate(Math.toRadians(45), 0, 0);
        Path2D rotatedSquare = (Path2D) transform.createTransformedShape(square2);
        PathIterator iterator = rotatedSquare.getPathIterator(null);
        float[] coords = new float[5];
        while (!iterator.isDone()) {
            int type = iterator.currentSegment(coords);
            if (type != PathIterator.SEG_CLOSE) {
                square2.addPoint((int)coords[0], (int)coords[1]);
            }
            iterator.next();
        }

        // Dessinez les carrés
        g.setColor(Color.RED);
        g.fill(square1);
        g.fill(square2);

        // Réinitialiser la translation
        g.translate(-(int)point.getX(), -(int)point.getY());
    }
}
