/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.divpanel;

import org.fswingui.tools.gui.component.*;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JComponent; 
import unitExample.divpanel.adapter.DropMouseAdapterDemo;
import unitExample.divpanel.adapter.ScaleMouseAdapterDemo;
/**
 *
 * @author cloud
 */
public class DivPanelDemo extends JPanel{
    private JPanel parentPanel;
    private JPanel titlePanel;
    public String title;
    private JLabel titleLabel;
    private Icon  titleIcon;
    private JPanel contextPanel;
    public static final int NO_TITLE =0;
    public int titleHeight=30;
    
    public DivPanelDemo(JPanel parent,String title){
        
        this.parentPanel=parent;
        this.title=title;
        this.titleLabel=new JLabel(title);
      // this.add(new JLabel("dd"));
        init( 2);
    }

    public JPanel getContextPanel() {
        return contextPanel;
    }

    public void setContextPanel(JPanel contextPanel) {
        this.contextPanel = contextPanel;
    }

    public JPanel getTitlePanel() {
        return titlePanel;
    }

    public void setTitlePanel(JPanel titlePanel) {
        this.titlePanel = titlePanel;
    }
    
    
    private JComponent init(int panelModel){
        Dimension d=new Dimension(200,150);
        this.setSize(d);
        this.setPreferredSize(d);
        
        switch(panelModel){
        case 0:
            
            break;
        default:
           
        }
    
        
        titlePanel=new JPanel();
        titlePanel.add(titleLabel);
        titlePanel.setBackground(Color.red);
        titlePanel.setPreferredSize(new Dimension(200,titleHeight));
        if (this.parentPanel !=null){            
            DropMouseAdapterDemo fml=new DropMouseAdapterDemo(this.parentPanel,this);
            titlePanel.addMouseListener(fml); 
        }
        
        contextPanel=new JPanel();
        contextPanel.setPreferredSize(new Dimension(200,100));
        contextPanel.setBackground(Color.darkGray);
        ScaleMouseAdapterDemo fsl=new ScaleMouseAdapterDemo(this.parentPanel,this);
      
        contextPanel.addMouseMotionListener(fsl);  
        
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);
        if (titlePanel !=null){
            this.add(titlePanel);
        }        
        this.add(contextPanel);
       
        layout.putConstraint(SpringLayout.WEST, titlePanel, 0,
                SpringLayout.WEST, this);   
        layout.putConstraint(SpringLayout.NORTH, titlePanel, 0,
                SpringLayout.NORTH, this);   
        layout.putConstraint(SpringLayout.EAST, titlePanel, 0,
                SpringLayout.EAST, this);  
        
        layout.putConstraint(SpringLayout.WEST, contextPanel, 0,
                SpringLayout.WEST, this);   
        layout.putConstraint(SpringLayout.NORTH, contextPanel, titleHeight,
                SpringLayout.NORTH, titlePanel);   
        layout.putConstraint(SpringLayout.EAST, contextPanel, 0,
                SpringLayout.EAST, this);   
        layout.putConstraint(SpringLayout.SOUTH, contextPanel, 0,
                SpringLayout.SOUTH, this);   
        
        return this;
    }
    
    public static  void createPanel() {
        JFrame f = new JFrame("Wallpaper");   
        f.setSize(400, 300);
        JPanel p = (JPanel) f.getContentPane();
//        p.setLayout(new AbsoluteLayout());
        
         DivPanelDemo fp=new DivPanelDemo(p,"");
//        p.add(fp,new AbsoluteConstraints(10, 10, 200, 100));
        f.add(fp);
        
        
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        f.setVisible (true);
    }
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {      
                createPanel();
            }
        });
    } 
}
