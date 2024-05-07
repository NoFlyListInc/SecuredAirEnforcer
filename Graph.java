import org.graphstream.graph.implementations.SingleGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import org.graphstream.graph.Node;


public class Graph extends SingleGraph
{
    //#region attribut
    private int kmax;
    //#endregion

    //#region constructeur
    public Graph(String id) {
        super(id);
    }
    //#endregion

    //#region accesseurs
    public int getKmax() {
        return this.kmax;
    }
    //#endregion

    //#region mutateurs
    public void setKmax(int kmax) {
        this.kmax = kmax;
    }
    //#endregion

    //#region coloration
    public void coloration() {
        this.getNode("1").setAttribute("ui.style", "fill-color: red;");
    }
    //#endregion

    //#region fill the graph

    /**
     * Rempli le graph depuis un fichier graph-testx.txt
     * @param file adresse du fichier
     */
    public void fillFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            //première ligne => kmax
            this.kmax = Integer.parseInt(reader.readLine());
            //deuxième ligne => nombre de noeuds
            int nbr_noeuds = Integer.parseInt(reader.readLine());
            //creation des noeuds
            for (int i = 1; i <= nbr_noeuds; i++) {
                this.addNode(Integer.toString(i));
            }
            //creations des arretes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                this.addEdge(parts[0]+","+parts[1], parts[0], parts[1]);
            }
            reader.close();
        } catch (IOException e) { //erreur de lecture du fichier
            e.printStackTrace();
        }
    }

    /**
     * Rempli le graph des collisions depuis une liste de vol
     * @param listVol objet ListVol
     */
    public void fillVol(ListVol listVol) {
        //creation des noeuds
        for (Vol vol : listVol.getList()) {
            this.addNode(vol.getCode()).addAttribute("ui.label", vol.getCode()); //ajout du label
        }
        //creation des arretes
        for (int i = 0; i < listVol.getList().size(); i++) {
            for (int j = i+1; j < listVol.getList().size(); j++) {
                //si les vols i et j sont en collision
                if (listVol.getVol(i).collision(listVol.getVol(j), 15)) {
                    this.addEdge(listVol.getVol(i).getCode()+","+listVol.getVol(j).getCode(), listVol.getVol(i).getCode(), listVol.getVol(j).getCode()); //code de l'arrete = "codei,codej"
                }
            }
        }
    }

    /**
     * Rempli le graph des aeroports et des vols pour une représentation géographique
     * @param listAeroport objet ListAeroport
     * @param listVol objet ListVol
     */
    public void fillMap(ListAeroport listAeroport, ListVol listVol) {
        //creation des noeuds
        for (Aeroport aeroport : listAeroport.getList()) {
            Node noeud=this.addNode(aeroport.getCode());
            noeud.addAttribute("ui.label", aeroport.getCode()); //ajout du label
            noeud.setAttribute("xyz", aeroport.getx(), -aeroport.gety(), 0); //ajout des coordonnées
        }
        //creation des arretes
        for (Vol vol : listVol.getList()) {
            //si l'arrete n'existe pas on l'ajoute
            if (this.getEdge(vol.getDepart().getCode()+","+vol.getArrivee().getCode())==null && this.getEdge(vol.getArrivee().getCode()+","+vol.getDepart().getCode())==null) {
                this.addEdge(vol.getDepart().getCode()+","+vol.getArrivee().getCode(), vol.getDepart().getCode(), vol.getArrivee().getCode()); //code de l'arrete = "codedepart,codearrivee"
            }
        }
    }
    //#endregion

    //#region methode

    /**
     * rend non visible les noeuds de degré 0
     */
    public void hideSoloNode() {
        //parcours de tous les noeuds
        for (Node node : this.getEachNode()) {
            //si le noeud est de degré 0 on le rend non visible
            if (node.getDegree() == 0) {
                node.addAttribute("ui.hide");
            }
        }
    }
    //#endregion
}
