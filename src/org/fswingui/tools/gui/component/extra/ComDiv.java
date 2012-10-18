/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component.extra;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.Border;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.MapPropertys;
import org.fswingui.tools.frame.model.MapPropertys.MapPropertyChangeEvent;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
import org.fswingui.tools.frame.model.PropertyCriterion;
import org.fswingui.tools.gui.component.ComDivPanel;
import org.fswingui.tools.gui.component.adapter.DivSta;

 
public class ComDiv extends AbstractDiv 
{
    //<editor-fold desc="变量">    
    private Border oldBorder ;
    private ComDiv me;
    private ComDivPanel view1;
    //</editor-fold>
    public ComDiv(CurrentData currentData,JPanel parent, String name) {
        
        super(currentData);
        view1=new ComDivPanel(parent,name);
        view1.setCurrentData(currentData);
        this.name=name;
        super.setView(view1);
        
    }

    //<editor-fold defaultstate="collapsed" desc="getter and setter"> 
 

    @Override
    public String getType() {
        return "ComDivPanelExtra";
    }
    
 
    
    //</editor-fold>
    
 
    @Override
    protected AbstractDiv initSon() {
        init1();
        return this;
    }
    
    private void init1( ){
      
        me=this;
        view1.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
//                preBind(e);
            }
            
            @Override
            public void mouseReleased(MouseEvent e) { }

            @Override
            public void mousePressed(MouseEvent e) { preBind(e);}
            @Override
            public void mouseEntered(MouseEvent e) {}
            @Override
            public void mouseExited(MouseEvent e) {}
             
        });     
         
       
    }
    
    public void preBind(Object event){
        if(MouseEvent.class.isAssignableFrom(event.getClass())){
            MouseEvent e=(MouseEvent) event;
            //如果是根 
            if ((JComponent.class.isAssignableFrom(e.getSource().getClass()))) {
                JComponent j=(JComponent) ((JComponent) e.getSource());
                if( (  DivSta.class.isAssignableFrom(j.getClass())) &&
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
            
    }
    
    
    @Override
    public void bindImp() {
        if (this.getCurrentData()==null) return;
        BaseData bd=this.getCurrentData().getBaseData(view1.getName());
        if (bd==null) {
            bd=this.getCurrentData().getCurrBaseData();
        }


        MapPropertys p=bd.getMapPropertys();    
        if (p==null) return;       
         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
                 PropertyCriterion.NAME, view.getName(),view.getName());  
         p.setPropertyValue(MapPropertys.TYPE_OBJECT,view.getName(),
                 PropertyCriterion.SUBJECT_ID, 
                 p.getProperty(PropertyCriterion.SUBJECT_ID).strValue,
                 p.getProperty(PropertyCriterion.SUBJECT_ID).value
         ); 
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
        if (!e.objectId.equals(me.getName()) ) return;
        
        PropertyUnit p=null;
        p=(PropertyUnit) evt.getNewValue();
        if (p==null) return;
        if (PropertyCriterion.NAME.equals(p.name)){
            view1.setName((String)p.value);
        } else if(PropertyCriterion.SUBJECT_ID.equals(p.name)){
            //this.currData.getMapPropertys().setProperty(p);
        }else if(PropertyCriterion.WIDTH.equals(p.name)){            
            view1.setSize((Integer)p.value, this.getHeight());
        }else if(PropertyCriterion.HEIGHT.equals(p.name)){
            view1.setSize( this.getWidth(),(Integer)p.value);            
        }else if(PropertyCriterion.X.equals(p.name)){
            view1.setLocation((Integer)p.value, view1.getLocation().y);
        }else if(PropertyCriterion.Y.equals(p.name)){
            view1.setLocation( view1.getLocation().x,(Integer)p.value);
        }
        view1.repaint();
        view1.revalidate();
    }

    @Override
    protected void initMyPropertys() {
        super.myPropertys=new MapPropertys();
         Object[][] p={
            {PropertyCriterion.NAME,"名称",String.class,"",""},
            {PropertyCriterion.SUBJECT_ID,"主题",String.class,"",""},
            {PropertyCriterion.WIDTH,"宽",Integer.class,"0",0},
            {PropertyCriterion.HEIGHT,"高",Integer.class,"0",0},
            {PropertyCriterion.X,"横坐标",Integer.class,"0",0},
            {PropertyCriterion.Y,"纵坐标",Integer.class,"0",0}
        };
        super.myPropertys.initPropertys(p);
    }

  

    
}
