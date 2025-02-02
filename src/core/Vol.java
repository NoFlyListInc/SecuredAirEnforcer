package src.core;

//#region import
import org.jxmapviewer.viewer.GeoPosition;
//#endregion

/**
 * Cette classe définit un Vol par son code, son aeroport de depart, son aeroport d'arrivee, son horaire de depart et sa duree.
 * @see Aeroport
 * @see Horaire
 * @author NOUVEL Armand
 * @version 1.0
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
     * Constructeur de la classe Vol
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
     * Constructeur de la classe Vol
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
     * Renvoie le code du vol
     * @return String
     */
    public String getCode() {
        return this.code;
    }

    /**
     * Renvoie l'aeroport de depart
     * @return Aeroport
     */
    public Aeroport getDepart() {
        return this.depart;
    }

    /**
     * Renvoie l'aeroport d'arrivee
     * @return Aeroport
     */
    public Aeroport getArrivee() {
        return this.arrivee;
    }

    /**
     * Renvoie l'horaire de depart
     * @return Horaire
     */
    public Horaire getHeureDepart() {
        return this.heureDepart;
    }

    /**
     * Renvoie la duree du vol
     * @return int
     */
    public int getDuree() {
        return this.duree;
    }
    
    //#endregion

    //#region méthodes

    /**
     * Vérifie si le vol est en cours à un horaire donné
     * @param heure Horaire
     * @return boolean
     */
    public boolean estEnVol(Horaire heure) {
        return this.heureDepart.getEnMinute() <= heure.getEnMinute() && this.getHeureArrivee().getEnMinute() >= heure.getEnMinute();
    }

    /**
     * Vérifie si deux vols sont égaux
     * @param autre Vol
     * @return boolean
     */
    @Override
    public boolean equals(Object autre) {
        if (this == autre) return true;
        if (autre == null || getClass() != autre.getClass()) return false;
        Vol autreVol = (Vol) autre;
        if (this.code.equals(autreVol.code) && 
            this.depart.equals(autreVol.depart) &&
            this.arrivee.equals(autreVol.arrivee) &&
            this.heureDepart.equals(autreVol.heureDepart) &&
            this.duree == autreVol.duree) {
            return true;
        }
        return false;
    }

    /**
     * Renvoie l'heure d'arrivée du vol
     * @return Horaire
     */
    public Horaire getHeureArrivee() {
        try {
            Horaire heureArrivee = new Horaire(this.heureDepart.getHeure(), this.heureDepart.getMinute());
            heureArrivee.ajouterMinutes(this.duree);
            return heureArrivee;
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Erreur lors du calcul de l'heure d'arrivée : " + e.getMessage(), e);
        }
    }

    /**
     * Vérifie si le vol rendre en collision avec un autre dans un intervalle données
     * @param autre l'autre vol
     * @param ecart l'intervalle en minute
     * @return la GeoPosition de la collision ou null
     */
    public GeoPosition collision(Vol autre, int ecart) {

        //*se croise sur la meme ligne à contre sens

        if (this.arrivee==autre.depart && this.depart==autre.arrivee) {
            if (this.getHeureArrivee().getEnMinute() >= autre.heureDepart.getEnMinute()  && autre.getHeureArrivee().getEnMinute() >= this.heureDepart.getEnMinute()) {
                //on renvoie les coordonnées du milieu des deux vols 
                double midLatitude = (this.depart.getLatitude().getDecimal() + this.arrivee.getLatitude().getDecimal()) / 2;
                double midLongitude = (this.depart.getLongitude().getDecimal() + this.arrivee.getLongitude().getDecimal()) / 2;
                return new GeoPosition(midLatitude, midLongitude);
            }
        }

        //*utilise la meme ligne dans le meme sens

        else if (this.depart==autre.depart && this.arrivee==autre.arrivee) {
            if ((this.heureDepart.getEnMinute() >= autre.heureDepart.getEnMinute() && this.getHeureArrivee().getEnMinute() <= autre.getHeureArrivee().getEnMinute()) || (autre.heureDepart.getEnMinute() >= this.heureDepart.getEnMinute() && autre.heureDepart.getEnMinute() <= this.getHeureArrivee().getEnMinute())) {
                //on renvoie les coordonnées du milieu des deux vols 
                double midLatitude = (this.depart.getLatitude().getDecimal() + this.arrivee.getLatitude().getDecimal()) / 2;
                double midLongitude = (this.depart.getLongitude().getDecimal() + this.arrivee.getLongitude().getDecimal()) / 2;
                return new GeoPosition(midLatitude, midLongitude);
            }
        }

        //*si les deux vols arrive au meme aeroport

        else if (this.arrivee==autre.arrivee) {
            if (Math.abs(this.getHeureArrivee().getEnMinute()-autre.getHeureArrivee().getEnMinute()) <= ecart) {
                //on renvoie les coordonnées de l'arrivée
                return new GeoPosition(this.arrivee.getLatitude().getDecimal(), this.arrivee.getLongitude().getDecimal());
            }
        }

        //*si les deux vols parte du meme aeroport

        else if (this.depart==autre.depart) {
            if (Math.abs(this.heureDepart.getEnMinute()-autre.heureDepart.getEnMinute()) <= ecart) {
                //on renvoie les coordonnées du depart
                return new GeoPosition(this.depart.getLatitude().getDecimal(), this.depart.getLongitude().getDecimal());
            }
        }

        //*si le vol this arrive au depart de vol autre

        else if (this.arrivee==autre.depart) {
            if (Math.abs(this.getHeureArrivee().getEnMinute()-autre.heureDepart.getEnMinute()) <= ecart) {
                //on renvoie les coordonnées de l'arrivee de this
                return new GeoPosition(this.arrivee.getLatitude().getDecimal(), this.arrivee.getLongitude().getDecimal());
            }
        }

        //*si le vol this part de l'arrivee de vol autre

        else if (this.depart==autre.arrivee) {
            if (Math.abs(this.heureDepart.getEnMinute()-autre.getHeureArrivee().getEnMinute()) <= ecart) {
                //on renvoie les coordonnées du depart de this
                return new GeoPosition(this.depart.getLatitude().getDecimal(), this.depart.getLongitude().getDecimal());
            }
        } 

        //*si les droites se croises
        else {
            //coeficient directeur
            double coefThis = (this.arrivee.gety() - this.depart.gety()) / (this.arrivee.getx() - this.depart.getx());
            double coefAutre = (autre.arrivee.gety() - autre.depart.gety()) / (autre.arrivee.getx() - autre.depart.getx());

            if (coefThis != coefAutre) {
                //ordonnée à l'origine
                double ordThis = coefThis * -(this.depart.getx()) + this.depart.gety();
                double ordOther = coefAutre * -(autre.depart.getx()) + autre.depart.gety();
                //coordonnées du point d'intersection
                double interx = (ordOther - ordThis) / (coefThis - coefAutre);
                double intery = coefThis * interx + ordThis;

                //si le point d'intersection est sur les deux segments
                if (interx >= Math.min(this.depart.getx(), this.arrivee.getx()) && interx <= Math.max(this.depart.getx(), this.arrivee.getx()) && interx >= Math.min(autre.depart.getx(), autre.arrivee.getx()) && interx <= Math.max(autre.depart.getx(), autre.arrivee.getx())) {
                    //longueur des vols jusqu'a l'arrivee
                    double longThis = Math.sqrt(Math.pow(this.arrivee.getx() - this.depart.getx(),2)+Math.pow(this.arrivee.gety() - this.depart.gety(),2));
                    double longOther = Math.sqrt(Math.pow(autre.arrivee.getx() - autre.depart.getx(),2)+Math.pow(autre.arrivee.gety() - autre.depart.gety(),2));
                    //distance entre le point d'intersection et les aeroports de depart
                    double distThis = Math.sqrt(Math.pow(intery - this.depart.gety(),2)+Math.pow(interx - this.depart.getx(),2));
                    double distOther = Math.sqrt(Math.pow(intery - autre.depart.gety(),2)+Math.pow(interx - autre.depart.getx(),2));
                    //temps pour arriver au point d'intersection
                    double tempsThis = distThis * this.duree / longThis;
                    double tempsOther = distOther * autre.duree / longOther;
                    //heure d'arrivée au point d'intersection (en minute)

                    double heureThis = this.heureDepart.getEnMinute()+tempsThis;
                    double heureOther = autre.heureDepart.getEnMinute()+tempsOther;

                    //si les vols se croisent dans les [ecart] minutes
                    if (Math.abs(heureThis-heureOther) <= ecart) {
                        //transforme interx et intery en latitude et longitude
                        //on utilise l'arrivee et le depart de this pour déterminer le scale     
                        double difLatCord = this.arrivee.getLatitude().getDecimal()-this.depart.getLatitude().getDecimal();
                        double difLongCord = this.arrivee.getLongitude().getDecimal()-this.depart.getLongitude().getDecimal();
                        double difxCord = this.arrivee.getx()-this.depart.getx();
                        double difyCord = this.arrivee.gety()-this.depart.gety();
                        double scaleX = difxCord/difLongCord;
                        double scaleY = difyCord/difLatCord;
                        //on utilise le depart de this comme point de reference
                        double refLat = this.depart.getLatitude().getDecimal();
                        double refLong = this.depart.getLongitude().getDecimal();
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
     * Affiche le vol sous forme de chaine de caractère
     * @return String
     */
    public String toString() {
        //AF030218 : [LYS] Lyon -> [BOD] Bordeaux : 8h58 - 9h58 (60 minutes)
        return this.code + " : " + "[" + this.depart.getCode() + "]" + " " + this.depart.getVille() + " -> " + "[" + this.arrivee.getCode() + "]" + " " + this.arrivee.getVille() + " : " + this.heureDepart + " - " + this.getHeureArrivee() + " (" + this.duree + " minutes)";
    }
    
    //#endregion
}
