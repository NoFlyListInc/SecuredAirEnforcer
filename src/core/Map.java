package src.core;
//#region import
//import painter and waypoint objects
import src.ihm.AeroportWaypointRenderer;
import src.ihm.CollisionWaypointRenderer;
import src.ihm.LineOverlayPainter;
//import JXMapViewer objects
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
//import awt objects
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
//import swing objects
import javax.swing.JLabel;
//import graphstream objects
import org.graphstream.graph.Node;
import org.graphstream.graph.Edge;
//import util objects
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;
//#endregion

/**
 * Class Map
 * Permet de gérer une carte
 * @attributs ListAeroportWaypoint, ListCollisionWaypoint, listAeroport, listVols, label
 * @methodes Map, addInformation, getListAeroport, getListVols, setListAeroport, setListVols
 * @autor NOUVEL Armand
 */
public class Map extends JXMapViewer
{
    //#region attributs

    /**
     * liste des waypoints d'aeroports
     */
    private Set<DefaultWaypoint> ListAeroportWaypoint = new HashSet<>();

    /**
     * liste des waypoints de collisions
     */
    private Set<DefaultWaypoint> ListCollisionWaypoint = new HashSet<>();

    /**
     * liste des aeroports
     */
    private ListAeroport listAeroport;

    /**
     * liste des vols
     */
    private ListVol listVols;

    /**
     * label pour afficher les informations
     */
    private JLabel label;

    //#endregion

    //#region constructeur

    /**
     * Constructeur de Map
     * @param label, JLabel, label pour afficher les informations
     */
    public Map(JLabel label) {
        super();
        this.label = label;

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

    /**
     * retourne la liste des aeroports
     * @return listAeroport
     */
    public ListAeroport getListAeroport() {
        return listAeroport;
    }
    
    /**
     * retourne la liste des vols
     * @return listVols
     */
    public ListVol getListVols() {
        return listVols;
    }

    //#endregion

    //#region mutateurs

    /**
     * modifie la liste des aeroports
     * @param listAeroport
     */
    public void setListAeroport(ListAeroport listAeroport) {
        this.listAeroport=listAeroport;
    }

    /**
     * modifie la liste des vols
     * @param listVols
     */
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
    public void addInformation(ListAeroport listAeroport, ListVol listVol, int marge, int kmax) {
        //on met à jour les attributs
        this.listAeroport = listAeroport;
        this.listVols = listVol;
        //on crée le graph des aeroports
        Graph graphMap = new Graph("graphMap");
        graphMap.fillMap(listAeroport, listVol);
        graphMap.hideSoloNode();
        //on créée le graph des collisions
        Graph graphCollision = new Graph("graphCollision");
        graphCollision.fillVol(listVol, marge);
        graphCollision.dSature(kmax);
        // vide les listes de waypoint
        ListAeroportWaypoint.clear();
        ListCollisionWaypoint.clear();

        //contenant des éléments à dessiner
        List<Painter<JXMapViewer>> painters = new ArrayList<>();

        //créer les waypoint pour les aeroports
        for (Node node : graphMap) {
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
        for (Edge edge : graphMap.getEachEdge()) {
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
                //si les vols i et j sont en collision et de meme couleur
                GeoPosition collisionPoint=(listVol.getVol(i).collision(listVol.getVol(j), marge));
                if (collisionPoint!=null && graphCollision.getNode(listVol.getVol(i).getCode()).getAttribute("ui.style").equals(graphCollision.getNode(listVol.getVol(j).getCode()).getAttribute("ui.style"))) {
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

    /**
     * ActionListener pour les clics sur les aeroports
     */
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
                        label.setText(aeroport.toString());
                    }
                    break;
                }
            }
        }
    }

    /**
     * ActionListener pour les clics sur les collisions
     */
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
                                label.setText(listVols.getVol(i).getCode() + " <=> " + listVols.getVol(j).getCode());
                            }
                        }
                    }
                    break;
                }
                
            }
        }
    }
    
    //#endregion
}
