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

    /**
     * code unique de l'aeroport
     */
    private String code;

    /**
     * ville où se trouve l'aeroport
     */
    private String ville;

    /**
     * coordonnee latitude de l'aeroport, (N ou S)
     */
    private Coordonnee latitude;

    /**
     * coordonnee longitude de l'aeroport (E ou W/O)
     */
    private Coordonnee longitude;

    //#endregion

    //#region Constructeurs

    /**
     * Constructructeur de Aeroport
     * @param code String
     * @param ville String
     * @param latitude Coordonnee, (N ou S)
     * @param longitude Coordonnee, (E ou W/O)
     * @throws IllegalArgumentException si les valeurs sont null ou vides
     */
    public Aeroport(String code, String ville, Coordonnee latitude, Coordonnee longitude) throws IllegalArgumentException
    {
        //verification des valeurs
        if (code == null || code.isEmpty())
            throw new IllegalArgumentException("Le code de l'aeroport ne peut pas etre vide");
        if (ville == null || ville.isEmpty())
            throw new IllegalArgumentException("La ville de l'aeroport ne peut pas etre vide");
        if (latitude == null)
            throw new IllegalArgumentException("La latitude de l'aeroport ne peut pas etre vide");
        if (longitude == null)
            throw new IllegalArgumentException("La longitude de l'aeroport ne peut pas etre vide");
        if (latitude.getDirection() != 'N' && latitude.getDirection() != 'S')
            throw new IllegalArgumentException("La latitude de l'aeroport doit etre soit N ou S");
        if (longitude.getDirection() != 'E' && longitude.getDirection() != 'W' && longitude.getDirection() != 'O')
            throw new IllegalArgumentException("La longitude de l'aeroport doit etre soit E ou W/O");
        //affectation des valeurs
        this.code = code;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
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
     * renvoie une chaine de caractere representant les informations de l'aeroport
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