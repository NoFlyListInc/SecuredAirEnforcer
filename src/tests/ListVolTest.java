package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

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
    private Aeroport lyon;
    private Aeroport paris;
    private Horaire heureDepart;
    private Vol vol;

    @Before
    public void setUp() {
        listVol = new ListVol();
        lyon = new Aeroport("LYS", "Lyon", new Coordonnee(45, 44, 56, 'N'), new Coordonnee(5, 4, 20, 'E'));
        paris = new Aeroport("CDG", "Paris Charles de Gaulle", new Coordonnee(49, 0, 14, 'N'), new Coordonnee(2, 34, 54, 'E'));
        heureDepart = new Horaire(8, 30);
        vol = new Vol("AF030218", lyon, paris, heureDepart, 90);
    }

    @Test
    public void testListVolCreation() {
        assertNotNull(listVol);
        assertNotNull(listVol.getList());
        assertTrue(listVol.getList().isEmpty());
    }

    @Test
    public void testAddVol() {
        assertTrue(listVol.addVol(vol));
        assertEquals(1, listVol.getList().size());
        assertTrue(listVol.getList().contains(vol));
    }

    @Test
    public void testRemoveVol() {
        listVol.addVol(vol);
        assertTrue(listVol.removeVol(vol));
        assertTrue(listVol.getList().isEmpty());
    }

    @Test
    public void testGetVol() {
        listVol.addVol(vol);
        assertEquals(vol, listVol.getVol(0));
    }

    @Test
    public void testGetVolByCode() {
        listVol.addVol(vol);
        assertEquals(vol, listVol.getVolByCode("AF030218"));
    }

    @Test
    public void testFill() {
        ListAeroport listAeroport = new ListAeroport();
        listAeroport.addAeroport(lyon);
        listAeroport.addAeroport(paris);

        listVol.fill("testfile.txt", listAeroport);

        ArrayList<Vol> vols = listVol.getList();
        assertEquals(1, vols.size());
        assertEquals(vol, vols.get(0));
    }

    // Add more tests as needed
}
