public class Aeroport
{
    //#region Attributs
    private String code;
    private String ville;
    private Coordonnee latitude;
    private Coordonnee longitude;
    //#endregion

    //#region Constructeurs
    public Aeroport(String code, String ville, Coordonnee latitude, Coordonnee longitude)
    {
        this.code = code;
        this.ville = ville;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Aeroport()
    {
        this.code = "";
        this.ville = "";
        this.latitude = new Coordonnee();
        this.longitude = new Coordonnee();
    }
    //#endregion

    //#region accesseurs
    public String getCode()
    {
        return this.code;
    }

    public String getVille()
    {
        return this.ville;
    }

    public Coordonnee getLatitude()
    {
        return this.latitude;
    }

    public Coordonnee getLongitude()
    {
        return this.longitude;
    }
    //#endregion

    //#region mutateurs
    public void setCode(String code)
    {
        this.code = code;
    }

    public void setVille(String ville)
    {
        this.ville = ville;
    }

    public void setlatitude(Coordonnee latitude)
    {
        this.latitude = latitude;
    }

    public void setlongitude(Coordonnee longitude)
    {
        this.longitude = longitude;
    }
    //#endregion

    //#region methodes

    /**
     * renvoie la coordonnee x de l'aeroport
     * @return une coordonnée x
     */
    public double getx()
    {
        return 6371 * Math.cos(this.latitude.getDecimalRadians()) * Math.sin(this.longitude.getDecimalRadians());
    }

    /**
     * renvoie la coordonnee y de l'aeroport
     * @return une coordonnée y
     */
    public double gety()
    {
        return 6371 * Math.cos(this.latitude.getDecimalRadians()) * Math.cos(this.longitude.getDecimalRadians());
    }
    //#endregion

    //#region affichage
    public String toString()
    {
        //Code : LYS
        //Ville : Lyon
        //Latitude : 45° 43' 35'' N
        //Longitude : 5° 5' 27'' E
        return "Code : " + this.code + "\nVille : " + this.ville + "\nLatitude : " + this.latitude.toString() + "\nLongitude : " + this.longitude.toString();
    }
    //#endregion
} 