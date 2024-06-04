package src.core;
//import graphstream tools
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
//import reader files tools 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList; // pour DSature
import java.util.Comparator;
import java.util.HashMap;//pour DSature
import java.util.HashSet; //pour DSature
import java.util.PriorityQueue; //pour DSature
import java.util.Iterator; // pour DSature


public class Graph extends SingleGraph {
    // #region attribut
    private int kmax;
    private int koptimal; // nombre de coloration optimal trouvé par dsature
    private ArrayList<HashMap<Vol, Vol>> volsMemesNiveaux;

    // #endregion

    // #region constructeur
    public Graph(String id) {
        super(id);
        this.kmax = 0;
        this.koptimal = 0;
        this.volsMemesNiveaux = new ArrayList<HashMap<Vol, Vol>>();

    }
    // #endregion

    // #region accesseurs
    public int getKmax() {
        return this.kmax;
    }

    public int getKoptimal() {
        return this.koptimal;
    }
    public ArrayList<HashMap<Vol, Vol>> getVolsMemesNiveaux(){
        return this.volsMemesNiveaux;
    }
    // #endregion

    // #region mutateurs
    public void setKmax(int kmax) {
        this.kmax = kmax;
    }

    public void setKoptimal(int koptimal) {
        this.koptimal = koptimal;
    }
    // #endregion

    // #region coloration
    /**
     * La classe NodeInfo stocke des informations sur un nœud, sa saturation, son
     * degré et le
     * nœud lui-même.
     */
    private class NodeInfo {
        int saturation;
        int degree;
        Node node;

        NodeInfo(int saturation, int degree, Node node) {
            this.saturation = saturation;
            this.degree = degree;
            this.node = node;
        }
    }

    /**
     * La classe MaxSat implémente une interface Comparator pour comparer les objets
     * NodeInfo en
     * fonction des valeurs de saturation et de degré.
     */
    private class MaxSat implements Comparator<NodeInfo> {
        public int compare(NodeInfo n1, NodeInfo n2) {
            if (n1.saturation != n2.saturation) {
                return Integer.compare(n2.saturation, n1.saturation);
            }
            return Integer.compare(n2.degree, n1.degree);
        }
    }

    
    /** 
     * La fonction ajoute à volsMemeNiveaux une paire de vol si elle est en risque de collision
     * et que kmax à été atteint
     * @param vol1 premier vol
     * @param vol2 deuxième vol, qui est en risque de collision avec le premier
     */
    private void gestionNiveauMaxAtteint(Vol vol1, Vol vol2) {
        HashMap<Vol, Vol> paireCollision = new HashMap<Vol, Vol>();
        paireCollision.put(vol1, vol2);
        this.volsMemesNiveaux.add(paireCollision);
    }

    /**
     * Procédure colorant le graphe selon l'algorithme de coloration DSATURE.
     * Cette méthode utilise l'algorithme DSATURE pour attribuer une couleur à
     * chaque nœud du graphe.
     * Chaque couleur représente un niveau de vol.
     * 
     * L'algorithme DSATURE fonctionne de la manière suivante :
     * 1. Initialise les structures de données nécessaires.
     * 2. Parcourt les nœuds du graphe dans un ordre spécifique.
     * 3. Pour chaque nœud, vérifie les couleurs utilisées par ses voisins.
     * 4. Sélectionne la première couleur disponible pour le nœud courant.
     * 5. Met à jour les structures de données en conséquence.
     * 6. Répète les étapes 3 à 5 jusqu'à ce que tous les nœuds soient colorés.
     * 
     * @param kdonne niveau.x de vol.s à utiliser au maximum
     * 
     * Après l'exécution de cette méthode, chaque nœud du graphe sera
     * coloré et le niveau de vol correspondant sera affiché.
     * Le nombre de coloration optimal est stocké dans la variable
     * koptimal
     */
    public void dSature(int kdonne) {

        int n = this.getNodeCount(); // Nombre de nœuds dans le graphe
        boolean[] couleursVoisins = new boolean[n]; // Tableau pour vérifier les couleurs utilisées par les voisins
        int[] couleurs = new int[n]; // Tableau pour les couleurs des nœuds
        int[] degreNoeud = new int[n]; // Tableau pour les degrés des nœuds
        HashSet<Integer>[] adjCols = new HashSet[n]; // Tableau de sets pour les couleurs adjacentes
        PriorityQueue<NodeInfo> queueDePriorite = new PriorityQueue<>(new MaxSat()); // File de priorité pour les nœuds
        HashMap<Node, Integer> nodeIndexMap = new HashMap<>(); // Map pour associer les nœuds à leurs indices
        Node[] indexNoeudMap = new Node[n]; // Tableau pour associer les indices aux nœuds

        int idx = 0;

        /**
         * instancier tous les noeuds à une couleur nulle (-1)
         */
        for (Node node : this) {
            couleurs[idx] = -1;
            degreNoeud[idx] = node.getDegree();
            adjCols[idx] = new HashSet<>();
            nodeIndexMap.put(node, idx);
            indexNoeudMap[idx] = node;
            queueDePriorite.add(new NodeInfo(0, degreNoeud[idx], node));
            idx++;
        }

        if (!queueDePriorite.isEmpty()) {
            NodeInfo maxPtr = queueDePriorite.poll();
            Iterator<Node> neighborIterator = maxPtr.node.getNeighborNodeIterator();
            Node neighbor = null;
            int i;

            while (!queueDePriorite.isEmpty()) {
                maxPtr = queueDePriorite.poll();
                int u = nodeIndexMap.get(maxPtr.node);

                // Utilisation de l'itérateur pour les voisins
                neighborIterator = maxPtr.node.getNeighborNodeIterator();
                while (neighborIterator.hasNext()) {
                    neighbor = neighborIterator.next();
                    int v = nodeIndexMap.get(neighbor);
                    if (couleurs[v] != -1) {
                        couleursVoisins[couleurs[v]] = true;
                    }
                }

                i = 0;
                while (i < couleursVoisins.length) {
                    
                        if (!couleursVoisins[i]) {
                            if (i + 1 > this.getKoptimal()) {
                                this.setKoptimal(i + 1);
                            }

                            if (i > kdonne){
                                gestionNiveauMaxAtteint((Vol) maxPtr.node.getAttribute("vol"), (Vol) neighbor.getAttribute("vol"));
                                i++;
                            }
                            break;
                        }
                        i++;
                    
                }

                // Réinitialisation de l'itérateur pour les voisins
                neighborIterator = maxPtr.node.getNeighborNodeIterator();
                while (neighborIterator.hasNext()) {
                    neighbor = neighborIterator.next();
                    int v = nodeIndexMap.get(neighbor);
                    if (couleurs[v] != -1) {
                        couleursVoisins[couleurs[v]] = false;
                    }
                }

                couleurs[u] = i;
                maxPtr.node.setAttribute("ui.style",
                        "fill-color: rgb(" + (i * 50) % 255 + ", " + (i * 80) % 255 + ", " + (i * 120) % 255 + ");");

                // Réinitialisation de l'itérateur pour les voisins
                neighborIterator = maxPtr.node.getNeighborNodeIterator();
                while (neighborIterator.hasNext()) {
                    neighbor = neighborIterator.next();
                    int v = nodeIndexMap.get(neighbor);
                    if (couleurs[v] == -1) {
                        queueDePriorite.remove(new NodeInfo(adjCols[v].size(), degreNoeud[v], neighbor));
                        adjCols[v].add(i);
                        degreNoeud[v]--;
                        queueDePriorite.add(new NodeInfo(adjCols[v].size(), degreNoeud[v], neighbor));
                    }
                }
            }
        }

        for (int u = 0; u < n; u++) {
            System.out.println("Vol " + indexNoeudMap[u].getId() + " ---> Niveau de vol : " + couleurs[u]);
        }

    }

    // #endregion

    // #region fill the graph

    /**
     * Rempli le graph depuis un fichier graph-testx.txt
     * 
     * @param file adresse du fichier
     */
    public void fillFile(String file) {
        this.clear();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            // première ligne => kmax
            this.kmax = Integer.parseInt(reader.readLine());
            // deuxième ligne => nombre de noeuds
            int nbr_noeuds = Integer.parseInt(reader.readLine());
            // creation des noeuds
            for (int i = 1; i <= nbr_noeuds; i++) {
                this.addNode(Integer.toString(i)).addAttribute("ui.label", i); // ajout du label
            }
            // creations des arretes
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                this.addEdge(parts[0] + "," + parts[1], parts[0], parts[1]);
            }
            reader.close();
        } catch (IOException e) { // erreur de lecture du fichier
            e.printStackTrace();
        }
    }

    /**
     * Rempli le graph des collisions depuis une liste de vol
     * 
     * @param listVol objet ListVol
     */
    public void fillVol(ListVol listVol) {
        this.clear();
        // creation des noeuds
        for (Vol vol : listVol.getList()) {
            this.addNode(vol.getCode()).addAttribute("ui.label", vol.getCode()); // ajout du label
        }
        // creation des arretes
        for (int i = 0; i < listVol.getList().size(); i++) {
            for (int j = i + 1; j < listVol.getList().size(); j++) {
                // si les vols i et j sont en collision
                if ((listVol.getVol(i).collision(listVol.getVol(j), 15)) != null) {
                    this.addEdge(listVol.getVol(i).getCode() + "," + listVol.getVol(j).getCode(),
                            listVol.getVol(i).getCode(), listVol.getVol(j).getCode()); // code de l'arrete =
                                                                                       // "codei,codej"
                }
            }
        }
    }

    /**
     * Rempli le graph des aeroports et des vols pour une représentation
     * géographique
     * 
     * @param listAeroport objet ListAeroport
     * @param listVol      objet ListVol
     */
    public void fillMap(ListAeroport listAeroport, ListVol listVol) {
        this.clear();
        // creation des noeuds
        for (Aeroport aeroport : listAeroport.getList()) {
            Node noeud = this.addNode(aeroport.getCode());
            noeud.addAttribute("ui.label", aeroport.getCode()); // ajout du label
        }
        // creation des arretes
        for (Vol vol : listVol.getList()) {
            // si l'arrete n'existe pas on l'ajoute
            if (this.getNode(vol.getDepart().getCode()).getEdgeBetween(vol.getArrivee().getCode()) == null) {
                this.addEdge(vol.getCode(), vol.getDepart().getCode(), vol.getArrivee().getCode()); // code de l'arrete
                                                                                                    // = code du vol
            }
        }
    }
    // #endregion

    // #region methode

    /**
     * 
     */
    public void hideSoloNode() {
        // parcours de tous les noeuds
        for (Node node : this.getEachNode()) {
            // si le noeud est de degré 0 on le rend non visible
            if (node.getDegree() == 0) {
                node.addAttribute("ui.hide");
            }
        }
    }
    // #endregion
}
