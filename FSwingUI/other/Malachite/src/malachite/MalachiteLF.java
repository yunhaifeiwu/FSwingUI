/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package malachite;

import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.*;
import javax.swing.plaf.basic.BasicLookAndFeel;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class MalachiteLF extends MetalLookAndFeel 
//public class MalachiteLF extends BasicLookAndFeel 
//当然你也可以继承BasicLookAndFeel，代价是你要实现全套皮肤
    implements java.io.Serializable
{
    private static UIDefaults myTable;
    private NimbusLookAndFeel nlf=new NimbusLookAndFeel();
    
    @Override
    public String getID() { return "Malachite"; }
    @Override
    public String getName() { return "Malachite Look and Feel"; }
    public String getDescription() { return "Sample L&F from Swing"; }
    public boolean isNativeLookAndFeel() { return false; }
    public boolean isSupportedLookAndFeel() { return true; }
    

//    public UIDefaults getDefaults() {
//        UIDefaults table =super.getDefaults();
//        table.put("ButtonUI", "malachite.MalachiteButtonUI"); 
//               ColorUIResource commonBackground = new ColorUIResource(152, 208, 128);
//        ColorUIResource commonForeground = new ColorUIResource(0, 0, 0);
//        ColorUIResource buttonBackground = new ColorUIResource(4, 108, 2);
//        ColorUIResource buttonForeground = new ColorUIResource(236, 236, 0);
//        ColorUIResource menuBackground = new ColorUIResource(128, 192, 128);
//        FontUIResource commonFont = new FontUIResource("Arial", Font.BOLD, 12 );
////        Icon ubox=(Icon) SwingUtilities2.makeIcon(getClass(),BasicLookAndFeel.class,
////                                                          "icons/FloppyDrive.gif");
//        Icon ubox = new ImageIcon("src/malachite/dd.jpg");
//        
////        Icon ubull = new ImageIcon("Malachite/ubull.gif");
//        //这里也必须指定 UI所对应的类
//           
//        table.put("Button.font", commonFont);
//        table.put("Button.background", buttonBackground);
//        table.put("Button.foreground", buttonForeground);
////        table.put("CheckBoxUI", MultiButtonUI.class.getName());
//        table.put("CheckBox.font", commonFont);
//        table.put("CheckBox.background", commonBackground);
//        table.put("CheckBox.foreground", commonForeground);
//        table.put("CheckBox.font", commonFont);
////        table.put( "CheckBox.icon", new IconUIResource(ubox));
//        return table;
//    }
 
    protected void initClassDefaults(UIDefaults table) {
        super.initClassDefaults(table);       
        table.put("ButtonUI", "malachite.MalachiteButtonUI");   
//        table.put("CheckBoxUI", SynthCheckBoxUI.class.getName());   
    }

    protected void initComponentDefaults(UIDefaults table) {        
        super.initComponentDefaults(table);
        
        ColorUIResource commonBackground = new ColorUIResource(152, 208, 128);
        ColorUIResource commonForeground = new ColorUIResource(0, 0, 0);
        ColorUIResource buttonBackground = new ColorUIResource(4, 108, 2);
        ColorUIResource buttonForeground = new ColorUIResource(236, 236, 0);
        ColorUIResource menuBackground = new ColorUIResource(128, 192, 128);
        FontUIResource commonFont = new FontUIResource("Arial", Font.BOLD, 9 ); 
        Icon ubox = new ImageIcon("src/malachite/dd.jpg"); 
        table.put("Button.font", commonFont);
        table.put("Button.background", buttonBackground);
        table.put("Button.foreground", buttonForeground);
        table.put("CheckBox.font", commonFont);
        table.put("CheckBox.background", commonBackground);
        table.put("CheckBox.foreground", commonForeground);
        table.put("CheckBox.font", commonFont);
//        table.put( "CheckBox.icon", new IconUIResource(ubox)); 
        this.myTable=table;
    }     
 
}