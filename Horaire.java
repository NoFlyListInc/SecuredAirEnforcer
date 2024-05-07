public class Horaire 
{
    //#region attribut
    private int heure;
    private int minute;
    //#endregion

    //#region constructeur

    /**
     * Dans notre cas il est possible de créer une horaire négative ou supérieure à 23h59
     */
    public Horaire(int heure, int minute) {
        this.heure = heure;
        this.minute = minute;
    }

    public Horaire() {
        this.heure = 0;
        this.minute = 0;
    }
    //#endregion

    //#region accesseurs
    public int getHeure() {
        return this.heure;
    }

    public int getMinute() {
        return this.minute;
    }
    //#endregion

    //#region mutateurs
    public void setHeure(int heure) {
        this.heure = heure;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }
    //#endregion

    //#region méthodes

    /**
     * Retourne l'heure en minute
     * @return des minutes
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
