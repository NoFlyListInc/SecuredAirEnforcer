package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Test;
import src.core.Aeroport;
import src.core.Coordonnee;
import src.core.ListAeroport;


public class AeroportTest {

    // Test de la création d'un aéroport avec des coordonnées valides
    @Test
    public void testCreationAeroport() {
        Coordonnee lattitude = new Coordonnee(50, 3, 30, 'N');
        Coordonnee longitude = new Coordonnee(2, 30, 30, 'E');
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", lattitude, longitude);
        assertNotNull(aeroport);
        assertEquals("CDG", aeroport.getCode());
        assertEquals("Charles de Gaulle", aeroport.getVille());
        assertEquals(lattitude, aeroport.getLatitude());
        assertEquals(longitude, aeroport.getLongitude());
    }

    // Test de modification du code de l'aéroport
    @Test
    public void testModificationCodeAeroport() {
        Coordonnee lattitude = new Coordonnee(50, 3, 30, 'N');
        Coordonnee longitude = new Coordonnee(2, 30, 30, 'E');
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", lattitude, longitude);
        aeroport.setCode("ORY");
        assertEquals("ORY", aeroport.getCode());
    }

    // Test d'ajout d'un aéroport à la liste d'aéroports
    @Test
    public void testAjoutAeroportListe() {
        Coordonnee lattitude = new Coordonnee(50, 3, 30, 'N');
        Coordonnee longitude = new Coordonnee(2, 30, 30, 'E');
        ListAeroport listeAeroport = new ListAeroport();
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", lattitude, longitude);
        assertTrue(listeAeroport.addAeroport(aeroport));
        assertEquals(1, listeAeroport.getList().size());
        assertTrue(listeAeroport.getList().contains(aeroport));
    }

    // Test de suppression d'un aéroport de la liste d'aéroports
    @Test
    public void testSuppressionAeroportListe() {
        ListAeroport listeAeroport = new ListAeroport();
        Coordonnee lattitude = new Coordonnee(50, 3, 30, 'N');
        Coordonnee longitude = new Coordonnee(2, 30, 30, 'E');
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", lattitude, longitude);
        listeAeroport.addAeroport(aeroport);
        assertTrue(listeAeroport.removeAeroport(aeroport));
        assertEquals(0, listeAeroport.getList().size());
        assertFalse(listeAeroport.getList().contains(aeroport));
    }

    // Test de récupération d'un aéroport de la liste d'aéroports par son code
    @Test
    public void testRecuperationAeroportParCode() {
        ListAeroport listeAeroport = new ListAeroport();
        Coordonnee lattitude = new Coordonnee(50, 3, 30, 'N');
        Coordonnee longitude = new Coordonnee(2, 30, 30, 'E');
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", lattitude, longitude);
        listeAeroport.addAeroport(aeroport);
        assertEquals(aeroport, listeAeroport.getAeroportByCode("CDG"));
    }

}
