package src.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.graphstream.graph.Node;

/**
 * Cette classe permet de représenter les noeuds qui sont sur le même niveau
 * @see Graphe
 * @author LACROIX Xavier
 * @version 1.0
 */
public class NoeudsMemesNiveaux {
    private ArrayList<HashMap<Node, Node>> noeudsMemesNiveaux;
    private ListeVol listVol;

    /**
     * Constructeur vide de NoeudsMemesNiveaux
     */
    public NoeudsMemesNiveaux() {
        this.noeudsMemesNiveaux = new ArrayList<HashMap<Node, Node>> ();
        this.listVol = new ListeVol();
    }

    /**
     * Constructeur de NoeudsMemesNiveaux
     * @param listVol ListeVol
     */
    public NoeudsMemesNiveaux(ListeVol listVol) {
        this.noeudsMemesNiveaux = new ArrayList<HashMap<Node, Node>> ();
        this.listVol = listVol;
    }

    /**
     * Renvoie la liste des noeuds qui sont sur le même niveau
     * @return ArrayList<HashMap<Node, Node>>
     */
    public ArrayList<HashMap<Node, Node>>  getNoeudsMemesNiveaux() {
        return this.noeudsMemesNiveaux;
    }

    /**
     * Renvoie la liste des vols
     * @return ListeVol
     */
    public ListeVol getListVol() {
        return this.listVol;
    }

    //Setters

    /**
     * Modifie la liste de vols
     * @param listVol ListeVol
     */
    public void setListVol(ListeVol listVol) {
        this.listVol = listVol;
    }

    //Méthodes

    /**
     * Affiche les vols aux meme niveau sous forme de chaine de caractère
     * @return String
     */
    public String toString() {
        String str = "";
        for (HashMap<Node, Node> paire : this.noeudsMemesNiveaux) {
            for (Node noeud1 : paire.keySet()) {
                for (Node noeud2 : paire.values()) {
                    str += noeud1 + " et " + noeud2 + "\n";
                }
            }
        }
        return str;
    }
    /**
     * La fonction ajoute à volsMemeNiveaux une paire de vol si elle est en risque
     * de collision
     * et que kmax à été atteint
     * 
     * @param noeud1 premier vol
     * @param noeud2 deuxième vol, qui est en risque de collision avec le premier
     */
    public void gestionNiveauMaxAtteint(Node noeud1, Node noeud2) {
        HashMap<Node, Node> paireCollision = new HashMap<Node, Node>();
        paireCollision.put(noeud1, noeud2);
        if (!this.contient(noeud1, noeud2)) {
            this.noeudsMemesNiveaux.add(paireCollision);
        }
    }

    /**
     * Renvoie le vol depuis un noeud
     * @param node
     * @param listVol
     * @return Vol
     */
    public Vol getVolDepuisNoeud(Node node, ListeVol listVol) {
        // parcours de la liste des vols
        for (Vol vol : listVol) {
            // si le code du vol est égal à l'id du noeud
            if (vol.getCode().equals(node.getId())) {
                return vol;
            }
        }
        return null;
    }

    /**
     * Renvoie la taille de la liste des vols en risque de collision
     * @return int
     */
    public int taille() {
        return this.noeudsMemesNiveaux.size();
    }

    /**
     * Fonction qui indique si une paire de vol est déja dans la liste des vols en risque de collision
     * @param vol1 Node
     * @param vol2 Node
     * @return boolean
     */
    public boolean contient(Node vol1, Node vol2) {
        for (HashMap<Node, Node> paire : this.noeudsMemesNiveaux) {
            if (paire.containsKey(vol1) && paire.containsValue(vol2)) {
                return true;
            }
            if (paire.containsKey(vol2) && paire.containsValue(vol1)) {
                return true;
            }
        }
        return false;
    }
}