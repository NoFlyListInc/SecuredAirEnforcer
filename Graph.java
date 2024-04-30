import org.graphstream.graph.implementations.SingleGraph;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Graph extends SingleGraph
{
    //#region attribut
    private int kmax;
    //#endregion

    //#region constructeur
    public Graph(String id) {
        super(id);
    }
    //#endregion

    //#region accesseurs
    public int getKmax() {
        return this.kmax;
    }
    //#endregion

    //#region mutateurs
    public void setKmax(int kmax) {
        this.kmax = kmax;
    }
    //#endregion

    //#region coloration
    public void coloration() {
        this.getNode("1").setAttribute("ui.style", "fill-color: red;");
    }
    //#endregion

    //#region fill with a file
    public void fill(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            this.kmax = Integer.parseInt(reader.readLine());
            int nbr_noeuds = Integer.parseInt(reader.readLine());
            for (int i = 1; i <= nbr_noeuds; i++) {
                this.addNode(Integer.toString(i));
            }
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                this.addEdge(parts[0]+","+parts[1], parts[0], parts[1]);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //#endregion
}
