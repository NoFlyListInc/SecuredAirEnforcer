package src.core;

//#region Imports
//import graphstream class
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
//import reader files class 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import list class
import java.util.Comparator;
import java.util.HashMap;//pour DSature
import java.util.HashSet; //pour DSature
import java.util.PriorityQueue; //pour DSature
import java.util.Set;
import java.util.Iterator; // pour DSature
import java.util.List;
import java.awt.Color;

//#endregion

/**
 * Classe qui défini un Graph
 * 
 * @attributs kmax, koptimal, volsMemesNiveaux
 * @methodes getKmax, getKoptimal, getVolsMemesNiveaux, setKmax, setKoptimal,
 *           dSature, fillFile, fillVol, fillMap, hideSoloNode
 * @extends SingleGraph
 * @author LACROIX Xavier et NOUVEL Armand
 */
public class Graph extends SingleGraph {
    // #region Attribut

    /**
     * Nombre maximum de couleurs possibles pour la coloration
     */
    private int kmax;

    /**
     * Nombre de coloration optimal trouvé arpès coloration
     */
    private int koptimal;

    /**
     * Nombre de coloration donné par l'utilisateur
     */
    private int kdonne; // nombre de coloration donné par l'utilisateur

    /**
     * Liste des vols en risque de collision et qui ont le même niveau de vol
     */
    private VolsMemesNiveaux volsMemesNiveaux;

    // #endregion

    // #region Constructeur

    /**
     * Constructeur de la classe Graph
     * 
     * @param id String, identifiant du graph
     */
    public Graph(String id) {
        super(id);
        this.kmax = 20; // nombre max de niveaux de vols possibles par défaut
        this.koptimal = 1;
        this.kdonne = 20;
        this.volsMemesNiveaux = new VolsMemesNiveaux();

    }
    // #endregion

    // #region Accesseurs

    /**
     * Retourne le kmax
     * 
     * @return int
     */
    public int getKmax() {
        return this.kmax;
    }

    /**
     * Retourne le koptimal
     * 
     * @return int
     */
    public int getKoptimal() {
        return this.koptimal;
    }

    /**
     * Retourne le kdonne
     * 
     * @return int
     */
    public int getKdonne() {
        return this.kdonne;
    }

    /**
     * Retourne les vols de même niveaux
     * 
     * @return VolsMemesNiveaux
     */
    public VolsMemesNiveaux getVolsMemesNiveaux() {
        return this.volsMemesNiveaux;
    }

    // #endregion

    // #region Mutateurs

    /**
     * Modifie le kmax
     * 
     * @param kmax int
     */
    public void setKmax(int kmax) {
        this.kmax = kmax;
    }

    /**
     * Modifie le koptimal
     * 
     * @param koptimal int
     */
    public void setKoptimal(int koptimal) {
        this.koptimal = koptimal;
    }

    /**
     * Modifie le kdonne
     * 
     * @param kdonne int
     */
    public void setKdonne(int kdonne) {
        this.kdonne = kdonne;
    }
    // #endregion

    // #region Coloration
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
    // #region DSature

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
     *               Après l'exécution de cette méthode, chaque nœud du graphe sera
     *               coloré et le niveau de vol correspondant sera affiché.
     *               Le nombre de coloration optimal est stocké dans la variable
     *               koptimal
     * @author Xavier LACROIX
     */
    public void dSature() {
        int n = this.getNodeCount(); // Nombre de nœuds dans le graphe
        boolean[] couleursVoisins = new boolean[n]; // Tableau pour vérifier les couleurs utilisées par les voisins
        int[] couleurs = new int[n]; // Tableau pour les couleurs des nœuds
        String[] couleursHex = {
                "#e6194b", "#3cb44b", "#ffe119", "#4363d8", "#f58231", "#911eb4", "#46f0f0", "#f032e6",
                "#bcf60c", "#fabebe", "#008080", "#e6beff", "#9a6324", "#fffac8", "#800000", "#aaffc3",
                "#808000", "#ffd8b1", "#000075", "#808080", "#ffffff", "#000000"
        }; // Tableau de couleurs pour les nœuds
        Color[] cols = new Color[getKdonne()];
        for (int i = 0; i < getKdonne(); i++) {
            cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        }
        int[] degreNoeud = new int[n]; // Tableau pour les degrés des nœuds
        HashSet<Integer>[] adjCols = new HashSet[n]; // Tableau de sets pour les couleurs adjacentes
        PriorityQueue<NodeInfo> queueDePriorite = new PriorityQueue<>(new MaxSat()); // File de priorité pour les nœuds
        HashMap<Node, Integer> nodeIndexMap = new HashMap<>(); // Map pour associer les nœuds à leurs indices
        Node[] indexNoeudMap = new Node[n]; // Tableau pour associer les indices aux nœuds

        int idx = 0;

        // Instancier tous les noeuds à une couleur nulle (-1)
        for (Node node : this) {
            couleurs[idx] = -1;
            degreNoeud[idx] = node.getDegree();
            adjCols[idx] = new HashSet<>();
            nodeIndexMap.put(node, idx);
            indexNoeudMap[idx] = node;
            queueDePriorite.add(new NodeInfo(0, degreNoeud[idx], node));
            idx++;
        }

        while (!queueDePriorite.isEmpty()) {
            NodeInfo maxPtr = queueDePriorite.poll();
            int u = nodeIndexMap.get(maxPtr.node);

            // Réinitialiser le tableau couleursVoisins pour chaque nœud
            // Arrays.fill(couleursVoisins, false);

            // Utilisation de l'itérateur pour les voisins
            Iterator<Node> neighborIterator = maxPtr.node.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int v = nodeIndexMap.get(neighbor);
                if (couleurs[v] != -1) {
                    couleursVoisins[couleurs[v]] = true;
                }
            }

            int i = 0;
            while (i < couleursVoisins.length) {
                if (!couleursVoisins[i]) {
                    break;
                }
                i++;
            }

            if (i >= this.kdonne) {
                // Si la couleur choisie dépasse kdonne, chercher la couleur avec le moins de
                // collisions
                int minCollisions = Integer.MAX_VALUE;
                int bestColor = -1;
                for (int j = 0; j < this.kdonne; j++) {
                    int collisions = 0;
                    neighborIterator = maxPtr.node.getNeighborNodeIterator();
                    while (neighborIterator.hasNext()) {
                        Node neighbor = neighborIterator.next();
                        int v = nodeIndexMap.get(neighbor);
                        if (couleurs[v] == j) {
                            collisions++;
                        }
                    }
                    if (collisions < minCollisions) {
                        minCollisions = collisions;
                        bestColor = j;
                    }
                }
                i = bestColor;

                // Ajoute les voisins ayant la même couleur à volsMemesNiveaux
                neighborIterator = maxPtr.node.getNeighborNodeIterator();
                while (neighborIterator.hasNext()) {
                    Node neighbor = neighborIterator.next();
                    int v = nodeIndexMap.get(neighbor);
                    if (couleurs[v] == i) {
                        Vol vol1 = this.getVolsMemesNiveaux().getVolFromNode(maxPtr.node,
                                this.getVolsMemesNiveaux().getListVol());
                        Vol vol2 = this.getVolsMemesNiveaux().getVolFromNode(neighbor,
                                this.getVolsMemesNiveaux().getListVol());
                        this.getVolsMemesNiveaux().gestionNiveauMaxAtteint(vol1, vol2);
                    }
                }
            }

            couleurs[u] = i;
            
            int col = (int) maxPtr.node.getNumber("color");
            maxPtr.node.setAttribute("ui.style", "fill-color:rgba(" + cols[i].getRed() + "," + cols[i].getGreen() + "," + cols[i].getBlue() + ",200);");
            // maxPtr.node.setAttribute("ui.style", "fill-color: " + couleursHex[i] + ";");

            // Réinitialisation de l'itérateur pour les voisins
            neighborIterator = maxPtr.node.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int v = nodeIndexMap.get(neighbor);
                if (couleurs[v] != -1) {
                    couleursVoisins[couleurs[v]] = false;
                }
            }

            // Mise à jour des informations de saturation et de degré pour les voisins
            neighborIterator = maxPtr.node.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int v = nodeIndexMap.get(neighbor);
                if (couleurs[v] == -1) {
                    queueDePriorite.remove(new NodeInfo(adjCols[v].size(), degreNoeud[v], neighbor));
                    adjCols[v].add(couleurs[u]);
                    degreNoeud[v]--;
                    queueDePriorite.add(new NodeInfo(adjCols[v].size(), degreNoeud[v], neighbor));
                }
            }
        }

        System.out.println("Nombre de coloration optimal : " + this.koptimal);
        System.out.println("Nombre de niveaux de vols utilisés : " + this.kdonne);
        System.out.println("Nombre de vols en risque de collision : " + this.getVolsMemesNiveaux().size());
        System.out.println(this.getVolsMemesNiveaux());
        System.out.println("Kmax : " + this.kmax);
    }
    // #endregion
    // #region Welsh-Powell
    /**
     * Coloration du graphe selon l'algorithme de Welsh-Powell
     * 
     * @author Xavier LACROIX
     */
    public void welshPowell() {
        WelshPowell wp = new WelshPowell("color");
        wp.init(this);
        wp.compute();
    
        setKoptimal(wp.getChromaticNumber());
        System.out.println("Nombre de coloration optimal : " + getKoptimal());
    
        // Display colors
        Color[] cols = new Color[getKdonne()];
        for (int i = 0; i < getKdonne(); i++) {
            cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        }
        for (Node n : this) {
            int col = (int) n.getNumber("color");
            n.setAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
        }
    }
    // #endregion
    // #endregion

    // #region Remplissage du graphe

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
    public void fillVol(ListVol listVol, int marge) {
        this.clear();
        this.getVolsMemesNiveaux().setListVol(listVol);
        ;
        // creation des noeuds
        for (Vol vol : listVol.getList()) {
            this.addNode(vol.getCode()).addAttribute("ui.label", vol.getCode()); // ajout du label
        }
        // creation des arretes
        for (int i = 0; i < listVol.getList().size(); i++) {
            for (int j = i + 1; j < listVol.getList().size(); j++) {
                // si les vols i et j sont en collision
                if ((listVol.getVol(i).collision(listVol.getVol(j), marge)) != null) {
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
        this.getVolsMemesNiveaux().setListVol(listVol);
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

    // #region Méthodes

    /**
     * cache les noeud avec un degrée de 0
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
