package src.core;

/**
 * Cette classe définit un aéroport par son code, sa ville, sa latitude et sa longitude.
 * @see Coordonnee
 * @author NOUVEL Armand
 * @version 1.0
 */
public class Aeroport
{
    //#region Attributs

    /**
     * code unique de l'aéroport
     */
    private String code;

    /**
     * ville où se trouve l'aéroport
     */
    private String ville;

    /**
     * coordonnee latitude de l'aéroport, (N ou S)
     */
    private Coordonnee latitude;

    /**
     * coordonnee longitude de l'aéroport (E ou W/O)
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
     * Renvoie le code de l'aéroport
     * @return String
     */
    public String getCode()
    {
        return this.code;
    }

    /**
     * Renvoie la ville de l'aéroport
     * @return String
     */
    public String getVille()
    {
        return this.ville;
    }

    /**
     * Renvoie la latitude de l'aéroport
     * @return Coordonnee
     */
    public Coordonnee getLatitude()
    {
        return this.latitude;
    }

    /**
     * Renvoie la longitude de l'aéroport
     * @return Coordonnee
     */
    public Coordonnee getLongitude()
    {
        return this.longitude;
    }

    //#endregion

    //#region methodes

    /**
     * Vérifie si deux aéroports sont égaux
     * @param autre Vol
     * @return boolean
     */
    @Override
    public boolean equals(Object autre) {
        if (this == autre) return true;
        if (autre == null || getClass() != autre.getClass()) return false;
        Aeroport autreAeroport = (Aeroport) autre;
        if (this.code.equals(autreAeroport.code) &&
            this.ville.equals(autreAeroport.ville) &&
            this.longitude.equals(autreAeroport.longitude) &&
            this.latitude.equals(autreAeroport.latitude)) {
                return true;
            }
        return false;
    }

    /**
     * Renvoie la coordonnee x de l'aéroport
     * @return Double
     */
    public double getx()
    {
        //6371 * cos(latitude) * sin(longitude)
        return (6371 * Math.cos(this.latitude.getDecimalRadians()) * Math.sin(this.longitude.getDecimalRadians()));
    }

    /**
     * Renvoie la coordonnee y de l'aéroport
     * @return Double
     */
    public double gety()
    {
        //6371 * cos(latitude) * cos(longitude)
        return (6371 * Math.cos(this.latitude.getDecimalRadians()) * Math.cos(this.longitude.getDecimalRadians()));
    }

    //#endregion

    //#region affichage

    /**
     * Renvoie une chaine de caractere representant les informations de l'aéroport
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
        StringBuilder constrChaines = new StringBuilder();
        constrChaines.append("<html>");
        constrChaines.append(this.code + "<br>");
        constrChaines.append(this.ville + "<br>");
        constrChaines.append(this.latitude.toString() + "<br>");
        constrChaines.append(this.longitude.toString() + "<br>");
        constrChaines.append("</html>");
        return constrChaines.toString();
    }
    
    //#endregion
} 