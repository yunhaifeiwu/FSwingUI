/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame;

import javax.swing.UnsupportedLookAndFeelException;
import org.fswingui.tools.frame.part.Factory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.util.Collection;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSplitPane;
import javax.swing.LookAndFeel;
import javax.swing.SpringLayout;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.Border;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.plaf.ui.FButtonUI;
import org.fswingui.plaf.ui.FPanelUI;
import org.fswingui.plaf.ui.FSwingLF;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.part.CreateCenterPanel;
import org.fswingui.tools.frame.part.CreateEastPanel;
import org.fswingui.tools.frame.part.CreateMenuBar;
import org.fswingui.tools.frame.part.CreateNorthPanel;
import org.fswingui.tools.frame.part.CreateSouthPanel;
import org.fswingui.tools.frame.part.CreateWestPanel;
import org.fswingui.tools.frame.part.adapter.PanelScaleAdpter;

/**
 * 皮肤设计GUI工具；主程序。以FSwingUi提供的皮肤包，对SWing组件外观进行设置，<br/> 
 * 并形成FSwingUi所需要的设置文件。该程序，边框布局。各个边框的JPanel，皆由自定义<br/> 
 * JPanel组成。每当向该JPanel增加一个panel，将根据父panel性质决定是否自动分割一次面板。
 * @author cloud
 */
public class MainFrame extends JFrame{
    public final static String ROOT_PANEL ="rootPanel";
    public final static String NORTH_PANEL ="northPanel";
    public final static String SOUTH_PANEL ="southPanel";
    public final static String WEST_PANEL ="westPanel";
    public final static String EAST_PANEL ="eastPanel";
    public final static String CENTER_PANEL ="centerPanel";
    public final static String ROOT_FRAME ="mainFrame";
    public final static String STYLE_PANEL ="stylePanel";
    
    
    
    private Container rootPanel;
    private JPanel northPanel;
    private JPanel southPanel;
    private JPanel westPanel; 
    private JPanel eastPanel;
    private JPanel centerPanel;
    private CreatePart createPart;
    private DataBus dataBus ;
    
    

    public CreatePart getCreatePart() {
        return createPart;
    }

    public void setCreatePart(CreatePart createPart) {
        this.createPart = createPart;
    }

    public DataBus getDataBus() {
        return dataBus;
    }

   
    
    
    
    public MainFrame(){
        this.dataBus=new DataBus();
        CreatePart c =new AbstractCreatePart(true);
        c.setDataBus(dataBus);
        c.add(new CreateMenuBar());
        c.add(new CreateNorthPanel() );
        c.add(new CreateSouthPanel() );
        c.add(new CreateWestPanel() );
        c.add(new CreateEastPanel() );
        c.add(new CreateCenterPanel() );       
        this.createPart=c;         
        
        
    }
    
    
    // <editor-fold defaultstate="collapsed" desc="初始化"> 
    public void init(){     
        
        
        rootPanel= this.getContentPane();
        this.setSize(850, 650); // Size
        this.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo (null);

        BorderLayout border = new BorderLayout();
        rootPanel.setLayout(border);

        northPanel= (JPanel)(createPart.create(CreatePart.NORTH_PANEL));
        southPanel=(JPanel)(createPart.create(CreatePart.SOUTH_PANEL));
        westPanel=(JPanel)(createPart.create(CreatePart.WEST_PANEL));
        eastPanel=(JPanel)(createPart.create(CreatePart.EAST_PANEL));
        
        centerPanel=(JPanel)(createPart.create(CreatePart.CENTER_PANEL));
        
        
      
        PanelScaleAdpter scaleAdapter=new PanelScaleAdpter(PanelScaleAdpter.STYLE_TOP);
        
        southPanel.setBorder( BorderFactory.createMatteBorder(1, 0, 0, 0, Color.gray));
         
        northPanel.addMouseMotionListener(scaleAdapter);
        Border  lineBorder = BorderFactory.createMatteBorder(0, 0, 1, 0, Color.gray);
        northPanel.setBorder(lineBorder); 
        
        scaleAdapter=new PanelScaleAdpter(PanelScaleAdpter.STYLE_BOTTOM);
        southPanel.addMouseMotionListener(scaleAdapter);
        
        scaleAdapter=new PanelScaleAdpter(PanelScaleAdpter.STYLE_LEFT);
        westPanel.addMouseMotionListener(scaleAdapter);
        westPanel.setBorder( BorderFactory.createMatteBorder(0, 0, 0, 1, Color.gray));
        
        scaleAdapter=new PanelScaleAdpter(PanelScaleAdpter.STYLE_RIGHT);
        eastPanel.addMouseMotionListener(scaleAdapter);       
        eastPanel.setBorder( BorderFactory.createMatteBorder(0, 1, 0, 0, Color.gray));
         
        rootPanel.add(northPanel,BorderLayout.NORTH);
        rootPanel.add(southPanel,BorderLayout.SOUTH);
        rootPanel.add(westPanel,BorderLayout.WEST);
        rootPanel.add(eastPanel,BorderLayout.EAST);
        rootPanel.add(centerPanel,BorderLayout.CENTER);
        
        this.dataBus.getGuiParts().put(ROOT_FRAME, this);
        this.dataBus.getGuiParts().put(ROOT_PANEL, rootPanel);
                
        this.setJMenuBar( (JMenuBar)(createPart.create(CreatePart.MENU_BAR)));
        
    }
    
 
    //</editor-fold >
    
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException, Exception {
       
        
        
        String lnfName ="org.fswingui.plaf.ui.FSwingLF";
        LookAndFeel fSwingLF = new FSwingLF();
        FSwingLF.setInitStyle(FSwingLF.DEFALT_LOAD);
        UIManager.LookAndFeelInfo info = new UIManager.LookAndFeelInfo(
            fSwingLF.getName(),fSwingLF.getClass().getName()
        );
        UIManager.installLookAndFeel(info);
        UIManager.setLookAndFeel(fSwingLF);
       
        MainFrame f=new MainFrame();
        f.init();
        
        
        f.setVisible(true); // Display the window 
  }

   
   
}
