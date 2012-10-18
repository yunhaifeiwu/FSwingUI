/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import org.fswingui.tools.frame.CreatePart;
import org.fswingui.tools.frame.AbstractCreatePart;
import org.fswingui.tools.frame.cmd.CombiCmd;
import org.fswingui.tools.frame.cmd.sort.DownSort;
import org.fswingui.tools.frame.cmd.sort.FirstSort;
import org.fswingui.tools.frame.cmd.sort.LastSort;
import org.fswingui.tools.frame.cmd.UnCombiCmd;
import org.fswingui.tools.frame.cmd.file.DefaultLFSet;
import org.fswingui.tools.frame.cmd.file.ExportLFSet;
import org.fswingui.tools.frame.cmd.file.ImportLFSet;
import org.fswingui.tools.frame.cmd.file.OpenProject;
import org.fswingui.tools.frame.cmd.file.SaveProject;
import org.fswingui.tools.frame.cmd.sort.UpSort;

/**
 *
 * @author cloud
 */
public class CreateMenuBar extends AbstractCreatePart {
    private CombiCmd ccd;
    private UnCombiCmd unccd;
    private UpSort up;
    private DownSort down;
    private FirstSort firstsort;
    private LastSort  lastSort;
    
    private ExportLFSet exportLFSet;
    private ImportLFSet importLFSet;
    private DefaultLFSet defaultLFSet;
    private SaveProject saveProject;
    private OpenProject openProject;
    
    public CreateMenuBar(){
        super.setName(CreatePart.MENU_BAR);
    }
    
    @Override
    public Object create() {
         JMenuBar menuBar = new JMenuBar();

        // Build the first menu.
        JMenu menu = new JMenu("文件");
        menu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(menu);
   
        openProject=new OpenProject(super.dataBus,"openProject");        
        JMenuItem menuItem = new JMenuItem("打开工程");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                openProject.excute();
            }        
        });
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1,
            ActionEvent.ALT_MASK));
        menuItem.getAccessibleContext().setAccessibleDescription(
            "This doesn't really do anything");
        menu.add(menuItem);

   
        saveProject=new SaveProject(super.dataBus,"saveProject");
        menuItem = new JMenuItem("保存工程");
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                saveProject.excute();
            }        
        });
        menuItem.setMnemonic(KeyEvent.VK_S);
        menu.add(menuItem);
          
          
//        menuItem = new JMenuItem(icon);
//        menuItem.setMnemonic(KeyEvent.VK_D);
//        menu.add(menuItem);

        // a group of radio button menu items
        menu.addSeparator();
        ButtonGroup group = new ButtonGroup();
 

 

        // a submenu
        menu.addSeparator();
        JMenu submenu = new JMenu("导入/导出");
        submenu.setMnemonic(KeyEvent.VK_S);
        
        menuItem = new JMenuItem("刷新皮肤");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
            ActionEvent.ALT_MASK));      
        importLFSet=new ImportLFSet(super.dataBus,"importLFSet");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                importLFSet.excute();
            }        
        });
        submenu.add(menuItem);       
        
          
        menuItem = new JMenuItem("恢复默认皮肤");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
            ActionEvent.ALT_MASK));      
        defaultLFSet=new DefaultLFSet(super.dataBus,"defaultLFSet");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                defaultLFSet.excute();
            }        
        });
        submenu.add(menuItem);
        submenu.addSeparator();

       menuItem = new JMenuItem("生成皮肤设置文件");
        menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2,
            ActionEvent.ALT_MASK));      
        exportLFSet=new ExportLFSet(super.dataBus,"exportLFSet");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                exportLFSet.excute();
            }        
        });
        submenu.add(menuItem);
        
        menu.add(submenu);        

        //<editor-fold desc="工具">          
        //<editor-fold desc="组合">
        menu = new JMenu("工具");
        menu.setMnemonic(KeyEvent.VK_N);
        menu.getAccessibleContext().setAccessibleDescription(
            "This menu does nothing");
        
        menuItem = new JMenuItem("组合"); 
        menuBar.add(menu); 
        ccd=new CombiCmd(super.dataBus,"assembledCmd");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                ccd.excute();
            }
        
        });
        menu.add(menuItem);
        
        menuItem = new JMenuItem("取消组合");        
        unccd=new UnCombiCmd(super.dataBus,"assembledCmd");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                unccd.excute();
            }
        
        });
        menu.add(menuItem);
       //</editor-fold>
        
        //<editor-fold desc="排序">
        JMenu  menu1 = new JMenu("排序");
        menu.add(menu1); 
        menu1.setMnemonic(KeyEvent.VK_S);
        menu1.getAccessibleContext().setAccessibleDescription(
            "排序");
        
        menuItem = new JMenuItem("向上");        
        up=new UpSort(super.dataBus,"upSort");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                up.excute();
            }
        
        });
        menu1.add(menuItem);
        
        menuItem = new JMenuItem("向下");        
        down=new DownSort(super.dataBus,"downSort");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                down.excute();
            }
        
        });
        menu1.add(menuItem);
        
        menuItem = new JMenuItem("最后");        
        lastSort=new LastSort(super.dataBus,"lastSort");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                lastSort.excute();
            }
        
        });
        menu1.add(menuItem);
        
        menuItem = new JMenuItem("最前");        
        firstsort=new FirstSort(super.dataBus,"firstSort");    
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                firstsort.excute();
            }
        
        });
        menu1.add(menuItem);
       //</editor-fold>         
        //</editor-fold>
        
        
        
        menuBar.setBorder(null); 
        this.getDataBus().getGuiParts().put(CreatePart.MENU_BAR, menuBar);
        return menuBar;
    }
}
