public class Aeroport
{
    //#region Attributs
    private String code;
    private String ville;
    private Coordonnee cordY;
    private Coordonnee cordX;
    //#endregion

    //#region Constructeurs
    public Aeroport(String code, String ville, Coordonnee cordY, Coordonnee cordX)
    {
        this.code = code;
        this.ville = ville;
        this.cordY = cordY;
        this.cordX = cordX;
    }

    public Aeroport()
    {
        this.code = "";
        this.ville = "";
        this.cordY = new Coordonnee();
        this.cordX = new Coordonnee();
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

    public Coordonnee getcordY()
    {
        return this.cordY;
    }

    public Coordonnee getcordX()
    {
        return this.cordX;
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

    public void setcordY(Coordonnee cordY)
    {
        this.cordY = cordY;
    }

    public void setcordX(Coordonnee cordX)
    {
        this.cordX = cordX;
    }
    //#endregion

    //#region methodes
    
    //#endregion

    //#region affichage
    public String toString()
    {
        return "Code: " + this.code + "\nVille: " + this.ville + "\nCoordonnee 1: " + this.cordY.toString() + "\nCoordonnee 2: " + this.cordX.toString();
    }
    //#endregion
} 