package src.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import src.core.Horaire;


public class HoraireTest {

    // Test de la création d'un horaire avec des valeurs valides
    @Test
    public void testCreationHoraire() {
        Horaire horaire = new Horaire(8, 30);
        assertEquals(8, horaire.getHeure());
        assertEquals(30, horaire.getMinute());
    }

    // Test de modification des heures
    @Test
    public void testModificationHeure() {
        Horaire horaire = new Horaire(8, 30);
        horaire.setHeure(10);
        assertEquals(10, horaire.getHeure());
    }

    // Test de modification des minutes
    @Test
    public void testModificationMinute() {
        Horaire horaire = new Horaire(8, 30);
        horaire.setMinute(45);
        assertEquals(45, horaire.getMinute());
    }

    // Test de représentation sous forme de chaîne de caractères
    @Test
    public void testRepresentationString() {
        Horaire horaire = new Horaire(8, 30);
        assertEquals("8h30", horaire.toString());
    }

    // Ajoutez d'autres tests selon les fonctionnalités que vous souhaitez tester
}
