package Src.Core;
//#region import
//import graphstream tools
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
//import reader files tools 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//#endregion

/**
 * Classe Graph héritant de SingleGraph, permet de gérer un graph
 * @attribut kmax
 * @methodes fillFile, fillVol, fillMap, hideSoloNode, coloration
 * @extends SingleGraph
 * @autor NOUVEL Armand et LACROIX Xavier
 */
public class Graph extends SingleGraph
{
    //#region attribut
    private int kmax;
    //#endregion

    //#region constructeur

    /**
     * Constructeur de Graph
     * @param id String
     */
    public Graph(String id) {
        super(id);
    }

    //#endregion

    //#region accesseurs

    /**
     * Renvoie la valeur de kmax
     * @return int
     */
    public int getKmax() {
        return this.kmax;
    }

    //#endregion

    //#region mutateurs

    /**
     * Modifie la valeur de kmax
     * @param kmax int
     */
    public void setKmax(int kmax) {
        this.kmax = kmax;
    }

    //#endregion

    //#region coloration

    public void coloration() {
        this.getNode(1).setAttribute("ui.style", "fill-color: red;");
    }

    //#endregion

    //#region fill the graph

    /**
     * Remplit le graph depuis un fichier graph-testx.txt
     * @param file String, adresse du fichier
     * @throws IOException si le fichier n'existe pas
     */
    public void fillFile(String file) {
        this.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            //première ligne => kmax
            this.kmax = Integer.parseInt(reader.readLine());
            //deuxième ligne => nombre de noeuds
            int nbr_noeuds = Integer.parseInt(reader.readLine());
            //creation des noeuds
            for (int i = 1; i <= nbr_noeuds; i++) {
                this.addNode(Integer.toString(i)).addAttribute("ui.label", i); //ajout du label
            }
            //creations des arretes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if(this.getEdge(parts[0]+","+parts[1]) == null && this.getEdge(parts[1]+","+parts[0])
                 == null) { //si l'arrete n'existe pas on l'ajoute
                    this.addEdge(parts[0]+","+parts[1], parts[0], parts[1]);
                }
            }
            reader.close();
        } catch (IOException e) { //erreur de lecture du fichier
            e.printStackTrace();
        }
    }

    /**
     * Remplit le graph des collisions depuis une liste de vol
     * @param listVol ListVol
     */
    public void fillVol(ListVol listVol) {
        this.clear();
        //creation des noeuds
        for (Vol vol : listVol.getList()) {
            this.addNode(vol.getCode()).addAttribute("ui.label", vol.getCode()); //ajout du label
        }
        //creation des arretes
        for (int i = 0; i < listVol.getList().size()-1; i++) {
            for (int j = i+1; j < listVol.getList().size(); j++) {
                //si les vols i et j sont en collision
                if ((listVol.getVol(i).collision(listVol.getVol(j), 15))!=null) {
                    this.addEdge(listVol.getVol(i).getCode()+","+listVol.getVol(j).getCode(), listVol.getVol(i).getCode(), listVol.getVol(j).getCode()); //code de l'arrete = "codei,codej"
                }
            }
        }
    }

    /**
     * Remplit le graph des aeroports reliés par des vols pour une représentation sur une carte
     * @param listAeroport ListAeroport
     * @param listVol ListVol
     */
    public void fillMap(ListAeroport listAeroport, ListVol listVol) {
        this.clear();
        //creation des noeuds
        for (Aeroport aeroport : listAeroport.getList()) {
            Node noeud=this.addNode(aeroport.getCode());
            noeud.addAttribute("ui.label", aeroport.getCode()); //ajout du label
        }
        //creation des arretes
        for (Vol vol : listVol.getList()) {
            //si l'arrete n'existe pas on l'ajoute
            if (this.getNode(vol.getDepart().getCode()).getEdgeBetween(vol.getArrivee().getCode()) == null){
                this.addEdge(vol.getCode(), vol.getDepart().getCode(), vol.getArrivee().getCode()); //code de l'arrete = code du vol
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
