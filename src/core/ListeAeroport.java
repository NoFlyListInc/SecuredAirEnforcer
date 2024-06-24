package src.core;
//#region import
//import exception
import src.exception.ExceptionAnalyse;
//import ArrayList object
import java.util.ArrayList;
//import reader files tools
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//import GeoPosition object
import org.jxmapviewer.viewer.GeoPosition;
//#endregion


/**
 * Class ListAeroport
 * Permet de gérer une liste d'aeroports
 * @attributs list
 * @methodes addAeroport, removeAeroport, getAeroport, getAeroportByCode, getAeroportByPosition, fill, toString
 * @autor NOUVEL Armand
 */
public class ListeAeroport extends ArrayList<Aeroport>
{
    //#region constructeur

    /**
     * Constructeur de ListAeroport
     */
    public ListeAeroport() {
        super();
    }

    //#endregion

    //#region méthodes

    /**
     * Retourne un aeroport de la liste à partir de son code
     * @param code code de l'aeroport
     * @return l'aeroport recherché si le code est valide, sinon null
     */
    public Aeroport getAeroportByCode(String code) {
        // si le code est null ou vide
        if (code == null || code.isEmpty()) {
            return null;
        }
        //parcours de la liste
        for (Aeroport aeroport : this) {
            //si le code est trouvé
            if (aeroport.getCode().equals(code)) {
                return aeroport;
            }
        }
        return null;
    }

    /**
     * Retourne un aeroport de la liste à partir de sa position
     * @param position position de l'aeroport
     * @return l'aeroport recherché si la position est valide, sinon null
     */
    public Aeroport getAeroportByPosition(GeoPosition position) {
        //parcours de la liste
        for (Aeroport aeroport : this) {
            //si la position est trouvée
            if (aeroport.getLatitude().getDecimal()==position.getLatitude() && aeroport.getLongitude().getDecimal()==position.getLongitude()) {
                return aeroport;
            }
        }
        return null;
    }
    //#endregion

    //#region fill with a file

    /**
     * Rempli la liste des aeroports depuis un fichier
     * @param fichier adresse du fichier
     * @throws IOException erreur de lecture du fichier
     * @throws ExceptionAnalyse erreur dans les données du fichier
     */
    public void fill(String fichier) throws ExceptionAnalyse, IOException {
        ArrayList<ExceptionAnalyse> exceptions = new ArrayList<ExceptionAnalyse>();
        BufferedReader lecteur = new BufferedReader(new FileReader(fichier));
        String ligne;
        //parcours du fichier ligne par ligne
        int cpt = 0;
        while ((ligne = lecteur.readLine()) != null) {
            cpt++;
            String[] parties = ligne.split(";");
            if (parties.length != 10) {
                exceptions.add(new ExceptionAnalyse(cpt, "Nombre de champs incorrect"));
                continue;
            }
            try {
                //création de l'aeroport
                Aeroport aeroport = new Aeroport(parties[0], parties[1], 
                        new Coordonnee(parties[2], parties[3], parties[4], parties[5]),
                        new Coordonnee(parties[6], parties[7], parties[8], parties[9]));
                //ajout de l'aeroport à la liste
                this.add(aeroport);
            } catch(IllegalArgumentException e) {
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
     * Affiche la liste des aeroports
     * @return String
     */
    public String toString() {
        //[affiche un aeroport] saut de ligne .....
        StringBuilder constrChaines = new StringBuilder();
        for (Aeroport aeroport : this) {
            constrChaines.append(aeroport.toString()).append("\n\n");
        }
        return constrChaines.toString();
    }
    //#endregion
}
