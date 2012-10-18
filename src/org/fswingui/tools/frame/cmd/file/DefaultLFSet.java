/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd.file;

import javax.swing.JFrame;
import javax.swing.JTree;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.ui.FSwingLF;
import org.fswingui.tools.frame.MainFrame;
import org.fswingui.tools.frame.cmd.Command;
import org.fswingui.tools.frame.model.DataBus;

/**
 *
 * @author Administrator
 */
public class DefaultLFSet extends Command  {
    private transient JTree tree;
    public DefaultLFSet(DataBus dataBus, String id) {
        super(dataBus, id);
        
    }
    @Override
    public void excute() {
        UIEngine.init(FSwingLF.DEFALT_LOAD);
        JFrame f=(JFrame) dataBus.getGuiParts().get(MainFrame.ROOT_FRAME);
        if(f!=null){
            f.revalidate();
            f.repaint();
        }
    }
}
