/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part;

import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.tools.frame.CreatePart;
import org.fswingui.tools.frame.AbstractCreatePart;
import org.fswingui.tools.frame.part.base.FSplitPanel;
import org.fswingui.tools.frame.part.base.PaintPanel;
import org.fswingui.tools.frame.part.base.PropertyPanel;
import org.fswingui.tools.frame.part.extra.PaintPanelExtra;
import org.fswingui.tools.frame.part.extra.PropertyPanelExtra;

/**
 *
 * @author cloud
 */
public class CreateEastPanel  extends AbstractCreatePart{
    private FSplitPanel eastPanel;
    public CreateEastPanel (){
        super.setName(CreatePart.EAST_PANEL);
    }
    
    @Override
    public Object create(){       
        eastPanel=new FSplitPanel(); 
        JPanel t=null;
        
        t=new PropertyPanelExtra();
        ((PropertyPanelExtra) t).setBaseData(super.dataBus.getCurrentData().getCurrBaseData());
         
        ((PropertyPanelExtra) t).init();
        t.setMinimumSize(new Dimension(150,330));
        eastPanel.add("root", "propertyPanel", t, FSplitPanel.VERTICAL_SPLIT); 
         
        t=new PaintPanelExtra(new CrystalPaint()); 
        ((PaintPanelExtra) t).setCurrData(this.getDataBus().getCurrentData());
        ((PaintPanelExtra) t).init();
        eastPanel.add("propertyPanel", "paintPane", t, FSplitPanel.VERTICAL_SPLIT); 
        
        Dimension d=new Dimension(200,eastPanel.getHeight());
        eastPanel.setPreferredSize(d);
        this.getDataBus().getGuiParts().put(CreatePart.EAST_PANEL, eastPanel);
        return eastPanel;
    }
    
}
