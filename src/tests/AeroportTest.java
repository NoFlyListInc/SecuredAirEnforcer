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
        Coordonnee coordonnee = new Coordonnee(50, 3, 30, 'N');
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", coordonnee);
        assertNotNull(aeroport);
        assertEquals("CDG", aeroport.getCode());
        assertEquals("Charles de Gaulle", aeroport.getNom());
        assertEquals(coordonnee, aeroport.getPosition());
    }

    // Test de modification du code de l'aéroport
    @Test
    public void testModificationCodeAeroport() {
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", new Coordonnee(50, 3, 30, 'N'));
        aeroport.setCode("ORY");
        assertEquals("ORY", aeroport.getCode());
    }

    // Test de modification du nom de l'aéroport
    @Test
    public void testModificationNomAeroport() {
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", new Coordonnee(50, 3, 30, 'N'));
        aeroport.setNom("Orly");
        assertEquals("Orly", aeroport.getNom());
    }

    // Test de modification de la position de l'aéroport
    @Test
    public void testModificationPositionAeroport() {
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", new Coordonnee(50, 3, 30, 'N'));
        Coordonnee nouvelleCoordonnee = new Coordonnee(48, 2, 20, 'N');
        aeroport.setPosition(nouvelleCoordonnee);
        assertEquals(nouvelleCoordonnee, aeroport.getPosition());
    }

    // Test d'ajout d'un aéroport à la liste d'aéroports
    @Test
    public void testAjoutAeroportListe() {
        ListAeroport listeAeroport = new ListAeroport();
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", new Coordonnee(50, 3, 30, 'N'));
        assertTrue(listeAeroport.addAeroport(aeroport));
        assertEquals(1, listeAeroport.getList().size());
        assertTrue(listeAeroport.getList().contains(aeroport));
    }

    // Test de suppression d'un aéroport de la liste d'aéroports
    @Test
    public void testSuppressionAeroportListe() {
        ListAeroport listeAeroport = new ListAeroport();
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", new Coordonnee(50, 3, 30, 'N'));
        listeAeroport.addAeroport(aeroport);
        assertTrue(listeAeroport.removeAeroport(aeroport));
        assertEquals(0, listeAeroport.getList().size());
        assertFalse(listeAeroport.getList().contains(aeroport));
    }

    // Test de récupération d'un aéroport de la liste d'aéroports par son code
    @Test
    public void testRecuperationAeroportParCode() {
        ListAeroport listeAeroport = new ListAeroport();
        Aeroport aeroport = new Aeroport("CDG", "Charles de Gaulle", new Coordonnee(50, 3, 30, 'N'));
        listeAeroport.addAeroport(aeroport);
        assertEquals(aeroport, listeAeroport.getAeroportByCode("CDG"));
    }

    // Ajoutez d'autres tests selon les fonctionnalités que vous souhaitez tester
}
