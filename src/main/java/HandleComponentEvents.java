import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
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

//        filePath = fileChooser.getSelectedFile();
        if (result == JFileChooser.APPROVE_OPTION) {
            setFilePath(fileChooser.getSelectedFile().getPath());
            System.out.printf("completed%sn",  filePath);
        }
    }



    String getPathName(){
        return filePath.getAbsolutePath();
    }

    public void setFilePath(String filePath) {
        this.filePath = new File(filePath);
        System.out.println(filePath);
    }

    void setFileChooser(){
        this.filePath = new File(this.getPathName());
    }
    String getFileName(){
        File selectedFile = fileChooser.getSelectedFile();
        System.out.println(selectedFile);
        return selectedFile.getName();
    }

     File[] getListFiles(String Path) {
        File file = new File(Path);
        return file.listFiles();
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

//    String getFile(){
//        return selectedFile = new File(file.getAbsolutePath());;
//    }
//    public void setFileChooser(JFileChooser fileChooser) {
//        this.fileChooser = fileChooser;
//        if (result == JFileChooser.APPROVE_OPTION) {
//            System.out.println("Selected file: " + getNewSelectedFile().length);
//        }
//    }
//
//    File[] getNewSelectedFile(){
//        selectedFile = fileChooser.getSelectedFile();
//
//        System.out.println("OUCH: SELECTED" + selectedFile);
//        return selectedFile.listFiles();
//    }

    void handleTerminal(JComponent terminal){
        if (terminal.isVisible()){
            terminal.setVisible(false);
        }
    }
}
