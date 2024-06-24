package src.core;

//#region Imports
//import graphstream class
import org.graphstream.graph.implementations.SingleGraph;

import src.exception.ParseException;

import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.graph.Node;
//import reader files class 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
//import list class
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet; 
import java.util.PriorityQueue; 

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Iterator;
import java.awt.Color;

//#endregion

/**
 * Classe qui défini un Graphe
 * 
 * @attributs kmax, koptimal, volsMemesNiveaux
 * @methodes getKmax, getKoptimal, getVolsMemesNiveaux, setKmax, setKoptimal,
 *           dSature, fillFile, fillVol, fillMap, hideSoloNode
 * @extends SingleGraph
 * @author LACROIX Xavier et NOUVEL Armand
 */
public class Graphe extends SingleGraph {
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
    public Graphe(String id) {
        super(id);
        this.kmax = 20; // nombre max de niveaux de vols possibles par défaut
        this.koptimal = 1;
        this.kdonne = Integer.MAX_VALUE;
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
    private class InfoNoeud {
        int saturation;
        int degre;
        Node noeud;

        InfoNoeud(int saturation, int degre, Node noeud) {
            this.saturation = saturation;
            this.degre = degre;
            this.noeud = noeud;
        }
    }

    /**
     * La classe MaxSat implémente une interface Comparator pour comparer les objets
     * NodeInfo en
     * fonction des valeurs de saturation et de degré.
     */
    private class MaxSat implements Comparator<InfoNoeud> {
        public int compare(InfoNoeud n1, InfoNoeud n2) {
            if (n1.saturation != n2.saturation) {
                return Integer.compare(n2.saturation, n1.saturation);
            }
            return Integer.compare(n2.degre, n1.degre);
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
        this.setKoptimal(0);
        int nombreNoeud = this.getNodeCount(); // Nombre de nœuds dans le graphe
        boolean[] couleursVoisins = new boolean[nombreNoeud]; // Tableau pour vérifier les couleurs utilisées par les voisins
        int[] tableauCouleurNoeud = new int[nombreNoeud]; // Tableau pour les couleurs des nœuds
        Color[] couleursVisuelles = new Color[getKdonne()];
        for (int i = 0; i < getKdonne(); i++) {
            couleursVisuelles[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        } //couleurs visuelles à afficher pour chaque niveau de vol
        int[] degreNoeud = new int[nombreNoeud]; // Tableau pour les degrés des nœuds
        HashSet<Integer>[] adjCols = new HashSet[nombreNoeud]; // Tableau de sets pour les couleurs adjacentes
        PriorityQueue<InfoNoeud> queueDePriorite = new PriorityQueue<>(new MaxSat()); // File de priorité pour les nœuds
        HashMap<Node, Integer> mapIndexNoeud = new HashMap<>(); // Map pour associer les nœuds à leurs indices
        Node[] mapNoeudIndex = new Node[nombreNoeud]; // Tableau pour associer les indices aux nœuds

        int index = 0;

        // Instancier tous les noeuds à une couleur nulle (-1)
        for (Node node : this) {
            tableauCouleurNoeud[index] = -1;
            degreNoeud[index] = node.getDegree();
            adjCols[index] = new HashSet<>();
            mapIndexNoeud.put(node, index);
            mapNoeudIndex[index] = node;
            queueDePriorite.add(new InfoNoeud(0, degreNoeud[index], node));
            index++;
        }

        while (!queueDePriorite.isEmpty()) {
            InfoNoeud maxPointeur = queueDePriorite.poll();
            int u = mapIndexNoeud.get(maxPointeur.noeud);

            // Réinitialiser le tableau couleursVoisins pour chaque nœud
            // Arrays.fill(couleursVoisins, false);

            // Utilisation de l'itérateur pour les voisins
            Iterator<Node> neighborIterator = maxPointeur.noeud.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int v = mapIndexNoeud.get(neighbor);
                if (tableauCouleurNoeud[v] != -1) {
                    couleursVoisins[tableauCouleurNoeud[v]] = true;
                }
            }

            int i = 0;
            while (i < couleursVoisins.length) {
                if (!couleursVoisins[i]) {
                    break;
                }
                i++;
            }

            if (i > this.koptimal) {
                this.setKoptimal(i);
            }

            if (i >= kdonne) {
                // Si la couleur choisie dépasse kdonne, chercher la couleur avec le moins de
                // collisions
                int minCollisions = Integer.MAX_VALUE;
                int bestColor = -1;
                for (int j = 0; j < this.kdonne; j++) {
                    int collisions = 0;
                    neighborIterator = maxPointeur.noeud.getNeighborNodeIterator();
                    while (neighborIterator.hasNext()) {
                        Node neighbor = neighborIterator.next();
                        int v = mapIndexNoeud.get(neighbor);
                        if (tableauCouleurNoeud[v] == j) {
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
                neighborIterator = maxPointeur.noeud.getNeighborNodeIterator();
                while (neighborIterator.hasNext()) {
                    Node neighbor = neighborIterator.next();
                    int v = mapIndexNoeud.get(neighbor);
                    if (tableauCouleurNoeud[v] == i) {
                        Vol vol1 = this.getVolsMemesNiveaux().getVolDepuisNoeud(maxPointeur.noeud,
                                this.getVolsMemesNiveaux().getListVol());
                        Vol vol2 = this.getVolsMemesNiveaux().getVolDepuisNoeud(neighbor,
                                this.getVolsMemesNiveaux().getListVol());
                        this.getVolsMemesNiveaux().gestionNiveauMaxAtteint(vol1, vol2);
                    }
                }
            }

            tableauCouleurNoeud[u] = i;

            maxPointeur.noeud.setAttribute("ui.style", "fill-color:rgba(" + couleursVisuelles[i].getRed() + "," + couleursVisuelles[i].getGreen() + ","
                    + couleursVisuelles[i].getBlue() + ",200);");

            // Réinitialisation de l'itérateur pour les voisins
            neighborIterator = maxPointeur.noeud.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int v = mapIndexNoeud.get(neighbor);
                if (tableauCouleurNoeud[v] != -1) {
                    couleursVoisins[tableauCouleurNoeud[v]] = false;
                }
            }

            // Mise à jour des informations de saturation et de degré pour les voisins
            neighborIterator = maxPointeur.noeud.getNeighborNodeIterator();
            while (neighborIterator.hasNext()) {
                Node neighbor = neighborIterator.next();
                int v = mapIndexNoeud.get(neighbor);
                if (tableauCouleurNoeud[v] == -1) {
                    queueDePriorite.remove(new InfoNoeud(adjCols[v].size(), degreNoeud[v], neighbor));
                    adjCols[v].add(tableauCouleurNoeud[u]);
                    degreNoeud[v]--;
                    queueDePriorite.add(new InfoNoeud(adjCols[v].size(), degreNoeud[v], neighbor));
                }
            }
        }
    }
    // #endregion

    // #region Welsh-Powell
    /**
     * Coloration du graphe selon l'algorithme de Welsh-Powell
     * 
     * @author Xavier LACROIX
     */
    public void welshPowell() {
        WelshPowell colorationWelshPowell = new WelshPowell("color");
        colorationWelshPowell.init(this);
        colorationWelshPowell.compute();

        setKoptimal(colorationWelshPowell.getChromaticNumber());

        // Display colors
        Color[] cols = new Color[getKdonne()];
        for (int i = 0; i < getKdonne(); i++) {
            cols[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        }
        for (Node n : this) {
            int col = (int) n.getNumber("color");
            n.setAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + ","
                    + cols[col].getBlue() + ",200);");
        }
    }

    // #endregion

    public String getGrapheColore() {
        String graphInfo = "";
        Pattern pattern = Pattern.compile("fill-color: (.*?);");
        for (Node node : this) {
            String style = node.getAttribute("ui.style");
            Matcher matcher = pattern.matcher(style);
            String color = "";
            if (matcher.find()) {
                color = matcher.group(1);
            }
            System.out.println(graphInfo += node.getId() + " " + color + "\n");
        }
        return graphInfo;
    }

    // #endregion

    // #region Remplissage du graphe

    /**
     * Rempli le graph depuis un fichier graph-testx.txt
     * 
     * @param file adresse du fichier
     */
    public void remplirAvecFichier(String file) throws ParseException, IOException {
        this.clear();
        ArrayList<ParseException> exceptions = new ArrayList<ParseException>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        // première ligne => kmax
        String premiereLigne = reader.readLine();
        if (premiereLigne == null) {
            exceptions.add(new ParseException(1, "La première ligne ne doit pas etre vide"));
        } else if (!premiereLigne.matches("\\d+")) {
            exceptions.add(new ParseException(1, "La première ligne doit être un entier positif"));
        }
        this.kdonne = Integer.parseInt(premiereLigne);

        // deuxième ligne => nombre de noeuds
        String deuxiemeligne = reader.readLine();
        if (deuxiemeligne == "") {
            exceptions.add(new ParseException(2, "La deuxième ligne ne doit pas etre vide"));
        } else if (!deuxiemeligne.matches("\\d+")) {
            exceptions.add(new ParseException(2, "La deuxième ligne doit être un entier positif"));
        }
        int nbr_noeuds = Integer.parseInt(deuxiemeligne);

        // creation des noeuds
        for (int i = 1; i <= nbr_noeuds; i++) {
            this.addNode(Integer.toString(i)).addAttribute("ui.label", i); // ajout du label
        }

        // creations des arretes
        int iterateur = 2;
        while ((line = reader.readLine()) != null) {
            iterateur++;
            if (line == "") {
                exceptions.add(new ParseException(iterateur, "La ligne est vide"));
            } else {
                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    exceptions.add(new ParseException(iterateur, "La ligne doit contenir 2 entiers séparés par un espace"));
                } else if (!parts[0].matches("\\d+")) {
                    exceptions.add(new ParseException(iterateur, "la première valeur n'est pas un entier"));
                } else if (!parts[1].matches("\\d+")) {
                    exceptions.add(new ParseException(iterateur, "la deuxième valeur n'est pas un entier"));
                } else if (Integer.parseInt(parts[0]) < 0) {
                    exceptions.add(new ParseException(iterateur, "le premier entier doit être positif"));
                } else if (Integer.parseInt(parts[1]) < 0) {
                    exceptions.add(new ParseException(iterateur, "Le deuxième entier doit être positif"));
                } else if (Integer.parseInt(parts[0]) > nbr_noeuds) {
                    exceptions.add(new ParseException(iterateur,
                            "Le premier entier doit être inférieur ou égal au nombre de noeuds : " + nbr_noeuds));
                } else if (Integer.parseInt(parts[1]) > nbr_noeuds) {
                    exceptions.add(new ParseException(iterateur,
                            "Le deuxième entier doit être inférieur ou égal au nombre de noeuds " + nbr_noeuds));
                } else if (parts[0].equals(parts[1])) {
                    exceptions.add(new ParseException(iterateur, "Un noeuds ne peut pas être relié à lui même"));
                } else if (this.getEdge(parts[0] + "," + parts[1]) == null
                        && this.getEdge(parts[1] + "," + parts[0]) == null) {
                    this.addEdge(parts[0] + "," + parts[1], parts[0], parts[1]);
                }
            }
        }
        reader.close();
        // gestion des exceptions
        if (exceptions.size() > 0) {
            throw new ParseException(file, exceptions);
        }
    }

    /**
     * Rempli le graph des collisions depuis une liste de vol
     * 
     * @param listVol objet ListVol
     */
    public void remplirAvecListeVol(ListVol listVol, int marge) {
        this.clear();
        this.getVolsMemesNiveaux().setListVol(listVol);
        ;
        // creation des noeuds
        for (Vol vol : listVol) {
            this.addNode(vol.getCode()).addAttribute("ui.label", vol.getCode()); // ajout du label
        }
        // creation des arretes
        for (int i = 0; i < listVol.size(); i++) {
            for (int j = i + 1; j < listVol.size(); j++) {
                // si les vols i et j sont en collision
                if ((listVol.get(i).collision(listVol.get(j), marge)) != null) {
                    this.addEdge(listVol.get(i).getCode() + "," + listVol.get(j).getCode(),
                            listVol.get(i).getCode(), listVol.get(j).getCode()); // code de l'arrete =
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
    public void remplirCarte(ListAeroport listAeroport, ListVol listVol) {
        this.clear();
        this.getVolsMemesNiveaux().setListVol(listVol);
        // creation des noeuds
        for (Aeroport aeroport : listAeroport) {
            Node noeud = this.addNode(aeroport.getCode());
            noeud.addAttribute("ui.label", aeroport.getCode()); // ajout du label
        }
        // creation des arretes
        for (Vol vol : listVol) {
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
    public void cacherNoeudSeul() {
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
