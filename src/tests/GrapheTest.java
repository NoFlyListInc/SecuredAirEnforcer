package src.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.graphstream.graph.Node;
import org.junit.Before;
import org.junit.Test;
import src.core.Graphe;
import src.core.ListeVol;
import src.core.ListeAeroport;

import java.io.File;

public class GrapheTest {
    private Graphe graph;
    private Graphe graphVol;
    private Graphe littleGraph;

    @Before
    public void setUp() {
        try {
            this.graph = new Graphe("test");
            String path = "src/tests/testData/testGraph.txt";
            if (!new File(path).exists()) {
                fail("File not found: " + path);
            }
            this.graph.remplirAvecFichier(path);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            fail("Exception during setup: " + e.getMessage());
        }

        try {
            this.graphVol = new Graphe("test");
            String pathVol = "src/tests/testData/testListVol2.csv";
            String pathAeroport = "src/tests/testData/testListAeroportFull.txt";

            if (!new File(pathVol).exists()) {
                fail("File not found: " + pathVol);
            }
            if (!new File(pathAeroport).exists()) {
                fail("File not found: " + pathAeroport);
            }

            ListeAeroport listAeroport = new ListeAeroport();
            listAeroport.remplir(pathAeroport);

            ListeVol listVol = new ListeVol();
            listVol.remplir(pathVol, listAeroport);

            this.graphVol.remplirCarte(listAeroport, listVol);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during setup: " + e.getMessage());
        }

        try {
            this.littleGraph = new Graphe("littleTest");
            littleGraph.addNode("1");
            littleGraph.addNode("2");
            littleGraph.addEdge("1-2", "1", "2");
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during setup: " + e.getMessage());
        }
    }

    @Test
    public void testGetKmax() {
        int testKmax = 20; // Ensure this is the correct expected value based on your test data
        assertEquals(testKmax, graph.getKmax());
    }

    @Test
    public void testColorationWelshPowell() {
        graph.setKdonne(5);
        graph.welshPowell();
        assertEquals(4, graph.getKoptimal());
    }

    @Test
    public void testColorationDsatur() {
        graph.setKdonne(5);
        graph.dSature();
        assertEquals(3, graph.getKoptimal());
    }

    @Test
    public void testGestionNiveauMaxAtteint() {
        try {
            ListeVol listVol2 = new ListeVol();
            ListeAeroport listAeroport = new ListeAeroport();
            String pathVol2 = "src/tests/testData/testListVol2.csv";
            String pathAeroport = "src/tests/testData/testListAeroportFull.txt";
            if (!new File(pathVol2).exists()) {
                fail("File not found: " + pathVol2);
            }
            if (!new File(pathAeroport).exists()) {
                fail("File not found: " + pathAeroport);
            }
            listAeroport.remplir(pathAeroport);
            listVol2.remplir(pathVol2, listAeroport);

            this.graphVol.remplirAvecListeVol(listVol2, 15);
            graphVol.setKdonne(1);
            graphVol.dSature();
            int volsMemesNiveauxSize = graphVol.getVolsMemesNiveaux().getVolsMemesNiveaux().size();
            assertEquals(5, volsMemesNiveauxSize);
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception during test: " + e.getMessage());
        }
    }

    @Test
    public void testCacherNoeudSeul() {
        littleGraph.cacherNoeudSeul();

        Node node1 = littleGraph.getNode("1");
        Node node2 = littleGraph.getNode("2");

        assertTrue(!node1.hasAttribute("ui.hide"));
        assertTrue(!node2.hasAttribute("ui.hide"));
    }

    // Ajoutez d'autres tests pour les méthodes restantes
    public static void main(String args[]) {
        GrapheTest test = new GrapheTest();
        test.setUp();
        test.testGetKmax();
        test.testColorationWelshPowell();
        test.testColorationDsatur();
        test.testGestionNiveauMaxAtteint();
        test.testCacherNoeudSeul();
    }
}
