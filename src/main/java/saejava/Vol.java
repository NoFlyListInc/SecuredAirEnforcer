package saejava;

public class Vol 
{
    //#region attribut
    private String code;
    private Aeroport depart;
    private Aeroport arrivee;
    private Horaire heureDepart;
    private int duree;
    //#endregion

    //#region constructeur
    public Vol(String code, Aeroport depart, Aeroport arrivee, Horaire heureDepart, int duree) {
        this.code = code;
        this.depart = depart;
        this.arrivee = arrivee;
        this.heureDepart = heureDepart;
        this.duree = duree;
    }

    public Vol() {
        this.code = "";
        this.depart = new Aeroport();
        this.arrivee = new Aeroport();
        this.heureDepart = new Horaire();
        this.duree = 0;
    }
    //#endregion

    //#region accesseurs
    public String getCode() {
        return this.code;
    }

    public Aeroport getDepart() {
        return this.depart;
    }

    public Aeroport getArrivee() {
        return this.arrivee;
    }

    public Horaire getHeureDepart() {
        return this.heureDepart;
    }

    public int getDuree() {
        return this.duree;
    }
    //#endregion

    //#region mutateurs
    public void setCode(String code) {
        this.code = code;
    }

    public void setDepart(Aeroport depart) {
        this.depart = depart;
    }

    public void setArrivee(Aeroport arrivee) {
        this.arrivee = arrivee;
    }

    public void setHeureDepart(Horaire heureDepart) {
        this.heureDepart = heureDepart;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }
    //#endregion

    //#region méthodes
    public Horaire getHeureArrivee() {
        Horaire heureArrivee = new Horaire(this.heureDepart.getHeure(), this.heureDepart.getMinute());
        heureArrivee.ajouterMinutes(this.duree);
        return heureArrivee;
    }
    //#endregion

    //#region affichage
    public String toString() {
        return this.code + " : " + "[" + this.depart.getCode() + "]" + " " + this.depart.getVille() + " -> " + "[" + this.arrivee.getCode() + "]" + " " + this.arrivee.getVille() + " : " + this.heureDepart + " - " + this.getHeureArrivee() + " (" + this.duree + " minutes)";
    }
    //#endregion
}
