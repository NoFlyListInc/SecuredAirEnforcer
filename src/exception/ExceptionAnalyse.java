package src.exception;

import java.util.ArrayList;

public class ExceptionAnalyse extends Exception
{
    public ExceptionAnalyse(int ligne, String msg) {
        super("ligne " + ligne + " : " + msg);
    }

    public ExceptionAnalyse(String file, ArrayList<? extends Exception> exceptions) {
        super(CombinedException(file, exceptions));
    }

    private static String CombinedException(String file, ArrayList<? extends Exception> exceptions) {
        StringBuilder sb = new StringBuilder();
        sb.append("Le fichier ").append(file).append(" contient des erreurs :\n");
        int cpt = 0;
        for (Exception e : exceptions) {
            sb.append("       ").append(e.getMessage()).append("\n");
            if (cpt++ == 10) {
                sb.append("Il y a ").append(exceptions.size() - 10).append(" erreurs supplémentaires.\n");
                break;
            }
        }
        sb.append("Les informations seront chargées partiellement.");
        return sb.toString();
    }
}
