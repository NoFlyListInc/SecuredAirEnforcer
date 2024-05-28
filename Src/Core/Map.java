package Src.Core;
//import JXMapViewer objects
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.input.ZoomMouseWheelListenerCursor;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;

import Src.Interface.AeroportWaypointRenderer;
import Src.Interface.CollisionWaypointRenderer;
import Src.Interface.LineOverlayPainter;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.painter.Painter;
import org.jxmapviewer.painter.CompoundPainter;
//import awt objects
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
//import graphstream objects
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
//import util objects
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
//#endregion


public class Map extends JXMapViewer
{
    //#region attributs
    private Set<DefaultWaypoint> ListAeroportWaypoint = new HashSet<>();
    private Set<DefaultWaypoint> ListCollisionWaypoint = new HashSet<>();
    private ListAeroport listAeroport;
    private ListVol listVols;
    //#endregion

    //#region constructeur
    public Map() {
        super();

        //connection à OpenStreetMap
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        this.setTileFactory(tileFactory);
        //position et zoom par defaut
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
        addMouseListener(new MouseClickCollisionListener());
    }
    //#endregion

    //#region accesseurs
    public ListAeroport getListAeroport() {
        return listAeroport;
    }

    public ListVol getListVols() {
        return listVols;
    }
    //#endregion

    //#region mutateurs
    public void setListAeroport(ListAeroport listAeroport) {
        this.listAeroport=listAeroport;
    }

    public void setListVols(ListVol listVols) {
        this.listVols=listVols;
    }
    //#endregion

    //#region methodes

    /**
     * rajoute les aeroports, les vols et les collisions sur la carte
     * @param listAeroport
     * @param listVol
     */
    public void addInformation(ListAeroport listAeroport, ListVol listVol) {
        //on met à jour les attributs
        this.listAeroport = listAeroport;
        this.listVols = listVol;
        //on crée le graph des aeroports
        Graph graph = new Graph("graph");
        graph.fillMap(listAeroport, listVol);
        graph.hideSoloNode();
        // vide les listes de waypoint
        ListAeroportWaypoint.clear();
        ListCollisionWaypoint.clear();

        //contenant des éléments à dessiner
        List<Painter<JXMapViewer>> painters = new ArrayList<>();

        //créer les waypoint pour les aeroports
        for (Node node : graph) {
            if (!node.hasAttribute("ui.hide")) {
                Double latitude=this.listAeroport.getAeroportByCode(node.getId()).getLatitude().getDecimal();
                Double longitude=this.listAeroport.getAeroportByCode(node.getId()).getLongitude().getDecimal();
                GeoPosition position = new GeoPosition(latitude, longitude);
                DefaultWaypoint waypoint = new DefaultWaypoint(position);
                ListAeroportWaypoint.add(waypoint);
            }
        }

        //défini le design des waypoint et l'ajoute a painters
        WaypointPainter<DefaultWaypoint> waypointAeoroportPainter = new WaypointPainter<>();
        waypointAeoroportPainter.setRenderer(new AeroportWaypointRenderer());
        waypointAeoroportPainter.setWaypoints(ListAeroportWaypoint);
        painters.add(waypointAeoroportPainter);

        //créer des droites pour les vols
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

        //créer les waypoint pour les collisions
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

        //défini le design des waypoint est l'ajoute à painters
        WaypointPainter<DefaultWaypoint> waypointCollisionPainter = new WaypointPainter<>();
        waypointCollisionPainter.setRenderer(new CollisionWaypointRenderer());
        waypointCollisionPainter.setWaypoints(ListCollisionWaypoint);
        painters.add(waypointCollisionPainter);

        //affiche sur la carte
        CompoundPainter<JXMapViewer> compoundPainter = new CompoundPainter<>(painters);
        this.setOverlayPainter(compoundPainter);

    }
    //#endregion

    //#region listener class
    class MouseClickAeroportListener extends MouseAdapter {
        //click sur un aeroport
        public void mouseClicked(MouseEvent e) {
            //on recupere la position du click
            Point point = e.getPoint();
            //on crée un rectangle autour du click
            Rectangle rect = new Rectangle(point.x - 8, point.y - 10, 16, 24);
            
            //on cherche si un waypoint d'aeroport est dans le rectangle
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

    class MouseClickCollisionListener extends MouseAdapter {
        //click sur une collision
        public void mouseClicked(MouseEvent e) {
            //on recupere la position du click
            Point point = e.getPoint();
            //on crée un rectangle autour du click
            Rectangle rect = new Rectangle(point.x - 8, point.y - 10, 16, 24);
            
            //on cherche si un waypoint de collision est dans le rectangle
            for (DefaultWaypoint waypoint : ListCollisionWaypoint) {
                Point2D waypointPosition = convertGeoPositionToPoint(waypoint.getPosition());
                if (rect.contains(waypointPosition)) {
                    for(int i=0; i<listVols.getList().size(); i++){
                        //parcours les vols pour trouver la collision
                        for(int j=i+1; j<listVols.getList().size(); j++){
                            GeoPosition collisionPoint=(listVols.getVol(i).collision(listVols.getVol(j), 15));
                            if(collisionPoint!=null && collisionPoint.equals(waypoint.getPosition())){
                                // Afficher les informations sur la collision
                                System.out.println("Collision entre les vols : \n" + listVols.getVol(i) + "\n et \n" + listVols.getVol(j));
                            }
                        }
                    }
                }
                break;
            }
        }
    }
    //#endregion
}
