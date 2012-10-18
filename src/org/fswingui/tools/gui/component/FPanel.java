/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component;

import java.awt.LayoutManager;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.SubjectEntityDoor;

/**
 *
 * @author cloud
 */
public class FPanel extends JPanel implements SubjectEntityDoor {
    private SubjectEntity subjectEntity;

    public FPanel(){super();}
    
    public FPanel(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }

    public FPanel(boolean isDoubleBuffered) {
        super(isDoubleBuffered);
    }

    public FPanel(LayoutManager layout) {
        super(layout);
    }

    public FPanel(LayoutManager layout, boolean isDoubleBuffered) {
        super(layout, isDoubleBuffered);
    }

     
    
    
 

    @Override
    public SubjectEntity getSubjectEntity() {
        return subjectEntity;
    }

    @Override
    public void setSubjectEntity(SubjectEntity subjectEntity) {
        this.subjectEntity = subjectEntity;
    }
    
    
    
}
