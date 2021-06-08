import org.jdesktop.swingx.JXTree;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.EventObject;

import static java.nio.file.StandardWatchEventKinds.*;

public class HandleComponentEvents {
    JFileChooser fileChooser = new JFileChooser();
    static File filePath;
    int result;
    /**
     * @constructor
     * @param component, represents component to be added
     * @param name, represents the eventObject
     * @return  void, return type void
     * */
    void handleFileAndFolder(Component component, @NotNull EventObject name) {

        String text = ((JMenuItem) name.getSource()).getText();
        // conditional statement using a ternary operator in a batch
        fileChooser.setFileSelectionMode(text.equalsIgnoreCase("open file") ? JFileChooser.FILES_ONLY
                : JFileChooser.DIRECTORIES_ONLY);
        // set current directory
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        result = fileChooser.showOpenDialog(component);


        if (result == JFileChooser.APPROVE_OPTION) {
            Initialize initialSet = new Initialize();
            setFilePath(fileChooser.getSelectedFile().getPath());
            JXTree tree = createJTree(filePath.getName(), String.valueOf(filePath));
            initialSet.setJTree(tree);
            Initialize.getLeftContainer().add(initialSet.makeTreeComponent(tree));

            DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
            DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();

            tree.addTreeSelectionListener(e -> {
                TreePath tp = tree.getSelectionPath();
                if (tp != null) {
                    Object filePathToAdd = tp.getLastPathComponent();

                    System.out.println("file path "+filePathToAdd);

                }
            });


            model.reload(root);

            SwingUtilities.updateComponentTreeUI(tree);
        }
    }


    public static File getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        HandleComponentEvents.filePath = new File(filePath);
    }

    /**
     * @param curDir, takes in current directory
     * from the current rendered tree
     * Handles Save Event,
    */
    void handleSaveEvent(File curDir) throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path path = Paths.get(curDir.getAbsolutePath());
        try {
            if (path != null) {
                if (!Files.exists(path)) Files.createDirectory(path);

                path.register(watchService, ENTRY_DELETE, ENTRY_CREATE, ENTRY_MODIFY);
            }

            System.out.println("Monitoring.......");
            WatchKey watchKey = null;

            do {
                try {
                    watchKey = watchService.take();

                    watchKey.pollEvents().stream().map(event -> event.context().toString() + " " +
                            event.kind()).forEach(System.out::println);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            } while (watchKey.reset());
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    File[] getListFiles(String Path) {
        File file = new File(Path);
        return file.listFiles();
    }

    JXTree createJTree(@NotNull String fileName, String files) {
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
                  If the event lists children, then the changed
                  node is the child of the node we have already
                  gotten.  Otherwise, the changed node and the
                  specified node are the same.
                 */
                try {
                    int index = e.getChildIndices()[0];
                    node = (DefaultMutableTreeNode)
                            (node.getChildAt(index));
                    System.out.println("Node changed "+ node);
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
        return new JXTree(treeModel);
    }

    void addChild(DefaultMutableTreeNode rootNode, String path) {
        File[] files = this.getListFiles(path);

        for (File file : files) {
            if (file != null) {
                if (file.isDirectory()) {
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
