package src.core;

import java.util.ArrayList;
import java.util.HashMap;

import org.graphstream.graph.Node;

public class VolsMemesNiveaux {
    private ArrayList<HashMap<Vol, Vol>> volsMemesNiveaux;
    private ListVol listVol;

   //Constructeur
    public VolsMemesNiveaux() {
        this.volsMemesNiveaux = new ArrayList<HashMap<Vol, Vol>>();
        this.listVol = new ListVol();   
    }

    public VolsMemesNiveaux(ListVol listVol) {
        this.volsMemesNiveaux = new ArrayList<HashMap<Vol, Vol>>();
        this.listVol = listVol;
    }

    //Getters
    public ArrayList<HashMap<Vol, Vol>> getVolsMemesNiveaux() {
        return this.volsMemesNiveaux;
    }
    public ListVol getListVol() {
        return this.listVol;
    }

    //Setters
    public void setListVol(ListVol listVol) {
        this.listVol = listVol;
    }

    //Méthodes
    public String toString() {
        String str = "";
        for (HashMap<Vol, Vol> paire : this.volsMemesNiveaux) {
            for (Vol vol1 : paire.keySet()) {
                for (Vol vol2 : paire.values()) {
                    str += vol1 + " et " + vol2 + "\n";
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
     * @param vol1 premier vol
     * @param vol2 deuxième vol, qui est en risque de collision avec le premier
     */
    public void gestionNiveauMaxAtteint(Vol vol1, Vol vol2) {
        HashMap<Vol, Vol> paireCollision = new HashMap<Vol, Vol>();
        paireCollision.put(vol1, vol2);
        if (!this.contient(vol1, vol2)) {
            this.volsMemesNiveaux.add(paireCollision);
        }
    }

    public Vol getVolDepuisNoeud(Node node, ListVol listVol) {
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
        return this.volsMemesNiveaux.size();
    }

    /**
     * Fonction qui indique si une paire de vol est déja dans la liste des vols en risque de collision
     * @param vol1
     * @param vol2
     * @return boolean
     */
    public boolean contient(Vol vol1, Vol vol2) {
        for (HashMap<Vol, Vol> paire : this.volsMemesNiveaux) {
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