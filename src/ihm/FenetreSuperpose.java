package src.ihm;
import javax.swing.JLayeredPane;
import javax.swing.JFrame;
/**
 * <h3>Cette classe crée une fenêtre superposée.</h3>
 * @author NOUVEL Armand
 */
public abstract class FenetreSuperpose extends JFrame
{
    //#region attributes
    protected JLayeredPane superposePan = new JLayeredPane();
    protected MenuOngletPan menu = new MenuOngletPan(this);
    protected MenuButtonPanel menuButton = new MenuButtonPanel(12, this);
    //#endregion

    //#region accesseurs
    public JLayeredPane getSuperposePan() {
        return this.superposePan;
    }
    //#endregion
}
