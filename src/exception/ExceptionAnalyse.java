package src.exception;

import java.util.ArrayList;

/**
 * Cette classe permet de gérer les exceptions lors de l'analyse des fichiers.
 * @see Exception
 * @author NOUVEL Armand
 * @version 1.0 
 */
public class ExceptionAnalyse extends Exception
{
    /**
     * Constructeur de ExceptionAnalyse pour une erreur
     * @param ligne int
     * @param msg String
     */
    public ExceptionAnalyse(int ligne, String msg) {
        super("ligne " + ligne + " : " + msg);
    }

    /**
     * Constructeur de ExceptionAnalyse pour une liste d'erreur
     * @param file adresse du fichier
     * @param ListeException ArrayList<Exception>
     */
    public ExceptionAnalyse(String file, ArrayList<? extends Exception> ListeException) {
        super(CombinedException(file, ListeException));
    }

    private static String CombinedException(String file, ArrayList<? extends Exception> ListeException) {
        StringBuilder sb = new StringBuilder();
        sb.append("Le fichier ").append(file).append(" contient des erreurs :\n");
        int cpt = 0;
        for (Exception e : ListeException) {
            sb.append("       ").append(e.getMessage()).append("\n");
            if (cpt++ == 10) {
                sb.append("Il y a ").append(ListeException.size() - 10).append(" erreurs supplémentaires.\n");
                break;
            }
        }
        sb.append("Les informations seront chargées partiellement.");
        return sb.toString();
    }
}
