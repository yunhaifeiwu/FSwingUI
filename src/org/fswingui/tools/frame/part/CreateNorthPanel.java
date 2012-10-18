/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part;

import javax.swing.JPanel;
import org.fswingui.tools.frame.CreatePart;
import org.fswingui.tools.frame.AbstractCreatePart;

/**
 *
 * @author cloud
 */
public class CreateNorthPanel extends AbstractCreatePart{
    public CreateNorthPanel (){
        super.setName(CreatePart.NORTH_PANEL);
    }
    
    @Override
    public Object create(){
        
        return new JPanel();
    }
    
}
