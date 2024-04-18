public class Main 
{
    //#region creation
    private static ListAeroport aeroports = new ListAeroport();
    //#endregion
    
    //#region main
    public static void main(String[] args) {
        aeroports.fill("./Data/aeroports.txt");
        System.out.println(aeroports);
    }
    //#endregion
}
