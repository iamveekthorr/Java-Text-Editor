import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;

public class SetUp extends JFrame {

    private final static  String TITLE = "Text Editor";
    private final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static JSplitPane SPLIT_PANE = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    JPanel topPane= new JPanel();
    JPanel bottomComponent = new JPanel();
    JTree jTree = new JTree();
    JScrollPane scrollPane;
    int comp;
    JSplitPane rightPane;
    JTextArea txArea;

    public SetUp(){
        super(TITLE);
        this.setTitle(TITLE);
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(new Dimension(screenSize.width, screenSize.height));
        this.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2) );
        this.validate();
        jTree.setBorder(new EmptyBorder(10, 20 , 10 , 20));
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

                    if(element.equalsIgnoreCase("terminal")){
                        System.out.printf("This is a %s component of height %s",element,
                                topPane.getPreferredSize().getHeight());
                        SPLIT_PANE.getBottomComponent().setVisible(!bottomComponent.isVisible());
                        topPane.setPreferredSize(new Dimension(screenSize.width, 800));

                        for (Component components : topPane.getComponents()) {
                            components.setPreferredSize(new Dimension(screenSize.width, 900));
                        }
                        // testing
                        System.out.println(topPane.getPreferredSize().height);
                        System.out.printf("This is a %s component of height %s",element,
                                topPane.getPreferredSize().getHeight());
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
        
        openFile.addActionListener(e -> new HandleComponentEvents(this, e).handleFileAndFolder());
        openFolder.addActionListener(e -> new HandleComponentEvents(this, e).handleFileAndFolder());

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

    void initializePanels(){
        SPLIT_PANE.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        comp = SPLIT_PANE.getPreferredSize().height;
        txArea = new JTextArea();
        txArea.setPreferredSize(new Dimension(80, 80));
        
        scrollPane = new JScrollPane(txArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        rightPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jTree, scrollPane);


        // Set sizes for panels
        topPane.setPreferredSize(new Dimension(screenSize.width, comp-300)); // sets size for all elements in the topPane
        scrollPane.setPreferredSize(new Dimension(1400, topPane.getPreferredSize().height)); // sets JScrollPane size
        jTree.setPreferredSize(new Dimension(screenSize.width/8, topPane.getPreferredSize().height)); // sets JTree size
        SPLIT_PANE.setPreferredSize(SPLIT_PANE.getPreferredSize());
        scrollPane.validate();
        SPLIT_PANE.validate();


        topPane.add(rightPane);
        SPLIT_PANE.setTopComponent(topPane);
        SPLIT_PANE.setBottomComponent(bottomComponent);


        this.add(SPLIT_PANE);

    }

}
