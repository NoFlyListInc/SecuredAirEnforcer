package src.exception;

import java.util.ArrayList;

public class ParseException extends Exception
{
    public ParseException(int ligne, String msg) {
        super("ligne " + ligne + " : " + msg);
    }

    public ParseException(String file, ArrayList<? extends Exception> exceptions) {
        super(CombinedException(file, exceptions));
    }

    private static String CombinedException(String file, ArrayList<? extends Exception> exceptions) {
        StringBuilder sb = new StringBuilder();
        sb.append("Le fichier ").append(file).append(" contient des erreurs :\n");
        for (Exception e : exceptions) {
            sb.append(e.getMessage()).append("\n");
        }
        sb.append("Les informations seront chargées partiellement.");
        return sb.toString();
    }
}
