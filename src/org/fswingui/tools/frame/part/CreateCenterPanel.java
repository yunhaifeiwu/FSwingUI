/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part;

import javax.swing.JPanel;
import org.fswingui.tools.frame.CreatePart;
import org.fswingui.tools.frame.AbstractCreatePart;
import org.fswingui.tools.gui.component.extra.BaseDiv;

/**
 *
 * @author cloud
 * 主控制面板。其布局管理器必须为null,其他使用的地方不能改变
 */
public class CreateCenterPanel extends AbstractCreatePart{
    private JPanel centerPanel;
    public CreateCenterPanel (){
        super.setName(CreatePart.CENTER_PANEL);
    }
    
    @Override
    public Object create(){    
        centerPanel=new JPanel();
        centerPanel.setLayout(null);
//        BaseDiv fp=new BaseDiv(centerPanel,"1");    
//        fp.setBounds(20, 20, fp.getWidth(), fp.getHeight());
//        super.getDataBus().getCurrData().setName("1");
//        fp.setCurrData(super.getDataBus().getCurrData());
//        centerPanel.add(fp,0);       
//        
//        fp=new BaseDiv(centerPanel,"2"); 
//        fp.setBounds(80, 100, fp.getWidth(), fp.getHeight());
//        super.getDataBus().getCurrData().setName("2");
//        fp.setCurrData(super.getDataBus().getCurrData());
//        centerPanel.add(fp,0);
//        
//       
//       
//        
//        fp=new BaseDiv(centerPanel,"3"); 
//        fp.setBounds(160, 180, fp.getWidth(), fp.getHeight());
//        super.getDataBus().getCurrData().setName("3");
//        fp.setCurrData(super.getDataBus().getCurrData());
//        fp.bind();
//        centerPanel.add(fp,1);
        
        this.getDataBus().getGuiParts().put(CreatePart.CENTER_PANEL, centerPanel);
        return centerPanel;
    }
}
