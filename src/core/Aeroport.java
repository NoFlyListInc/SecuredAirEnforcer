package src.core;
/**
 * Classe qui défini un Aeroport
 * @attributs code, ville, latitude, longitude
 * @methodes getx, gety
 * @author NOUVEL Armand
 */
public class Aeroport
{
    //#region Attributs
    private String code;
    private String ville;
    private Coordonnee latitude;
    private Coordonnee longitude;
    //#endregion

    //#region Constructeurs

    /**
     * Constructructeur de Aeroport
     * @param code String
     * @param ville String
     * @param latitude Coordonnee
     * @param longitude Coordonnee
     */
    public Aeroport(String code, String ville, Coordonnee latitude, Coordonnee longitude)
    {
        this.code = code;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Constructructeur vide de Aeroport, 
     * le code et la ville sont des chaines vides et les deux coordonnees sont 0° 0' 0'' N 
     */
    public Aeroport()
    {
        this.code = "";
        this.ville = "";
        this.latitude = new Coordonnee();
        this.longitude = new Coordonnee();
    }

    //#endregion

    //#region accesseurs

    /**
     * renvoie le code de l'aeroport
     * @return String
     */
    public String getCode()
    {
        return this.code;
    }

    /**
     * renvoie la ville de l'aeroport
     * @return String
     */
    public String getVille()
    {
        return this.ville;
    }

    /**
     * renvoie la latitude de l'aeroport
     * @return Coordonnee
     */
    public Coordonnee getLatitude()
    {
        return this.latitude;
    }

    /**
     * renvoie la longitude de l'aeroport
     * @return Coordonnee
     */
    public Coordonnee getLongitude()
    {
        return this.longitude;
    }

    //#endregion

    //#region mutateurs

    /**
     * modifie le code de l'aeroport
     * @param code String
     */
    public void setCode(String code)
    {
        this.code = code;
    }

    /**
     * modifie la ville de l'aeroport
     * @param ville String
     */
    public void setVille(String ville)
    {
        this.ville = ville;
    }

    /**
     * modifie la latitude de l'aeroport
     * @param latitude Coordonnee
     */
    public void setlatitude(Coordonnee latitude)
    {
        this.latitude = latitude;
    }

    /**
     * modifie la longitude de l'aeroport
     * @param longitude Coordonnee
     */
    public void setlongitude(Coordonnee longitude)
    {
        this.longitude = longitude;
    }

    //#endregion

    //#region methodes

    /**
     * renvoie la coordonnee x de l'aeroport
     * @return double
     */
    public double getx()
    {
        //6371 * cos(latitude) * sin(longitude)
        return 6371 * Math.cos(this.latitude.getDecimalRadians()) * Math.sin(this.longitude.getDecimalRadians());
    }

    /**
     * renvoie la coordonnee y de l'aeroport
     * @return double
     */
    public double gety()
    {
        //6371 * cos(latitude) * cos(longitude)
        return 6371 * Math.cos(this.latitude.getDecimalRadians()) * Math.cos(this.longitude.getDecimalRadians());
    }

    //#endregion

    //#region affichage

    /**
     * renvoie une chaine de caractere representant l'aeroport
     * @return String
     */
    public String toString()
    {
        /*
        Code : LYS
        Ville : Lyon
        Latitude : 45° 43' 35'' N
        Longitude : 5° 5' 27'' E
        */
        StringBuilder sb = new StringBuilder();
        sb.append("<html>");
        sb.append(this.code + "<br>");
        sb.append(this.ville + "<br>");
        sb.append(this.latitude.toString() + "<br>");
        sb.append(this.longitude.toString() + "<br>");
        sb.append("</html>");
        return sb.toString();
    }
    
    //#endregion
} 