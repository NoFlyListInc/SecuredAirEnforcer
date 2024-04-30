public class Coordonnee 
{
    //#region Attributs
    private int degree;
    private int minute;
    private int seconde;
    private char direction;
    //#endregion

    //#region Constructeurs
    public Coordonnee(int degree, int minute, int seconde, char direction)
    {
        this.degree = degree;
        this.minute = minute;
        this.seconde = seconde;
        this.direction = direction;
    }

    public Coordonnee()
    {
        this.degree = 0;
        this.minute = 0;
        this.seconde = 0;
        this.direction = 'N';
    }
    //#endregion

    //#region accesseurs
    public int getDegree()
    {
        return this.degree;
    }

    public int getMinute()
    {
        return this.minute;
    }

    public int getSeconde()
    {
        return this.seconde;
    }

    public char getDirection()
    {
        return this.direction;
    }
    //#endregion

    //#region mutateurs
    public void setDegree(int degree)
    {
        this.degree = degree;
    }

    public void setMinute(int minute)
    {
        this.minute = minute;
    }

    public void setSeconde(int seconde)
    {
        this.seconde = seconde;
    }

    public void setDirection(char direction)
    {
        this.direction = direction;
    }
    //#endregion

    //#region methodes
    //#endregion

    //#region affichage
    public String toString()
    {
        return this.degree + "° " + this.minute + "' " + this.seconde + "'' " + this.direction;
    }
    //#endregion
}
