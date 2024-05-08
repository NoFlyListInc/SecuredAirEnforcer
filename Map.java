import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;


public class Map extends JXMapViewer
{
    private Set<DefaultWaypoint> ListWaypoint = new HashSet<>();
    private ListAeroport listAeroport;

    public Map() {
        super();

        TileFactoryInfo info = new OSMTileFactoryInfo();

        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        this.setTileFactory(tileFactory);

        this.setZoom(7);
        this.setAddressLocation(new GeoPosition(48.8566, 2.3522));

        // Créer un MouseInputListener pour le panoramique
        PanMouseInputListener listener = new PanMouseInputListener(this);

        // Ajouter les listeners pour le panoramique
        for (MouseListener ml : getMouseListeners()) {
            removeMouseListener(ml);
        }

        for (MouseMotionListener mml : getMouseMotionListeners()) {
            removeMouseMotionListener(mml);
        }

        addMouseListener(listener);
        addMouseMotionListener(listener);

        // Ajouter le listener pour le zoom
        addMouseWheelListener(new ZoomMouseWheelListenerCursor(this));

        // Ajouter un listener pour les clics sur aeroport
        addMouseListener(new MouseClickAeroportListener());
    }

    public void addGraph(Graph graph, ListAeroport listAeroport) {
        this.listAeroport = listAeroport;
        ListWaypoint.clear(); // Clear the list before adding new waypoints
        for (Node node : graph) {
            Double latitude=this.listAeroport.getAeroportByCode(node.getId()).getLatitude().getDecimal();
            Double longitude=this.listAeroport.getAeroportByCode(node.getId()).getLongitude().getDecimal();
            GeoPosition position = new GeoPosition(latitude, longitude);
            DefaultWaypoint waypoint = new DefaultWaypoint(position);
            ListWaypoint.add(waypoint);
        }

        WaypointPainter<DefaultWaypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(ListWaypoint);
        this.setOverlayPainter(waypointPainter);
    }

    class MouseClickAeroportListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            Rectangle rect = new Rectangle(point.x - 10, point.y - 10, 20, 40);
            for (DefaultWaypoint waypoint : ListWaypoint) {
                Point2D waypointPosition = convertGeoPositionToPoint(waypoint.getPosition());
                if (rect.contains(waypointPosition)) {
                    // Récupérer l'aéroport correspondant au waypoint
                    Aeroport aeroport = listAeroport.getAeroportByPosition(waypoint.getPosition());
                    if (aeroport != null) {
                        // Afficher les informations sur l'aéroport
                        System.out.println("Aéroport cliqué : " + aeroport);
                    }
                    break;
                }
            }
        }
    }
}
