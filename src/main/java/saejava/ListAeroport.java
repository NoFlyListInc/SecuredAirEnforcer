package saejava;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ListAeroport 
{
    //#region attribut
    private ArrayList<Aeroport> list;
    //#endregion

    //#region constructeur
    public ListAeroport() {
        this.list = new ArrayList<Aeroport>();
    }
    //#endregion

    //#region accesseurs
    public ArrayList<Aeroport> getList() {
        return this.list;
    }
    //#endregion

    //#region méthodes
    public boolean addAeroport(Aeroport aeroport) {
        if (aeroport!=null && this.list.contains(aeroport)==false) {
            this.list.add(aeroport);
            return true;
        }
        return false;
    }

    public boolean removeAeroport(Aeroport aeroport) {
        if (aeroport!=null && this.list.contains(aeroport)) {
            this.list.remove(aeroport);
            return true;
        }
        return false;
    }

    public Aeroport getAeroport(int i) {
        if (i >= 0 && i < this.list.size()) {
            return this.list.get(i);
        }
        return null;
    }

    public Aeroport getAeroportByCode(String code) {
        for (Aeroport aeroport : this.list) {
            if (aeroport.getCode().equals(code)) {
                return aeroport;
            }
        }
        return null;
    }
    //#endregion

    //#region fill with a file
    public void fill(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                Aeroport aeroport = new Aeroport(parts[0], parts[1], 
                        new Coordonnee(Integer.parseInt(parts[2]), Integer.parseInt(parts[3]), Integer.parseInt(parts[4]), parts[5].charAt(0)),
                        new Coordonnee(Integer.parseInt(parts[6]), Integer.parseInt(parts[7]), Integer.parseInt(parts[8]), parts[9].charAt(0)));
                this.addAeroport(aeroport);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //#endregion

    //#region affichage
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Aeroport aeroport : this.list) {
            sb.append(aeroport.toString()).append("\n\n");
        }
        return sb.toString();
    }
    //#endregion
}
