package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import org.junit.Before;
import org.junit.Test;
import src.core.Aeroport;
import src.core.Coordonnee;


public class AeroportTest {

    private Aeroport aeroport;
    private Coordonnee lattitude;
    private Coordonnee longitude;

    @Before
    public void setUp() {
        this.lattitude = new Coordonnee(50, 3, 30, 'N');
        this.longitude = new Coordonnee(2, 30, 30, 'E');
        this.aeroport = new Aeroport("CDG", "Charles de Gaulle", this.lattitude, this.longitude);
    }

    @Test
    public void testCreationAeroport() {
        // Vérification des valeurs
        assertNotNull(this.aeroport);
        assertEquals("CDG", this.aeroport.getCode());
        assertEquals("Charles de Gaulle", this.aeroport.getVille());
        assertEquals(this.lattitude, this.aeroport.getLatitude());
        assertEquals(this.longitude, this.aeroport.getLongitude());

        // Vérification des erreurs lors de valeur null
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport(null, "Charles de Gaulle", this.lattitude, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", null, this.lattitude, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", null, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", this.lattitude, null);
        });

        // Vérification des erreurs lors de valeur vide
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("", "Charles de Gaulle", this.lattitude, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "", this.lattitude, this.longitude);
        });

        // Verification des différentes possibilité de direction des coordonnées (N et E deja testé en haut)
        Coordonnee latitudeSouth = new Coordonnee(50, 3, 30, 'S');
        Coordonnee longitudeWest = new Coordonnee(2, 30, 30, 'W');
        Coordonnee longitudeWest2 = new Coordonnee(2, 30, 30, 'O');
        Aeroport aeroportSouthWest = new Aeroport("CDG", "Charles de Gaulle", latitudeSouth, longitudeWest);
        Aeroport aeroportSouthWest2 = new Aeroport("CDG", "Charles de Gaulle", latitudeSouth, longitudeWest2);
        assertNotNull(aeroportSouthWest);
        assertNotNull(aeroportSouthWest2);

        // Vérification des erreurs lors de valeur de direction incorrecte
        Coordonnee lattitudeWrongDirection = new Coordonnee(50, 3, 30, 'A');
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", lattitudeWrongDirection, longitude);
        });
        Coordonnee longitudeWrongDirection = new Coordonnee(2, 30, 30, 'A');
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", lattitude, longitudeWrongDirection);
        });
    }

    @Test
    public void testGetx() {
        assertEquals(this.aeroport.getx(), 50.05833333333333, 0.0001);
    }

    @Test
    public void testGety() {
        assertEquals(this.aeroport.gety(), 2.5083333333333333, 0.0001);
    }
}
