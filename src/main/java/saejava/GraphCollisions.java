package saejava;

import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;

public class GraphCollisions 
{
    public static void main(String args[]) {
        System.setProperty("org.graphstream.ui", "swing"); // Utilisez cette ligne si vous utilisez GS 2.0

        Graph graph = new SingleGraph("Example");

        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("AB", "A", "B");

        graph.display();
    }
}
