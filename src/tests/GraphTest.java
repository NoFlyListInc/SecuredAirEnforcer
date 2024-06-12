package src.tests;

import org.junit.Before;
import org.junit.Test;
import org.graphstream.graph.Node;

import java.util.ArrayList;
import java.util.HashMap;

import src.core.Graph;
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
        Vol vol1 = new Vol("Vol1");
        Vol vol2 = new Vol("Vol2");
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
}
