import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.*;
import java.io.File;
import java.util.Arrays;

public class HandleJTree {

    JTree createJTree() {
        HandleComponentEvents componentEvent =  new HandleComponentEvents();
        String folderName = "TextEditor";

        try {
            String filesInFolder = componentEvent.getPathName();

            DefaultMutableTreeNode root = new DefaultMutableTreeNode(folderName);

            componentEvent.addChild(root, filesInFolder);

            DefaultTreeModel treeModel = new DefaultTreeModel(root);
            treeModel.addTreeModelListener(new TreeModelListener() {
                @Override
                public void treeNodesChanged(TreeModelEvent e) {
                    System.out.println(Arrays.toString(e.getPath()));
                    DefaultMutableTreeNode node;
                    node = (DefaultMutableTreeNode)
                            (e.getTreePath().getLastPathComponent());

                    /*
                     * If the event lists children, then the changed
                     * node is the child of the node we have already
                     * gotten.  Otherwise, the changed node and the
                     * specified node are the same.
                     */
                    try {
                        int index = e.getChildIndices()[0];
                        node = (DefaultMutableTreeNode)
                                (node.getChildAt(index));
                        System.out.println(node);
                        System.out.println(e);
                        System.out.println(componentEvent.getPathName());
                    } catch (NullPointerException exc) {
                        exc.printStackTrace();
                    }

                    System.out.println("The user has finished editing the node.");
                    System.out.println("New value: " + node.getUserObject());
                }
                public void treeNodesInserted(TreeModelEvent e) {
                    System.out.println(e.getSource());
                }
                public void treeNodesRemoved(TreeModelEvent e) {System.out.println(e.getSource());
                }
                public void treeStructureChanged(TreeModelEvent e) {
                    System.out.println(Arrays.toString(e.getPath()));
                    System.out.println(e.getSource());
                }

            });
            return new JTree(treeModel);
        }catch (NullPointerException ex){
            System.out.println(ex.getMessage());
        }
        return new JTree();
    }
}
