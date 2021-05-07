import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.EventObject;

public class HandleComponentEvents {
    File selectedFile;

    void handleFileAndFolder(Component component, EventObject name){
         JFileChooser fileChooser = new JFileChooser();
         String text = ((JMenuItem) name.getSource()).getText();
         // conditional statement using a ternary operator in a batch
         fileChooser.setFileSelectionMode(text.equalsIgnoreCase("open file") ? JFileChooser.FILES_ONLY
                 : JFileChooser.DIRECTORIES_ONLY);

        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(component);
        selectedFile = fileChooser.getSelectedFile();
        if (result == JFileChooser.APPROVE_OPTION) {
            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
            System.out.println(selectedFile);
        }
    }

    void handleTerminal(JComponent terminal){
        if (terminal.isVisible()){
            terminal.setVisible(false);
        }
    }
}
