/*
 * To change this template, choose Tools | Templates
 */
package org.fswingui.tools.gui.component.extra;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.SubjectEntityDoor;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.plaf.ui.FButtonUI;
import org.fswingui.plaf.ui.FPanelUI;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.MapPropertys;
import org.fswingui.tools.frame.model.MapPropertys.MapPropertyChangeEvent;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
import org.fswingui.tools.frame.model.PropertyCriterion;
import org.fswingui.tools.gui.component.DivPanel;
import org.fswingui.tools.gui.component.adapter.DivSta;
 
/**
 * 本组件直接参与框架，成为框架核心受操控元件之一。<br>
 * 比DivPanel,增加了适合框架运行的消息沟通属性与方法。<br>
 * @author cloud
 */
public class BaseDiv extends AbstractDiv
  
{
    //<editor-fold desc="变量">
    BaseDiv  me;   
    DivPanel view1;
    
    //</editor-fold>
    FPanelUI panelUI=new FPanelUI(new CrystalPaint());
    
    
    /**
     * BaseDiv 中的view需要父容器为JPanel,且布局管理器为null,
     * name 将是view 的名称（相当于ID）,
     */
    public BaseDiv(CurrentData currentData,JPanel parent,String name){  
        
        super(currentData);
       
        view1=new DivPanel(parent,name);
        view1.setCurrentData(currentData);
        this.name=name;
        super.setView(view1);
         
        SubjectEntity sm=new SubjectEntity();
        ImageIcon icon = new ImageIcon("test/old/org/fswingui/plaf/backgound.jpg");
 
        sm.setBackgroundIcon(icon);
        sm.setBackgroundTransparence(0.3f);              
        AbstractPaint root=new CrystalPaint();
        try {
            root.addArg("azimuth", 0);
            root.addArg("ColorSource", Color.pink.darker());
        } catch (Exception ex) {
            Logger.getLogger(BaseDiv.class.getName()).log(Level.SEVERE, null, ex);
        }
        sm.setPaint(root);
        
        view1.setSubjectEntity(sm);
        if(view1.getTextPanel()!=null ){
            view1.getTextPanel().setSubjectEntity(sm);
        }
        
      
    }
    
    //<editor-fold defaultstate="collapsed" desc="getter and setter"> 
 
    
    @Override
    public String getType() {
        return BaseDiv.class.getSimpleName();
    }
    //</editor-fold>
   
    @Override
    protected AbstractDiv initSon() {
        me=this;
        init1();
        return this;
        
    }
 
    private void  init1(){
         
       view1.init();
       view1.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
            }
            
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) {
                if (JComponent.class.isAssignableFrom(e.getSource().getClass())) {
                    JComponent j=(JComponent) ((JComponent) e.getSource());
                    if(   DivSta.class.isAssignableFrom(j.getClass()) &&  
                          ((DivSta)j).getCurrentData().
                            getBaseData(j.getName()).isContained()
                    ){                         
                        return ;
                    }
                }
           
                 
                Map<String,BaseData> map= CurrentData.getSelectBaseDatas();
                if (e.getModifiers()==18 && map!=null
                )//按下Ctrl键 表示要选中                
                {   
                    String nm=view.getName();             
                    if (map.containsKey(nm)) {
                        map.remove(nm);                        
                        view.setBorder(oldBorder);
                    } else{
                        oldBorder=view.getBorder();
                        view.setBorder(BorderFactory.createLineBorder(view.getBackground().brighter()));
                        map.put(nm, me.getCurrentData().getBaseData(nm));   
                    }
                    view.getParent().repaint();
                    view.getParent().invalidate();
                } else {
                     bind();
                }
            }
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
             
        });     
         
 
       
  
    }
       
    /**
     * 把自身的panel相关属性，设定到MapPropertys中
     */
 
 
    @Override
    protected  void bindImp(){
       if (this.getCurrentData()==null) {
            return;
        }
       BaseData bd=this.getCurrentData().getBaseData(view1.getName());
       if (bd==null) {
           bd=this.getCurrentData().getCurrBaseData();     
       }
      
       
       MapPropertys p=bd.getMapPropertys();    
       if (p==null) {
            return;
        }       
        
         
         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
                 PropertyCriterion.NAME, view.getName(),view.getName()
         );  
         
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
//                 PropertyCriterion.SUBJECT_ID,
//                 p.getProperty(PropertyCriterion.SUBJECT_ID).strValue,
//                 p.getProperty(PropertyCriterion.SUBJECT_ID).value
//         ); 
         
         if( p.getProperty(PropertyCriterion.SUBJECT_ID) !=null ){
             String temp= (String) p.getProperty(PropertyCriterion.SUBJECT_ID).strValue;       
             view1.getSubjectEntity().setSubjectID(temp);
             //更新数据总线 中 当前主题实体集
             if(!temp.equals("")) {
                 CurrentData.getSubjectEntitys().put(temp, view1.getSubjectEntity());
             }
         } 
         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
                 PropertyCriterion.WIDTH, String.valueOf(view.getSize().width),
                 view.getSize().width
         );
         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
                 PropertyCriterion.HEIGHT,String.valueOf(view.getSize().height),
                 view.getSize().height
         );
         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
                 PropertyCriterion.X, String.valueOf(view.getX()),
                 view.getX()
         );
         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
                 PropertyCriterion.Y,  String.valueOf(view.getY()),
                 view.getY()
         );
         if( p.getProperty(PropertyCriterion.OPAQUE) !=null ){
             Boolean temp= (Boolean) p.getProperty(PropertyCriterion.OPAQUE).value;       
             view1.getSubjectEntity().setOpaque(temp);
         } 
         if( p.getProperty(PropertyCriterion.ARRANGEMENT) !=null ){
             Integer temp= (Integer) p.getProperty(PropertyCriterion.ARRANGEMENT).value;       
             view1.getSubjectEntity().setArrangement(temp);
         } 
         
        if( p.getProperty(PropertyCriterion.DISABLE_TEXT) !=null ){
             boolean temp= (Boolean) p.getProperty(PropertyCriterion.DISABLE_TEXT).value;       
             view1.getSubjectEntity().setDisableText(temp);
         } 

         if( p.getProperty(PropertyCriterion.FONT) !=null ){
             Font temp= (Font) p.getProperty(PropertyCriterion.FONT).value;       
             view1.getSubjectEntity().setFont(temp);
         }    
         if( p.getProperty(PropertyCriterion.FONT_COLOR) !=null ){
             Color temp= (Color) p.getProperty(PropertyCriterion.FONT_COLOR).value;       
             view1.getSubjectEntity().setFontColor(temp);
         }  
         if( p.getProperty(PropertyCriterion.TEXT) !=null  ){            
             String temp= (String) p.getProperty(PropertyCriterion.TEXT).value;       
             view1.getSubjectEntity().setText(temp);      
         } 
         if( p.getProperty(PropertyCriterion.TEXT_TRANSPARENCE) !=null ){
             Float temp= (Float) p.getProperty(PropertyCriterion.ICON_TRANSPARENCE).value;       
             view1.getSubjectEntity().setIconTransparence(temp);
         } 
         if( p.getProperty(PropertyCriterion.DISABLE_ICON) !=null ){
             Float temp= (Float) p.getProperty(PropertyCriterion.TEXT_TRANSPARENCE).value;       
             view1.getSubjectEntity().setTextTransparence(temp);
         } 
         if( p.getProperty(PropertyCriterion.ICON) !=null ){
             ImageIcon temp= (ImageIcon) p.getProperty(PropertyCriterion.ICON).value;       
             view1.getSubjectEntity().setIcon(temp);
         } 
         if( p.getProperty(PropertyCriterion.ICON_TRANSPARENCE) !=null ){
             Float temp= (Float) p.getProperty(PropertyCriterion.ICON_TRANSPARENCE).value;       
             view1.getSubjectEntity().setIconTransparence(temp);
         } 
         if( p.getProperty(PropertyCriterion.BACKIMG) !=null  ){
             ImageIcon temp= (ImageIcon) p.getProperty(PropertyCriterion.BACKIMG).value;  
             if (temp!=null) view1.getSubjectEntity().setBackgroundIcon(temp);
         } 
         if( p.getProperty(PropertyCriterion.BACKGROUD_TRANSPARENCE) !=null ){
             Float temp= (Float) p.getProperty(PropertyCriterion.BACKGROUD_TRANSPARENCE).value;       
             view1.getSubjectEntity().setBackgroundTransparence(temp);
         } 
         if( p.getProperty(PropertyCriterion.PAINT) !=null ){            
             String temp= (String) p.getProperty(PropertyCriterion.PAINT).strValue;       
             if (temp !=null &&  !temp.equals("")){
                 AbstractPaint paint=CurrentData.getPaints().getPaint(temp);
                 view1.getSubjectEntity().setPaint(paint);
             }             
            
         } 
        
        
    }
    
    /**
     * 当用户改变属性值时，本Panel按属性变化值进行设定
     */
    @Override
    public void propertyChangeImp(PropertyChangeEvent evt) {
//        if (CurrData.class.isAssignableFrom(
//            evt.getSource().getClass() ) ){
//            return;
//        }
        MapPropertyChangeEvent e=(MapPropertyChangeEvent) evt;        
        
        if (MapPropertys.TYPE_OBJECT.equals(e.sourceType)) return;
        if (!e.objectId.equals(view.getName()) ) return;
        
        PropertyUnit p=null;
        p=(PropertyUnit) evt.getNewValue();
        if (p==null) return;
        if (PropertyCriterion.NAME.equals(p.name)){
            view.setName((String)p.value);
             
        } else if(PropertyCriterion.SUBJECT_ID.equals(p.name)){
            String temp=(String)p.strValue;
            view1.getSubjectEntity().setSubjectID(temp);
            //更新数据总线 中 当前主题实体集
            if(!temp.equals("")) {
                CurrentData.getSubjectEntitys().put(temp, view1.getSubjectEntity());
            }
        }else if(PropertyCriterion.WIDTH.equals(p.name)){            
            view.setSize((Integer)p.value, view.getHeight());
        }else if(PropertyCriterion.HEIGHT.equals(p.name)){
            view.setSize( this.getWidth(),(Integer)p.value);            
        }else if(PropertyCriterion.X.equals(p.name)){
            view.setLocation((Integer)p.value, view.getLocation().y);
        }else if(PropertyCriterion.Y.equals(p.name)){
            view.setLocation( view.getLocation().x,(Integer)p.value);
        }else if(PropertyCriterion.OPAQUE.equals(p.name)){
            view1.getSubjectEntity().setOpaque((Boolean)p.value);
        }else if(PropertyCriterion.ARRANGEMENT.equals(p.name)){
            view1.getSubjectEntity().setArrangement((Integer)p.value);
        }
        else if(PropertyCriterion.DISABLE_TEXT.equals(p.name)){           
            view1.getSubjectEntity().setDisableText((Boolean)p.value);
        }else if(PropertyCriterion.TEXT.equals(p.name)){
            view1.setText((String) p.value);
            view1.getSubjectEntity().setText((String) p.value);
        }else if(PropertyCriterion.FONT.equals(p.name)){  
            view1.getSubjectEntity().setFont((Font) p.value);
        }else if(PropertyCriterion.FONT_COLOR.equals(p.name)){  
            view1.getSubjectEntity().setFontColor((Color) p.value);
        }else if(PropertyCriterion.TEXT_TRANSPARENCE.equals(p.name)){  
            view1.getSubjectEntity().setTextTransparence((Float)p.value);
        }else if(PropertyCriterion.BACKIMG.equals(p.name)){            
        }else if(PropertyCriterion.DISABLE_ICON.equals(p.name)){           
            view1.getSubjectEntity().setDisableIcon((Boolean)p.value);
        }else if(PropertyCriterion.ICON.equals(p.name)){     
             view1.getSubjectEntity().setIcon((ImageIcon)p.value);
        }if(PropertyCriterion.ICON_TRANSPARENCE.equals(p.name)){     
             view1.getSubjectEntity().setIconTransparence((Float)p.value);
        }else if(PropertyCriterion.BACKIMG.equals(p.name) && p.value!=null){   
             view1.getSubjectEntity().setBackgroundIcon((ImageIcon)p.value);
        }if(PropertyCriterion.BACKGROUD_TRANSPARENCE.equals(p.name)){     
             view1.getSubjectEntity().setBackgroundTransparence((Float)p.value);
        }else if(PropertyCriterion.PAINT.equals(p.name)){
             AbstractPaint paint=CurrentData.getPaints().getPaint((String)p.strValue);    
             view1.getSubjectEntity().setPaint(paint);
        }
        
        view.repaint();
        view.revalidate();
    }

    @Override
    protected void initMyPropertys() {
  
            
         super.myPropertys=new MapPropertys();
         Object[][] p={
            {PropertyCriterion.NAME,"名称",String.class,"",""},
            {PropertyCriterion.SUBJECT_ID,"主题",SubjectEntity.class,"",null},
            {PropertyCriterion.WIDTH,"宽",Integer.class,"0",0},
            {PropertyCriterion.HEIGHT,"高",Integer.class,"0",0},
            {PropertyCriterion.X,"横坐标",Integer.class,"0",0},
            {PropertyCriterion.Y,"纵坐标",Integer.class,"0",0},
            {PropertyCriterion.OPAQUE,"透明",Boolean.class,"false",false},  
            {PropertyCriterion.ARRANGEMENT,"图文位置",Integer.class,"0",0},            
            {PropertyCriterion.DISABLE_TEXT,"禁止文本",Boolean.class,"true",true},
            {PropertyCriterion.TEXT,"文本",String.class,"",""},
            {PropertyCriterion.FONT,"字体",Font.class,"",null},
            {PropertyCriterion.FONT_COLOR,"字体颜色",Color.class,"",null},
            {PropertyCriterion.TEXT_TRANSPARENCE,"文本透明度",Float.class,"1",1f},
            {PropertyCriterion.DISABLE_ICON,"禁止图标",Boolean.class,"true",true},
            {PropertyCriterion.ICON,"图标",Icon.class,"",null},
            {PropertyCriterion.ICON_TRANSPARENCE,"图标透明度",Float.class,"1",1f},
            {PropertyCriterion.BACKIMG,"背景图",Icon.class,"",null},                
            {PropertyCriterion.BACKGROUD_TRANSPARENCE,"背景透明度",Float.class,"1",1f},
            {PropertyCriterion.PAINT,"背景绘制",AbstractPaint.class,"",null}
        };
         
        super.myPropertys.initPropertys(p);
    }





 
   

   




   


}
