package src.core;
//#region import
//import ArrayList object
import java.util.ArrayList;

import src.exception.ExceptionAnalyse;

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
public class ListeVol extends ArrayList<Vol>
{

    //#region constructeur

    /**
     * Constructeur de ListVol
     */
    public ListeVol() {
        super();
    }

    //#endregion

    //#region methode

    /**
     * renvoie le vol correspondant au code
     * @param String code
     */
    public Vol getVolByCode(String code) {
        for (Vol vol : this) {
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
     * @param fichier adresse du fichier
     * @param listAeroport liste des aeroports
     */
    public void remplir(String fichier, ListeAeroport listAeroport) throws ExceptionAnalyse ,IOException {
        ArrayList<ExceptionAnalyse> exceptions = new ArrayList<ExceptionAnalyse>();
        BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
        String line;
        //lecture des lignes
        int cpt = 0;
        while ((line = lecteur.readLine()) != null) {
            cpt++;
            String[] parties = line.split(";");
            if (parties.length != 6) {
                exceptions.add(new ExceptionAnalyse(cpt, "Le nombre de champs est incorrect"));
                continue;
            }
            //création du vol
            try {
                Vol vol = new Vol(parties[0],
                                    listAeroport.getAeroportByCode(parties[1]),
                                    listAeroport.getAeroportByCode(parties[2]),
                                    new Horaire(parties[3], parties[4]),
                                    parties[5]);
                //ajout du vol à la liste
                this.add(vol);
            } catch (IllegalArgumentException e) {
                exceptions.add(new ExceptionAnalyse(cpt, e.getMessage()));
            }
        }
        lecteur.close();
        if (exceptions.size() > 0) {
            throw new ExceptionAnalyse(fichier, exceptions);
        }
    }

    //#endregion

    //#region affichage

    /**
     * Affiche la liste des vols
     * @return Chaine de caractère
     */
    public String toString() {
        //[affiche un vol] saut de ligne .....
        String chaine = "";
        for (Vol vol : this) {
            chaine += vol.toString() + "\n\n";
        }
        return chaine;
    }
    
    //#endregion
}
