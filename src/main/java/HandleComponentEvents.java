import javax.swing.*;
import java.awt.*;

public class HandleComponentEvents {

    void handleFileOpen(Object file){

    }

    void handleTerminal(JComponent terminal){
        if (terminal.isVisible()){
            terminal.setVisible(false);
        }
    }
}
