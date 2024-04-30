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

    public Coordonnee getlatitude()
    {
        return this.latitude;
    }

    public Coordonnee getlongitude()
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
    public double getx()
    {
        return 6371 * Math.cos(this.latitude.getDegree()) * Math.sin(this.longitude.getDegree());
    }

    public double gety()
    {
        return 6371 * Math.cos(this.latitude.getDegree()) * Math.cos(this.longitude.getDegree());
    }
    //#endregion

    //#region affichage
    public String toString()
    {
        return "Code: " + this.code + "\nVille: " + this.ville + "\nCoordonnee 1: " + this.latitude.toString() + "\nCoordonnee 2: " + this.longitude.toString();
    }
    //#endregion
} 