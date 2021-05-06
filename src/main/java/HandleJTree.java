import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import java.awt.*;
import java.io.File;
import java.util.Collections;
import java.util.Objects;
import java.util.Vector;

public class HandleJTree {
    HandleJTree(File dir, JTree tree){
        tree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) e.getPath()
                    .getLastPathComponent();
            System.out.printf("You selected %s", node);
            DefaultMutableTreeNode defNode =  addNodes(node, dir);
            System.out.println(defNode);
        });
         tree.getCellRenderer();
    }


    private DefaultMutableTreeNode addNodes(DefaultMutableTreeNode curTop, File dir) {
        String curPath = dir.getPath();
        DefaultMutableTreeNode curDir = new DefaultMutableTreeNode(curPath);
        if (curTop != null) {
            curTop.add(curDir);
        }
        Vector<String> ol = new Vector<String>();
        for (String s : Objects.requireNonNull(dir.list())) {
            ol.addElement(s);
        }
        ol.sort(String.CASE_INSENSITIVE_ORDER);
        File f;
        Vector<Object> files = new Vector<Object>();
        for (int i = 0; i < ol.size(); i++) {
            String thisObject = ol.elementAt(i);
            String newPath;
            if (curPath.equals(".")) {
                newPath = thisObject;
            } else {
                newPath = curPath + File.separator + thisObject;
            }
            if ((f = new File(newPath)).isDirectory()) {
                addNodes(curDir, f);
            } else {
                files.addElement(thisObject);
            }
        }
        for (int fnum = 0; fnum < files.size(); fnum++) {
            curDir.add(new DefaultMutableTreeNode(files.elementAt(fnum)));
        }
        return curDir;
    }
}
