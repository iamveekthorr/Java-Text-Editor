import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;

import javax.swing.*;
import java.awt.*;

public class SetUp extends JFrame{
    Initialize init = new Initialize();
    private final static  String TITLE = "Text Editor";

    SetUp(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        init();

        Initialize.SPLIT_PANE.setBorder(null);
    }

    void init(){
        try {
            this.setTitle(TITLE);
            UIManager.setLookAndFeel( new FlatLightLaf());
            FlatLightFlatIJTheme.install();
            UIManager.put( "ScrollBar.showButtons", true );
            UIManager.put( "TabbedPane.showTabSeparators", true );
            UIManager.put( "TabbedPane.selectedBackground", Color.lightGray );
            UIManager.put( "Component.hideMnemonics", false );
            SwingUtilities.updateComponentTreeUI(this);
            SwingUtilities.updateComponentTreeUI(init.topSplitPane);
            SwingUtilities.updateComponentTreeUI(Initialize.SPLIT_PANE);
            SwingUtilities.updateComponentTreeUI(init.jTree == null ? init.jTree = new JTree() : init.jTree);
            initializeMenuBar();
            initializePanels();

        }catch ( UnsupportedLookAndFeelException ex){
            System.out.println(ex.getMessage());
        }

        // Sets frame to visible only when the elements are already set inside the frame
        this.setVisible(true);
    }

    void initializeMenuBar(){
        init.menuBar = new JMenuBar();
        JMenu[] menuItemComponents = insertMenuItems();
        // Array of JMenus
        for (JMenu element: menuItemComponents) { init.menuBar.add(element); }
        init.menuBar.setBorder(null);
        // Added MenuBar to the frame
        this.setJMenuBar(init.menuBar);
    }

    JMenu[] insertMenuItems(){
        JMenu edit, find, preferences, window, terminal,  file;
        file= new JMenu("File");
        edit = new JMenu("Edit");
        find= new JMenu("Find");
        preferences= new JMenu("Preferences");
        window = new JMenu("Window");
        terminal = new JMenu("Terminal");

        for (JMenuItem fileMenuItems: addFileMenuItems()) {
            file.add(fileMenuItems);
        }

        for (JMenuItem terminalMenuItems: addTerminalMenuItems()){
            terminal.add(terminalMenuItems);
        }

        for (JMenuItem themes : addPreferencesMenuItems()){
            preferences.add(themes);
        }

        return new JMenu[]{file, edit, find, preferences, window, terminal};
    }

    JMenuItem[] addPreferencesMenuItems(){
        JMenuItem darkModeTheme, lightTheme;

        darkModeTheme = new JMenuItem("Dark Theme");
        lightTheme = new JMenuItem("Light Theme");
        addListenersToMenuItemComponents(darkModeTheme, lightTheme);

        return new JMenuItem[]{darkModeTheme, lightTheme};
    }

    JMenuItem[] addTerminalMenuItems(){
        JMenuItem showTerminal, hideTerminal;

        showTerminal = new JMenuItem("Show Terminal");
        hideTerminal = new JMenuItem("Hide Terminal");

        addListenersToMenuItemComponents(showTerminal, hideTerminal);

        return new JMenuItem[]{showTerminal, hideTerminal};
    }

    JMenuItem[] addFileMenuItems(){
        JMenuItem openFile, openFolder, save, exit, newFile;
        newFile = new JMenuItem("New File");
        openFile = new JMenuItem("Open File");
        openFolder = new JMenuItem("Open Folder");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        addListenersToMenuItemComponents(newFile, openFile, openFolder, exit, save);

        return new JMenuItem[]{newFile,openFile, openFolder, save, exit};
    }

    void addListenersToMenuItemComponents(JMenuItem... args){
        for (JMenuItem el: args ){
            el.addActionListener(e -> {
                String text = ((JMenuItem) e.getSource()).getText();
                // Set event listener for open file
                if ((text.equalsIgnoreCase("Open File")) || (text.equalsIgnoreCase("Open Folder"))){
                    new HandleComponentEvents().handleFileAndFolder(this, e);
                }

                if (text.equalsIgnoreCase("exit")){
                    System.exit(0);
                }
                if(text.equalsIgnoreCase("Show Terminal")){
                    handleShowTerminalEvent();
                }
                if (text.equalsIgnoreCase("hide Terminal")){
                    handleHideTerminal();
                }
                if (text.equalsIgnoreCase("Dark Theme")){
                    handleDarkModeEvent();
                }
                if (text.equalsIgnoreCase("Light Theme")){
                    handleLightModeEvent();
                }
                if (text.equalsIgnoreCase("New File")){
                    addNewFileListener();
                }
            });
        }
    }

    void handleDarkModeEvent(){
        if (!FlatLightLaf.isLafDark()){
            FlatDarkFlatIJTheme.install();
            UIManager.put( "TabbedPane.selectedBackground", Color.darkGray );
            SwingUtilities.updateComponentTreeUI(this);
            System.out.println("Changed");
        }
    }

    void handleLightModeEvent(){
        if (FlatLightLaf.isLafDark()){
            FlatLightFlatIJTheme.install();
            UIManager.put( "TabbedPane.selectedBackground", Color.lightGray );
            SwingUtilities.updateComponentTreeUI(this);
            System.out.println("Changed");
        }
    }

    void handleShowTerminalEvent(){
        Initialize.SPLIT_PANE.getBottomComponent().setVisible(true);
        for (Component components :  init.topPane.getComponents()) {
            components.setPreferredSize(new Dimension(Initialize.screenSize.width, 900));
        }
    }

    void handleHideTerminal(){
        Initialize.SPLIT_PANE.getBottomComponent().setVisible(false);
        init.topPane.setPreferredSize(new Dimension(Initialize.screenSize.width, 800));
    }

    void addNewFileListener(){
        init.tabPane.addTab("[UNTITLED]", makeTabComponent(new JPanel(),
                new JScrollPane(), new JEditorPane() ));
        init.tabPane.setBorder(null);
    }

    void initializePanels(){
        Initialize.SPLIT_PANE.setPreferredSize(new Dimension(Initialize.screenSize.width,
                Initialize.screenSize.height));
        Initialize.SPLIT_PANE.setBorder(null);
        Initialize.SPLIT_PANE.setBackground(Color.white);
        init.comp = Initialize.SPLIT_PANE.getPreferredSize().height;
        JPanel rightPanelContainer = new JPanel(false);
        rightPanelContainer.setLayout(new GridLayout(1, 1));

        // adding tabbed view
        init.tabPane = new JTabbedPane();
        init.tabPane.putClientProperty("JTabbedPane.showTabSeparators", true);
        init.tabPane.putClientProperty("JTabbedPane.showContentSeparator", true);
        init.tabPane.putClientProperty("JTabbedPane.hasFullBorder", true);
        rightPanelContainer.add(init.tabPane, BorderLayout.CENTER);

        Initialize.topSplitPane.setRightComponent(rightPanelContainer);
        Initialize.topSplitPane.setLeftComponent(Initialize.leftContainer);
        init.tabPane.setPreferredSize(Initialize.SPLIT_PANE.getPreferredSize());
        Initialize.topSplitPane.setPreferredSize(new Dimension((int) Initialize.screenSize.getWidth(),
                Initialize.screenSize.height));
        rightPanelContainer.setPreferredSize(new Dimension((int) Initialize.screenSize.getWidth(),
                Initialize.screenSize.height));
        Initialize.topSplitPane.setDividerLocation(0.5);
        Initialize.topSplitPane.setResizeWeight(0.2);



        // Set sizes for panels
        Initialize.SPLIT_PANE.setPreferredSize(Initialize.SPLIT_PANE.getPreferredSize());

        init.topPane.add(Initialize.topSplitPane);
        init.topPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        Initialize.SPLIT_PANE.setTopComponent(init.topPane);

        Initialize.SPLIT_PANE.setBottomComponent(init.bottomComponent);
        init.bottomComponent.setBackground(getContentPane().getBackground());
        init.bottomComponent.setBorder(null);
        Initialize.SPLIT_PANE.setResizeWeight(0.7);
        Initialize.topSplitPane.setOneTouchExpandable(true);
        Initialize.SPLIT_PANE.validate();


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setSize(new Dimension(Initialize.screenSize.width, Initialize.screenSize.height));
        this.setMinimumSize(new Dimension(Initialize.screenSize.width/2,
                Initialize.screenSize.height/2) );
        this.validate();
        this.add(Initialize.SPLIT_PANE);
    }

    // Take in an arbitrary number of components
    Component makeTabComponent(JComponent...args ){
        for (JComponent component : args){
            component.setBorder(null);
            SwingUtilities.updateComponentTreeUI(this);
        }
        JPanel panel = new JPanel();
        JEditorPane textArea = new JEditorPane();
        JScrollPane scrollPanel = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.putClientProperty("JScrollPane.smoothScrolling", true);
        return panel.add(scrollPanel);
    }


}
