
package unitExample.divpanel;

import org.fswingui.tools.gui.component.*;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DragPanelDemo extends JPanel {
 
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
        
        DivPanelDemo fp=new DivPanelDemo(p,"1");    
        fp.setBounds(20, 20, fp.getWidth(), fp.getHeight());
        p.add(fp,0);
       
//        
//        fp=new DivPanelDemo(p,"2"); 
//        fp.setBounds(80, 100, fp.getWidth(), fp.getHeight());
//        p.add(fp,0);
//       
//        
//        fp=new DivPanelDemo(p,"3"); 
//        fp.setBounds(160, 180, fp.getWidth(), fp.getHeight());
//        p.add(fp,1);
        
        
        f.add(p);        
        f.setSize(400, 300);
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        f.setVisible (true);
    }
      
}
