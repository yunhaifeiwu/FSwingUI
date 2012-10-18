/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.tools.paint;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import org.fswingui.tools.frame.model.BaseData;

/**
 * 这是对方法进封装的类。<br>
 * 实现者（该类的子类) 必须把要使用的参数通过addParameter函数填入args中，
 *   然后在paintImp方法中实用这些参数；<br>
 * 调用时，必须先通过addArg把实参填入到args中，方可调用paint方法。
 * 
 * @author cloud
 */
public  abstract class AbstractPaint implements Serializable,Cloneable {
    //<editor-fold defaultstate="collapsed" desc="in class"> 

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" var"> 
    public static final String TYPE_COLOUR=Color.class.getName();    
    public static final int TOSTRING_CLASS=0;
    public static final int TOSTRING_ID=1;
    public static final int TOSTRING_DESCRIBE=2;
    public static final int TOSTRING_ORIGINAL=3;
    
  
    /**
     * 绘制方式ID
     */
    private String paintID;
    /**
     * 绘制方式描述
     */
    private String describe;       
    
    /**
     * 存储paint所用参数；String -----参数名,Parameter----参数值；
     * 实现者（该类的子类) 必须把要使用的参数通过addParameter函数填入args中，
     * 然后在paintImp方法中实用这些参数；<br>
     * 调用时，必须先通过addArg把实参填入到args中，方可调用paint方法。
     * 请使用LinkedHashMap，系统以他为准，目的是保证顺序
     */
    private LinkedHashMap<String,Parameter> args;
    
    private int toStrinStyle=0;
    
  
    //</editor-fold> 
    
        @Override  
    public AbstractPaint clone()  {  
        AbstractPaint o=null;
        try {
            o=(AbstractPaint) super.clone();  
          
            
           if (args==null) return null;
           LinkedHashMap<String,Parameter>  m= new LinkedHashMap();
          
           for(  Entry<String,Parameter>  en:args.entrySet() ){
               String k=en.getKey();
               m.put(en.getKey() , en.getValue().clone());
           }
           o.args=  (LinkedHashMap<String, Parameter>) m;

        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(AbstractPaint.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        return o;
    } 
        
    //<editor-fold defaultstate="collapsed" desc=" Constructor"> 
    public AbstractPaint() {
        init();
    }
    
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc=" getter and setter"> 

    
    
    
    
    public String getDescribe() {
        return describe;
    }
   
    /**
     * 使用者不能改变
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPaintID() {
        return paintID;
    }
    
    /**
     * 使用者不可变,paint制作者的编码
     */
    public void setPaintID(String paintID) {
        this.paintID = paintID;
    }
    
    /**
     * 注：结点类没有参数；具体绘制实现类有参数
     */
    public LinkedHashMap<String, Parameter> getArgs() {
        return args;
    }

    /**
     * 请使用LinkedHashMap，系统以他为准，目的是保证顺序
     */
    public void setArgs(LinkedHashMap<String, Parameter> args) {
        this.args = args;
    }

    public int getToStrinStyle() {
        return toStrinStyle;
    }

    public void setToStrinStyle(int toStrinStyle) {
        this.toStrinStyle = toStrinStyle;
    }
    
    
    //</editor-fold>
          
    //<editor-fold defaultstate="collapsed" desc=" function"> 
    
    /**
     * 扩展toString目的是方便swing之类的组件操作对象是，呈现给用户的显示。
     * 不同的组件，显示是有可能需要显示其描述，有可能显示其类名，有可能显示其ID    
     */
    @Override
    public String toString(){
       String str="";
       switch(toStrinStyle){
           case TOSTRING_CLASS:
               str=this.getClass().getSimpleName();                       
               break;
           case AbstractPaint.TOSTRING_ID:
               str=this.paintID;
               break;
           case AbstractPaint.TOSTRING_DESCRIBE:
               str=this.getDescribe();
               break;
           default:
               str=super.toString();
           
       }
        return str;
    }
    
    /**
     * 用户在调用该方法时，必须先通过addArg函数传入实参；
     */
    public void paint(Graphics g, JComponent c) throws Exception{
        
        for(  Map.Entry<String,Parameter>  e :args.entrySet()){
             Parameter p=e.getValue();
             String k=e.getKey();
             if ( (!p.allowNull) && p.value==null)
                 throw new Exception("参数值："+k+",不允许为null！");

             if (! p.value.getClass().equals(p.type))
                 throw new Exception("参数："+k+"的值 与类型不一致！");

             if (! p.defaultValue.getClass().equals(p.type))
                 throw new Exception("参数："+k+"的值 与类型不一致！");
        }
       
        paintImp(g,c);
    }
    
    public abstract boolean isNodeFlag();
        
    /**
     * 对子类而设；完成具体的背景绘制。绘制时需抽象出各种参数， 这些参数通过<br/>
     *  继承实现setParameter方法设置；然后paintImp中得到这些参数，然后开始<br/>
     * 使用这些参数完成具体绘制。
     */
    protected abstract void paintImp(Graphics g, JComponent c);
    

    /**
     *在这里设置paintImp函数的参数
     */
    protected  abstract void setParameter();
    private void init(){
        setParameter();
    }
    
    
    //<editor-fold defaultstate="collapsed" desc=" Parameter function"> 
    /**
     * 子类可设置.新增一个参数
     * @param name  参数名
     * @param type  参数类型
     * @param allowNull  是否允许值为空
     */
    protected void addParameter(String name,Class type,Object defaultValue,String title,boolean allowNull) throws Exception { 
        if (args==null)
            args=new LinkedHashMap<String,Parameter>();
        if (args.containsKey(name))
            throw new Exception("参数："+name+",不允许为null！");
        if (type==null)
            throw new java.lang.NullPointerException();
        
         if (defaultValue!=null && !type.equals(defaultValue.getClass()))
            throw new Exception("参数值的类型不是："+type.getSimpleName());
         
         Parameter p=new Parameter();
         p.type=type;
         p.value=defaultValue;
         p.defaultValue=defaultValue;
         p.allowNull=allowNull;
         p.title=title;
         args.put(name, p);           
    }
   
    /**
     * 子类可设置.新增一个参数,默认不允许为空，缺省值为null;
     * @param name  参数名
     * @param type  参数类型 
     */
    protected void addParameter(String name,Class type) throws Exception { 
        addParameter(name,type,null,"",false);       
    }
    
    /**
     * 子类可设置.新增一个参数,默认不允许为空，缺省值为null;
     * @param name  参数名
     * @param type  参数类型 
     * 
     */
    protected void addParameter(String name,String title,Object defaultValue) throws Exception { 
        addParameter(name,defaultValue.getClass(),defaultValue,title,false);       
    }
    
    
    /**
     * 子类可设置.新增一个参数,默认不允许为空，缺省值为null;
     * @param name  参数名
     * @param type  参数类型 
     */
    protected void addParameter(String name,Object defaultValue) throws Exception { 
        addParameter(name,defaultValue.getClass(),defaultValue,"",false);       
    }
    
    /**
     * 当要调用paint方法时，必须通过本类函数填入实参
     * @param name  实参名；
     * @param value  实参值；
     */
    public void addArg(String name,Object value) throws Exception{
         if (args==null)
            throw new Exception("不需要设参数！ args is null");
         
         if (name==null)
            throw new java.lang.NullPointerException();
         
        if (!args.containsKey(name))
            throw new Exception("参数："+name+",不存在！");
        
        Parameter p=args.get(name);
        if (value.getClass().getName().equals(String.class.getName()))
            value=p.coding.valueFromString((String)value);
        
        if ( !p.type.equals(p.value.getClass()))
             throw new Exception("参数："+name+"的值 与类型不一致！");
        
        p.value=value;
    }
   
    public Object  getParameterValue(String name){
        
        return args.get(name).value;
    }
    
    public Parameter  getParameter(String name){
        
        return args.get(name);
    }
   
    
    public LinkedHashMap<String,Parameter>  getParameters(){
        return args;
    }
    //</ditor-fold>    
    //</editor-fold> 
    

    
    //</editor-fold>
    
}
