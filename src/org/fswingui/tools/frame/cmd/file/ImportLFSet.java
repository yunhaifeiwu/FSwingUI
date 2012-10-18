/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd.file;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTree;
import javax.swing.UIManager;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.ui.FSwingLF;
import org.fswingui.tools.frame.MainFrame;
import org.fswingui.tools.frame.cmd.Command;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.part.extra.StylePanelExtra;

/**
 *
 * @author Administrator
 */
public class ImportLFSet extends Command  {
    private transient JTree tree;
    public ImportLFSet(DataBus dataBus, String id) {
        super(dataBus, id);
        
    }
    @Override
    public void excute() {
        
        UIEngine.init(FSwingLF.SET_LOAD);
        UIManager.put(UIEngine.CURRENT_COLOR, 
                dataBus.getCurrentData().getCurrentMainColor()
        );   
        UIManager.put(UIEngine.CURRENT_STYLEID, 
                dataBus.getCurrentData().getCurrentStyle()
        );  
        
        JFrame f=(JFrame) dataBus.getGuiParts().get(MainFrame.ROOT_FRAME);
        if(f!=null){
            f.revalidate();
            f.repaint();
        }
    }
}
