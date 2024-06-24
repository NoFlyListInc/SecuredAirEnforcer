package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Before;
import org.junit.Test;

import src.core.Aeroport;
import src.core.Coordonnee;
import src.core.ListeAeroport;

public class ListAeroportTest {

    private ListeAeroport listAeroport;

    @Before
    public void setUp() {
        this.listAeroport = new ListeAeroport();
    }

    @Test
    public void testListVolCreation() {
        assertNotNull(this.listAeroport);
        assertNotNull(this.listAeroport);
        assertTrue(this.listAeroport.isEmpty());
    }

    @Test
    public void testFill() {
        Aeroport lyon = new Aeroport("LYS", "Lyon", new Coordonnee(45, 44, 56, 'N'), new Coordonnee(5, 4, 20, 'E'));
        Aeroport paris = new Aeroport("CDG", "Paris Charles de Gaulle", new Coordonnee(49, 0, 14, 'N'), new Coordonnee(2, 34, 54, 'E'));

        try{
            this.listAeroport.fill("");
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), IOException.class);
        }

        try {
            this.listAeroport.fill("testListAeroportError.csv");
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), ParseException.class);
        }

        try {
            this.listAeroport.fill("testListAeroport.csv");
        } catch (Exception e) {
            fail();
        }
        assertEquals(2, this.listAeroport.size());
        assertEquals(lyon, this.listAeroport.get(0));
        assertEquals(paris, this.listAeroport.get(1));
    }
}
