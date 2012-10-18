/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import javax.swing.JComponent;
import javax.swing.OverlayLayout;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.SubjectEntityDoor;
import org.fswingui.plaf.ui.FPanelUI;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.utility.SpringUtilities;
import org.fswingui.tools.gui.component.adapter.DropMouseAdapter;
import org.fswingui.tools.gui.component.adapter.DropMouseAdapter;
import org.fswingui.tools.gui.component.adapter.DropMouseAdapter;
import org.fswingui.tools.gui.component.adapter.ScaleMouseAdapter;
import org.fswingui.tools.gui.component.adapter.FocusAndDropMouseAdapter;
import org.fswingui.tools.gui.component.adapter.UpDispatchMouseEvent;
 
import org.fswingui.tools.gui.component.adapter.DivSta;
/**
 *
 * @author cloud
 */
public class DivPanel extends JPanel implements DivSta,SubjectEntityDoor
 {
    //<editor-fold defaultstate="collapsed" desc="变量"> 
    private JPanel parentPanel;
    protected FPanel textPanel;
    protected FPanel iconPanel;
    private String text="";
    private JLabel textLabel;
    private JLabel iconLabel;
    private Icon  icon;
    private Icon  backgroundIcon;
    private float  backgroundTransparence;
    private int arrangement=0;//排列方式
    private int initX=0; 
    private int initY=0;
    private int xPad=0;
    private int yPad=0;
  
    public static final int ICON_LEFT_AND_TEXT_RIGHT =0;
    public static final int ICON_RIGHT_AND_TEXT_LEFT =1;
    public static final int ICON_TOP_AND_TEXT_BOTTOM =2;
    public static final int ICON_BOTTOM_AND_TEXT_TOP =3;
    public static final int ICON_BACK_AND_TEXT_FRONT =4;
    
    private FocusAndDropMouseAdapter focusAndDropMouseAdapter;
    private ScaleMouseAdapter scaleMouseAdapter;
    private UpDispatchMouseEvent upDispatchMouseEvent;
    private SubjectEntity subjectModel;
    private CurrentData currentData;
    
    //</editor-fold > 
    public DivPanel(JPanel parent,String name){
        super();
        this.parentPanel=parent;
        super.setName(name);
        if (this.parentPanel !=null){       
            focusAndDropMouseAdapter= new FocusAndDropMouseAdapter(this.parentPanel,this);
            this.addMouseListener(focusAndDropMouseAdapter);
            scaleMouseAdapter=new ScaleMouseAdapter(this.parentPanel,this);
            this.addMouseMotionListener(scaleMouseAdapter); 

        }
         
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter"> 
 
    public CurrentData getCurrentData() {
        return currentData;
    }

    public void setCurrentData(CurrentData currentData) {
        this.currentData = currentData;
    }
    
    
    public int getArrangement() {
        return arrangement;
    }

    public void setArrangement(int arrangement) {
        this.arrangement = arrangement;
    }
    
     

    public Icon getBackgroundIcon() {
        return backgroundIcon;
    }

    public void setBackgroundIcon(Icon backgroundIcon) {
        this.backgroundIcon = backgroundIcon;
    }

    public float getBackgroundTransparence() {
        return backgroundTransparence;
    }

    public void setBackgroundTransparence(float backgroundTransparence) {
        this.backgroundTransparence = backgroundTransparence;
    }

    public JLabel getIconLabel() {
        return iconLabel;
    }

    public void setIconLabel(JLabel iconLabel) {
        this.iconLabel = iconLabel;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        if (iconLabel==null) {
            iconLabel=new JLabel(); 
            iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        iconLabel.setIcon(icon);
//        this.initX=6;
//        this.initY=6;
//        this.xPad=6;
//        this.yPad=6;
        if( iconPanel==null ){
            iconPanel=new FPanel(new BorderLayout());        
            iconPanel.add(iconLabel,BorderLayout.CENTER);
        } else {
            iconPanel.add(iconLabel);
        }
        this.icon = icon;
    }

   

    public JPanel getParentPanel() {
        return parentPanel;
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }

    public JLabel getTextLabel() {
        return textLabel;
    }

    public void setTextLabel(JLabel textLabel) {
        this.textLabel = textLabel;
    }

    public FPanel getIconPanel() {
        return iconPanel;
    }

    public void setIconPanel(FPanel iconPanel) {
        this.iconPanel = iconPanel;
    }

    public FPanel getTextPanel() {
        return textPanel;
    }

    public void setTextPanel(FPanel textPanel) {
        this.textPanel = textPanel;
    }



     

    public String getText() {
        return text;
    }

    public  void setText(String text) {
        if (textLabel==null)  {
            textLabel=new JLabel();
            textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        }
        textLabel.setText(text);
        if( textPanel==null ){
            textPanel=new FPanel(new BorderLayout());        
            textPanel.add(textLabel,BorderLayout.CENTER);
        } else {
            textPanel.add(textLabel);
        }
        this.text = text;
    }

    public SubjectEntity getSubjectEntity() {
        return subjectModel;
    }

    @Override
    public void setSubjectEntity(SubjectEntity subjectModel) {
        this.subjectModel = subjectModel;
    }
    
   //</editor-fold> 
    
    //<editor-fold defaultstate="collapsed" desc="事件"> 
       
    @Override
    public void addScaleMouseAdapte() {
         this.addMouseMotionListener(this.scaleMouseAdapter);
    }
 
    @Override
    public void removeScaleMouseAdapte() {
        this.removeMouseMotionListener(scaleMouseAdapter);
        
    }
 
    @Override
    public void addFocusAndDropMouseAdapter() {
        focusAndDropMouseAdapter.addDropMouseAdapter();
        this.addMouseListener(this.focusAndDropMouseAdapter);
        
    }

    @Override
    public void removeFocusAndDropMouseAdapter() {
        focusAndDropMouseAdapter.removeDropMouseAdapter();
        this.removeMouseListener(focusAndDropMouseAdapter);
        
    }
    
    @Override
    public void addUpDispatchMouseEvent(){
        
         upDispatchMouseEvent=new UpDispatchMouseEvent();
         
        this.addMouseListener(upDispatchMouseEvent);
        this.addMouseMotionListener(upDispatchMouseEvent);
    }
    
    public void removeUpDispatchMouseEvent(){
        this.removeMouseListener(upDispatchMouseEvent);
        this.removeMouseMotionListener(upDispatchMouseEvent);
    }
     //</editor-fold >
    
    
    public DivPanel init(){
        Dimension d=new Dimension(120,80);
        this.setSize(d);
        this.setPreferredSize(d);
        
        if(iconPanel==null && textPanel==null ){
            return this;
        }
        arrangement=arrangement%5;
        
        
        this.setLayout( new SpringLayout());
        
        
       
        if (icon !=null ) {
            iconPanel=iconPanel==null?new FPanel():iconPanel;
        } 
        textPanel=textPanel==null?new FPanel():textPanel;
      
   
    
        switch(arrangement){
            case ICON_LEFT_AND_TEXT_RIGHT:
                if ((icon !=null && text !=null) ){
                    this.add(iconPanel);
                    this.add(textPanel);
                     SpringUtilities.makeCompactGrid(this,1, 2,  
                                        initX, initY, xPad, yPad);     
                } else if ( icon!=null ) {
                    this.add(iconPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);     
                } else if ( text!=null ) {
                    this.add(textPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);   
                }
                
             
                break;
                
            case ICON_RIGHT_AND_TEXT_LEFT:
                if ((icon !=null && text !=null) ){
                     this.add(textPanel);
                     this.add(iconPanel);
                     SpringUtilities.makeCompactGrid(this,1, 2,  
                                        initX, initY, xPad, yPad);     
                } else if ( icon!=null ) {
                    this.add(iconPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);     
                } else if ( text!=null ) {
                    this.add(textPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);   
                }
                break;
 
    
            case ICON_TOP_AND_TEXT_BOTTOM:
                if ((icon !=null && text !=null) ){
                    this.add(iconPanel);
                    this.add(textPanel);                     
                    SpringUtilities.makeCompactGrid(this,2, 1,  
                                        initX, initY, xPad, yPad);     
                } else if ( icon!=null ) {
                    this.add(iconPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);     
                } else if ( text!=null ) {
                    this.add(textPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);   
                }
                break;
             
   
            case ICON_BOTTOM_AND_TEXT_TOP:
                if ((icon !=null && text !=null) ){
                    this.add(textPanel);    
                    this.add(iconPanel);                                      
                    SpringUtilities.makeCompactGrid(this,2, 1,  
                                        initX, initY, xPad, yPad);     
                } else if ( icon!=null ) {
                    this.add(iconPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);     
                } else if ( text!=null ) {
                    this.add(textPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);   
                }
                break;
                
            case ICON_BACK_AND_TEXT_FRONT:
                if ((icon !=null && text !=null) ){                    
                    JPanel tempPanel=new JPanel();
                    this.add(tempPanel);
                    OverlayLayout ov=new OverlayLayout(tempPanel);

                    tempPanel.setLayout(ov);
                    tempPanel.setMaximumSize(iconPanel.getMaximumSize());
                    if (iconPanel!=null)
                        tempPanel.setBackground(iconPanel.getBackground());

                    textLabel.setAlignmentX(0.5f);
                    textLabel.setAlignmentY(0.5f); 
                    textLabel.setMaximumSize(iconLabel.getMaximumSize());
                    tempPanel.add(textLabel);

                    iconLabel.setAlignmentX(0.5f);
                    iconLabel.setAlignmentY(0.5f);                   
                    tempPanel.add(iconLabel);
                    SpringUtilities.makeCompactGrid(this,1,1,  
                                        initX, initY, xPad, yPad); 
                } else if ( icon!=null ) {
                    this.add(iconPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);     
                } else if ( text!=null ) {
                    this.add(textPanel);
                    SpringUtilities.makeCompactGrid(this,1, 1,  
                                        initX, initY, xPad, yPad);   
                }
                            
        }
        return this;
    }
    
    //<editor-fold desc="测试">
    
    public static  void createPanel() throws ClassNotFoundException, 
            InstantiationException, IllegalAccessException, 
            UnsupportedLookAndFeelException {
        JFrame f = new JFrame("Wallpaper");   
        f.setSize(400, 300);
        JPanel p = (JPanel) f.getContentPane();
//        p.setLayout(new AbsoluteLayout());
     
        
         DivPanel fp=new DivPanel(p,"11");
          
     
         
         ImageIcon icon = new ImageIcon("src/org/fswingui/tools/gui/component/res/bb.jpg");
         fp.setIcon(icon);
         fp.setBackgroundTransparence(0.4f);
         fp.setText("dddddddxxx");
//         fp.setText(null);
        fp.getTextLabel().setHorizontalAlignment( SwingConstants.CENTER);
//        fp.getIconLabel().setHorizontalAlignment( SwingConstants.LEFT);
//        fp.getIconPanel().setBackground(Color.red);
        fp.getTextPanel().setBackground(Color.pink);
        fp.setArrangement(4);
        fp.init();
        
        f.add(fp);
        
        
        f.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        f.setLocationRelativeTo (null);
        f.setVisible (true);
    
    }
    
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {      
                try {
                    createPanel();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(DivPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InstantiationException ex) {
                    Logger.getLogger(DivPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(DivPanel.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(DivPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    } 
    //</editor-fold>

   
}
