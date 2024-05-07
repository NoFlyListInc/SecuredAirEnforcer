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

    /**
     * Convertit les coordonnées en degrés décimaux (radians)
     * @return degrés décimaux (radians)
     */
    public double getDecimal()
    {
        // Conversion en degrés décimaux
        double decimal = this.degree + (this.minute / 60.0) + (this.seconde / 3600.0);
        // Si la direction est Sud ou Ouest, on inverse le signe
        if (this.direction == 'S' || this.direction == 'O')
        {
            decimal = -decimal;
        }
        //return en radians
        return Math.toRadians(decimal);
    }
    //#endregion

    //#region affichage
    
    public String toString()
    {
        //45° 43' 35'' N
        return this.degree + "° " + this.minute + "' " + this.seconde + "'' " + this.direction;
    }
    //#endregion
}
