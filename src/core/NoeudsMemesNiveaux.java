package src.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.graphstream.graph.Node;

public class NoeudsMemesNiveaux {
    private ArrayList<HashMap<Node, Node>> noeudsMemesNiveaux;
    private ListeVol listVol;

   //Constructeur
    public NoeudsMemesNiveaux() {
        this.noeudsMemesNiveaux = new ArrayList<HashMap<Node, Node>> ();
        this.listVol = new ListeVol();
    }

    public NoeudsMemesNiveaux(ListeVol listVol) {
        this.noeudsMemesNiveaux = new ArrayList<HashMap<Node, Node>> ();
        this.listVol = listVol;
    }

    //Getters
    public ArrayList<HashMap<Node, Node>>  getNoeudsMemesNiveaux() {
        return this.noeudsMemesNiveaux;
    }
    public ListeVol getListVol() {
        return this.listVol;
    }

    //Setters
    public void setListVol(ListeVol listVol) {
        this.listVol = listVol;
    }

    //Méthodes
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
     * Retourne la taille de la liste des vols en risque de collision
     * @return
     */
    public int taille() {
        return this.noeudsMemesNiveaux.size();
    }

    /**
     * Fonction qui indique si une paire de vol est déja dans la liste des vols en risque de collision
     * @param vol1
     * @param vol2
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