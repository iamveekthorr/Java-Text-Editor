import javax.swing.*;
import java.awt.*;

public class Initialize {
    public static JSplitPane SPLIT_PANE = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    public final static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final static  String TITLE = "Text Editor";
    JPanel topPane= new JPanel();
    JPanel bottomComponent = new JPanel();
    JTree jTree = new HandleJTree().createJTree();
    int comp;
    JSplitPane topSplitPane;
    JMenuBar menuBar;
    JTabbedPane tabPane;

}
