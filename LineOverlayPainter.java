import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.painter.Painter;

import java.awt.*;
import java.awt.geom.Point2D;

public class LineOverlayPainter implements Painter<JXMapViewer> {
    private GeoPosition point1;
    private GeoPosition point2;

    public LineOverlayPainter(GeoPosition point1, GeoPosition point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    @Override
    public void paint(Graphics2D g, JXMapViewer map, int w, int h) {
        // Convertir les coordonnées géographiques en coordonnées de pixel
        Point2D pt1 = map.convertGeoPositionToPoint(point1);
        Point2D pt2 = map.convertGeoPositionToPoint(point2);

        // Dessiner la ligne
        g.setColor(Color.GRAY);
        g.setStroke(new BasicStroke(2));
        g.drawLine((int)pt1.getX(), (int)pt1.getY(), (int)pt2.getX(), (int)pt2.getY());
    }
}
