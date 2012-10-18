/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JPanel;
import org.fswingui.tools.frame.CreatePart;
import org.fswingui.tools.frame.AbstractCreatePart;
import org.fswingui.tools.frame.part.base.FSplitPanel;
import org.fswingui.tools.frame.part.extra.MainColortPanelExtra;
import org.fswingui.tools.frame.part.extra.PropertyPanelExtra;

/**
 *
 * @author cloud
 */
public class CreateSouthPanel extends AbstractCreatePart{
    private FSplitPanel southPanel;
    public CreateSouthPanel (){
        super.setName(CreatePart.SOUTH_PANEL);
    }
    
    @Override
    public Object create(){  
        southPanel=new FSplitPanel(); 
//        southPanel.setOpaque(false);
        JPanel t=null;
        t=new MainColortPanelExtra();
        ((MainColortPanelExtra) t).setCurrentData(super.dataBus.getCurrentData());
         
        ((MainColortPanelExtra) t).init();
        JPanel p=new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.setPreferredSize(new Dimension(200,100));
        p.setMinimumSize(new Dimension(200,100));
        p.add(t);
        southPanel.add("root", "mainColorPanel", p, FSplitPanel.HORIZONTAL_SPLIT); 
        this.getDataBus().getGuiParts().put("mainColorPanel", t);
        Dimension d=new Dimension(200,100);
        southPanel.setPreferredSize(d);  
        
        this.getDataBus().getGuiParts().put(CreatePart.SOUTH_PANEL, southPanel);
        return southPanel;
    }
}
