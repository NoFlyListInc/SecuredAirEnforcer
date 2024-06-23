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
public class ListVol extends ArrayList<Vol>
{

    //#region constructeur

    /**
     * Constructeur de ListVol
     */
    public ListVol() {
        super();
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
                this.add(vol);
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
        for (Vol vol : this) {
            str += vol.toString() + "\n\n";
        }
        return str;
    }
    
    //#endregion
}
