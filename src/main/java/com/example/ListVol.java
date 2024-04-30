import java.util.ArrayList;
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
    public ArrayList<Vol> getVols() {
        return this.vols;
    }
    //#endregion

    //#region méthodes
    public boolean addVol(Vol vol) {
        if (vol!=null && this.vols.contains(vol)==false) {
            this.vols.add(vol);
            return true;
        }
        return false;
    }

    public boolean removeVol(Vol vol) {
        if (vol!=null && this.vols.contains(vol)) {
            this.vols.remove(vol);
            return true;
        }
        return false;
    }

    public Vol getVol(int i) {
        if (i >= 0 && i < this.vols.size()) {
            return this.vols.get(i);
        }
        return null;
    }

    public Vol getVolByCode(String code) {
        for (Vol vol : this.vols) {
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
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Vol vol = new Vol(parts[0],
                                  listAeroport.getAeroportByCode(parts[1]),
                                  listAeroport.getAeroportByCode(parts[2]),
                                  new Horaire(Integer.parseInt(parts[3]), Integer.parseInt(parts[4])),
                                  Integer.parseInt(parts[5]));
                this.addVol(vol);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //#endregion

    //#region affichage
    public String toString() {
        String str = "";
        for (Vol vol : this.vols) {
            str += vol.toString() + "\n\n";
        }
        return str;
    }
    //#endregion
}
