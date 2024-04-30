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
    public void fillFile(String file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            //première ligne kmax
            this.kmax = Integer.parseInt(reader.readLine());
            //deuxième ligne nombre de noeuds
            int nbr_noeuds = Integer.parseInt(reader.readLine());
            //creation des noeuds
            for (int i = 1; i <= nbr_noeuds; i++) {
                this.addNode(Integer.toString(i));
            }
            //creations des arretes
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

    //#region fill with listVol
    public void fillVol(ListVol listVol) {
        for (Vol vol : listVol.getVols()) {
            this.addNode(vol.getCode());
        }
        for (int i = 0; i < listVol.getVols().size(); i++) {
            for (int j = i+1; j < listVol.getVols().size(); j++) {
                if (listVol.getVol(i).collision(listVol.getVol(j))) {
                    this.addEdge(listVol.getVol(i).getCode()+","+listVol.getVol(j).getCode(), listVol.getVol(i).getCode(), listVol.getVol(j).getCode());
                }
            }
        }
    }
    //#endregion
}
