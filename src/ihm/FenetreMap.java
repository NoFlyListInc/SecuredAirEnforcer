package src.ihm;
//#region import
//swing objects
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;

//src objects
import src.core.ListVol;
import src.core.Map;
import src.core.ListAeroport;

//awt objects
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
//File object
import java.io.File;
//#endregion

/**
 * Classe FenetreMap
 * Fenêtre pour afficher une map
 * @extends SuperposedFenetre
 * @autor NOUVEL Armand
 */
public class FenetreMap extends FenetreSuperpose
{
    //#region Attributes
    private JLabel infoLabel = new JLabel();
    private Map map = new Map(this.infoLabel);
    private JPanel settingMenuPosition = new JPanel();
    private JButton settingButton = new JButton();
    //#endregion

    //#region Constructeur

    /**
     * Constructeur de la classe FenetreMap
     */
    public FenetreMap() {
        this.constrFen();
    }
    //#endregion

    //#region méthodes

    /**
     * construie la fenetre
     */
    private void constrFen() {
        // creation de la fenetre
        this.setTitle("Carte");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.constrPan();
        this.setContentPane(this.superposePan);
        this.setMinimumSize(new Dimension(400,300));        
        this.setSize(800, 600);        
        this.setLocationRelativeTo(null);
    }

    
    private void constrPan() {
        //construie la map
        JScrollPane mapPan = new JScrollPane(this.map);
        superposePan.add(mapPan, JLayeredPane.DEFAULT_LAYER);

        //panel border des boutons (setting et menu)
        JPanel buttonPan = new JPanel();
        buttonPan.setOpaque(false);
        buttonPan.setLayout(new BorderLayout());
    
        //? settingPan
        JPanel settingPan = new JPanel();
        settingPan.setOpaque(false);
        settingPan.setLayout(new BoxLayout(settingPan, BoxLayout.PAGE_AXIS));

        //button setting
        Image settingImage = new ImageIcon("image/parametre.png").getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        settingButton.setToolTipText("Ouvrir les paramètres");
        this.settingButton.setIcon(new ImageIcon(settingImage));
        this.settingButton.setBorderPainted(false);
        this.settingButton.setContentAreaFilled(false);
        this.settingButton.setFocusPainted(false);
        this.settingButton.addActionListener((ActionListener) -> {
            Dimension size = getContentPane().getSize();
            settingMenuPosition.setBounds(0, 0, size.width, size.height);
            getSuperposePan().add(settingMenuPosition, JLayeredPane.MODAL_LAYER);
            settingButton.setVisible(false);
            getSuperposePan().revalidate();
            getSuperposePan().repaint();
        });
        settingPan.add(this.settingButton);
        settingPan.add(Box.createVerticalGlue());
        buttonPan.add(settingPan, BorderLayout.EAST);

        JPanel menuLabelPan = new JPanel();
        menuLabelPan.setOpaque(false);
        menuLabelPan.setLayout(new BoxLayout(menuLabelPan, BoxLayout.PAGE_AXIS));
        //Label
        JPanel infoLabelPan = new JPanel();
        infoLabelPan.setOpaque(true);
        infoLabelPan.setLayout(new BoxLayout(infoLabelPan, BoxLayout.LINE_AXIS));
        this.infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.infoLabel.setBackground(Color.WHITE);
        infoLabelPan.add(this.infoLabel);
        infoLabelPan.add(Box.createHorizontalGlue());
        //
        menuLabelPan.add(infoLabelPan);
        menuLabelPan.add(Box.createVerticalGlue());
        JPanel menuButtonPan = new JPanel();
        menuButtonPan.setOpaque(false);
        menuButtonPan.setLayout(new BoxLayout(menuButtonPan, BoxLayout.LINE_AXIS));
        this.menuButton.setMaximumSize(new Dimension(121, 30));
        menuButtonPan.add(this.menuButton);
        menuButtonPan.add(Box.createHorizontalGlue());
        menuLabelPan.add(menuButtonPan);
        buttonPan.add(menuLabelPan, BorderLayout.WEST);

        superposePan.add(buttonPan, JLayeredPane.PALETTE_LAYER);

        //construie le setting menu
        this.constrSettingMenu();


        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getContentPane().getSize();
                mapPan.setBounds(0, 0, size.width, size.height);
                buttonPan.setBounds(0, 0, size.width, size.height);
                menu.setBounds(0, 0, size.width, size.height);
                settingMenuPosition.setBounds(0, 0, size.width, size.height);
            }
        });
    }

    private void constrSettingMenu() {
        //parametre le panel border pour positionner le settingMenu
        this.settingMenuPosition.setLayout(new BorderLayout());
        this.settingMenuPosition.setOpaque(false);

        //panel du popUp setting
        RoundedPanel settingPopUp = new RoundedPanel(12);
        settingPopUp.setBackground(Color.WHITE);
        settingPopUp.setLayout(new BoxLayout(settingPopUp, BoxLayout.PAGE_AXIS));

        //panel du retour et du label SETTING
        JPanel retourPan = new JPanel();
        retourPan.setOpaque(false);
        retourPan.setLayout(new BoxLayout(retourPan, BoxLayout.LINE_AXIS));
        //button retour
        Image retourImage = new ImageIcon("image/rightArrow.png").getImage().getScaledInstance(25, 20, Image.SCALE_SMOOTH);
        JButton retour = new JButton(new ImageIcon(retourImage));
        retour.setToolTipText("Fermer les paramètres");
        retour.setBorderPainted(false);
        retour.setContentAreaFilled(false);
        retour.setFocusPainted(false);
        retour.addActionListener((ActionListener) -> {
            getSuperposePan().remove(settingMenuPosition);
            settingButton.setVisible(true);
            getSuperposePan().revalidate();
            getSuperposePan().repaint();
        });
        retourPan.add(Box.createRigidArea(new Dimension(10,0)));
        retourPan.add(retour);
        retourPan.add(Box.createHorizontalGlue());
        //label SETTING
        JLabel settingLabel = new JLabel("PARAMETRE");
        settingLabel.setForeground(Color.BLACK);
        retourPan.add(settingLabel);
        retourPan.add(Box.createHorizontalGlue());
        retourPan.add(Box.createHorizontalGlue());
        //ajout du retour et du label dans le settingPopUp
        settingPopUp.add(Box.createRigidArea(new Dimension(0,10)));
        settingPopUp.add(retourPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,10)));

        //button import
        JPanel importPan = new JPanel();
        importPan.setOpaque(false);
        importPan.setLayout(new BoxLayout(importPan, BoxLayout.LINE_AXIS));
        RoundedButton importButton = new RoundedButton("importer une liste de vols");
        importButton.setBackground(new Color(176, 226, 255));
        importButton.setToolTipText("<html>Charger un fichier CSV <br> Exemple : <br> ID_vol ; ID_aéro_départ ; ID_aéro_départ ; Heure_départ ; Min_départ ; duration</html>");
        importPan.add(Box.createHorizontalGlue());
        importPan.add(importButton);
        importPan.add(Box.createHorizontalGlue());


        //ajout du button import dans le settingPopUp
        settingPopUp.add(importPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,15)));

        // hauteur setting (hmax)
        //slider Pan
        JPanel sliderHauteurPan = new JPanel();
        sliderHauteurPan.setMaximumSize(new Dimension(200, 50));
        sliderHauteurPan.setOpaque(false);
        sliderHauteurPan.setLayout(new BoxLayout(sliderHauteurPan, BoxLayout.LINE_AXIS));
        //JLabel
        sliderHauteurPan.add(Box.createHorizontalGlue());
        sliderHauteurPan.add(new JLabel("hmax : "));
        sliderHauteurPan.add(Box.createRigidArea(new Dimension(10,0)));
        //slider
        JSlider sliderHauteur = new JSlider(JSlider.HORIZONTAL, 1, 20, 1);
        sliderHauteur.setEnabled(false);
        sliderHauteurPan.add(sliderHauteur);
        sliderHauteurPan.add(Box.createRigidArea(new Dimension(10,0)));
        //JLabel int
        JLabel HauteurIntLabel = new JLabel("1");
        sliderHauteur.addChangeListener((ChangeEvent) -> {
            HauteurIntLabel.setText(Integer.toString(sliderHauteur.getValue()));
        });
        //ajout du slider et du label dans le settingPopUp
        sliderHauteurPan.add(HauteurIntLabel);
        sliderHauteurPan.add(Box.createHorizontalGlue());
        settingPopUp.add(sliderHauteurPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,8)));

        //marge setting
        //slider Pan
        JPanel sliderMargePan = new JPanel();
        sliderMargePan.setMaximumSize(new Dimension(200, 50));
        sliderMargePan.setOpaque(false);
        sliderMargePan.setLayout(new BoxLayout(sliderMargePan, BoxLayout.LINE_AXIS));
        //JLabel
        sliderMargePan.add(Box.createHorizontalGlue());
        sliderMargePan.add(new JLabel("marge : "));
        sliderMargePan.add(Box.createRigidArea(new Dimension(10,0)));
        //slider
        JSlider sliderMarge = new JSlider(JSlider.HORIZONTAL, 1, 60, 15);
        sliderMarge.setEnabled(false);
        sliderMargePan.add(sliderMarge);
        sliderMargePan.add(Box.createRigidArea(new Dimension(10,0)));
        //JLabel int
        JLabel margeIntLabel = new JLabel("15");
        sliderMarge.addChangeListener((ChangeEvent) -> {
            margeIntLabel.setText(Integer.toString(sliderMarge.getValue()));
        });
        sliderMargePan.add(margeIntLabel);
        sliderMargePan.add(Box.createHorizontalGlue());
        //ajout du slider et du label dans le settingPopUp
        settingPopUp.add(sliderMargePan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,8)));


        //button
        JPanel buttonPan = new JPanel();
        buttonPan.setOpaque(false);
        buttonPan.setLayout(new BoxLayout(buttonPan, BoxLayout.LINE_AXIS));
        RoundedButton button = new RoundedButton("Appliquer les paramètres");
        button.setBackground(new Color(176, 226, 255));
        button.setToolTipText("Appliquer les paramètres sur la carte");
        button.setEnabled(false);
        button.addActionListener((ActionListener) -> {
            map.addInformation(map.getListAeroport(), map.getListVols(), sliderMarge.getValue(), sliderHauteur.getValue());
        });
        buttonPan.add(Box.createHorizontalGlue());
        buttonPan.add(button);
        buttonPan.add(Box.createHorizontalGlue());
        //ajout du button dans le settingPopUp
        settingPopUp.add(buttonPan);
        settingPopUp.add(Box.createRigidArea(new Dimension(0,15)));

        //panel du settingPopUp
        JPanel settingMenuPan = new JPanel();
        settingMenuPan.setOpaque(false);
        settingMenuPan.setLayout(new BoxLayout(settingMenuPan, BoxLayout.PAGE_AXIS));
        settingMenuPan.add(settingPopUp);
        settingMenuPan.add(Box.createVerticalGlue());

        this.settingMenuPosition.add(settingMenuPan, BorderLayout.EAST);

        //action listener du import button
        importButton.addActionListener((ActionListener) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers CSV", "csv"));
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                ListAeroport listAeroport = new ListAeroport();
                // lecture du fichier aeroport
                try {
                    listAeroport.fill("data/aeroports.txt");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
                // lecture du fichier vol-test
                ListVol listVols = new ListVol();
                try {
                    listVols.fill(filePath, listAeroport);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
                map.addInformation(listAeroport, listVols, 15, 1);
                sliderHauteur.setEnabled(true);
                sliderMarge.setEnabled(true);
                sliderHauteur.setValue(1);
                HauteurIntLabel.setText("1");
                sliderMarge.setValue(15);
                margeIntLabel.setText("15");
                button.setEnabled(true);
                retour.doClick();
            } else {
                
            }
            infoLabel.setText("Cliquez sur un élément");
        });
    }
}