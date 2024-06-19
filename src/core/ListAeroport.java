package src.core;
//#region import
//import exception
import src.exception.ParseException;
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
public class ListAeroport 
{
    //#region attribut

    /**
     * ArrayList des aeroports
     */
    private ArrayList<Aeroport> list;

    //#endregion

    //#region constructeur

    /**
     * Constructeur de ListAeroport
     */
    public ListAeroport() {
        this.list = new ArrayList<Aeroport>();
    }

    //#endregion

    //#region accesseurs

    /**
     * Retourne la liste des aeroports
     * @return ArrayList des aeroports
     */
    public ArrayList<Aeroport> getList() {
        return this.list;
    }

    //#endregion

    //#region méthodes

    /**
     * Ajoute un aeroport à la liste
     * @param aeroport Aeroport à ajouter
     * @return true si l'ajout a été effectué, sinon false
     */
    public boolean addAeroport(Aeroport aeroport) {
        //si l'aeroport n'est pas null et n'est pas déjà dans la liste
        if (aeroport!=null && this.list.contains(aeroport)==false) {
            return this.list.add(aeroport);
        }
        return false;
    }

    /**
     * Supprime un aeroport de la liste
     * @param aeroport Aeroport à supprimer
     * @return true si la suppression a été effectuée, sinon false
     */
    public boolean removeAeroport(Aeroport aeroport) {
        //si l'aeroport n'est pas null et est dans la liste
        if (aeroport!=null && this.list.contains(aeroport)) {
            return this.list.remove(aeroport);
        }
        return false;
    }

    /**
     * Retourne un aeroport de la liste à partir de son index
     * @param i index de l'aeroport
     * @return l'aeroport recherché si l'index est valide, sinon null
     */
    public Aeroport getAeroport(int i) {
        //si l'index est valide
        if (i >= 0 && i < this.list.size()) {
            return this.list.get(i);
        }
        return null;
    }

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
        for (Aeroport aeroport : this.list) {
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
        for (Aeroport aeroport : this.list) {
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
     * @param file adresse du fichier
     * @throws IOException erreur de lecture du fichier
     * @throws ParseException erreur dans les données du fichier
     */
    public void fill(String file) throws ParseException, IOException {
        ArrayList<ParseException> exceptions = new ArrayList<ParseException>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        //parcours du fichier ligne par ligne
        int cpt = 0;
        while ((line = reader.readLine()) != null) {
            cpt++;
            String[] parts = line.split(";");
            if (parts.length != 10) {
                exceptions.add(new ParseException(file, cpt, "Nombre de champs incorrect"));
                continue;
            }
            try {
                //création de l'aeroport
                Aeroport aeroport = new Aeroport(parts[0], parts[1], 
                        new Coordonnee(parts[2], parts[3], parts[4], parts[5]),
                        new Coordonnee(parts[6], parts[7], parts[8], parts[9]));
                //ajout de l'aeroport à la liste
                this.addAeroport(aeroport);
            } catch(IllegalArgumentException e) {
                exceptions.add(new ParseException(file, cpt, e.getMessage()));
            }
        }
        reader.close();
        if (exceptions.size() > 0) {
            throw new ParseException(exceptions);
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
        StringBuilder sb = new StringBuilder();
        for (Aeroport aeroport : this.list) {
            sb.append(aeroport.toString()).append("\n\n");
        }
        return sb.toString();
    }
    //#endregion
}
