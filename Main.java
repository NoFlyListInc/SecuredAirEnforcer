public class Main 
{
    //#region creation
    private static ListAeroport aeroports = new ListAeroport();
    private static ListVol vols = new ListVol();
    //#endregion
    
    //#region main
    public static void main(String[] args) {
        aeroports.fill("./ressources/aeroports.txt");
        System.out.println(aeroports);
        vols.fill("./ressources/vol-test9.csv", aeroports);
        System.out.println(vols);
    }
    //#endregion
}

