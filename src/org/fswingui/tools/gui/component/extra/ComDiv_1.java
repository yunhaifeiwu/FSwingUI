///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.fswingui.tools.gui.component.extra;
//
//import java.awt.event.MouseEvent;
//import java.awt.event.MouseListener;
//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;
//import java.util.Map;
//import javax.swing.BorderFactory;
//import javax.swing.JComponent;
//import javax.swing.JPanel;
//import javax.swing.border.Border;
//import org.fswingui.tools.frame.model.CurrData;
//import org.fswingui.tools.frame.model.MapPropertys;
//import org.fswingui.tools.frame.model.MapPropertys.MapPropertyChangeEvent;
//import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
//import org.fswingui.tools.gui.component.ComDivPanel;
//
// 
//public class ComDiv_1 extends ComDivPanel implements Div,
//        PropertyChangeListener
//{
//    //<editor-fold desc="变量">    
//    private Border oldBorder ;
//    private CurrData currData;
//    private boolean contained  =false;//true,被组合,
//    private Div parentDiv;//  组合中，父容器
//    private ComDiv me;
//    //</editor-fold>
//    public ComDiv_1(JPanel parent, String name) {
//        super(parent, name);
//    }
//
//    //<editor-fold defaultstate="collapsed" desc="getter and setter"> 
//    @Override
//    public CurrData getCurrData() {
//        return currData;
//    }
//
//    @Override
//    public void setCurrData(CurrData currData) {
//        this.currData = currData;
//    }
//
//
//    @Override
//    public String getType() {
//        return "ComDivPanelExtra";
//    }
//    
//        /**
//     * 为true,被组合
//     */
//    @Override
//    public boolean isContained() {
//        return contained;
//    }
//
//    /**
//     * 为true,被组合
//     */
//    @Override
//    public void setContained(boolean contained) {
//        this.contained = contained;
//    }
//
//    /**
//     * 组合中，父容器
//     */
//    @Override
//    public Div getParentDiv() {
//        return parentDiv;
//    }
//    /**
//     * 组合中，父容器
//     */
//    @Override
//    public void setParentDiv(Div parentDiv) {
//        this.parentDiv = parentDiv;
//    }
//    
//        @Override
//    public int getZOrder() {
//        return (this.getParent().getComponentZOrder(this));
//    }
//
//    @Override
//    public void setOldBorder(boolean selected) {
//        if(selected){
//            oldBorder=this.getBorder();
//            this.setBorder(BorderFactory.createLineBorder(this.getBackground().brighter()));
//        } else {
//            this.setBorder(oldBorder);
//        }      
//         
//    }
//    
//    //</editor-fold>
//    
//    
//       public ComDivPanel init( ){
//        ComDivPanel div =this;
//        me=this;
//        super.addMouseListener(new MouseListener(){
//
//            @Override
//            public void mouseClicked(MouseEvent e) {
////                preBind(e);
//            }
//            
//            @Override
//            public void mouseReleased(MouseEvent e) { }
//
//            @Override
//            public void mousePressed(MouseEvent e) { preBind(e);}
//            @Override
//            public void mouseEntered(MouseEvent e) {}
//            @Override
//            public void mouseExited(MouseEvent e) {}
//            
//             
//        });     
//         
//        if (this.currData==null) return div;
//        MapPropertys p=this.currData.getMapPropertys();
//        if (p==null) return div ;            
//        p.addMapChangesListener(this);
//        bind();
//        return div;
//    }
//    
//    public void preBind(Object event){
//        if(MouseEvent.class.isAssignableFrom(event.getClass())){
//            MouseEvent e=(MouseEvent) event;
//            if ((JComponent.class.isAssignableFrom(e.getSource().getClass()))) {
//                JComponent j=(JComponent) ((JComponent) e.getSource());
//                if( (  Div.class.isAssignableFrom(j.getClass())) &&
//                    ((Div)j).isContained()
//                ){                        
//                    return ;
//                }
//            }
//
//            Map<String,Div> map= currData.getSelectDivs();
//            if (e.getModifiers()==18 && map!=null
//            )//按下Ctrl键 表示要选中                
//            {                     
//                if (map.containsKey(me.getName())) {
//                    map.remove(me.getName());                        
//                    me.setBorder(oldBorder);
//                } else{
//                    oldBorder=me.getBorder();
//                    me.setBorder(BorderFactory.createLineBorder(me.getBackground().brighter()));
//                    map.put(me.getName(), me);   
//                }
//                me.getParent().repaint();
//                me.getParent().invalidate();
//            } else {
//                 bind();
//            }
//        }
//            
//    }
//    
//    
//    @Override
//    public void bind() {
//        if (this.currData==null) return;
//       CurrData cd=this.currData.getCurrData(this.getName());
//       if (cd==null) {
//           cd=this.currData;           
//       }
//      
//       
//       MapPropertys p=cd.getMapPropertys();    
//       if (p==null) return;       
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,this.getName(),
//                 "name", me.getName());  
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,this.getName(),
//                 "subjectID", p.getProperty("subjectID").value); 
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,this.getName(),
//                 "width", me.getSize().width);
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,this.getName(),
//                 "height", me.getSize().height);
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,this.getName(),
//                 "x", me.getX());
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,this.getName(),
//                 "y", me.getY());
//         p.setPropertyValue(MapPropertys.TYPE_OBJECT,this.getName(),
//                 "text", p.getProperty("text").value); 
//        
//          
//        
//    }
//    
//    /**
//     * 当用户改变属性值时，本Panel按属性变化值进行设定
//     */
//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        MapPropertyChangeEvent e=(MapPropertyChangeEvent) evt;        
//        if (MapPropertys.TYPE_OBJECT.equals(e.sourceType)) return;
//        if (!e.objectId.equals(me.getName()) ) return;
//        
//        PropertyUnit p=null;
//        p=(PropertyUnit) evt.getNewValue();
//        if (p==null) return;
//        if ("name".equals(p.name)){
//            this.setName((String)p.value);
//        } else if("subjectID".equals(p.name)){
//            //this.currData.getMapPropertys().setProperty(p);
//        }else if("width".equals(p.name)){            
//            this.setSize((Integer)p.value, this.getHeight());
//        }else if("height".equals(p.name)){
//            this.setSize( this.getWidth(),(Integer)p.value);            
//        }else if("x".equals(p.name)){
//            this.setLocation((Integer)p.value, this.getLocation().y);
//        }else if("y".equals(p.name)){
//            this.setLocation( this.getLocation().x,(Integer)p.value);
//        }
//        repaint();
//        revalidate();
//    }
//
//    
//}
