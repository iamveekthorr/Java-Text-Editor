import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;
import java.util.*;

public class HandleComponentEvents {
    JFileChooser fileChooser = new JFileChooser();
    File filePath;
    int result ;

    void handleFileAndFolder(Component component, EventObject name){

         String text = ((JMenuItem) name.getSource()).getText();
         // conditional statement using a ternary operator in a batch
         fileChooser.setFileSelectionMode(text.equalsIgnoreCase("open file") ? JFileChooser.FILES_ONLY
                 : JFileChooser.DIRECTORIES_ONLY);
        // set current directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        result =  fileChooser.showOpenDialog(component);


        if (result == JFileChooser.APPROVE_OPTION) {
            Initialize initialSet = new Initialize();
            setFilePath(fileChooser.getSelectedFile().getPath());
            JTree tree = createJTree(filePath.getName(), String.valueOf(filePath));
            initialSet.setjTree(tree);
            Initialize.leftContainer.add(initialSet.makeTreeComponent(tree));
            SwingUtilities.updateComponentTreeUI(tree);
            System.out.printf("completed %s",  filePath);
        }
    }

    public void setFilePath(String filePath) {
        this.filePath = new File(filePath);
        System.out.println(filePath);

    }

     File[] getListFiles(String Path) {
        File file = new File(Path);
        return file.listFiles();
    }

    JTree createJTree(@NotNull String fileName, String files) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(fileName);
        addChild(root, files);
        DefaultTreeModel treeModel = new DefaultTreeModel(root);
        treeModel.addTreeModelListener(new TreeModelListener() {
            @Override
            public void treeNodesChanged(TreeModelEvent e) {
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
                } catch (NullPointerException exc) {
                    exc.printStackTrace();
                }
            }

            @Override
            public void treeNodesInserted(TreeModelEvent e) {

            }

            @Override
            public void treeNodesRemoved(TreeModelEvent e) {

            }

            @Override
            public void treeStructureChanged(TreeModelEvent e) {

            }
        });
        treeModel.reload();
        return new JTree(treeModel);
    }

    void addChild(DefaultMutableTreeNode rootNode, String path) {
        File[] files = this.getListFiles(path);

        for(File file:files) {
            if (file != null){
                if(file.isDirectory()) {
                    DefaultMutableTreeNode subDirectory = new DefaultMutableTreeNode(file.getName());
                    addChild(subDirectory, file.getAbsolutePath());
                    rootNode.add(subDirectory);
                } else {
                    rootNode.add(new DefaultMutableTreeNode(file.getName()));
                }
            }
        }

    }

}
