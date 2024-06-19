package src.exception;

import java.util.ArrayList;

public class ParseException extends Exception
{
    public ParseException(String file, int ligne, String msg) {
        super("Erreur lors de la lecture du fichier " + file + " à la ligne " + ligne + " : " + msg);
    }

    public ParseException(ArrayList<? extends Exception> exceptions) {
        super(CombinedException(exceptions));
    }

    private static String CombinedException(ArrayList<? extends Exception> exceptions) {
        StringBuilder sb = new StringBuilder();
        for (Exception e : exceptions) {
            sb.append(e.getMessage()).append("\n");
        }
        sb.append("Les informations seront chargées partiellement.");
        return sb.toString();
    }
}
