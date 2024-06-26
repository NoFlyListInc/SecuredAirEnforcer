package src.ihm;
import javax.swing.JLayeredPane;
import javax.swing.JFrame;
/**
 * Cette classe est abstraite et permet de créer une fenetre avec plusieurs couches
 * @author NOUVEL Armand
 * @version 1.0
 */
public abstract class FenetreSuperpose extends JFrame
{
    //#region attributes
    protected JLayeredPane superposePan = new JLayeredPane();
    protected MenuNavigationPan menu = new MenuNavigationPan(this);
    protected MenuButtonPanel menuButton = new MenuButtonPanel(12, this);
    //#endregion

    //#region accesseurs
    public JLayeredPane getSuperposePan() {
        return this.superposePan;
    }
    //#endregion
}
