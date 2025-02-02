package src.ihm;
//#region import
//swing objects
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;

//src objects
import src.core.ListeVol;
import src.core.Carte;
import src.core.ListeAeroport;
import src.core.Horaire;


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
 * Fenêtre pour afficher la carte
 * @see FenetreSuperpose
 * @author NOUVEL Armand et FERNANDES Thomas
 * @version 1.0
 */
public class FenetreCarte extends FenetreSuperpose
{
    //#region Attributes
    private JLabel infoLabel = new JLabel();
    private Carte map = new Carte(this.infoLabel);
    private JPanel settingMenuPosition = new JPanel();
    private JButton settingButton = new JButton();
    private JSlider sliderMarge;
    private JSlider sliderHauteur;
    private JSpinner spinnerNiveau;
    private JPanel spinnerNiveauPan;
    private JSpinner spinnerHeure;
    private JSpinner spinnerMinute;
    private JPanel heurePan;
    private JRadioButton heureBouton = new JRadioButton();
    private JPanel infoLabelPan;
    //#endregion

    //#region Constructeur

    /**
     * Constructeur de la classe FenetreMap
     */
    public FenetreCarte() {
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

        //ajout de la fonctionnalité d'afficher chaque vol dans un niveau de vol donne
        //spinner Pan
        this.spinnerNiveauPan = new JPanel();
        this.spinnerNiveauPan.setVisible(false);
        this.spinnerNiveauPan.setMaximumSize(new Dimension(1000,30));
        this.spinnerNiveauPan.setOpaque(false);
        this.spinnerNiveauPan.setLayout(new BoxLayout(spinnerNiveauPan, BoxLayout.LINE_AXIS));
        //JLabel
        this.spinnerNiveauPan.add(Box.createHorizontalGlue());
        JLabel niveauLabel = new JLabel("hauteur :");
        niveauLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.spinnerNiveauPan.add(niveauLabel);
        this.spinnerNiveauPan.add(Box.createRigidArea(new Dimension(3,0)));
        //spinner
        this.spinnerNiveau = new JSpinner(new SpinnerNumberModel(0, 0, 0, 1));                
        this.spinnerNiveau.setOpaque(false);
        this.spinnerNiveau.setEnabled(false);
        this.spinnerNiveauPan.add(this.spinnerNiveau);
        this.spinnerNiveauPan.add(Box.createRigidArea(new Dimension(3,0)));
        //ajout du spinner dans le settingPan
        settingPan.add(this.spinnerNiveauPan);
        settingPan.add(Box.createRigidArea(new Dimension(0,3)));

        //changeListener
        this.spinnerNiveau.addChangeListener((ChangeEvent) -> {
            Horaire heure = null;
            if (this.heureBouton.isSelected()) {
                heure = new Horaire((int)spinnerHeure.getValue(), (int)spinnerMinute.getValue());
            }
            map.ajouterInformation(map.getListeAeroport(), map.getListeVols(), sliderMarge.getValue(), sliderHauteur.getValue(), (int)spinnerNiveau.getValue(), heure);
        });

        //ajout de la fonctionnalité d'afficher chaque vol à une heure donnée
        //heure Pan
        this.heurePan = new JPanel();
        this.heurePan.setVisible(false);
        this.heurePan.setOpaque(false);
        this.heurePan.setMaximumSize(new Dimension(1000,30));
        this.heurePan.setLayout(new BoxLayout(this.heurePan, BoxLayout.LINE_AXIS));
        //bouton
        this.heurePan.add(Box.createHorizontalGlue());
        this.heurePan.add(this.heureBouton);
        this.heurePan.add(Box.createRigidArea(new Dimension(5,0)));
        this.heureBouton.setEnabled(false);
        this.heureBouton.setOpaque(false);
        this.heureBouton.addActionListener((ActionListener) -> {
            if (this.heureBouton.isSelected()) {
                this.map.ajouterInformation(map.getListeAeroport(), map.getListeVols(), sliderMarge.getValue(), sliderHauteur.getValue(), (int)spinnerNiveau.getValue(), new Horaire((int)spinnerHeure.getValue(), (int)spinnerMinute.getValue()));
            } else {
                this.map.ajouterInformation(map.getListeAeroport(), map.getListeVols(), sliderMarge.getValue(), sliderHauteur.getValue(), (int)spinnerNiveau.getValue(), null);
            }
        });
        //spinner heure
        this.spinnerHeure = new JSpinner(new SpinnerNumberModel(0, -1, 24, 1));
        this.spinnerHeure.addChangeListener((ChangeEvent) -> {
            if (this.spinnerHeure.getNextValue() == null) {
                this.spinnerHeure.setValue(0);
            }
            else if (this.spinnerHeure.getPreviousValue() == null) {
                this.spinnerHeure.setValue(23);
            }
            if (this.heureBouton.isSelected()) {
                this.map.ajouterInformation(map.getListeAeroport(), map.getListeVols(), sliderMarge.getValue(), sliderHauteur.getValue(), (int)spinnerNiveau.getValue(), new Horaire((int)spinnerHeure.getValue(), (int)spinnerMinute.getValue()));
            }
        });
        this.heurePan.add(this.spinnerHeure);
        this.heurePan.add(Box.createRigidArea(new Dimension(5,0)));
        JLabel heureLabel = new JLabel("H");
        heureLabel.setForeground(Color.BLACK);
        this.heurePan.add(heureLabel);
        this.heurePan.add(Box.createRigidArea(new Dimension(5,0)));
        //spinner minute
        this.spinnerMinute = new JSpinner(new SpinnerNumberModel(0, -1, 60, 1));
        this.spinnerMinute.addChangeListener((ChangeEvent) -> {
            if (this.spinnerMinute.getNextValue() == null) {
                this.spinnerMinute.setValue(0);
            }
            else if (this.spinnerMinute.getPreviousValue() == null) {
                this.spinnerMinute.setValue(59);
            }
            if (this.heureBouton.isSelected()) {
                this.map.ajouterInformation(map.getListeAeroport(), map.getListeVols(), sliderMarge.getValue(), sliderHauteur.getValue(), (int)spinnerNiveau.getValue(), new Horaire((int)spinnerHeure.getValue(), (int)spinnerMinute.getValue()));
            }
        });
        this.heurePan.add(this.spinnerMinute);
        this.heurePan.add(Box.createRigidArea(new Dimension(3,0)));
        settingPan.add(this.heurePan);
        settingPan.add(Box.createRigidArea(new Dimension(0,3)));


        buttonPan.add(settingPan, BorderLayout.EAST);

        JPanel menuLabelPan = new JPanel();
        menuLabelPan.setOpaque(false);
        menuLabelPan.setLayout(new BoxLayout(menuLabelPan, BoxLayout.PAGE_AXIS));
        //Label
        this.infoLabelPan = new JPanel();
        this.infoLabelPan.setVisible(false);
        this.infoLabelPan.setOpaque(true);
        this.infoLabelPan.setLayout(new BoxLayout(this.infoLabelPan, BoxLayout.LINE_AXIS));
        this.infoLabelPan.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        this.infoLabel.setFont(new Font("Arial", Font.BOLD, 14));
        this.infoLabel.setBackground(Color.WHITE);
        this.infoLabelPan.add(this.infoLabel);
        this.infoLabelPan.add(Box.createHorizontalGlue());
        

        // button menu
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
        this.sliderHauteur = new JSlider(JSlider.HORIZONTAL, 1, 20, 1);
        this.sliderHauteur.setEnabled(false);
        sliderHauteurPan.add(this.sliderHauteur);
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
        this.sliderMarge = new JSlider(JSlider.HORIZONTAL, 1, 60, 15);
        this.sliderMarge.setEnabled(false);
        sliderMargePan.add(this.sliderMarge);
        sliderMargePan.add(Box.createRigidArea(new Dimension(10,0)));
        //JLabel int
        JLabel margeIntLabel = new JLabel("15");
        this.sliderMarge.addChangeListener((ChangeEvent) -> {
            margeIntLabel.setText(Integer.toString(this.sliderMarge.getValue()));
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
            if ((int)spinnerNiveau.getValue() == 0) {
                Horaire heure = null;
                if (this.heureBouton.isSelected()) {
                    heure = new Horaire((int)spinnerHeure.getValue(), (int)spinnerMinute.getValue());
                }
                map.ajouterInformation(map.getListeAeroport(), map.getListeVols(), sliderMarge.getValue(), sliderHauteur.getValue(), 0, heure);
            } else {
                spinnerNiveau.setValue(0); 
            }
            spinnerNiveau.setModel(new SpinnerNumberModel(0, 0, sliderHauteur.getValue(), 1));
            retour.doClick();
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
                ListeAeroport listAeroport = new ListeAeroport();
                // lecture du fichier aeroport
                try {
                    listAeroport.remplir("data/aeroports.txt");
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
                // lecture du fichier vol-test
                ListeVol listVols = new ListeVol();
                try {
                    listVols.remplir(filePath, listAeroport);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e.getMessage(), "Avertissement", JOptionPane.WARNING_MESSAGE);
                }
                Horaire heure = null;
                if (this.heureBouton.isSelected()) {
                    heure = new Horaire((int)spinnerHeure.getValue(), (int)spinnerMinute.getValue());
                }
                map.ajouterInformation(listAeroport, listVols, 15, 1, 0, heure);
                sliderHauteur.setEnabled(true);
                this.sliderMarge.setEnabled(true);
                sliderHauteur.setValue(1);
                HauteurIntLabel.setText("1");
                this.sliderMarge.setValue(15);
                margeIntLabel.setText("15");
                button.setEnabled(true);
                spinnerNiveauPan.setVisible(true);
                spinnerNiveau.setEnabled(true);
                spinnerNiveau.setModel(new SpinnerNumberModel(0, 0, 0, 1));
                heurePan.setVisible(true);
                heureBouton.setEnabled(true);
                infoLabelPan.setVisible(true);
                infoLabel.setText("Cliquez sur un élément");
                retour.doClick();
            } 
        });
    }
}