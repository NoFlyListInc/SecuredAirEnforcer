package saejava;

public class Horaire 
{
    //#region attribut
    private int heure;
    private int minute;
    //#endregion

    //#region constructeur
    public Horaire(int heure, int minute) {
        if (heure < 0 || heure > 23 || minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Heure non valide.");
        }
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
        if (heure < 0 || heure > 23) {
            throw new IllegalArgumentException("Heure non valide.");
        }
        this.heure = heure;
    }

    public void setMinute(int minute) {
        if (minute < 0 || minute > 59) {
            throw new IllegalArgumentException("Minute non valide.");
        }
        this.minute = minute;
    }
    //#endregion

    //#region méthodes
    public int getHeureEnMinute() {
        return this.heure * 60 + this.minute;
    }

    public void ajouterMinutes(int minutes) {
        int totalMinutes = this.getHeureEnMinute() + minutes;
        int nouvelleHeure = totalMinutes / 60;
        int nouvelleMinute = totalMinutes % 60;
        
        if (nouvelleHeure < 0 || nouvelleHeure > 23 || nouvelleMinute < 0 || nouvelleMinute > 59) {
            throw new IllegalArgumentException("Heure non valide.");
        }
        
        this.heure = nouvelleHeure;
        this.minute = nouvelleMinute;
    }
    //#endregion

    //#region affichage
    public String toString() {
        String heureString = (this.heure < 10) ? "0" + this.heure : String.valueOf(this.heure);
        String minuteString = (this.minute < 10) ? "0" + this.minute : String.valueOf(this.minute);
        return heureString + "h" + minuteString;
    }
    //#endregion
}
