
package org.fswingui.tools.gui.component;

import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.plaf.ui.FPanelUI;

public class DragPanel extends JPanel {
 
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {  
                //propertyListener
                createPanel();
            }
        });
    }
    
    public static  void createPanel() {
        JFrame f = new JFrame("Wallpaper");
      
        JPanel p = new JPanel();
        p.setLayout(null);
        
        DivPanel fp=new DivPanel(p,"aa1");    
        fp.setText("1");
        fp.setArrangement(4);
        fp.init();
        fp.getTextPanel().setBackground(Color.pink);
        fp.setBounds(20, 20, fp.getWidth(), fp.getHeight());
        fp.setBackground(Color.red);
        JLabel l=new JLabel("ddd");
        l.setBounds(10, 10, 30, 30);
        fp.setLayout(null);
        fp.add(l);
        
        fp.getTextPanel().setUI(new FPanelUI(new CrystalPaint()));
        
       
        p.add(fp,0);
          
        
        fp=new DivPanel(p,"aa2"); 
        fp.setText("2");
        fp.getTextPanel().setBackground(Color.yellow);
        fp.setArrangement(2);
        fp.init();
        fp.setBounds(80, 100, fp.getWidth(), fp.getHeight());
        p.add(fp,0);
       
        
        fp=new DivPanel(p,"aa3"); 
        fp.setText("3");
        ImageIcon icon = new ImageIcon("src/org/fswingui/tools/gui/component/res/bb.jpg");
        fp.setIcon(icon);         
        fp.getTextLabel().setHorizontalAlignment( SwingConstants.CENTER);
        fp.getIconPanel().setBackground(Color.BLUE);
        fp.setArrangement(4);
        fp.init();
        
        fp.setBounds(160, 180, fp.getWidth(), fp.getHeight());
        p.add(fp,1);
        
        
        f.add(p);        
        f.setSize(800, 300);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        f.setVisible (true);
    }
      
}
