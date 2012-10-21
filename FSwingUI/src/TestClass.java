
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.LookAndFeel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.fswingui.plaf.ui.FSwingLF;
import org.fswingui.tools.frame.MainFrame;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Administrator
 */
public class TestClass {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {      
                String lnfName ="org.fswingui.plaf.ui.FSwingLF";
                LookAndFeel fSwingLF = new FSwingLF();
                FSwingLF.setInitStyle(FSwingLF.DEFALT_LOAD);
                UIManager.LookAndFeelInfo info = new UIManager.LookAndFeelInfo(
                    fSwingLF.getName(),fSwingLF.getClass().getName()
                );
                UIManager.installLookAndFeel(info);
                try {
                    UIManager.setLookAndFeel(fSwingLF);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
                }

                MainFrame f=new MainFrame();
                f.init();


                f.setVisible(true); // Display the window 
            }
        }); 
    }
    
}
