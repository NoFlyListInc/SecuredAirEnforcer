import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.painter.CompoundPainter;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashSet;
import java.util.Set;

import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;

import java.util.ArrayList;
import java.util.List;


public class Map extends JXMapViewer
{
    private Set<DefaultWaypoint> ListAeroportWaypoint = new HashSet<>();
    private Set<DefaultWaypoint> ListCollisionWaypoint = new HashSet<>();
    private ListAeroport listAeroport;
    private ListVol listVols;

    public Map() {
        super();

        TileFactoryInfo info = new OSMTileFactoryInfo();

        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        this.setTileFactory(tileFactory);

        this.setZoom(13);
        this.setAddressLocation(new GeoPosition(46.603354, 1.888334));

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

    public void addInformation(ListAeroport listAeroport, ListVol listVol) {
        this.listAeroport = listAeroport;
        this.listVols = listVol;
        Graph graph = new Graph("graph");
        graph.fillMap(listAeroport, listVol);
        graph.hideSoloNode();
        // Clear the list before adding new waypoints
        ListAeroportWaypoint.clear();
        ListCollisionWaypoint.clear();

        List<Painter<JXMapViewer>> painters = new ArrayList<>();

        for (Node node : graph) {
            if (!node.hasAttribute("ui.hide")) {
                Double latitude=this.listAeroport.getAeroportByCode(node.getId()).getLatitude().getDecimal();
                Double longitude=this.listAeroport.getAeroportByCode(node.getId()).getLongitude().getDecimal();
                GeoPosition position = new GeoPosition(latitude, longitude);
                DefaultWaypoint waypoint = new DefaultWaypoint(position);
                ListAeroportWaypoint.add(waypoint);
            }
        }

        WaypointPainter<DefaultWaypoint> waypointAeoroportPainter = new WaypointPainter<>();
        waypointAeoroportPainter.setRenderer(new AeroportWaypointRenderer());
        waypointAeoroportPainter.setWaypoints(ListAeroportWaypoint);
        painters.add(waypointAeoroportPainter);

        for (Edge edge : graph.getEachEdge()) {
                Node node1 = edge.getNode0();
                Node node2 = edge.getNode1();
                Double latitude1=this.listAeroport.getAeroportByCode(node1.getId()).getLatitude().getDecimal();
                Double longitude1=this.listAeroport.getAeroportByCode(node1.getId()).getLongitude().getDecimal();
                Double latitude2=this.listAeroport.getAeroportByCode(node2.getId()).getLatitude().getDecimal();
                Double longitude2=this.listAeroport.getAeroportByCode(node2.getId()).getLongitude().getDecimal();
                GeoPosition position1 = new GeoPosition(latitude1, longitude1);
                GeoPosition position2 = new GeoPosition(latitude2, longitude2);
                LineOverlayPainter line = new LineOverlayPainter(position1, position2);
                painters.add(line);
        }

        for (int i = 0; i < listVol.getList().size(); i++) {
            for (int j = i+1; j < listVol.getList().size(); j++) {
                //si les vols i et j sont en collision
                GeoPosition collisionPoint=(listVol.getVol(i).collision(listVol.getVol(j), 15));
                if (collisionPoint!=null) {
                    DefaultWaypoint waypoint = new DefaultWaypoint(collisionPoint);
                    ListCollisionWaypoint.add(waypoint);
                }
            }
        }

        WaypointPainter<DefaultWaypoint> waypointCollisionPainter = new WaypointPainter<>();
        waypointCollisionPainter.setRenderer(new CollisionWaypointRenderer());
        waypointCollisionPainter.setWaypoints(ListCollisionWaypoint);
        painters.add(waypointCollisionPainter);

        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        this.setOverlayPainter(compoundPainter);

    }

    class MouseClickAeroportListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            Point point = e.getPoint();
            Rectangle rect = new Rectangle(point.x - 8, point.y - 10, 16, 24);
            for (DefaultWaypoint waypoint : ListAeroportWaypoint) {
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
