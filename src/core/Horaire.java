package src.core;


/**
 * Classe qui définit un Horaire
 * @attributs heure, minute
 * @methodes getEnMinute, ajouterMinutes
 * @autor NOUVEL Armand
 */
public class Horaire 
{
    //#region attribut

    /**
     * valeur de la partie heure de l'horaire, 0 <= heure
     */
    private int heure;

    /**
     * valeur de la partie minute, 0 <= minute <= 60
     */
    private int minute;

    //#endregion

    //#region constructeur

    /**
     * Constructeur de Horaire, 
     * dans notre cas il est possible de créer un horaire supérieur à 23h59
     * @param heure String, 0 <= heure
     * @param minute String, 0 <= minute <= 60
     * @throws IllegalArgumentException si les valeurs ne sont pas correctes
     */
    public Horaire(String heure, String minute) throws IllegalArgumentException {
        // conversion des string en int
        int heureInt=0;
        try {
            heureInt = Integer.parseInt(heure);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("l'heure doit être un entier");
        }
        int minuteInt=0;
        try {
            minuteInt = Integer.parseInt(minute);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("la minute doit être un entier");
        }
        // vérification des valeurs
        if (minuteInt < 0 || minuteInt > 60) {
            throw new IllegalArgumentException("minute doit être compris entre 0 et 60");
        }
        if (heureInt < 0) {
            throw new IllegalArgumentException("heure doit être supérieur ou égal à 0");
        }
        // affectation des valeurs
        this.heure = heureInt;
        this.minute = minuteInt;
    }

    /**
     * Constructeur de Horaire, 
     * dans notre cas il est possible de créer un horaire supérieur à 23h59
     * @param heure int, 0 <= heure
     * @param minute int, 0 <= minute <= 60
     * @throws IllegalArgumentException si les valeurs ne sont pas correctes
     */
    public Horaire(int heure, int minute) throws IllegalArgumentException {
        this(String.valueOf(heure), String.valueOf(minute));
    }

    

    //#endregion

    //#region accesseurs
    /**
     * Renvoie l'heure
     * @return int
     */
    public int getHeure() {
        return this.heure;
    }

    /**
     * Renvoie les minutes
     * @return int
     */
    public int getMinute() {
        return this.minute;
    }

    //#endregion

    //#region méthodes

    /**
     * Retourne l'horaire en minute
     * @return int
     */
    public int getEnMinute() {
        return this.heure * 60 + this.minute;
    }

    /**
     * Ajoute des minutes à l'heure
     * @param minutes int, 0 <= minutes
     * @throws IllegalArgumentException
     */
    public void ajouterMinutes(int minutes) throws IllegalArgumentException {
        if (minutes < 0) {
            throw new IllegalArgumentException("minutes doit être supérieur ou égal à 0");
        }
        int totalMinutes = this.getEnMinute() + minutes;
        this.heure = totalMinutes / 60;
        this.minute = totalMinutes % 60;
    }
    //#endregion

    //#region affichage

    /**
     * Renvoie l'horaire sous forme de chaine de caractère
     * @return String
     */
    public String toString() {
        //21h02
        String heureString = (this.heure < 10) ? "0" + this.heure : String.valueOf(this.heure);
        String minuteString = (this.heure < 10) ? "0" + this.minute : String.valueOf(this.minute);
        return heureString + "h" + minuteString;
    }
    //#endregion
}
