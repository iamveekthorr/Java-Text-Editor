import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.Console;
import java.io.IOException;
import java.io.LineNumberReader;
import java.net.URISyntaxException;
import java.util.EventObject;

public class SetUp extends JFrame {

    private final static  String TITLE = "Text Editor";
    private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static JSplitPane SPLIT_PANE = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    public SetUp(){
        super(TITLE);
        this.setTitle(TITLE);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(screenSize.width, screenSize.height));
        this.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2) );
        initializeMenuBar();
        initializePanels();

    }


    void initializeMenuBar(){
        JMenuBar menuBar = new JMenuBar();
        UIManager.put("MenuBar.background", Color.GRAY);
        menuBar.setPreferredSize(new Dimension(100, 30));

        // Array of JMenus
        JMenu edit, find, preferences, window, terminal,  file;
        file= new JMenu("File");
        edit = new JMenu("Edit");
        find= new JMenu("Find");
        preferences= new JMenu("Preferences");
        window = new JMenu("Window");
        terminal = new JMenu("Terminal");

        JMenu[] jMenus = {file, edit, find, preferences, window, terminal};

        for (JMenu element: jMenus) {
            element.addMenuListener(new MenuListener() {
                @Override
                public void menuSelected(MenuEvent e) {
                    // Type change
                    String element = ( (JMenu) e.getSource()).getText();

                    // Checks if terminal is clicked
                    if (element.equalsIgnoreCase("terminal")){
                        // Sets frame visibility to false thereby hiding it
                        SPLIT_PANE.getBottomComponent().setVisible(!SPLIT_PANE.getBottomComponent().isVisible());

                        SPLIT_PANE.setResizeWeight(0.6);
                    }

                }

                @Override
                public void menuDeselected(MenuEvent e) {

                }

                @Override
                public void menuCanceled(MenuEvent e) {

                }
            });


        }

        menuBar.add(file).setForeground(Color.WHITE);
        menuBar.add(edit).setForeground(Color.WHITE);
        menuBar.add(find).setForeground(Color.WHITE);
        menuBar.add(preferences).setForeground(Color.WHITE);
        menuBar.add(terminal).setForeground(Color.WHITE);
        menuBar.add(window).setForeground(Color.WHITE);


        // File menuItems
        JMenuItem openFile, openFolder, save, exit;

        openFile= new JMenuItem("Open File");
        openFolder= new  JMenuItem("Open Folder",KeyEvent.VK_O);
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        exit.addActionListener(e -> {
            String text = ((JMenuItem) e.getSource()).getText();
            if(text.equalsIgnoreCase("exit")) System.exit(0);
        });


        file.add(openFile);
        file.addSeparator();
        file.add(openFolder);
        file.addSeparator();
        file.add(save);
        file.addSeparator();
        file.add(exit);

        // Adding the sub menus for the file
        JMenuItem newOption = new JMenu("New");
        newOption.add(new JMenuItem("File", KeyEvent.VK_N));
        newOption.add(new JMenuItem("Folder"));

        this.setJMenuBar(menuBar);
    }

    void handleComponentEvent(Component element){
        element.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {

            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });
    }

    void handleMenuItemClicks(JMenuItem element){
        element.setAccelerator(
                KeyStroke.getKeyStroke(
                        (char) KeyEvent.VK_O));
        System.out.println(element.getText());
    }

    void initializePanels(){
        JTextArea tx = new JTextArea();
        
        JScrollPane scrollPane = new JScrollPane(tx);
        SPLIT_PANE.setTopComponent(new JPanel().add(new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
                new JTree(), scrollPane)));
        SPLIT_PANE.setBottomComponent(new JPanel());

        SPLIT_PANE.setResizeWeight(0.6);
        this.add(SPLIT_PANE);

    }

}
