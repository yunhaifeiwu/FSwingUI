/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.ui;

import java.awt.Color;
import java.awt.Font;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIDefaults;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.FontUIResource;
import javax.swing.plaf.basic.BasicListUI;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.UIEngine;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;

/**
 *
 * @author cloud
 */
public class FSwingLF1 extends MetalLookAndFeel 
    implements java.io.Serializable
{
    private static UIDefaults myTable;
    
    @Override
    public String getID() { return "FSwingLF"; }
    @Override
    public String getName() { return "FSwingUI Look and Feel"; }
    @Override
    public String getDescription() { return "主题式皮肤"; }
    @Override
    public boolean isNativeLookAndFeel() { return false; }
    @Override
    public boolean isSupportedLookAndFeel() { return true; }    
   
 
    @Override
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);       
        table.put("ButtonUI", FButtonUI.class.getName());   
        table.put("PanelUI", FPanelUI.class.getName());   
        table.put("ListUI", FListUI.class.getName());  
        table.put("TreeUI", FTreeUI.class.getName());  
        table.put("LabelUI", FLabelUI.class.getName());  
    }

    @Override
    protected void initComponentDefaults(UIDefaults table) {        
        super.initComponentDefaults(table);
        
//        ColorUIResource commonBackground = new ColorUIResource(152, 208, 128);
//        ColorUIResource commonForeground = new ColorUIResource(0, 0, 0);
//        ColorUIResource buttonBackground = new ColorUIResource(4, 108, 2);
//        ColorUIResource buttonForeground = new ColorUIResource(236, 236, 0);
//        ColorUIResource menuBackground = new ColorUIResource(128, 192, 128);
//        FontUIResource commonFont = new FontUIResource("Arial", Font.BOLD, 9 ); 
//        table.put(UIEngine.PRE+"fbutton", "fyh_subject_1");
//        table.put(UIEngine.PRE+JButton.class.getSimpleName(), "fyh_subject_1");
//        table.put(UIEngine.PRE+"ConstrainedButton", "fyh_subject_1");
        table.put(UIEngine.PRE+JLabel.class.getSimpleName(), "fyh_subject_1");
        table.put(UIEngine.PRE+JList.class.getSimpleName(), "fyh_subject_1");
        table.put(UIEngine.PRE+JPanel.class.getSimpleName(), "fyh_subject_1");   
        table.put(UIEngine.PRE+JTree.class.getSimpleName(), "fyh_subject_1");   
        ImageIcon icon ;
        SubjectEntity sm=new SubjectEntity();
//        sm.setText("textButtonddd");
        sm.setIconTransparence(0.9f);
        sm.setTextTransparence(0.9f);
        icon = new ImageIcon("src/org/fswingui/tools/frame/resource/backgound.jpg");
        sm.setBackgroundIcon(icon);
        sm.setBackgroundTransparence(0.2f);
        AbstractPaint root=new CrystalPaint();
        try {
            root.addArg("azimuth", 0);
        } catch (Exception ex) {
            Logger.getLogger(FSwingLF1.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            root.addArg("ColorSource", Color.pink.darker());
        } catch (Exception ex) {
            Logger.getLogger(FSwingLF1.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.setPaint(root);
        sm.setArrangement(SubjectEntity.ICON_TOP_AND_TEXT_BOTTOM); 
        UIManager.put("fyh_subject_1",sm );
        
        FSwingLF1.myTable=table;
    }     
 
}
