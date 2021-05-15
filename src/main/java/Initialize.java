import javax.swing.*;
import java.awt.*;

public class Initialize {
    public static JSplitPane SPLIT_PANE = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    public final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    static final JPanel leftContainer = new JPanel(false);
    JPanel topPane = new JPanel();
    JPanel bottomComponent = new JPanel();
    JTree jTree;
    int comp;
    static final JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JMenuBar menuBar;
    JTabbedPane tabPane;
    JPanel tabContainer;
    JScrollPane jScrollPane;
    JTextPane jEditorPane;

    public JTextPane getJEditorPane() {
        return jEditorPane;
    }

    public void setJEditorPane(JTextPane jEditorPane) {
        this.jEditorPane = jEditorPane;
    }

    public JScrollPane getJScrollPane() {
        return jScrollPane;
    }

    public void setJScrollPane(JScrollPane jScrollPane) {
        this.jScrollPane = jScrollPane;
    }

    public JPanel getTabContainer() {
        return tabContainer;
    }

    public void setTabContainer(JPanel tabContainer) {
        this.tabContainer = tabContainer;
    }

    public JLabel getTabName() {
        return tabName;
    }

    public void setTabName(JLabel tabName) {
        this.tabName = tabName;
    }

    JLabel tabName;

    public void setMenuBar(JMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public static JPanel getLeftContainer() {
        return leftContainer;
    }

    public JPanel getTopPane() {
        return topPane;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    public JTree getJTree() {
        return jTree;
    }
    public void setJTree(JTree jTree) {
        this.jTree = jTree;
    }
    // Constructor
    Initialize() {
        SwingUtilities.updateComponentTreeUI(topSplitPane);
    }



    Component makeTreeComponent(JTree treeComponent) {
        JPanel panel = new JPanel();
        treeComponent.setPreferredSize(Initialize.topSplitPane.getLeftComponent().getSize());
        treeComponent.getCellRenderer();
        return panel.add(treeComponent);
    }
}
