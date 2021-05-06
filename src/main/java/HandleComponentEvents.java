import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.EventObject;

public class HandleComponentEvents {
    Component component;
    EventObject name;
    File selectedFile;
    HandleComponentEvents(Component componentName, EventObject eventObject){
        component = componentName;
        name = eventObject;
    }

    void handleFileAndFolder(){
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
        }
        new HandleJTree(selectedFile, new SetUp().jTree);
    }

    void handleTerminal(JComponent terminal){
        if (terminal.isVisible()){
            terminal.setVisible(false);
        }
    }
}
