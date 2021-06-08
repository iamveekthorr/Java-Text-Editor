import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDarkFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import org.jdesktop.swingx.JXFrame;
import org.jdesktop.swingx.JXTree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.undo.CannotRedoException;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.stream.IntStream;

public class SetUp extends JXFrame{
    Initialize init = new Initialize();
    private final static  String TITLE = "Text Editor";
    SetUp(){
        JXFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        this.setFont(new Font("FiraCode", Font.PLAIN, 16));
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
            SwingUtilities.updateComponentTreeUI(Initialize.topSplitPane);
            SwingUtilities.updateComponentTreeUI(Initialize.SPLIT_PANE);
            SwingUtilities.updateComponentTreeUI(init.getJTree() == null ? init.jTree = new JXTree() : init.getJTree());
            initializeMenuBar();
            initializePanels();

        }catch ( UnsupportedLookAndFeelException ex){
            System.out.println(ex.getMessage());
        }

        // Sets frame to visible only when the elements are already set inside the frame
        this.setVisible(true);
    }

    void initializeMenuBar(){
        init.setMenuBar(new JMenuBar());
        JMenu[] menuItemComponents = insertMenuItems();
        // Array of JMenus
        for (JMenu element: menuItemComponents) { init.getMenuBar().add(element); }
        init.getMenuBar().setBorder(null);
        // Added MenuBar to the frame
        this.setJMenuBar(init.getMenuBar());
    }

    /**
     * @return JMenu[]
     * Adds inserts all the elements into
     * the Menubar
     */
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

    /**
     * @return JMenuItem[] Array
     * For Preferences Menu
     * */
    JMenuItem[] addPreferencesMenuItems(){
        JMenuItem darkModeTheme, lightTheme;

        darkModeTheme = new JMenuItem("Dark Theme");
        lightTheme = new JMenuItem("Light Theme");
        addListenersToMenuItemComponents(darkModeTheme, lightTheme);

        return new JMenuItem[]{darkModeTheme, lightTheme};
    }

    /**
     * @return JMenuItem[] Array
     * for Terminal Menu
     * */
    JMenuItem[] addTerminalMenuItems(){
        JMenuItem showTerminal, hideTerminal;

        showTerminal = new JMenuItem("Show Terminal");
        hideTerminal = new JMenuItem("Hide Terminal");

        addListenersToMenuItemComponents(showTerminal, hideTerminal);

        return new JMenuItem[]{showTerminal, hideTerminal};
    }
    /**
     * @return JMenuItem[] Array
     * For File Menu
     * */
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

    /**
     * @param args (JMenuItems Array)
     * loops all the elements in the array
     * Adds event listener to each element
     * */
    void addListenersToMenuItemComponents(JMenuItem... args){
        for (JMenuItem el: args ){
            el.addActionListener(e -> {
                String text = ((JMenuItem) e.getSource()).getText();
                // Set event listener for open file
                if ((text.equalsIgnoreCase("Open File")) ||
                        (text.equalsIgnoreCase("Open Folder"))){
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
                if(text.equalsIgnoreCase("save")){
                    try {
                        new HandleComponentEvents().handleSaveEvent(HandleComponentEvents.getFilePath());
                    } catch (IOException| NullPointerException ex) {
                        ex.printStackTrace();
                    }
                }
                if(text.equalsIgnoreCase("show terminal")){
                    try {
                        new Terminal();
                    } catch (IOException | InterruptedException ioException) {
                        ioException.printStackTrace();
                    }
                }
            });
        }
    }

    void handleDarkModeEvent(){
        if (!FlatLightLaf.isLafDark()){
            FlatDarkFlatIJTheme.install();
            UIManager.put( "TabbedPane.selectedBackground", Color.darkGray );
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    void handleLightModeEvent(){
        if (FlatLightLaf.isLafDark()){
            FlatLightFlatIJTheme.install();
            UIManager.put( "TabbedPane.selectedBackground", Color.lightGray );
            SwingUtilities.updateComponentTreeUI(this);
        }
    }

    void handleShowTerminalEvent(){
        Initialize.SPLIT_PANE.getBottomComponent().setVisible(true);
        for (Component components :  init.getTopPane().getComponents()) {
            components.setPreferredSize(new Dimension(Initialize.screenSize.width, 900));
        }
    }

    void handleHideTerminal(){
        Initialize.SPLIT_PANE.getBottomComponent().setVisible(false);
        init.getTopPane().setPreferredSize(new Dimension(Initialize.screenSize.width, 800));
    }

    void addNewFileListener(){
        String newName = "[UNTITLED]";
        JPanel newTab = init.getTabContainer();
        init.tabPane.setBorder(null);
        init.tabPane.add(makeTabComponent(newTab));
        int tabCount = init.tabPane.getTabCount();

        IntStream.range(0, tabCount).forEach(tabIndex -> init.tabPane.setTitleAt(tabIndex, newName));
    }

    /**
     * Initializes all the panels
     * in the frame
     * @return void
     * */
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
        Initialize.topSplitPane.setLeftComponent(Initialize.getLeftContainer());
        init.tabPane.setPreferredSize(Initialize.SPLIT_PANE.getPreferredSize());
        Initialize.topSplitPane.setPreferredSize(new Dimension((int) Initialize.screenSize.getWidth(),
                Initialize.screenSize.height));
        rightPanelContainer.setPreferredSize(new Dimension((int) Initialize.screenSize.getWidth(),
                Initialize.screenSize.height));
        rightPanelContainer.setBorder(null);
        Initialize.topSplitPane.setDividerLocation(0.5);
        Initialize.topSplitPane.setResizeWeight(0.2);



        // Set sizes for panels
        Initialize.SPLIT_PANE.setPreferredSize(Initialize.SPLIT_PANE.getPreferredSize());

        init.getTopPane().add(Initialize.topSplitPane);
        init.getTopPane().setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        Initialize.SPLIT_PANE.setTopComponent(init.getTopPane());

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


    /**
     * @return component containing all the elements
     * in the Tab, and Adds mouse action listener to
     * each returned item
     * @param args (JPanel)
     * */
    // Take in an arbitrary number of components
    Component makeTabComponent(@NotNull JComponent...args ){
        JPanel panel = new JPanel();
        JTextPane textArea = new JTextPane();

        textArea.setBorder(new EmptyBorder(5, 10, 5, 0 ));
        JScrollPane scrollPanel = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setViewportView(textArea);
        TextLineNumber textLineNumber = new TextLineNumber(textArea);
        textLineNumber.setUpdateFont(true);
        scrollPanel.setRowHeaderView(textLineNumber);
        // EXPLICITLY SET BORDERS TO NULL FOR ALL COMPONENTS
        panel.setBorder(null);
        scrollPanel.setBorder(null);
        textLineNumber.setBorder(null);
        scrollPanel.putClientProperty("JScrollPane.smoothScrolling", true);

        // adds mouse event to all components created by this function
        showPopupMenu(textArea);



        return panel.add(scrollPanel);
    }

    void showPopupMenu(JTextPane component){
        JPopupMenu popupMenu = new JPopupMenu();

        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == 3) popupMenu.show(component, e.getX(),
                        e.getY()) ;
            }
        });

        for (JMenuItem popUpItem: addMenuItemsToPopUpMenu()) {
            popupMenu.add(popUpItem);

            popUpItem.addActionListener(e -> {
                String eventName = ((JMenuItem) e.getSource()).getText();
                if (eventName.equalsIgnoreCase("cut")){
                    handleCutAction(component);
                }
                if (eventName.equalsIgnoreCase("copy")){
                    handleCopyAction(component);
                }
                if (eventName.equalsIgnoreCase("paste")){
                    handlePasteAction(component);
                }
                if(eventName.equalsIgnoreCase("undo")){
                    handleUndoEvent();
                }
                if(eventName.equalsIgnoreCase("redo")){
                    handleRedoEvent();
                }
            });
        }

    } // Method show popup menu

    JMenuItem[] addMenuItemsToPopUpMenu(){
        JMenuItem cut = new JMenuItem("Cut");
        JMenuItem paste = new JMenuItem("Copy");
        JMenuItem copy = new JMenuItem("Paste");
        JMenuItem undoMenu = new JMenuItem("Undo");
        JMenuItem redoMenu = new JMenuItem("Redo");
        JMenuItem closeTab = new JMenuItem("Close Tab");
        return new JMenuItem[]{cut, copy, paste, undoMenu, redoMenu, closeTab};
    }

    void handleCutAction(JTextPane textArea){
        if (textArea.getSelectedText() != null) {
            textArea.cut();
        }
    }

    void handleCopyAction(JTextPane textArea){
        if (textArea.getSelectedText() != null) {
            textArea.copy();
        }
    }

    void handlePasteAction(JTextPane textArea){
        if (textArea.getSelectedText() != null) {
            textArea.paste();
        }
    }
    void handleUndoEvent(){
        try {
            Initialize.undo.undo();
        } catch (CannotRedoException cre) {
            cre.getMessage();
        }
    }
    void handleRedoEvent(){
        try {
            Initialize.undo.redo();
        } catch (CannotRedoException cre) {
            cre.getCause();
        }
    }
}// End of class Declaration
