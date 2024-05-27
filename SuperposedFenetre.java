import javax.swing.JLayeredPane;
import javax.swing.JFrame;

public abstract class SuperposedFenetre extends JFrame
{
    //#region attributes
    protected JLayeredPane superposePan = new JLayeredPane();
    protected MenuOngletPan menu = new MenuOngletPan(this);
    //#endregion

    //#region accesseurs
    public JLayeredPane getSuperposePan() {
        return this.superposePan;
    }
    //#endregion
}
