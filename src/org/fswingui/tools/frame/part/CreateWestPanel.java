/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.tools.frame.CreatePart;
import org.fswingui.tools.frame.AbstractCreatePart;
import org.fswingui.tools.frame.MainFrame;
import org.fswingui.tools.frame.cmd.DivPanelBirthCmd;
import org.fswingui.tools.frame.part.base.FSplitPanel;
import org.fswingui.tools.frame.part.extra.PaintPanelExtra;
import org.fswingui.tools.frame.part.extra.StylePanelExtra;

/**
 *
 * @author cloud
 */
public class CreateWestPanel  extends AbstractCreatePart{
    private FSplitPanel westPanel;
    public CreateWestPanel (){
        super.setName(CreatePart.WEST_PANEL);
    }
    
    @Override
    public Object create(){  
        westPanel=new FSplitPanel(); 
        
        
       
        FlowLayout layout = new  FlowLayout(); 
        
        layout.setVgap(10);      
        layout.setHgap(30);
        JPanel t=new JPanel(layout );   
      
        JButton b=new JButton("生    成");
        
        b.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                DivPanelBirthCmd cmd= new DivPanelBirthCmd(dataBus,"d");
                cmd.excute();
            }
            
        });
        t.add(b);       
                 
        
        westPanel.add("root", "birthPanel", t, FSplitPanel.VERTICAL_SPLIT);
        
        t.setPreferredSize(new Dimension(100,t.getPreferredSize().height));
        t.setMinimumSize(new Dimension(0,0));
        
        t=new StylePanelExtra(this.getDataBus().getCurrentData() ); 
        t.setOpaque(false);
        
        
        westPanel.add("birthPanel", MainFrame.STYLE_PANEL, t, FSplitPanel.VERTICAL_SPLIT); 
        this.getDataBus().getGuiParts().put(MainFrame.STYLE_PANEL, t); 
//        t=new JPanel(layout );   
//        Vector v=new Vector();
//        v.addElement("aa");
//         v.addElement("bb");
//        JList list=new JList(v);
//         list.setSize(150,80);
//         list.setPreferredSize(new Dimension(150,80));
//        t.add(list);
//        westPanel.add("stylePanelExtra", "list", t, FSplitPanel.VERTICAL_SPLIT);
        
        this.getDataBus().getGuiParts().put(CreatePart.WEST_PANEL, westPanel);
        
        return westPanel;
    }
}
