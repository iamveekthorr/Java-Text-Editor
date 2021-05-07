import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.IntelliJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

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
    JMenuBar menuBar;
    JTabbedPane tabPane;

    public SetUp(){
        super(TITLE);
        this.setTitle(TITLE);

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(new Dimension(screenSize.width, screenSize.height));
        this.setMinimumSize(new Dimension(screenSize.width/2, screenSize.height/2) );
        this.validate();

        jTree.setBorder(new EmptyBorder(10, 20 , 10 , 20));
        setLookAndFeel();
        initializeMenuBar();
        initializePanels();

        // Sets frame to visible only when the elements are already set inside the frame
        this.setVisible(true);
    }


    void setLookAndFeel(){

            try {
                UIManager.setLookAndFeel( new FlatLightLaf());
                UIManager.put( "ScrollBar.showButtons", true );
                UIManager.put( "TabbedPane.showTabSeparators", true );
                UIManager.put( "TabbedPane.selectedBackground", Color.white );
                UIManager.put( "Component.hideMnemonics", false );

                // Remove comment for dark mode
                // FlatDraculaIJTheme.install();

            }catch ( UnsupportedLookAndFeelException ex){
                System.out.println(ex.getMessage());
            }

    }

    void initializeMenuBar() {
        menuBar = new JMenuBar();
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

                    if (element.equalsIgnoreCase("preferences")){
                        try {
                            UIManager.setLookAndFeel( new FlatLightLaf() );
                            System.out.println("prefs");
                        } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
                            unsupportedLookAndFeelException.printStackTrace();
                        }
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
        // Adding items to the JMenuBar
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
        
        openFile.addActionListener(e -> new HandleComponentEvents().handleFileAndFolder(this, e));
        openFolder.addActionListener(e -> new HandleComponentEvents().handleFileAndFolder(this, e));

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

        // Added MenuBar to the frame
        this.setJMenuBar(menuBar);

    }

    void initializePanels(){
        SPLIT_PANE.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        comp = SPLIT_PANE.getPreferredSize().height;
        JPanel rightPanelContainer = new JPanel(false);
        rightPanelContainer.setLayout(new GridLayout(1, 1));


        // adding tabbed view
        tabPane = new JTabbedPane();
        tabPane.putClientProperty("JTabbedPane.showTabSeparators", true);
        tabPane.putClientProperty("JTabbedPane.showContentSeparator", true);

        tabPane.addTab("[UNTITLED]",makeComponent(new JPanel(), new JScrollPane(), new JTextArea() ));

        tabPane.addTab("[TITLE]", makeComponent(new JPanel(), new JScrollPane(), new JTextArea() ));
        tabPane.addTab("[DOTENV]", makeComponent(new JPanel(), new JScrollPane(), new JTextArea() ));


        rightPanelContainer.add(tabPane, BorderLayout.CENTER);

        rightPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jTree, rightPanelContainer);
        rightPane.setResizeWeight(0.2);
        topPane.setPreferredSize(SPLIT_PANE.getPreferredSize());
        rightPane.setPreferredSize(topPane.getPreferredSize());
        rightPanelContainer.setPreferredSize(getContentPane().getPreferredSize());



        // Set sizes for panels
        SPLIT_PANE.setPreferredSize(SPLIT_PANE.getPreferredSize());
        SPLIT_PANE.validate();


        topPane.add(rightPane);
        topPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        SPLIT_PANE.setTopComponent(topPane);
        SPLIT_PANE.setBottomComponent(bottomComponent);
        SPLIT_PANE.setResizeWeight(0.7);

        this.add(SPLIT_PANE);
    }

    // Take in an arbitrary number of components
    Component makeComponent(Component...args ){
        JPanel panel = new JPanel();
        JTextArea textArea = new JTextArea();
        JScrollPane scrollPanel = new JScrollPane(textArea,  JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.putClientProperty("JScrollPane.smoothScrolling", true);
        return panel.add(scrollPanel);
    }

}
