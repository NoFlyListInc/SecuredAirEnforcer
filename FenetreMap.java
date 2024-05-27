//#region imports
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.File;
//#endregion

public class FenetreMap extends JFrame
{
    //#region Attributs
    private Map map;
    private JLayeredPane superposePan = new JLayeredPane();
    private MenuOngletPan menu = new MenuOngletPan(superposePan);
    //#endregion

    //#region Constructeur
    public FenetreMap() {
        this.map = new Map();
        ListAeroport listAeroport = new ListAeroport();
        listAeroport.fill("./Data/aeroports.txt");
        map.setListAeroport(listAeroport);
        this.constrFen();
    }
    //#endregion

    //#region méthodes
    private void constrFen() {
        this.setTitle("Map");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(constrPan());        
        this.setSize(800, 600);        
        this.setLocationRelativeTo(null);
    }

    private JLayeredPane constrPan() {
        superposePan.setPreferredSize(new Dimension(800, 600));

        JScrollPane mapPan = new JScrollPane(this.map);
        mapPan.setBounds(0, 0, 800, 600);
        mapPan.setOpaque(true);
        superposePan.add(mapPan, JLayeredPane.DEFAULT_LAYER);

        JPanel buttonPan = new JPanel();
        buttonPan.setBounds(0, 0, 800, 600);
        buttonPan.setOpaque(false);
        buttonPan.setLayout(new BorderLayout());

        JPanel importPan = new JPanel();
        importPan.setOpaque(false);
        importPan.setLayout(new BoxLayout(importPan, BoxLayout.PAGE_AXIS));
        JButton importButton = new JButton("import");
        importButton.addActionListener((ActionListener) -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                String filePath = selectedFile.getAbsolutePath();
                ListVol listVols = new ListVol();
                listVols.fill(filePath, map.getListAeroport());
                map.addInformation(map.getListAeroport(), listVols);
                importButton.setBackground(Color.GREEN);
            } else {
                importButton.setBackground(Color.RED);
            }
        });
        importPan.add(importButton);
        importPan.add(Box.createVerticalGlue());
        buttonPan.add(importPan, BorderLayout.EAST);

        JPanel menuPan = new JPanel();
        menuPan.setOpaque(false);
        menuPan.setLayout(new BoxLayout(menuPan, BoxLayout.PAGE_AXIS));
        JButton menuButton = new JButton("menu");
        menuButton.addActionListener((ActionListner) -> {
            Dimension size = getContentPane().getSize();
            menu.setBounds(0, 0, size.width, size.height);
            superposePan.add(menu, JLayeredPane.MODAL_LAYER);
        });
        menuPan.add(Box.createVerticalGlue());
        menuPan.add(menuButton);
        buttonPan.add(menuPan, BorderLayout.WEST);

        superposePan.add(buttonPan, JLayeredPane.PALETTE_LAYER);

        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                Dimension size = getContentPane().getSize();
                mapPan.setBounds(0, 0, size.width, size.height);
                buttonPan.setBounds(0, 0, size.width, size.height);
                menu.setBounds(0, 0, size.width, size.height);
            }
        });

        return superposePan;
    }
}