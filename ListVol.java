//import ArrayList object
import java.util.ArrayList;
//import reader files tools
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ListVol 
{
    //#region
    private ArrayList<Vol> vols;
    //#endregion

    //#region constructeur
    public ListVol() {
        this.vols = new ArrayList<Vol>();
    }
    //#endregion

    //#region accesseurs
    public ArrayList<Vol> getList() {
        return this.vols;
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
        if (vol!=null && this.vols.contains(vol)==false) {
            this.vols.add(vol);
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
        if (vol!=null && this.vols.contains(vol)) {
            this.vols.remove(vol);
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
        if (i >= 0 && i < this.vols.size()) {
            return this.vols.get(i);
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
        for (Vol vol : this.vols) {
            //si le code correspond
            if (vol.getCode().equals(code)) {
                return vol;
            }
        }
        return null;
    }
    //#endregion

    //#region fill with a file
    public void fill(String file, ListAeroport listAeroport) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            //lecture des lignes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                //création du vol
                Vol vol = new Vol(parts[0],
                                  listAeroport.getAeroportByCode(parts[1]),
                                  listAeroport.getAeroportByCode(parts[2]),
                                  new Horaire(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])),
                                  Integer.parseInt(parts[5]));
                //ajout du vol à la liste
                this.addVol(vol);
            }
            reader.close();
        } catch (IOException e) { //erreur de lecture du fichier
            e.printStackTrace();
        }
    }
    //#endregion

    //#region affichage
    public String toString() {
        //[affiche un vol] saut de ligne .....
        String str = "";
        for (Vol vol : this.vols) {
            str += vol.toString() + "\n\n";
        }
        return str;
    }
    //#endregion
}
