package src.core;

//#region Imports
//import graphstream class
import org.graphstream.graph.implementations.SingleGraph;

import src.exception.ExceptionAnalyse;

import org.graphstream.graph.Node;
//import reader files class 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
//import list class
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import java.util.Iterator;
import java.util.List;
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
    private NoeudsMemesNiveaux noeudsMemesNiveaux;

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
        this.noeudsMemesNiveaux = new NoeudsMemesNiveaux();

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
    public NoeudsMemesNiveaux getNoeudsMemesNiveaux() {
        return this.noeudsMemesNiveaux;
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
     * Procédure vérifiant s'il les collisions entre les vols après la coloration.
     * 
     * @author Xavier LACROIX
     */
    public void verifierCollisions() {
        // WIP
    }

    /**
     * Procédure colorant le graphe selon l'algorithme de coloration dSature.
     * Cette méthode utilise l'algorithme dSature pour attribuer une couleur
     * à chaque nœud du graphe. Chaque couleur représente un niveau de vol.
     * 
     * L'algorithme dSature fonctionne de la manière suivante :
     * 1. Trie les nœuds par ordre décroissant de degrés.
     * 2. Sélectionne le nœud avec le plus haut degré de saturation.
     * 3. Attribue la première couleur disponible qui n'est pas utilisée par ses
     * voisins.
     * 4. Met à jour le degré de saturation des nœuds voisins.
     * 5. Répète les étapes 2 à 4 jusqu'à ce que tous les nœuds soient colorés.
     * 
     * @param kdonne niveau.x de vol.s à utiliser au maximum
     * 
     *               Après l'exécution de cette méthode, chaque nœud du graphe sera
     *               coloré et le niveau de vol correspondant sera affiché.
     *               Le nombre de coloration optimal est stocké dans la variable
     *               koptimal
     * @autheur Xavier LACROIX
     */
    public void dSature() {
        this.setKoptimal(0);

        int nombreNoeud = this.getNodeCount(); // Nombre de nœuds dans le graphe
        boolean[] couleursVoisins = new boolean[nombreNoeud]; // Tableau pour vérifier les couleurs utilisées par les
                                                              // voisins
        int[] tableauCouleurNoeud = new int[nombreNoeud]; // Tableau pour les couleurs des nœuds
        Color[] couleursVisuelles = new Color[getKdonne()];
        for (int i = 0; i < getKdonne(); i++) {
            couleursVisuelles[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        } // couleurs visuelles à afficher pour chaque niveau de vol
        int[] degreNoeud = new int[nombreNoeud]; // Tableau pour les degrés des nœuds
        HashSet<Integer>[] adjCols = new HashSet[nombreNoeud]; // Tableau de sets pour les couleurs adjacentes
        PriorityQueue<InfoNoeud> queueDePriorite = new PriorityQueue<>(new MaxSat()); // File de priorité pour les nœuds
        HashMap<Node, Integer> carteIndiceNoeud = new HashMap<>(); // Map pour associer les nœuds à leurs indices
        Node[] mapNoeudIndex = new Node[nombreNoeud]; // Tableau pour associer les indices aux nœuds

        int index = 0;

        // Réinitialiser la liste de vols en risque de collisions
        this.getNoeudsMemesNiveaux().getNoeudsMemesNiveaux().clear();

        // Instancier tous les noeuds à une couleur nulle (-1)
        for (Node node : this) {
            tableauCouleurNoeud[index] = -1;
            degreNoeud[index] = node.getDegree();
            adjCols[index] = new HashSet<>();
            carteIndiceNoeud.put(node, index);
            mapNoeudIndex[index] = node;
            queueDePriorite.add(new InfoNoeud(0, degreNoeud[index], node));
            index++;
        }

        while (!queueDePriorite.isEmpty()) {
            InfoNoeud maxPointeur = queueDePriorite.poll();
            int u = carteIndiceNoeud.get(maxPointeur.noeud);

            // Réinitialiser le tableau couleursVoisins pour chaque nœud
            // Arrays.fill(couleursVoisins, false);

            // Utilisation de l'itérateur pour les voisins
            Iterator<Node> itérateurVoisin = maxPointeur.noeud.getNeighborNodeIterator();
            while (itérateurVoisin.hasNext()) {
                Node neighbor = itérateurVoisin.next();
                int v = carteIndiceNoeud.get(neighbor);
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
                int meilleurCouleur = -1;
                for (int j = 0; j < this.kdonne; j++) {
                    int collisions = 0;
                    itérateurVoisin = maxPointeur.noeud.getNeighborNodeIterator();
                    while (itérateurVoisin.hasNext()) {
                        Node voisin = itérateurVoisin.next();
                        int v = carteIndiceNoeud.get(voisin);
                        if (tableauCouleurNoeud[v] == j) {
                            collisions++;
                        }
                    }
                    if (collisions < minCollisions) {
                        minCollisions = collisions;
                        meilleurCouleur = j;
                    }
                }
                i = meilleurCouleur;

                // Ajoute les voisins ayant la même couleur à volsMemesNiveaux
                itérateurVoisin = maxPointeur.noeud.getNeighborNodeIterator();
                while (itérateurVoisin.hasNext()) {
                    Node voisin = itérateurVoisin.next();
                    int v = carteIndiceNoeud.get(voisin);
                    if (tableauCouleurNoeud[v] == i) {
                        this.getNoeudsMemesNiveaux().gestionNiveauMaxAtteint(maxPointeur.noeud, voisin);
                    }
                }
            }

            tableauCouleurNoeud[u] = i;

            maxPointeur.noeud.setAttribute("ui.style",
                    "fill-color:rgba(" + couleursVisuelles[i].getRed() + "," + couleursVisuelles[i].getGreen() + ","
                            + couleursVisuelles[i].getBlue() + ",200);");

            // Réinitialisation de l'itérateur pour les voisins
            itérateurVoisin = maxPointeur.noeud.getNeighborNodeIterator();
            while (itérateurVoisin.hasNext()) {
                Node voisin = itérateurVoisin.next();
                int v = carteIndiceNoeud.get(voisin);
                if (tableauCouleurNoeud[v] != -1) {
                    couleursVoisins[tableauCouleurNoeud[v]] = false;
                }
            }

            // Mise à jour des informations de saturation et de degré pour les voisins
            itérateurVoisin = maxPointeur.noeud.getNeighborNodeIterator();
            while (itérateurVoisin.hasNext()) {
                Node neighbor = itérateurVoisin.next();
                int v = carteIndiceNoeud.get(neighbor);
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
     * Procédure colorant le graphe selon l'algorithme de coloration Welsh et
     * Powell.
     * Cette méthode utilise l'algorithme Welsh et Powell pour attribuer une couleur
     * à chaque nœud du graphe. Chaque couleur représente un niveau de vol.
     * 
     * L'algorithme Welsh et Powell fonctionne de la manière suivante :
     * 1. Trie les nœuds du graphe par ordre décroissant de degrés.
     * 2. Parcourt les nœuds triés.
     * 3. Pour chaque nœud, attribue la première couleur disponible qui n'est pas
     * utilisée par ses voisins.
     * 4. Met à jour les structures de données en conséquence.
     * 5. Répète les étapes 3 et 4 jusqu'à ce que tous les nœuds soient colorés.
     * 
     * @param kdonne niveau.x de vol.s à utiliser au maximum
     * 
     *               Après l'exécution de cette méthode, chaque nœud du graphe sera
     *               coloré et le niveau de vol correspondant sera affiché.
     *               Le nombre de coloration optimal est stocké dans la variable
     *               koptimal
     * @autheur Xavier LACROIX
     */
    public void welshPowell() {
        this.setKoptimal(0);
        int nombreNoeud = this.getNodeCount(); // Nombre de nœuds dans le graphe
        boolean[] couleursVoisins = new boolean[nombreNoeud]; // Tableau pour vérifier les couleurs utilisées par les
                                                              // voisins
        int[] tableauCouleurNoeud = new int[nombreNoeud]; // Tableau pour les couleurs des nœuds
        Color[] couleursVisuelles = new Color[getKdonne()];
        for (int i = 0; i < getKdonne(); i++) {
            couleursVisuelles[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        } // couleurs visuelles à afficher pour chaque niveau de vol
        int[] degreNoeud = new int[nombreNoeud]; // Tableau pour les degrés des nœuds
        HashMap<Node, Integer> carteIndiceNoeud = new HashMap<>(); // Map pour associer les nœuds à leurs indices
        Node[] mapNoeudIndex = new Node[nombreNoeud]; // Tableau pour associer les indices aux nœuds

        int index = 0;

        // Réinitialiser la liste de vols en risque de collisions
        this.getNoeudsMemesNiveaux().getNoeudsMemesNiveaux().clear();

        for (Node node : this) {
            tableauCouleurNoeud[index] = -1;
            degreNoeud[index] = node.getDegree();
            carteIndiceNoeud.put(node, index);
            mapNoeudIndex[index] = node;
            index++;
        }

        // Trie les nœuds par ordre décroissant de degrés
        Arrays.sort(mapNoeudIndex, (n1, n2) -> Integer.compare(degreNoeud[carteIndiceNoeud.get(n2)],
                degreNoeud[carteIndiceNoeud.get(n1)]));

        for (Node noeud : mapNoeudIndex) {
            int u = carteIndiceNoeud.get(noeud);

            // Réinitialiser le tableau couleursVoisins pour chaque nœud
            Arrays.fill(couleursVoisins, false);

            // Utilisation de l'itérateur pour les voisins
            Iterator<Node> iterateurVoisin = noeud.getNeighborNodeIterator();
            while (iterateurVoisin.hasNext()) {
                Node neighbor = iterateurVoisin.next();
                int v = carteIndiceNoeud.get(neighbor);
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
                // Trouver la couleur avec le moins de collisions
                int minCollisions = Integer.MAX_VALUE;
                int meilleurCouleur = -1;
                for (int j = 0; j < this.kdonne; j++) {
                    int collisions = 0;
                    iterateurVoisin = noeud.getNeighborNodeIterator();
                    while (iterateurVoisin.hasNext()) {
                        Node voisin = iterateurVoisin.next();
                        int v = carteIndiceNoeud.get(voisin);
                        if (tableauCouleurNoeud[v] == j) {
                            collisions++;
                        }
                    }
                    if (collisions < minCollisions) {
                        minCollisions = collisions;
                        meilleurCouleur = j;
                    }
                }
                i = meilleurCouleur;
                // Mise à jour des informations de couleurs pour les voisins
                iterateurVoisin = noeud.getNeighborNodeIterator();
                while (iterateurVoisin.hasNext()) {
                    Node voisin = iterateurVoisin.next();
                    int v = carteIndiceNoeud.get(voisin);
                    if (tableauCouleurNoeud[v] != -1) {
                        couleursVoisins[tableauCouleurNoeud[v]] = false;
                    }
                    if (tableauCouleurNoeud[v] == meilleurCouleur) {
                        this.getNoeudsMemesNiveaux().gestionNiveauMaxAtteint(noeud, voisin);
                    }
                }
            }

            tableauCouleurNoeud[u] = i;

            noeud.setAttribute("ui.style",
                    "fill-color:rgba(" + couleursVisuelles[i].getRed() + "," + couleursVisuelles[i].getGreen() + ","
                            + couleursVisuelles[i].getBlue() + ",200);");

        }
    }
    // #endregion
    // #region RLF

    /**
     * Procédure colorant le graphe selon l'algorithme de coloration RLF (Recursive
     * Largest First).
     * Cette méthode utilise l'algorithme RLF pour attribuer une couleur à chaque
     * nœud du graphe.
     * Chaque couleur représente un niveau de vol.
     * L'algo ne dépasse pas le kdonne donné par l'utilisateur.
     * 
     * L'algorithme RLF fonctionne de la manière suivante :
     * 1. Sélectionne le nœud de plus haut degré.
     * 2. Crée un groupe avec ce nœud et ses voisins non adjacents.
     * 3. Colorie tous les nœuds du groupe avec la même couleur.
     * 4. Répète les étapes 1 à 3 pour les nœuds restants.
     * 5. Si le nombre de couleurs dépasse kdonne, sélectionne la couleur avec le
     * moins de collisions.
     * 6. Ajoute les voisins ayant la même couleur à volsMemesNiveaux.
     * 
     * Après l'exécution de cette méthode, chaque nœud du graphe sera
     * coloré et le niveau de vol correspondant sera affiché.
     * Le nombre de coloration optimal est stocké dans la variable
     * koptimal
     * 
     * @autheur Xavier LACROIX
     */
    public void rlf() {
        this.setKoptimal(0);
        int nombreNoeud = this.getNodeCount(); // Nombre de nœuds dans le graphe
        int[] tableauCouleurNoeud = new int[nombreNoeud]; // Tableau pour les couleurs des nœuds
        Color[] couleursVisuelles = new Color[getKdonne()];
        for (int i = 0; i < getKdonne(); i++) {
            couleursVisuelles[i] = Color.getHSBColor((float) (Math.random()), 0.8f, 0.9f);
        } // couleurs visuelles à afficher pour chaque niveau de vol
        int[] degreNoeud = new int[nombreNoeud]; // Tableau pour les degrés des nœuds
        HashMap<Node, Integer> carteIndiceNoeud = new HashMap<>(); // Map pour associer les nœuds à leurs indices
        Node[] mapNoeudIndex = new Node[nombreNoeud]; // Tableau pour associer les indices aux nœuds

        int index = 0;

        // Réinitialiser la liste de vols en risque de collisions
        this.getNoeudsMemesNiveaux().getNoeudsMemesNiveaux().clear();

        for (Node node : this) {
            tableauCouleurNoeud[index] = -1;
            degreNoeud[index] = node.getDegree();
            carteIndiceNoeud.put(node, index);
            mapNoeudIndex[index] = node;
            index++;
        }

        int currentColor = 0;
        while (true) {
            Node maxDegreeNode = null;
            int maxDegree = -1;

            // Trouver le nœud de plus haut degré non coloré
            for (int i = 0; i < nombreNoeud; i++) {
                if (tableauCouleurNoeud[i] == -1 && degreNoeud[i] > maxDegree) {
                    maxDegree = degreNoeud[i];
                    maxDegreeNode = mapNoeudIndex[i];
                }
            }

            if (maxDegreeNode == null) {
                break; // Tous les nœuds ont été colorés
            }

            Set<Node> group = new HashSet<>();
            group.add(maxDegreeNode);
            List<Node> nonAdjacentNodes = new ArrayList<>();

            // Créer le groupe de nœuds non adjacents
            for (Node node : this) {
                if (node != maxDegreeNode && tableauCouleurNoeud[carteIndiceNoeud.get(node)] == -1) {
                    boolean isNonAdjacent = true;
                    for (Node groupNode : group) {
                        if (groupNode.hasEdgeBetween(node)) {
                            isNonAdjacent = false;
                            break;
                        }
                    }
                    if (isNonAdjacent) {
                        group.add(node);
                    } else {
                        nonAdjacentNodes.add(node);
                    }
                }
            }

            // Colorer le groupe de nœuds
            for (Node node : group) {
                int u = carteIndiceNoeud.get(node);
                tableauCouleurNoeud[u] = currentColor;
                node.setAttribute("ui.style",
                        "fill-color:rgba(" + couleursVisuelles[currentColor].getRed() + ","
                                + couleursVisuelles[currentColor].getGreen() + ","
                                + couleursVisuelles[currentColor].getBlue() + ",200);");
            }

            // Mettre à jour la couleur actuelle et le nombre optimal de couleurs
            currentColor++;
            if (currentColor > this.koptimal) {
                this.setKoptimal(currentColor);
            }

            if (currentColor >= kdonne) {
                // Trouver la couleur avec le moins de collisions
                for (Node noeud : nonAdjacentNodes) {
                    int u = carteIndiceNoeud.get(noeud);
                    int minCollisions = Integer.MAX_VALUE;
                    int meilleurCouleur = -1;
                    for (int j = 0; j < this.kdonne; j++) {
                        int collisions = 0;
                        Iterator<Node> itérateurVoisin = noeud.getNeighborNodeIterator();
                        while (itérateurVoisin.hasNext()) {
                            Node voisin = itérateurVoisin.next();
                            int v = carteIndiceNoeud.get(voisin);
                            if (tableauCouleurNoeud[v] == j) {
                                collisions++;
                            }
                        }
                        if (collisions < minCollisions) {
                            minCollisions = collisions;
                            meilleurCouleur = j;
                        }
                    }
                    tableauCouleurNoeud[u] = meilleurCouleur;
                    noeud.setAttribute("ui.style",
                            "fill-color:rgba(" + couleursVisuelles[meilleurCouleur].getRed() + ","
                                    + couleursVisuelles[meilleurCouleur].getGreen() + ","
                                    + couleursVisuelles[meilleurCouleur].getBlue() + ",200);");

                    // Ajoute les voisins ayant la même couleur à volsMemesNiveaux
                    Iterator<Node> itérateurVoisin = noeud.getNeighborNodeIterator();
                    while (itérateurVoisin.hasNext()) {
                        Node voisin = itérateurVoisin.next();
                        int v = carteIndiceNoeud.get(voisin);
                        if (tableauCouleurNoeud[v] == meilleurCouleur) {
                            Vol vol1 = this.getNoeudsMemesNiveaux().getVolDepuisNoeud(noeud,
                                    this.getNoeudsMemesNiveaux().getListVol());
                            Vol vol2 = this.getNoeudsMemesNiveaux().getVolDepuisNoeud(voisin,
                                    this.getNoeudsMemesNiveaux().getListVol());
                            if (vol1 != null && vol2 != null) {
                                this.getNoeudsMemesNiveaux().gestionNiveauMaxAtteint(noeud, voisin);
                            }
                        }
                    }
                }
                break;
            }
        }

        // Ajoute les voisins ayant la même couleur à volsMemesNiveaux pour les nœuds
        // déjà colorés
        for (Node noeud : this) {
            int u = carteIndiceNoeud.get(noeud);
            Iterator<Node> itérateurVoisin = noeud.getNeighborNodeIterator();
            while (itérateurVoisin.hasNext()) {
                Node voisin = itérateurVoisin.next();
                int v = carteIndiceNoeud.get(voisin);
                if (tableauCouleurNoeud[v] == tableauCouleurNoeud[u] && tableauCouleurNoeud[u] != -1) {
                        this.getNoeudsMemesNiveaux().gestionNiveauMaxAtteint(noeud, voisin);
                }
            }
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

    public void remplirAvecFichier(String file) throws ExceptionAnalyse, IOException {
        this.clear();
        ArrayList<ExceptionAnalyse> exceptions = new ArrayList<ExceptionAnalyse>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;

        // première ligne => kmax
        String premiereLigne = reader.readLine();
        if (premiereLigne == null) {
            exceptions.add(new ExceptionAnalyse(1, "La première ligne ne doit pas etre vide"));
        } else if (!premiereLigne.matches("\\d+")) {
            exceptions.add(new ExceptionAnalyse(1, "La première ligne doit être un entier positif"));
        } else {
            this.kdonne = Integer.parseInt(premiereLigne);
        }

        // deuxième ligne => nombre de noeuds
        String deuxiemeligne = reader.readLine();
        if (deuxiemeligne == "") {
            reader.close();
            throw new ExceptionAnalyse(2, "<html>La deuxième ligne ne doit pas etre vide<br>Le graph sera vide</html>");
        } else if (!deuxiemeligne.matches("\\d+")) {
            reader.close();
            throw new ExceptionAnalyse(2,
                    "<html>La deuxième ligne doit être un entier positif<br>Le graph sera vide</html>");
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
                exceptions.add(new ExceptionAnalyse(iterateur, "La ligne est vide"));
            } else {
                String[] parts = line.split(" ");
                if (parts.length != 2) {
                    exceptions.add(
                            new ExceptionAnalyse(iterateur, "La ligne doit contenir 2 entiers séparés par un espace"));
                } else if (!parts[0].matches("\\d+")) {
                    exceptions.add(new ExceptionAnalyse(iterateur, "la première valeur n'est pas un entier"));
                } else if (!parts[1].matches("\\d+")) {
                    exceptions.add(new ExceptionAnalyse(iterateur, "la deuxième valeur n'est pas un entier"));
                } else if (Integer.parseInt(parts[0]) < 0) {
                    exceptions.add(new ExceptionAnalyse(iterateur, "le premier entier doit être positif"));
                } else if (Integer.parseInt(parts[1]) < 0) {
                    exceptions.add(new ExceptionAnalyse(iterateur, "Le deuxième entier doit être positif"));
                } else if (Integer.parseInt(parts[0]) > nbr_noeuds) {
                    exceptions.add(new ExceptionAnalyse(iterateur,
                            "Le premier entier doit être inférieur ou égal au nombre de noeuds : " + nbr_noeuds));
                } else if (Integer.parseInt(parts[1]) > nbr_noeuds) {
                    exceptions.add(new ExceptionAnalyse(iterateur,
                            "Le deuxième entier doit être inférieur ou égal au nombre de noeuds " + nbr_noeuds));
                } else if (parts[0].equals(parts[1])) {
                    exceptions.add(new ExceptionAnalyse(iterateur, "Un noeuds ne peut pas être relié à lui même"));
                } else if (this.getEdge(parts[0] + "," + parts[1]) == null
                        && this.getEdge(parts[1] + "," + parts[0]) == null) {
                    this.addEdge(parts[0] + "," + parts[1], parts[0], parts[1]);
                }
            }
        }
        reader.close();
    }

    /**
     * Rempli le graph des collisions depuis une liste de vol
     * 
     * @param listeVol objet ListVol
     */
    public void remplirAvecListeVol(ListeVol listeVol, int marge) {
        this.clear();
        this.getNoeudsMemesNiveaux().setListVol(listeVol);
        ;
        // creation des noeuds
        for (Vol vol : listeVol) {
            this.addNode(vol.getCode()).addAttribute("ui.label", vol.getCode()); // ajout du label
        }
        // creation des arretes
        for (int i = 0; i < listeVol.size(); i++) {
            for (int j = i + 1; j < listeVol.size(); j++) {
                // si les vols i et j sont en collision

                if ((listeVol.get(i).collision(listeVol.get(j), marge)) != null) {
                    this.addEdge(listeVol.get(i).getCode() + "," + listeVol.get(j).getCode(),
                            listeVol.get(i).getCode(), listeVol.get(j).getCode()); // code de l'arrete =
                                                                                   // "codei,codej"
                }
            }
        }
    }

    /**
     * Rempli le graph des aeroports et des vols pour une représentation
     * géographique
     * 
     * @param listeAeroport objet ListAeroport
     * @param listeVol      objet ListVol
     */

    public void remplirCarte(ListeAeroport listeAeroport, ListeVol listeVol) {
        this.clear();
        this.getNoeudsMemesNiveaux().setListVol(listeVol);
        // creation des noeuds
        for (Aeroport aeroport : listeAeroport) {
            Node noeud = this.addNode(aeroport.getCode());
            noeud.addAttribute("ui.label", aeroport.getCode()); // ajout du label
        }
        // creation des arretes
        for (Vol vol : listeVol) {
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
