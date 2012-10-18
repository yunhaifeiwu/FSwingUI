/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd;

import java.awt.Color;
import javax.swing.JPanel;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.gui.component.ComDivPanel;
import org.fswingui.tools.gui.component.DivPanel;
import org.fswingui.tools.gui.component.extra.BaseDiv;

/**
 *
 * @author cloud
 */
public class DivPanelBirthCmd extends Command {

    public DivPanelBirthCmd(DataBus dataBus, String id) {
        super(dataBus, id);
    }
     
    @Override
    public void excute() {
        if (id==null) return;
        if (dataBus==null)  return;
        JPanel centerPanel =(JPanel) dataBus.getGuiParts().get("centerPanel");
        if ( centerPanel==null)  return;
        String nm=Double.toString(Math.random());    
       
        
         
        BaseDiv fp=new BaseDiv(super.getDataBus().getCurrentData(),centerPanel,nm );   
        ((DivPanel) fp.getView()).setArrangement(0);  
       
        fp.init();
        fp.getView().setBounds(20, 20, fp.getWidth(), fp.getHeight());   
        fp.getView().setOpaque(false);
        centerPanel.add(fp.getView(),0); 
        
        centerPanel.repaint();
        centerPanel.revalidate();
        
    }
    
}
