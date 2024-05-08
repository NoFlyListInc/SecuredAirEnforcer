import javax.swing.*;
import java.awt.*;

public class Main 
{
    //#region creation
    private static ListAeroport aeroports = new ListAeroport();
    private static ListVol vols = new ListVol();
    private static Graph graph = new Graph("test");

    private static Aeroport a1 = new Aeroport("MRS", "Marseille", new Coordonnee(43, 26, 8, 'N'), new Coordonnee(5, 12, 49, 'E'));
    private static Aeroport a2 = new Aeroport("BES", "Brest", new Coordonnee(48, 26, 52, 'N'), new Coordonnee(4, 25, 6, 'O'));
    private static Aeroport a3 = new Aeroport("LYS", "Lyon", new Coordonnee(45, 33, 35, 'N'), new Coordonnee(5, 5, 27, 'E'));
    private static Aeroport a4 = new Aeroport("BOD", "Bordeaux", new Coordonnee(44, 49, 42, 'N'), new Coordonnee(0, 42, 56, 'O'));

    private static Vol v1 = new Vol("AF001", a1, a2, new Horaire(7, 33), 81);
    private static Vol v2 = new Vol("AF002", a3, a4, new Horaire(7, 34), 47);

    private static Map map = new Map();
    //#endregion
    
    //#region main
    public static void main(String[] args) {
        aeroports.fill("./Data/aeroports.txt");
        //System.out.println(aeroports);
        vols.fill("./Data/vol-test9.csv", aeroports);
        //System.out.println(vols);

        System.out.println(vols.getVol(6).collision(vols.getVol(2), 15));

        //graph.fillFile("./Data/graph-test0.txt");
        //graph.coloration();
        //graph.fillVol(vols);
        graph.fillMap(aeroports, vols);
        System.out.println(graph.getNodeCount());
        System.out.println(graph.getEdgeCount());
        //graph.hideSoloNode();
        //graph.display();

        map.addGraph(graph, aeroports);

        Frame frame = new JFrame("JXMapViewer Test");
        frame.setLayout(new BorderLayout());
        frame.add(new JScrollPane(map));
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
    //#endregion
}

