package src.tests;

import org.junit.Before;
import org.junit.Test;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.HashMap;

import src.core.Aeroport;
import src.core.Coordonnee;
import src.core.Graph;
import src.core.Horaire;
import src.core.ListAeroport;
import src.core.ListVol;
import src.core.Vol;

import static org.junit.Assert.*;

public class GraphTest {
    private Graph graph;

    @Before
    public void setUp() {
        graph = new Graph("testGraph");

    }

    @Test
    public void testGetKmax() {
        assertEquals(20, graph.getKmax());
    }

    @Test
    public void testSetKmax() {
        graph.setKmax(30);
        assertEquals(30, graph.getKmax());
    }

    @Test
    public void testGetKoptimal() {
        assertEquals(1, graph.getKoptimal());
    }

    @Test
    public void testSetKoptimal() {
        graph.setKoptimal(5);
        assertEquals(5, graph.getKoptimal());
    }

    @Test
    public void testGetVolsMemesNiveaux() {
        assertTrue(graph.getVolsMemesNiveaux().isEmpty());
    }

    @Test
    public void testGestionNiveauMaxAtteint() {
        //Set up aéroport
        Coordonnee lattitude1 = new Coordonnee(50, 3, 30, 'N');
        Coordonnee longitude1 = new Coordonnee(2, 30, 30, 'E');
        Aeroport aeroport1 = new Aeroport("CDG", "Charles de Gaulle", lattitude1, longitude1);
        Coordonnee lattitude2 = new Coordonnee(48, 2, 30, 'N');
        Coordonnee longitude2 = new Coordonnee(2, 30, 30, 'E');
        Aeroport aeroport2 = new Aeroport("ORY", "Orly", lattitude2, longitude2);
        
        //Set up listVol
        ListVol listVol = new ListVol();
        Horaire horaire1 = new Horaire(12, 0);
        Horaire horaire2 = new Horaire(12, 2);
        Vol vol1 = new Vol("Vol1", aeroport1, aeroport2, horaire1, 5);
        Vol vol2 = new Vol("Vol2", aeroport2, aeroport1, horaire2, 6);
        listVol.add(vol1);
        listVol.add(vol2);
        
        //Set up graph
        graph.fillVol(listVol, 5);

        graph.gestionNiveauMaxAtteint(vol1, vol2);
        ArrayList<HashMap<Vol, Vol>> volsMemesNiveaux = graph.getVolsMemesNiveaux();
        assertEquals(1, volsMemesNiveaux.size());
        assertTrue(volsMemesNiveaux.get(0).containsKey(vol1));
        assertTrue(volsMemesNiveaux.get(0).containsValue(vol2));
    }

    @Test
    public void testHideSoloNode() {
        Node node1 = graph.addNode("1");
        Node node2 = graph.addNode("2");
        Node node3 = graph.addNode("3");
        graph.addEdge("1-2", "1", "2");

        graph.hideSoloNode();

        assertTrue(node1.hasAttribute("ui.hide") == false);
        assertTrue(node2.hasAttribute("ui.hide") == false);
        assertTrue(node3.hasAttribute("ui.hide"));
    }

    // Ajoutez d'autres tests pour les méthodes restantes
    public static void main(String args[]) {
        GraphTest test = new GraphTest();
        test.setUp();
        test.testGetKmax();
        test.testSetKmax();
        test.testGetKoptimal();
        test.testSetKoptimal();
        test.testGetVolsMemesNiveaux();
        test.testGestionNiveauMaxAtteint();
        test.testHideSoloNode();
    }
}
