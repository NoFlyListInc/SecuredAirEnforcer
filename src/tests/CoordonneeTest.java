package src.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import src.core.Coordonnee;
import src.core.ListAeroport;
import src.core.Aeroport;


public class CoordonneeTest {

    // Test de la création de coordonnées avec des valeurs valides
    @Test
    public void testCreationCoordonnee() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        assertEquals(48, coordonnee.getDegreLatitude());
        assertEquals(2, coordonnee.getDegreLongitude());
        assertEquals(30, coordonnee.getMinuteLatitude());
        assertEquals('N', coordonnee.getDirectionLatitude());
    }

    // Test de modification des degrés de latitude
    @Test
    public void testModificationDegreLatitude() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setDegreLatitude(50);
        assertEquals(50, coordonnee.getDegreLatitude());
    }

    // Test de modification des degrés de longitude
    @Test
    public void testModificationDegreLongitude() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setDegreLongitude(3);
        assertEquals(3, coordonnee.getDegreLongitude());
    }

    // Test de modification des minutes de latitude
    @Test
    public void testModificationMinuteLatitude() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setMinuteLatitude(15);
        assertEquals(15, coordonnee.getMinuteLatitude());
    }

    // Test de modification de la direction de latitude
    @Test
    public void testModificationDirectionLatitude() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setDirectionLatitude('S');
        assertEquals('S', coordonnee.getDirectionLatitude());
    }

    // Test de la représentation sous forme de chaîne de caractères
    @Test
    public void testRepresentationString() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        assertEquals("48°2'30\"N", coordonnee.toString());
    }

    // Ajoutez d'autres tests selon les fonctionnalités que vous souhaitez tester
}
