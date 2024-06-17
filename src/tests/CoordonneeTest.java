package src.tests;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import src.core.Coordonnee;


public class CoordonneeTest {

    // Test de la création de coordonnées avec des valeurs valides
    @Test
    public void testCreationCoordonnee() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        assertEquals(48, coordonnee.getDegree());
        assertEquals(2, coordonnee.getMinute());
        assertEquals(30, coordonnee.getSeconde());
        assertEquals('N', coordonnee.getDirection());
    }

    // Test de modification des degrés de latitude
    @Test
    public void testModificationDegreLatitude() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setDegree(50);
        assertEquals(50, coordonnee.getDegree());
    }

    // Test de modification des degrés
    @Test
    public void testModificationDegre() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setDegree(3);
        assertEquals(3, coordonnee.getDegree());
    }

    // Test de modification des minutes
    @Test
    public void testModificationMinuteLatitude() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setMinute(15);
        assertEquals(15, coordonnee.getMinute());
    }

    // Test de modification de la direction
    @Test
    public void testModificationDirectionLatitude() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        coordonnee.setDirection('S');
        assertEquals('S', coordonnee.getDirection());
    }

    // Test de la représentation sous forme de chaîne de caractères
    @Test
    public void testRepresentationString() {
        Coordonnee coordonnee = new Coordonnee(48, 2, 30, 'N');
        assertEquals("48°2'30\"N", coordonnee.toString());
    }

}
