package src;

import src.ihm.FenetrePrincipale;

/**
 * classe Main
 */
public class Main 
{
    //#region creation
    private static FenetrePrincipale fen = new FenetrePrincipale();
    //#endregion
    
    //#region main
    /**
     * main
     * @param args
     */
    public static void main(String[] args) {
        fen.setVisible(true);
    }
    //#endregion
}

