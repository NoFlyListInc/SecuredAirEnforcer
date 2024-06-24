package src.core;

//#region import
import org.jxmapviewer.viewer.GeoPosition;
//#endregion

/**
 * Class qui défini un Vol
 * @attributs code, depart, arrivee, heureDepart, duree
 * @methodes getHeureArrivee, collision, toString
 * @autor NOUVEL Armand
 */
public class Vol 
{
    //#region attribut

    /**
     * Code unique du vol
     */
    private String code;

    /**
     * Aeroport de depart
     */
    private Aeroport depart;

    /**
     * Aeroport d'arrivee
     */
    private Aeroport arrivee;

    /**
     * Horaire de depart
     */
    private Horaire heureDepart;

    /**
     * Duree du vol en minute
     */
    private int duree;

    //#endregion

    //#region constructeur

    /**
     * Constructeur de la class Vol
     * @param code code du vol
     * @param depart aeroport de depart
     * @param arrivee aeroport d'arrivee
     * @param heureDepart horaire de depart
     * @param duree (String) duree du vol en minute, duree > 0
     * @throws IllegalArgumentException si les valeurs sont null ou vides
     */
    public Vol(String code, Aeroport depart, Aeroport arrivee, Horaire heureDepart, String duree) throws IllegalArgumentException {
        if (code == null || code.isEmpty())
            throw new IllegalArgumentException("Le code du vol ne peut pas etre vide");
        if (depart == null)
            throw new IllegalArgumentException("L'aeroport de depart ne peut pas etre vide");
        if (arrivee == null)
            throw new IllegalArgumentException("L'aeroport d'arrivee ne peut pas etre vide");
        if (heureDepart == null)
            throw new IllegalArgumentException("L'horaire de depart ne peut pas etre vide");
        //conversion de la duree en int
        int dureeInt = 0;
        try {
            dureeInt = Integer.parseInt(duree);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("La duree du vol doit etre un nombre entier");
        }
        if (dureeInt <= 0)
            throw new IllegalArgumentException("La duree du vol doit etre superieur à 0");
        //initialisation des attributs
        this.code = code;
        this.depart = depart;
        this.arrivee = arrivee;
        this.heureDepart = heureDepart;
        this.duree = dureeInt;
    }

    /**
     * Constructeur de la class Vol
     * @param code code du vol
     * @param depart aeroport de depart
     * @param arrivee aeroport d'arrivee
     * @param heureDepart horaire de depart
     * @param duree duree du vol en minute, duree > 0
     * @throws IllegalArgumentException si les valeurs sont null ou vides
     */
    public Vol(String code, Aeroport depart, Aeroport arrivee, Horaire heureDepart, int duree) throws IllegalArgumentException {
        this(code, depart, arrivee, heureDepart, Integer.toString(duree));
    }

    //#endregion

    //#region accesseurs

    /**
     * Retourne le code du vol
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Retourne l'aeroport de depart
     * @return Aeroport
     */
    public Aeroport getDepart() {
        return this.depart;
    }

    /**
     * Retourne l'aeroport d'arrivee
     * @return Aeroport
     */
    public Aeroport getArrivee() {
        return this.arrivee;
    }

    /**
     * Retourne l'horaire de depart
     * @return Horaire
     */
    public Horaire getHeureDepart() {
        return this.heureDepart;
    }

    /**
     * Retourne la duree du vol
     * @return int
     */
    public int getDuree() {
        return this.duree;
    }
    
    //#endregion

    //#region méthodes

    /**
     * vérifie si deux vols sont égaux
     * @param other
     * @return boolean
     */
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Vol otherVol = (Vol) other;
        if (this.code == otherVol.code && 
            this.depart.equals(otherVol.depart) &&
            this.arrivee.equals(otherVol.arrivee) &&
            this.heureDepart.equals(otherVol.heureDepart) &&
            this.duree == otherVol.duree) {
            return true;
        }
        return false;
    }

    /**
     * Retourne l'heure d'arrivée du vol
     * @return un horaire
     */
    public Horaire getHeureArrivee() {
        try {
            Horaire heureArrivee = new Horaire(this.heureDepart.obtenirHeure(), this.heureDepart.obtenirMinute());
            heureArrivee.ajouterMinutes(this.duree);
            return heureArrivee;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Erreur lors du calcul de l'heure d'arrivée : " + e.getMessage(), e);
        }
    }

    /**
     * Vérifie si le vol rendre en collision avec un autre dans un intervalle données
     * @param other l'autre vol
     * @param ecart l'intervalle en minute
     * @return la GeoPosition de la collision ou null
     */
    public GeoPosition collision(Vol other, int ecart) {

        //*se croise sur la meme ligne à contre sens
        if (this.arrivee==other.depart && this.depart==other.arrivee) {
            if (this.getHeureArrivee().obtenirEnMinute() >= other.heureDepart.obtenirEnMinute()  && other.getHeureArrivee().obtenirEnMinute() >= this.heureDepart.obtenirEnMinute()) {
                //on renvoie les coordonnées du milieu des deux vols 
                double midLatitude = (this.depart.getLatitude().obtenirDecimal() + this.arrivee.getLatitude().obtenirDecimal()) / 2;
                double midLongitude = (this.depart.getLongitude().obtenirDecimal() + this.arrivee.getLongitude().obtenirDecimal()) / 2;
                return new GeoPosition(midLatitude, midLongitude);
            }
        }

        //*utilise la meme ligne dans le meme sens
        else if (this.depart==other.depart && this.arrivee==other.arrivee) {
            if ((this.heureDepart.obtenirEnMinute() >= other.heureDepart.obtenirEnMinute() && this.getHeureArrivee().obtenirEnMinute() <= other.getHeureArrivee().obtenirEnMinute()) || (other.heureDepart.obtenirEnMinute() >= this.heureDepart.obtenirEnMinute() && other.heureDepart.obtenirEnMinute() <= this.getHeureArrivee().obtenirEnMinute())) {
                //on renvoie les coordonnées du milieu des deux vols 
                double midLatitude = (this.depart.getLatitude().obtenirDecimal() + this.arrivee.getLatitude().obtenirDecimal()) / 2;
                double midLongitude = (this.depart.getLongitude().obtenirDecimal() + this.arrivee.getLongitude().obtenirDecimal()) / 2;
                return new GeoPosition(midLatitude, midLongitude);
            }
        }

        //*si les deux vols arrive au meme aeroport
        else if (this.arrivee==other.arrivee) {
            if (Math.abs(this.getHeureArrivee().obtenirEnMinute()-other.getHeureArrivee().obtenirEnMinute()) <= ecart) {
                //on renvoie les coordonnées de l'arrivée
                return new GeoPosition(this.arrivee.getLatitude().obtenirDecimal(), this.arrivee.getLongitude().obtenirDecimal());
            }
        }

        //*si les deux vols parte du meme aeroport
        else if (this.depart==other.depart) {
            if (Math.abs(this.heureDepart.obtenirEnMinute()-other.heureDepart.obtenirEnMinute()) <= ecart) {
                //on renvoie les coordonnées du depart
                return new GeoPosition(this.depart.getLatitude().obtenirDecimal(), this.depart.getLongitude().obtenirDecimal());
            }
        }

        //*si le vol this arrive au depart de vol other
        else if (this.arrivee==other.depart) {
            if (Math.abs(this.getHeureArrivee().obtenirEnMinute()-other.heureDepart.obtenirEnMinute()) <= ecart) {
                //on renvoie les coordonnées de l'arrivee de this
                return new GeoPosition(this.arrivee.getLatitude().obtenirDecimal(), this.arrivee.getLongitude().obtenirDecimal());
            }
        }

        //*si le vol this part de l'arrivee de vol other
        else if (this.depart==other.arrivee) {
            if (Math.abs(this.heureDepart.obtenirEnMinute()-other.getHeureArrivee().obtenirEnMinute()) <= ecart) {
                //on renvoie les coordonnées du depart de this
                return new GeoPosition(this.depart.getLatitude().obtenirDecimal(), this.depart.getLongitude().obtenirDecimal());
            }
        } 

        //*si les droites se croises
        else {
            //coeficient directeur
            double coefThis = (this.arrivee.gety() - this.depart.gety()) / (this.arrivee.getx() - this.depart.getx());
            double coefOther = (other.arrivee.gety() - other.depart.gety()) / (other.arrivee.getx() - other.depart.getx());

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
                    double heureThis = this.heureDepart.obtenirEnMinute()+tempsThis;
                    double heureOther = other.heureDepart.obtenirEnMinute()+tempsOther;

                    //si les vols se croisent dans les [ecart] minutes
                    if (Math.abs(heureThis-heureOther) <= ecart) {
                        //transforme interx et intery en latitude et longitude
                        //on utilise l'arrivee et le depart de this pour déterminer le scale     
                        double difLatCord = this.arrivee.getLatitude().obtenirDecimal()-this.depart.getLatitude().obtenirDecimal();
                        double difLongCord = this.arrivee.getLongitude().obtenirDecimal()-this.depart.getLongitude().obtenirDecimal();
                        double difxCord = this.arrivee.getx()-this.depart.getx();
                        double difyCord = this.arrivee.gety()-this.depart.gety();
                        double scaleX = difxCord/difLongCord;
                        double scaleY = difyCord/difLatCord;
                        //on utilise le depart de this comme point de reference
                        double refLat = this.depart.getLatitude().obtenirDecimal();
                        double refLong = this.depart.getLongitude().obtenirDecimal();
                        double refx = this.depart.getx();
                        double refy = this.depart.gety();
                        //on calcule les coordonnées géographique du point de collision 
                        double lat = refLat+(intery-refy)/scaleY;
                        double lon = refLong+(interx-refx)/scaleX;
                        return new GeoPosition(lat, lon);
                    }
                }
            }             
        }
        //*sinon return false  
        return null;    
    }
    //#endregion

    //#region affichage

    /**
     * Retourne une chaine de caractère représentant le vol
     * @return String
     */
    public String toString() {
        //AF030218 : [LYS] Lyon -> [BOD] Bordeaux : 8h58 - 9h58 (60 minutes)
        return this.code + " : " + "[" + this.depart.getCode() + "]" + " " + this.depart.getVille() + " -> " + "[" + this.arrivee.getCode() + "]" + " " + this.arrivee.getVille() + " : " + this.heureDepart + " - " + this.getHeureArrivee() + " (" + this.duree + " minutes)";
    }
    
    //#endregion
}
