package src.core;
/**
 * Classe qui défini une Coordonnee géophraphique
 * @attributs degree, minute, seconde, direction
 * @methodes getDecimal, getDecimalRadians
 * @author NOUVEL Armand
 */
public class Coordonnee 
{
    //#region Attributs
    private int degree;
    private int minute;
    private int seconde;
    private char direction;
    //#endregion

    //#region Constructeurs

    /**
     * Constructructeur de Coordonnee
     * @param degree int, 0 <= degree <= 180
     * @param minute int, 0 <= minute <= 60
     * @param seconde int, 0 <= seconde <= 60
     * @param direction char, {'N', 'S', 'E', 'O'}
     * @throws Exception si les valeurs ne sont pas correctes
     */
    public Coordonnee(int degree, int minute, int seconde, char direction)
    {
        try {
            if (degree < 0 || degree > 180){
                throw new Exception("Le degré doit être compris entre 0 et 180");
            }
            if (minute < 0 || minute > 60){
                throw new Exception("La minute doit être comprise entre 0 et 60");
            }
            if (seconde < 0 || seconde > 60){
                throw new Exception("La seconde doit être comprise entre 0 et 60");
            }
            if (direction != 'N' && direction != 'S' && direction != 'E' && direction != 'O') {
                throw new Exception("La direction doit être N, S, E ou O");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.degree = degree;
        this.minute = minute;
        this.seconde = seconde;
        this.direction = direction;
    }

    /**
     * Constructructeur vide de Coordonnee, 
     * les valeurs sont 0° 0' 0'' N 
     */
    public Coordonnee()
    {
        this.degree = 0;
        this.minute = 0;
        this.seconde = 0;
        this.direction = 'N';
    }

    //#endregion

    //#region accesseurs

    /**
     * renvoie le degré
     * @return int
     */
    public int getDegree()
    {
        return this.degree;
    }

    /**
     * renvoie la minute
     * @return int
     */
    public int getMinute()
    {
        return this.minute;
    }

    /**
     * renvoie la seconde
     * @return int
     */
    public int getSeconde()
    {
        return this.seconde;
    }

    /**
     * renvoie la direction
     * @return char
     */
    public char getDirection()
    {
        return this.direction;
    }

    //#endregion

    //#region mutateurs

    /**
     * modifie le degré
     * @param degree int, 0 <= degree <= 180
     * @throws Exception si la valeur n'est pas correcte
     */
    public void setDegree(int degree)
    {
        try {
            if (degree < 0 || degree > 180){
                throw new Exception("Le degré doit être compris entre 0 et 180");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.degree = degree;
    }

    /**
     * modifie la minute
     * @param minute int, 0 <= minute <= 60
     * @throws Exception si la valeur n'est pas correcte
     */
    public void setMinute(int minute)
    {
        try {
            if (minute < 0 || minute > 60){
                throw new Exception("La minute doit être comprise entre 0 et 60");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.minute = minute;
    }

    /**
     * modifie la seconde
     * @param seconde int, 0 <= seconde <= 60
     * @throws Exception si la valeur n'est pas correcte
     */
    public void setSeconde(int seconde)
    {
        try {
            if (seconde < 0 || seconde > 60){
                throw new Exception("La seconde doit être comprise entre 0 et 60");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.seconde = seconde;
    }

    /**
     * modifie la direction
     * @param direction char, {'N', 'S', 'E', 'O'}
     * @throws Exception si la valeur n'est pas correcte
     */
    public void setDirection(char direction)
    {
        try {
            if (direction != 'N' && direction != 'S' && direction != 'E' && direction != 'O') {
                throw new Exception("La direction doit être N, S, E ou O");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.direction = direction;
    }

    //#endregion

    //#region methodes

    /**
     * Convertit la coordonnée en degrés décimaux
     * @return double
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
        return decimal;
    }

    /**
     * Convertit la coordonnée en degrés décimaux radians
     * @return double
     */
    public double getDecimalRadians()
    {
        return Math.toRadians(this.getDecimal());
    }

    //#endregion

    //#region affichage
 
    /**
     * renvoie une chaine de caractere representant la coordonnee
     * @return String
     */
    public String toString()
    {
        //45° 43' 35'' N
        return this.degree + "° " + this.minute + "' " + this.seconde + "'' " + this.direction;
    }

    //#endregion
}
