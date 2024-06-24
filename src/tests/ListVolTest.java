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
import src.core.Horaire;
import src.core.ListAeroport;
import src.core.ListVol;
import src.core.Vol;

public class ListVolTest {

    private ListVol listVol;

    @Before
    public void setUp() {
        this.listVol = new ListVol();
    }

    @Test
    public void testListVolCreation() {
        assertNotNull(this.listVol);
        assertNotNull(this.listVol);
        assertTrue(this.listVol.isEmpty());
    }

    @Test
    public void testFill() {
        Aeroport lyon = new Aeroport("LYS", "Lyon", new Coordonnee(45, 44, 56, 'N'), new Coordonnee(5, 4, 20, 'E'));
        Aeroport paris = new Aeroport("CDG", "Paris Charles de Gaulle", new Coordonnee(49, 0, 14, 'N'), new Coordonnee(2, 34, 54, 'E'));
        Horaire heureDepart = new Horaire(8, 30);
        Vol vol = new Vol("AF030218", lyon, paris, heureDepart, 90);

        ListAeroport listAeroport = new ListAeroport();
        listAeroport.add(lyon);
        listAeroport.add(paris);

        try{
            this.listVol.fill("", listAeroport);
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), IOException.class);
        }

        try {
            this.listVol.fill("src/tests/testData/testListAeroportError.csv", listAeroport);
            fail();
        } catch (Exception e) {
            assertEquals(e.getClass(), ParseException.class);
        }

        try {
            this.listVol.fill("src/tests/testData/testListAeroport.csv", listAeroport);
        } catch (Exception e) {
            fail();
        }
        assertEquals(2, this.listVol.size());
        assertEquals(vol, this.listVol.get(0));
    }
}
