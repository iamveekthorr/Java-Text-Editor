import javax.swing.*;
import java.awt.*;

public class Initialize {
    public static JSplitPane SPLIT_PANE = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    public final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static String TITLE = "Text Editor";
    static final JPanel leftContainer = new JPanel(false);
    JPanel topPane = new JPanel();
    JPanel bottomComponent = new JPanel();
    JTree jTree;
    int comp;
    static final JSplitPane topSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JMenuBar menuBar;
    JTabbedPane tabPane;

    Initialize() {
        SwingUtilities.updateComponentTreeUI(topSplitPane);
    }

    public void setjTree(JTree jTree) {
        this.jTree = jTree;
    }

    Component makeTreeComponent(JTree treeComponent) {
        JPanel panel = new JPanel();
        treeComponent.setPreferredSize(Initialize.topSplitPane.getLeftComponent().getSize());
        treeComponent.getCellRenderer();
        return panel.add(treeComponent);
    }
}
