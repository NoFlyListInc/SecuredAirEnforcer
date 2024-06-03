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
    private int heure;
    private int minute;
    //#endregion

    //#region constructeur

    /**
     * Constructeur de Horaire, 
     * dans notre cas il est possible de créer un horaire inférieur à 00h00 ou supérieur à 23h59
     * @param heure int
     * @param minute int
     */
    public Horaire(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
    }

    /**
     * Constructeur vide de Horaire, 
     * l'heure et la minute sont à 0
     */
    public Horaire() {
        this.heure = 0;
        this.minute = 0;
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

    //#region mutateurs

    /**
     * Modifie l'heure
     * @param heure int
     */
    public void setHeure(int heure) {
        this.heure = heure;
    }

    /**
     * Modifie les minutes
     * @param minute int
     */
    public void setMinute(int minute) {
        this.minute = minute;
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
     * @param minutes Si négatif, on enlève des minutes
     */
    public void ajouterMinutes(int minutes) {
        int totalMinutes = this.getEnMinute() + minutes;
        this.heure = totalMinutes / 60;
        this.minute = totalMinutes % 60;
    }
    //#endregion

    //#region affichage
    public String toString() {
        //21h02
        String heureString = (this.heure < 10) ? "0" + this.heure : String.valueOf(this.heure);
        String minuteString = (this.minute < 10) ? "0" + this.minute : String.valueOf(this.minute);
        return heureString + "h" + minuteString;
    }
    //#endregion
}
