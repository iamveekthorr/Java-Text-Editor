import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.intellijthemes.FlatDraculaIJTheme;

import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.util.EventObject;

public class CreateMenuBar {


    JMenu[] insertMenuItems(){
        JMenu edit, find, preferences, window, terminal,  file;
        file= new JMenu("File");
        edit = new JMenu("Edit");
        find= new JMenu("Find");
        preferences= new JMenu("Preferences");
        window = new JMenu("Window");
        terminal = new JMenu("Terminal");

        for (JMenuItem fileMenuItems: addFileMenuItems()) {
            file.add(fileMenuItems);
        }

        for (JMenuItem terminalMenuItems: addTerminalMenuItems()){
            terminal.add(terminalMenuItems);
        }

        for (JMenuItem themes : addPreferencesMenuItems()){
            preferences.add(themes);
        }

        return new JMenu[]{file, edit, find, preferences, window, terminal};
    }
    JMenuItem[] addPreferencesMenuItems(){
        JMenuItem darkModeTheme, lightTheme;

        darkModeTheme = new JMenuItem("Dark Theme");
        lightTheme = new JMenuItem("Light Theme");
        addListenersToMenuItemComponents(darkModeTheme, lightTheme);

        return new JMenuItem[]{darkModeTheme, lightTheme};
    }

    JMenuItem[] addTerminalMenuItems(){
        JMenuItem showTerminal, hideTerminal;

        showTerminal = new JMenuItem("Show Terminal");
        hideTerminal = new JMenuItem("Hide Terminal");

        addListenersToMenuItemComponents(showTerminal, hideTerminal);

        return new JMenuItem[]{showTerminal, hideTerminal};
    }

    JMenuItem[] addFileMenuItems(){
        JMenuItem openFile, openFolder, save, exit, newFile;
        newFile = new JMenuItem("New File");
        openFile = new JMenuItem("Open File");
        openFolder = new JMenuItem("Open Folder");
        save = new JMenuItem("Save");
        exit = new JMenuItem("Exit");

        addListenersToMenuItemComponents(openFile, openFolder, newFile, exit, save);

        return new JMenuItem[]{openFile, openFolder,newFile, save, exit};
    }

    void addListenersToMenuItemComponents(JMenuItem... args){
        for (JMenuItem el: args ){
            el.addActionListener(e -> {
                String text = ((JMenuItem) e.getSource()).getText();
                // Set event listener for open file
                if ((text.equalsIgnoreCase("Open File")) || (text.equalsIgnoreCase("Open Folder"))){
                    new HandleComponentEvents().handleFileAndFolder(new JFrame(), e);
                }

                if (text.equalsIgnoreCase("exit")){
                    System.exit(0);
                }
                if(text.equalsIgnoreCase("Show Terminal")){
                    handleShowTerminalEvent();
                }
                if (text.equalsIgnoreCase("hide Terminal")){
                    handleHideTerminal();
                }
                if (text.equalsIgnoreCase("Dark Theme")){
//                    handledDarkModeEvent(this);
                }

            });
        }
    }
    void handledDarkModeEvent(JFrame frame){
        if (!FlatLightLaf.isLafDark()){
            FlatDraculaIJTheme.install();
            SwingUtilities.updateComponentTreeUI(frame);
            System.out.println("Changed");
        }
    }
    void handleShowTerminalEvent(){
        Initialize initialize = new Initialize();
        Initialize.SPLIT_PANE.getBottomComponent().setVisible(true);

        for (Component components :  initialize.topPane.getComponents()) {
            components.setPreferredSize(new Dimension(Initialize.screenSize.width, 900));
        }
    } // end of method HandleTerminalEvent

    void handleHideTerminal(){
        Initialize initialize = new Initialize();
        Initialize.SPLIT_PANE.getBottomComponent().setVisible(false);
        initialize.topPane.setPreferredSize(new Dimension(Initialize.screenSize.width, 800));


    }

}// end of class
