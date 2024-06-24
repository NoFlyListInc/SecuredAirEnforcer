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
    private Coordonnee latitude;
    private Coordonnee longitude;

    @Before
    public void setUp() {
        this.latitude = new Coordonnee(50, 3, 30, 'N');
        this.longitude = new Coordonnee(2, 30, 30, 'E');
        this.aeroport = new Aeroport("CDG", "Charles de Gaulle", this.latitude, this.longitude);
    }

    @Test
    public void testCreationAeroport() {
        // Vérification des valeurs
        assertNotNull(this.aeroport);
        assertEquals("CDG", this.aeroport.getCode());
        assertEquals("Charles de Gaulle", this.aeroport.getVille());
        assertEquals(this.latitude, this.aeroport.getLatitude());
        assertEquals(this.longitude, this.aeroport.getLongitude());

        // Vérification des erreurs lors de valeur null
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport(null, "Charles de Gaulle", this.latitude, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", null, this.latitude, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", null, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", this.latitude, null);
        });

        // Vérification des erreurs lors de valeur vide
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("", "Charles de Gaulle", this.latitude, this.longitude);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "", this.latitude, this.longitude);
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
        Coordonnee lattitudeWrongDirection = new Coordonnee(50, 3, 30, 'W');
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", lattitudeWrongDirection, longitude);
        });
        Coordonnee longitudeWrongDirection = new Coordonnee(2, 30, 30, 'N');
        assertThrows(IllegalArgumentException.class, () -> {
            new Aeroport("CDG", "Charles de Gaulle", latitude, longitudeWrongDirection);
        });
    }

    @Test
    public void testGetx() {
        assertEquals(this.aeroport.getx(), 4.8674362740633, 0.1);
    }

    @Test
    public void testGety() {
        assertEquals(this.aeroport.gety(), 6370.2574585802, 0.1);
    }
}
