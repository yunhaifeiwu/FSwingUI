/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;


/**
 *
 * @author cloud
 */
public class Factory {
    /**
     * 创建主窗口的菜单
     */
    public static JMenuBar createMenuBar(){
        JMenuBar menuBar = new JMenuBar();

        // Build the first menu.
        JMenu menu = new JMenu("A Menu");
        menu.setMnemonic(KeyEvent.VK_A);
        menu.getAccessibleContext().setAccessibleDescription(
            "The only menu in this program that has menu items");
        menuBar.add(menu);

        // a group of JMenuItems
        JMenuItem menuItem = new JMenuItem("A text-only menu item", KeyEvent.VK_T);
        // menuItem.setMnemonic(KeyEvent.VK_T); //used constructor instead
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
            ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
            "This doesn't really do anything");
        menu.add(menuItem);

     // ImageIcon icon = createImageIcon("images/middle.gif");
        menuItem = new JMenuItem("Both text and icon");
        menuItem.setMnemonic(KeyEvent.VK_B);
        menu.add(menuItem);

//        menuItem = new JMenuItem(icon);
//        menuItem.setMnemonic(KeyEvent.VK_D);
//        menu.add(menuItem);

        // a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
 

 

        // a submenu
        menu.addSeparator();
        JMenu submenu = new JMenu("A submenu");
        submenu.setMnemonic(KeyEvent.VK_S);

        menuItem = new JMenuItem("An item in the submenu");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
            ActionEvent.ALT_MASK));
        submenu.add(menuItem);

        menuItem = new JMenuItem("Another item");
        submenu.add(menuItem);
        menu.add(submenu);

        // Build second menu in the menu bar.
        menu = new JMenu("Another Menu");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
            "This menu does nothing");
        menuBar.add(menu); 
        menuBar.setBorder(null); 
        
        return menuBar;
    }
    
    public static JPanel createStatusPanel(){
        JPanel statusPanel = new javax.swing.JPanel();
        JLabel  lable=new JLabel("dd");
        JSeparator statusPanelSeparator = new JSeparator();
        statusPanel.add(lable);
                
        return statusPanel;
    }
 
    
    
}
