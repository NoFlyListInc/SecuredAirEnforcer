package src.core;

/**
 * <h3>Cette classe définit une coordonnée.</h3>
 * @attributs degree, minute, seconde, direction
 * @methodes getDecimal, getDecimalRadians, equals, toString
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
        int degreeEntier=0;
        try {
            degreeEntier = Integer.parseInt(degree);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("le degré doit être un entier");
        }

        int minuteEntier=0;
        try {
            minuteEntier = Integer.parseInt(minute);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("la minute doit être un entier");
        }

        int secondeEntier=0;
        try {
            secondeEntier = Integer.parseInt(seconde);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("la seconde doit être un entier");
        }

        //conversion de la direction en char
        char directionChar = direction.charAt(0);

        //vérification des valeurs
        if (degreeEntier < 0 || degreeEntier > 180){
            throw new IllegalArgumentException("Le degré doit être compris entre 0 et 180");
        }
        if (minuteEntier < 0 || minuteEntier > 60){
            throw new IllegalArgumentException("La minute doit être comprise entre 0 et 60");
        }
        if (secondeEntier < 0 || secondeEntier > 60){
            throw new IllegalArgumentException("La seconde doit être comprise entre 0 et 60");
        }
        if (directionChar != 'N' && directionChar != 'S' && directionChar != 'E' && directionChar != 'O' && directionChar != 'W') {
            throw new IllegalArgumentException("La direction doit être N, S, E ou O/W");
        }
        this.degree = degreeEntier;
        this.minute = minuteEntier;
        this.seconde = secondeEntier;
        this.direction = directionChar;
    }

    /**
     * Constructructeur de Coordonnee
     * @param degree Entier, 0 <= degree <= 180
     * @param minute Entier, 0 <= minute <= 60
     * @param seconde Entier, 0 <= seconde <= 60
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
     * @return Entier
     */
    public int getDegre()
    {
        return this.degree;
    }

    /**
     * renvoie la minute
     * @return Entier
     */
    public int getMinute()
    {
        return this.minute;
    }

    /**
     * renvoie la seconde
     * @return Entier
     */
    public int getSeconde()
    {
        return this.seconde;
    }

    /**
     * Renvoie la direction
     * @return char
     */
    public char getDirection()
    {
        return this.direction;
    }

    //#endregion

    //#region methodes

    /**
     * Vérifie si deux coordonnées sont égales
     * @param autre
     * @return boolean
     */
    @Override
    public boolean equals(Object autre) {
        if (this == autre) return true;
        if (autre == null || getClass() != autre.getClass()) return false;
        Coordonnee autreCoordonnee = (Coordonnee) autre;
        if (this.degree == autreCoordonnee.degree &&
            this.minute == autreCoordonnee.minute &&
            this.seconde == autreCoordonnee.seconde &&
            this.direction == autreCoordonnee.direction) {
                return true;
            }
        return false;
    }

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
     * Renvoie la coordonnée sous forme de chaine de caractères
     * @return String
     */
    public String toString()
    {
        //45° 43' 35'' N
        return this.degree + "° " + this.minute + "' " + this.seconde + "'' " + this.direction;
    }

    //#endregion
}
