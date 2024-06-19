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

    /**
     * degré de la coordonnée, 0 <= degree <= 180
     */
    private int degree;

    /**
     * minute de la coordonnée, 0 <= minute <= 60
     */
    private int minute;

    /**
     * seconde de la coordonnée, 0 <= seconde <= 60
     */
    private int seconde;

    /**
     * direction de la coordonnée, (N, S, E, O)
     */
    private char direction;

    //#endregion

    //#region Constructeurs

    /**
     * Constructructeur de Coordonnee
     * @param degree String, 0 <= degree <= 180
     * @param minute String, 0 <= minute <= 60
     * @param seconde String, 0 <= seconde <= 60
     * @param direction String, {'N', 'S', 'E', 'O'/'W'}
     * @throws IllegalArgumentException si les valeurs ne sont pas correctes
     */
    public Coordonnee(String degree, String minute, String seconde, String direction) throws IllegalArgumentException
    {
        //conversion des string en int
        int degreeInt=0;
        try {
            degreeInt = Integer.parseInt(degree);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("le degré doit être un entier");
        }
        int minuteInt=0;
        try {
            minuteInt = Integer.parseInt(minute);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("la minute doit être un entier");
        }
        int secondeInt=0;
        try {
            secondeInt = Integer.parseInt(seconde);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("la seconde doit être un entier");
        }
        //conversion de la direction en char
        char directionChar = direction.charAt(0);
        //vérification des valeurs
        if (degreeInt < 0 || degreeInt > 180){
            throw new IllegalArgumentException("Le degré doit être compris entre 0 et 180");
        }
        if (minuteInt < 0 || minuteInt > 60){
            throw new IllegalArgumentException("La minute doit être comprise entre 0 et 60");
        }
        if (secondeInt < 0 || secondeInt > 60){
            throw new IllegalArgumentException("La seconde doit être comprise entre 0 et 60");
        }
        if (directionChar != 'N' && directionChar != 'S' && directionChar != 'E' && directionChar != 'O' && directionChar != 'W') {
            throw new IllegalArgumentException("La direction doit être N, S, E ou O/W");
        }
        this.degree = degreeInt;
        this.minute = minuteInt;
        this.seconde = secondeInt;
        this.direction = directionChar;
    }

    /**
     * Constructructeur de Coordonnee
     * @param degree int, 0 <= degree <= 180
     * @param minute int, 0 <= minute <= 60
     * @param seconde int, 0 <= seconde <= 60
     * @param direction char, {'N', 'S', 'E', 'O'/'W'}
     * @throws IllegalArgumentException si les valeurs ne sont pas correctes
     */
    public Coordonnee(int degree, int minute, int seconde, char direction) throws IllegalArgumentException
    {
        this(String.valueOf(degree), String.valueOf(minute), String.valueOf(seconde), String.valueOf(direction));
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
