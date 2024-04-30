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

    public boolean collision(Vol other) {
        //coeficient directeur
        double coefThis = (this.arrivee.gety() - this.depart.gety()) / (this.arrivee.getx() - this.depart.getx());
        double coefOther = (other.arrivee.gety() - other.depart.gety()) / (other.arrivee.getx() - other.depart.getx());
        //si les droites se croises
        if (coefThis != coefOther) {
            //ordonnée à l'origine
            double ordThis = coefThis * -(this.depart.getx()) + this.depart.gety();
            double ordOther = coefOther * -(other.depart.getx()) + other.depart.gety();
            //coordonnées du point d'intersection
            double interx = (ordOther - ordThis) / (coefThis - coefOther);
            double intery = coefThis * interx + ordThis;
            //si le point d'intersection est sur les deux segments
            if (interx >= Math.min(this.depart.getx(), this.arrivee.getx()) && interx <= Math.max(this.depart.getx(), this.arrivee.getx()) && interx >= Math.min(other.depart.getx(), other.arrivee.getx()) && interx <= Math.max(other.depart.getx(), other.arrivee.getx())) {
                //longueur des vols jusqu'a l'arrivee
                double longThis = Math.sqrt(Math.pow(this.arrivee.getx() - this.depart.getx(),2)+Math.pow(this.arrivee.gety() - this.depart.gety(),2));
                double longOther = Math.sqrt(Math.pow(other.arrivee.getx() - other.depart.getx(),2)+Math.pow(other.arrivee.gety() - other.depart.gety(),2));
                //distance entre le point d'intersection et les aeroports de depart
                double distThis = Math.sqrt(Math.pow(intery - this.depart.gety(),2)+Math.pow(interx - this.depart.getx(),2));
                double distOther = Math.sqrt(Math.pow(intery - other.depart.gety(),2)+Math.pow(interx - other.depart.getx(),2));
                //temps pour arriver au point d'intersection
                double tempsThis = distThis * this.duree / longThis;
                double tempsOther = distOther * other.duree / longOther;
                //heure d'arrivée au point d'intersection (en minute)
                double heureThis = this.heureDepart.getHeure()*60+this.heureDepart.getMinute()+tempsThis;
                double heureOther = other.heureDepart.getHeure()*60+other.heureDepart.getMinute()+tempsOther;
                //si les vols se croisent dans les 15 minutes
                if (Math.abs(heureThis-heureOther) <= 15) {
                    return true;
                }
            }
        }
        return false;
    }
    //#endregion

    //#region affichage
    public String toString() {
        return this.code + " : " + "[" + this.depart.getCode() + "]" + " " + this.depart.getVille() + " -> " + "[" + this.arrivee.getCode() + "]" + " " + this.arrivee.getVille() + " : " + this.heureDepart + " - " + this.getHeureArrivee() + " (" + this.duree + " minutes)";
    }
    //#endregion
}
