package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;
import src.core.Horaire;
import src.core.Graphe;
import src.core.ListVol;
import src.core.ListeAeroport;

public class GraphTest {
    private Graphe graph;
    private Graphe graphVol;
    private Graphe littleGraph;

    @Before
    public void setUp() {
        try {
            this.graph = new Graphe("test");
            String path = "src/tests/testData/testGraph.txt";
            this.graph.remplirAvecFichier(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.graphVol = new Graphe("test");
            String pathVol = "src/tests/testData/testListVol2.txt";
            String pathAeroport = "src/tests/testData/testListAeroportFull.txt";

            ListeAeroport listAeroport = new ListeAeroport();
            listAeroport.fill(pathAeroport);

            ListVol listVol = new ListVol();
            listVol.remplir(pathVol, listAeroport);

            this.graphVol.remplirCarte(listAeroport, listVol);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.littleGraph = new Graphe("littleTest");
            littleGraph.addEdge("1-2", "1", "2");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testGetKmax() {
        int testKmax = 11;
        assertEquals(testKmax, graph.getKmax());
    }

    @Test
    public void testWelshPowellColoring() {
        graph.setKdonne(5);
        graph.welshPowell();
        assertEquals(4, graph.getKoptimal());
    }

    @Test
    public void testDsaturColoring() {
        graph.setKdonne(5);
        graph.dSature();
        assertEquals(3, graph.getKoptimal());
    }

    @Test
    public void testGestionNiveauMaxAtteint() {
        try {
            ListVol listVol2 = new ListVol();
            listVol2.remplir("src/tests/testData/testListVol2.txt", new ListeAeroport());

            this.graphVol.remplirAvecListeVol(null, 15);
            int volsMemesNiveauxSize = graph.getVolsMemesNiveaux().getVolsMemesNiveaux().size();
            assertEquals(5, volsMemesNiveauxSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHideSoloNode() {

        littleGraph.cacherNoeudSeul();

        Node node1 = littleGraph.addNode("1");
        Node node2 = littleGraph.addNode("2");
        Node node3 = littleGraph.addNode("3");
        assertTrue(node1.hasAttribute("ui.hide") == false);
        assertTrue(node2.hasAttribute("ui.hide") == false);
        assertTrue(node3.hasAttribute("ui.hide"));
    }

    // Ajoutez d'autres tests pour les méthodes restantes
    public static void main(String args[]) {
        GraphTest test = new GraphTest();
        test.setUp();
        test.testGetKmax();
        test.testWelshPowellColoring();
        test.testDsaturColoring();
        test.testGestionNiveauMaxAtteint();
        test.testHideSoloNode();
    }
}