import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import javax.swing.*;
import java.awt.*;

public class SetUp extends JFrame{
    Initialize init = new Initialize();
    private final static  String TITLE = "Text Editor";

    public SetUp(){
        JFrame.setDefaultLookAndFeelDecorated(true);
        JDialog.setDefaultLookAndFeelDecorated(true);
        init();
    }

    void init(){
        try {
            this.setTitle(TITLE);
            UIManager.setLookAndFeel( new FlatLightLaf());
            UIManager.put( "ScrollBar.showButtons", true );
            UIManager.put( "TabbedPane.showTabSeparators", true );
            UIManager.put( "TabbedPane.selectedBackground", Color.lightGray );
            UIManager.put( "Component.hideMnemonics", false );
            SwingUtilities.updateComponentTreeUI(this);
            SwingUtilities.updateComponentTreeUI(init.jTree);
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
        CreateMenuBar menuItems = new CreateMenuBar();
        JMenu[] menuItemComponents = menuItems.insertMenuItems();
        // Array of JMenus
        for (JMenu element: menuItemComponents) { init.menuBar.add(element); }
        init.menuBar.setBorder(null);
        // Added MenuBar to the frame
        this.setJMenuBar(init.menuBar);
    }


    void initializePanels(){
        Initialize.SPLIT_PANE.setPreferredSize(new Dimension(Initialize.screenSize.width,
                Initialize.screenSize.height));
        Initialize.SPLIT_PANE.setBorder(null);
        Initialize.SPLIT_PANE.setBackground(Color.white);
        init.comp = Initialize.SPLIT_PANE.getPreferredSize().height;
        JPanel rightPanelContainer = new JPanel(false);
        rightPanelContainer.setLayout(new GridLayout(1, 1));

        init.jTree.setBackground(Initialize.SPLIT_PANE.getBackground());


        // adding tabbed view
        init.tabPane = new JTabbedPane();
        init.tabPane.putClientProperty("JTabbedPane.showTabSeparators", true);
        init.tabPane.putClientProperty("JTabbedPane.showContentSeparator", true);
        init.tabPane.putClientProperty("JTabbedPane.hasFullBorder", true);

        init.tabPane.addTab("[UNTITLED]", makeTabComponent(new JPanel(), new JScrollPane(), new JEditorPane() ));

        init.tabPane.addTab("[TITLE]", makeTabComponent(new JPanel(), new JScrollPane(), new JEditorPane() ));
        init.tabPane.addTab("[DOTENV]", makeTabComponent(new JPanel(), new JScrollPane(), new JEditorPane() ));


        rightPanelContainer.add(init.tabPane, BorderLayout.CENTER);

        init.topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        init.topSplitPane.setLeftComponent( new HandleJTree().createJTree() != null ?
                new HandleJTree().createJTree() : new JTree());

        init.topSplitPane.setRightComponent(rightPanelContainer);
        init.topSplitPane.setResizeWeight(0.2);
        init.tabPane.setPreferredSize(Initialize.SPLIT_PANE.getPreferredSize());
        init.topSplitPane.setPreferredSize(new Dimension((int) Initialize.screenSize.getWidth(),
                Initialize.screenSize.height));
        rightPanelContainer.setPreferredSize(new Dimension((int) Initialize.screenSize.getWidth(),
                Initialize.screenSize.height));


        // Set sizes for panels
        Initialize.SPLIT_PANE.setPreferredSize(Initialize.SPLIT_PANE.getPreferredSize());

        init.topPane.add(init.topSplitPane);
        init.topPane.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        Initialize.SPLIT_PANE.setTopComponent(init.topPane);

        Initialize.SPLIT_PANE.setBottomComponent(init.bottomComponent);
        init.bottomComponent.setBackground(getContentPane().getBackground());
        init.bottomComponent.setBorder(null);
        Initialize.SPLIT_PANE.setResizeWeight(0.7);
        Initialize.SPLIT_PANE.setBorder(null);
        init.topSplitPane.setOneTouchExpandable(true);
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
    Component makeTabComponent(Component...args ){
        JPanel panel = new JPanel();
        panel.setBorder(null);
        JEditorPane textArea = new JEditorPane();
        textArea.setBorder(null);
        JScrollPane scrollPanel = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPanel.setBorder(null);
        scrollPanel.putClientProperty("JScrollPane.smoothScrolling", true);
        return panel.add(scrollPanel);
    }

}
