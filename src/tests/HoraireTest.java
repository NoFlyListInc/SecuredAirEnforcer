package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.Test;
import src.core.Horaire;


public class HoraireTest {

    Horaire horaire;

    @Before
    public void setUp() {
        this.horaire = new Horaire(8, 30);
    }

    @Test
    public void testCreationHoraire() {
        // Vérification des valeurs
        assertEquals(8, this.horaire.getHeure());
        assertEquals(30, this.horaire.getMinute());

        // Vérification des erreurs lors de valeur incorrecte
        // heure < 0
        assertThrows(IllegalArgumentException.class, () -> {
            new Horaire(-1, 30);
        });
        // minute < 0
        assertThrows(IllegalArgumentException.class, () -> {
            new Horaire(8, -1);
        });
        // minute > 60
        assertThrows(IllegalArgumentException.class, () -> {
            new Horaire(8, 61);
        });
        // heure n'est pas un nombre
        assertThrows(IllegalArgumentException.class, () -> {
            new Horaire("a", "30");
        });
        // minute n'est pas un nombre
        assertThrows(IllegalArgumentException.class, () -> {
            new Horaire("8", "a");
        });
    }

    @Test
    public void testGetEnMinute() {
        assertEquals(510, this.horaire.getEnMinute());
    }

    @Test
    public void testAjouterMinutes() {
        this.horaire.ajouterMinutes(30);
        assertEquals(9, this.horaire.getHeure());
        assertEquals(0, this.horaire.getMinute());

        // Vérification des erreurs lors de valeur incorrecte
        // minutes < 0
        assertThrows(IllegalArgumentException.class, () -> {
            this.horaire.ajouterMinutes(-1);
        });
    }
}
