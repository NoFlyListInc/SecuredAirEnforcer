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
import java.awt.Color;
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
 * <h3>Classe CARTES</h3>
 * <p>La classe Carte permet de créer une carte avec des aéroports, des vols et des collisions.</p>
 *  <p>La carte est une carte OpenStreetMap avec des aéroports représentés par des triangles et des collisions représentées par des points.</p>
 * @extends JXMapViewer
 * @attributs ListeAeroportWaypoint, ListeCollisionWaypoint, listeAeroport, listeVols, label
 * @methodes Map, ajouterInformation, getListeAeroport, getListeVols
 * @autor NOUVEL Armand
 */
public class Carte extends JXMapViewer
{
    //#region attributs

    /**
     * liste des waypoints d'aeroports
     */
    private Set<DefaultWaypoint> ListeAeroportWaypoint = new HashSet<>();

    /**
     * liste des waypoints de collisions
     */
    private Set<DefaultWaypoint> ListeCollisionWaypoint = new HashSet<>();

    /**
     * liste des aeroports
     */
    private ListeAeroport listeAeroport;



    /**
     * liste des vols
     */
    private ListeVol listeVols;

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
    public Carte(JLabel label) {
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
     * @return listeAeroport
     */
    public ListeAeroport getListeAeroport() {
        return listeAeroport;

    }
    
    /**
     * retourne la liste des vols
     * @return listeVols
     */
    public ListeVol getListeVols() {
        return listeVols;
    }

    //#endregion

    //#region methodes

    /**
     * rajoute les aeroports, les vols et les collisions sur la carte
     * @param listeVol
     */
    public void ajouterInformation(ListeAeroport listeAeroport, ListeVol listeVol, int marge, int kmax, int hauteur) {
        //on met à jour les attributs
        this.listeVols = listeVol;
        this.listeAeroport = listeAeroport;
        //on crée le graph des aeroports

        Graphe carteGraph = new Graphe("graphMap");
        carteGraph.remplirCarte(listeAeroport, listeVol);
        carteGraph.cacherNoeudSeul();
        //on crée le graph des collisions
        Graphe grapheCollision = new Graphe("graphCollision");
        grapheCollision.remplirAvecListeVol(listeVol, marge);
        grapheCollision.setKdonne(kmax);
        grapheCollision.dSature();
        ArrayList<String> colorList = new ArrayList<String>();
        for (Node noeud : grapheCollision.getEachNode()) {
            if (!colorList.contains(noeud.getAttribute("ui.style"))) {
                colorList.add(noeud.getAttribute("ui.style"));
            }
        }
        // vide les listes de waypoint
        ListeAeroportWaypoint.clear();
        ListeCollisionWaypoint.clear();

        //contenant des éléments à dessiner
        List<Painter<JXMapViewer>> peintres = new ArrayList<>();

        //créer les waypoint pour les aeroports

        for (Node noeud : carteGraph) {
            if (!noeud.hasAttribute("ui.hide")) {
                Double latitude=this.listeAeroport.getAeroportByCode(noeud.getId()).getLatitude().getDecimal();
                Double longitude=this.listeAeroport.getAeroportByCode(noeud.getId()).getLongitude().getDecimal();
                GeoPosition position = new GeoPosition(latitude, longitude);
                DefaultWaypoint waypoint = new DefaultWaypoint(position);
                ListeAeroportWaypoint.add(waypoint);
            }
        }

        //défini le design des waypoint et l'ajoute a painters
        WaypointPainter<DefaultWaypoint> waypointAeoroportPainter = new WaypointPainter<>();
        waypointAeoroportPainter.setRenderer(new AeroportWaypointRenderer());
        waypointAeoroportPainter.setWaypoints(ListeAeroportWaypoint);
        peintres.add(waypointAeoroportPainter);

        //si hauteur trop grand on affiche que les aeroports
        if(hauteur > colorList.size()) {
            CompoundPainter<JXMapViewer> compositeur = new CompoundPainter<>(peintres);
            this.setOverlayPainter(compositeur);
            return;
        }


        //créer des droites pour les vols
        for (Edge arrete : carteGraph.getEachEdge()) {
            if(hauteur == 0 || grapheCollision.getNode(arrete.getId()).getAttribute("ui.style").equals(colorList.get(hauteur-1))) {
                Node noeud1 = arrete.getNode0();
                Node noeud2 = arrete.getNode1();
                Double latitude1=this.listeAeroport.getAeroportByCode(noeud1.getId()).getLatitude().getDecimal();
                Double longitude1=this.listeAeroport.getAeroportByCode(noeud1.getId()).getLongitude().getDecimal();
                Double latitude2=this.listeAeroport.getAeroportByCode(noeud2.getId()).getLatitude().getDecimal();
                Double longitude2=this.listeAeroport.getAeroportByCode(noeud2.getId()).getLongitude().getDecimal();
                GeoPosition position1 = new GeoPosition(latitude1, longitude1);
                GeoPosition position2 = new GeoPosition(latitude2, longitude2);
                LineOverlayPainter ligne = new LineOverlayPainter(position1, position2);
                int indiceCouleur = colorList.indexOf(grapheCollision.getNode(arrete.getId()).getAttribute("ui.style"));
                if(kmax > 1) {
                    float hsb = (float)(indiceCouleur+1)/(float)(colorList.size()+1);
                    ligne.setColor(new Color(Color.HSBtoRGB(hsb, 0.9f, 0.6f)));
                }
                peintres.add(ligne);
            }
        }

        //créer les waypoint pour les collisions
        for (int i = 0; i < listeVol.size(); i++) {
            for (int j = i+1; j < listeVol.size(); j++) {
                if (hauteur == 0 || grapheCollision.getNode(listeVol.get(i).getCode()).getAttribute("ui.style").equals(colorList.get(hauteur-1)) && grapheCollision.getNode(listeVol.get(j).getCode()).getAttribute("ui.style").equals(colorList.get(hauteur-1))) {
                    //si les vols i et j sont en collision et de meme couleur
                    GeoPosition pointDeCollision=(listeVol.get(i).collision(listeVol.get(j), marge));
                    if (pointDeCollision!=null && grapheCollision.getNode(listeVol.get(i).getCode()).getAttribute("ui.style").equals(grapheCollision.getNode(listeVol.get(j).getCode()).getAttribute("ui.style"))) {
                        DefaultWaypoint waypoint = new DefaultWaypoint(pointDeCollision);
                        ListeCollisionWaypoint.add(waypoint);
                    }
                }
            }
        }

        //défini le design des waypoint est l'ajoute à painters
        WaypointPainter<DefaultWaypoint> waypointCollisionPainter = new WaypointPainter<>();
        waypointCollisionPainter.setRenderer(new CollisionWaypointRenderer());
        waypointCollisionPainter.setWaypoints(ListeCollisionWaypoint);
        peintres.add(waypointCollisionPainter);

        //affiche sur la carte
        CompoundPainter<JXMapViewer> compositeur = new CompoundPainter<>(peintres);
        this.setOverlayPainter(compositeur);

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
            for (DefaultWaypoint waypoint : ListeAeroportWaypoint) {
                Point2D waypointPosition = convertGeoPositionToPoint(waypoint.getPosition());
                if (rect.contains(waypointPosition)) {
                    // Récupérer l'aéroport correspondant au waypoint
                    Aeroport aeroport = listeAeroport.getAeroportByPosition(waypoint.getPosition());
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
            for (DefaultWaypoint waypoint : ListeCollisionWaypoint) {
                Point2D waypointPosition = convertGeoPositionToPoint(waypoint.getPosition());
                if (rect.contains(waypointPosition)) {
                    for(int i=0; i<listeVols.size(); i++){
                        //parcours les vols pour trouver la collision
                        for(int j=i+1; j<listeVols.size(); j++){
                            GeoPosition collisionPoint=(listeVols.get(i).collision(listeVols.get(j), 15));
                            if(collisionPoint!=null && collisionPoint.equals(waypoint.getPosition())){
                                // Afficher les informations sur la collision
                                label.setText(listeVols.get(i).getCode() + " <=> " + listeVols.get(j).getCode());
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
