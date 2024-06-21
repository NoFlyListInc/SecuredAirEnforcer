package src.core;
//#region import
//import ArrayList object
import java.util.ArrayList;

import src.exception.ParseException;

//import reader files tools
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//#endregion

/**
 * Class ListVol
 * Permet de gérer une liste de vols
 * @attributs vols
 * @methodes addVol, removeVol, getVol, getVolByCode, fill, toString
 * @autor NOUVEL Armand
 */
public class ListVol 
{
    //#region

    /**
     * ArrayList des vols
     */
    private ArrayList<Vol> list;

    //#endregion

    //#region constructeur

    /**
     * Constructeur de ListVol
     */
    public ListVol() {
        this.list = new ArrayList<Vol>();
    }

    //#endregion

    //#region accesseurs

    /**
     * Retourne la liste des vols
     * @return ArrayList des vols
     */
    public ArrayList<Vol> getList() {
        return this.list;
    }


    //#endregion

    //#region méthodes

    /**
     * Ajoute un vol à la liste
     * @param vol vol à ajouter
     * @return true si l'ajout a été effectué, sinon false
     */
    public boolean addVol(Vol vol) {
        //si le vol n'est pas null et n'est pas déjà dans la liste
        if (vol!=null && this.list.contains(vol)==false) {
            this.list.add(vol);
            return true;
        }
        return false;
    }

    /**
     * Supprime un vol de la liste
     * @param vol vol à supprimer
     * @return true si la suppression a été effectuée, sinon false
     */
    public boolean removeVol(Vol vol) {
        //si le vol n'est pas null et est dans la liste
        if (vol!=null && this.list.contains(vol)) {
            this.list.remove(vol);
            return true;
        }
        return false;
    }

    /**
     * Retourne un vol de la liste à partir de son index
     * @param i index du vol
     * @return le vol recherché si l'index est valide, sinon null
     */
    public Vol getVol(int i) {
        //si l'index est valide
        if (i >= 0 && i < this.list.size()) {
            return this.list.get(i);
        }
        return null;
    }

    /**
     * Retourne un vol de la liste à partir de son code
     * @param code code du vol
     * @return le vol recherché si le code est valide, sinon null
     */
    public Vol getVolByCode(String code) {
        //parcours de la liste
        for (Vol vol : this.list) {
            //si le code correspond
            if (vol.getCode().equals(code)) {
                return vol;
            }
        }
        return null;
    }
    //#endregion

    //#region fill with a file

    /**
     * Rempli la liste des vols à partir d'un fichier vol-testxx.csv
     * @param file adresse du fichier
     * @param listAeroport liste des aeroports
     */
    public void fill(String file, ListAeroport listAeroport) throws ParseException ,IOException {
        ArrayList<ParseException> exceptions = new ArrayList<ParseException>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        //lecture des lignes
        int cpt = 0;
        while ((line = reader.readLine()) != null) {
            cpt++;
            String[] parts = line.split(";");
            if (parts.length != 6) {
                exceptions.add(new ParseException(cpt, "Le nombre de champs est incorrect"));
                continue;
            }
            //création du vol
            try {
                Vol vol = new Vol(parts[0],
                                    listAeroport.getAeroportByCode(parts[1]),
                                    listAeroport.getAeroportByCode(parts[2]),
                                    new Horaire(parts[3], parts[4]),
                                    parts[5]);
                //ajout du vol à la liste
                this.addVol(vol);
            } catch (IllegalArgumentException e) {
                exceptions.add(new ParseException(cpt, e.getMessage()));
            }
        }
        reader.close();
        if (exceptions.size() > 0) {
            throw new ParseException(file, exceptions);
        }
    }

    //#endregion

    //#region affichage

    /**
     * Affiche la liste des vols
     * @return String
     */
    public String toString() {
        //[affiche un vol] saut de ligne .....
        String str = "";
        for (Vol vol : this.list) {
            str += vol.toString() + "\n\n";
        }
        return str;
    }
    
    //#endregion
}
