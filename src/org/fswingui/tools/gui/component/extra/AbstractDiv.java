/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component.extra;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.MapPropertys;
import org.fswingui.tools.frame.model.MapPropertys.MapPropertyChangeEvent;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
import org.fswingui.tools.frame.model.PropertyCriterion;
import org.fswingui.tools.gui.component.adapter.DivSta;

        

public abstract class AbstractDiv implements Div,DivSta ,PropertyChangeListener{
    //<editor-fold desc="变量">
    private CurrentData currentData;
    private boolean contained  =false;//true,被组合,
    private AbstractDiv son;
    protected JComponent view;
    protected Border oldBorder;
    protected String name;
    protected MapPropertys myPropertys;
     //</editor-fold>
 
    /**
     * Div 是一个控制类，外观JComponent 与抽象模型CurrData 一一对应，并通过Div控制     * 
     * @param div 子类，这里目的是要与把子类保存到currData中
     * @param currData -----抽象数据模型
     */
    public AbstractDiv(CurrentData currentData){      
        this.currentData=currentData; 
    }


    
    
    //<editor-fold desc="getter and setter ">
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
    
    

 
    @Override
    public CurrentData getCurrentData() {
        return currentData;
    }

    /**
     * 把自
     * @param currData 
     */
    @Override
    public void setCurrentData(CurrentData currentData) {
        this.currentData = currentData;
    }
   
    

    @Override
    public int getX() {
        return view.getX();
    }

   public void setX(int x) {
         view.setLocation(x, view.getY());
    }


    @Override
    public int getY() {
        return view.getY();
    }

    public void setY(int y) {
         view.setLocation( view.getX(),y);
    }

    @Override
    public int getWidth() {
        return view.getWidth();
    }

    @Override
    public int getHeight() {
        return view.getHeight();
    }
    

        
    public JComponent getView() {
        return view;
    }
    

    public void setView(JComponent view) {
        
        this.view = view;
    }
    
    @Override
    public int getZOrder() {
        return (view.getParent().getComponentZOrder(view));
    }
    
    @Override
    public void setOldBorder(boolean selected) {
        if(selected){
            oldBorder=view.getBorder();
            view.setBorder(BorderFactory.createLineBorder(view.getBackground().brighter()));
        } else {
            view.setBorder(oldBorder);
        }      
         
    }
    
    //</editor-fold>
    
    @Override
    public  Div clone(){
        AbstractDiv o = null;
        try {
            o = (AbstractDiv) super.clone();
//            o.view=this.view;
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AbstractDiv.class.getName()).log(Level.SEVERE, null, ex);
        }
      
                  
            
        
        return o;
    }
    

    
    @Override
    public void setBounds(int x, int y, int width, int height) {
        view.setBounds(x, y, width, height);
    }
    
    /**
     * 对外调用，完成初始化
     */
    public void init(){
    
//        this.currentData.getCurrBaseData().setId(this.name);
        this.son=initSon(); 
        this.son.initMyPropertys();
        PropertyUnit pu=this.myPropertys.getProperty(PropertyCriterion.NAME);
        pu.value=this.name;
        this.myPropertys.put(PropertyCriterion.NAME, pu);
        
        if(this.view==null){
            try {
                throw new Exception (this+";inti方法使用前必须设置view") ;
            } catch (Exception ex) {
                Logger.getLogger(AbstractDiv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if (! DivSta.class.isAssignableFrom(view.getClass()) 
        ){
            
            try {
                throw new Exception (this+";intit方法检查到view没有实现"+
                    DivSta.class.getSimpleName()+"接口"
                ) ;
            } catch (Exception ex) {
                Logger.getLogger(AbstractDiv.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        CurrentData.putDiv(son);
        MapPropertys p=son.getCurrentData().getCurrBaseData().getMapPropertys();
        fillMapProperty(son.myPropertys);
        if (p==null) {
             return   ;
        }    
        p.addMapChangesListener(son);       
        son.bindImp();
        
//        this.currData.save();
       
    }
    
    
    
    /**
     * 继承类必须实现它，完成初始化.并返回本继承类的实例
     */
    protected abstract AbstractDiv initSon();
    
    protected abstract void initMyPropertys();
    
    /**
     * 把子类固有属性，填充数据总线的MapPropertys.
     * 注意，切忌不可用新实例 替换数据总线的MapPropertys
     */
    protected void fillMapProperty(MapPropertys mps){
        
       MapPropertys  mapPropertys= currentData.getCurrBaseData().getMapPropertys();
        
       mapPropertys.clear(); 
    
       for(Map.Entry<String,PropertyUnit> en:mps.getPropertysClone().entrySet()){
          mapPropertys.setProperty(MapPropertys.TYPE_INIT,
                  currentData.getCurrBaseDataID(), en.getValue()
          );
       } 
       
       PropertyChangeEvent evt=new PropertyChangeEvent(
              AbstractDiv.class.getSimpleName(),
               CurrentData.DIVBIND_EVENT_NAME, 
               "", true
       );
     
       this.currentData.divBindChanges.firePropertyChange(evt);
    }
    

    /**
     * 供子类或外部调用。会进行一些公共处理。
     * 这里会自动调用子类的bindImp.
     */
    public  void bind(){
        if (currentData.getBaseData(name)!=null &&
          currentData.getBaseData(name).getMapPropertys()!=null &&  
          currentData.getBaseData(name).getMapPropertys().getPropertysClone()!=null&&
          currentData.getBaseData(name).getMapPropertys().getPropertysClone().size()>0      )
        {
           
           fillMapProperty(currentData.getBaseData(name).getMapPropertys()); 
           bindImp();
           return;
        } 
        
        if (myPropertys ==null || son.myPropertys.getPropertysClone()==null
                || myPropertys.getPropertysClone().size()<=0)
        {
            try {
                throw new Exception("子类的初始化属性无值;"+this);
            } catch (Exception ex) {
                Logger.getLogger(AbstractDiv.class.getName()).log(Level.SEVERE, null, ex);
            }
            return;
        }
        fillMapProperty(son.myPropertys);           
        bindImp();
    }
            
    
   /**
    * 子类实现的，让Div按总线的属性显示
    */     
    protected abstract void bindImp();
    
 
    @Override
    /**
     * 当用户改变属性值时，本Panel按属性变化值进行设定
     */
    public  void propertyChange(PropertyChangeEvent evt){
        MapPropertyChangeEvent e=(MapPropertyChangeEvent) evt;        
        
        if (MapPropertys.TYPE_OBJECT.equals(e.sourceType)) return;
        if (e.sourceType.equals(MapPropertys.TYPE_INIT)) return;
        if (!e.objectId.equals(view.getName()) ) return;
        
        PropertyUnit p=null;
        p=(PropertyUnit) evt.getNewValue();
        if (p==null) return;
        if (PropertyCriterion.NAME.equals(p.name)){
            view.setName((String)p.value);
        }
        propertyChangeImp(evt);
    }
    
 
    /**
     * 继承为必须实现，完成指定功能
     * 当用户改变属性值时，本Panel按属性变化值进行设定
     */
    protected abstract void propertyChangeImp(PropertyChangeEvent evt);
     
    //<editor-fold desc="事件">
     @Override
    public void addScaleMouseAdapte() {
      
        ((DivSta) view).addScaleMouseAdapte();
    }

    @Override
    public void removeScaleMouseAdapte() {
        ((DivSta) view).removeScaleMouseAdapte();
    }

    @Override
    public void addFocusAndDropMouseAdapter() {
//       Div div=   CurrData.getDivs().get(view.getName());
//       div.removeFocusAndDropMouseAdapter();
        ((DivSta) view).addFocusAndDropMouseAdapter();
    }

    @Override
    public void removeFocusAndDropMouseAdapter() {
        ((DivSta) view).removeFocusAndDropMouseAdapter();
    }

    @Override
    public void addUpDispatchMouseEvent() {
        ((DivSta) view).addUpDispatchMouseEvent();
    }

    @Override
    public void removeUpDispatchMouseEvent() {
        ((DivSta) view).removeUpDispatchMouseEvent();
    }
    
    
    
    //</editor-fold>
    
    //<editor-fold desc="Utility">
    
    //</editor-fold>


    
}
