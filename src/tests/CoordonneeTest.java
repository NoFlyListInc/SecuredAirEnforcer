package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

import org.junit.Before;
import org.junit.Test;
import src.core.Coordonnee;


public class CoordonneeTest {

    private Coordonnee coordonnee;

    @Before
    public void setUp() {
        this.coordonnee = new Coordonnee(48, 2, 30, 'N');
    }

    @Test
    public void testCreationCoordonnee() {
        // Vérification des valeurs
        assertNotNull(this.coordonnee);
        assertEquals(48, this.coordonnee.getDegree());
        assertEquals(2, this.coordonnee.getMinute());
        assertEquals(30, this.coordonnee.getSeconde());
        assertEquals('N', this.coordonnee.getDirection());

        // Vérification des erreurs lors de valeurs incorrectes
        // Degré négatif
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee(-1, 2, 30, 'N');
        });
        // Degré > 180
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee(181, 2, 30, 'N');
        });
        // Minute négative
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee(48, -1, 30, 'N');
        });
        // Minute > 60
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee(48, 61, 30, 'N');
        });
        // Seconde négative
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee(48, 2, -1, 'N');
        });
        // Seconde > 60
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee(48, 2, 61, 'N');
        });
        // Direction incorrecte
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee(48, 2, 30, 'A');
        });

        // Vérification des erreurs lors de String qui ne sont pas des chiffres
        // Degré
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee("a", "2", "30", "N");
        });
        // Minute
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee("48", "a", "30", "N");
        });
        // Seconde
        assertThrows(IllegalArgumentException.class, () -> {
            new Coordonnee("48", "2", "a", "N");
        });

        // Vérification des directions possibles (N deja testé en haut)
        Coordonnee coordonneeSouth = new Coordonnee(48, 2, 30, 'S');
        assertNotNull(coordonneeSouth);
        Coordonnee coordonneeWest = new Coordonnee(48, 2, 30, 'W');
        assertNotNull(coordonneeWest);
        Coordonnee coordonneeWest2 = new Coordonnee(48, 2, 30, 'O');
        assertNotNull(coordonneeWest2);
        Coordonnee coordonneeEast = new Coordonnee(48, 2, 30, 'E');
        assertNotNull(coordonneeEast);
    }

    @Test
    public void testGetDecimal() {
        assertEquals(48.041666666666664, this.coordonnee.getDecimal(), 0.0001);
    }

    @Test
    public void testGetDecimalRadians() {
        assertEquals(0.8377580409572781, this.coordonnee.getDecimalRadians(), 0.0001);
    }
}
