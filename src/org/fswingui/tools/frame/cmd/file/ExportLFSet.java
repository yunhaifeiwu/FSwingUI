/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd.file;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import org.fswingui.tools.frame.cmd.Command;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.part.extra.StylePanelExtra;

/**
 *
 * @author Administrator
 */
public class ExportLFSet extends Command  {
    private String stylePanelName="stylePanelExtra";
    private transient StylePanelExtra stylePanel;
    private transient JTree tree;
    public ExportLFSet(DataBus dataBus, String id) {
        super(dataBus, id);
        stylePanel=(StylePanelExtra) dataBus.getGuiParts().get(stylePanelName);
        
    }
    @Override
    public void excute() {
        if( stylePanel!=null) {
            stylePanel.exportLFSet();
            
        }
    }
    
}
