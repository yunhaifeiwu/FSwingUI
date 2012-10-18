/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;

/**
 *这是对MapPropertys 里的 map 变量值 进行约束并强行检查。 map定义原型为：
 * private Map<String,PropertyUnit>
 * 其他Div 及属性显示组件中，强烈建议使用该检测类检查。
 */
public  final   class PropertyCriterion {
    /**
     * 属性名称关键字，对应着DIV的ID
     */
     public final static String NAME="name";
     /**
      * 主题关键字
      */
     public final static String SUBJECT_ID="subjectID";
     /**
      * 宽度关键字
      */
     public final static String WIDTH="width";
     /**
      * 高度关键字
      */
     public final static String HEIGHT="height";
     /**
      * 横坐标关键字
      */
     public final static String X="x";
     /**
      * 纵坐标关键字
      */
     public final static String Y="y";
     
     /**
     * 组件是否透明，沿用JComponent的Opaque属性
     */
     public final static String OPAQUE="opaque";
     /**
      * 文本与图标之间的排列位置的关键字
      */
     public final static String ARRANGEMENT="arrangement";
     /**
      * 禁止使用文本关键字
      */
     public final static String DISABLE_TEXT="disableText";
     /**
      *文本关键字
      */
     public final static String TEXT="text";
     /**
      * 字体关键字
      */
     public final static String FONT="font";
     /**
      * 字体颜色关键字
      */
     public final static String FONT_COLOR="fontColor";
     /**
      * 文本透明度关键字
      */
     public final static String TEXT_TRANSPARENCE="textTransparence";
     /**
      * 禁止使用图标关键字
      */
     public final static String DISABLE_ICON="disableIcon";
     /**
      * 图标关键字
      */
     public final static String ICON="icon";
     /**
      * 图标透明度关键字
      */
     public final static String ICON_TRANSPARENCE="iconTransparence";
     /**
      * 背景图关键字
      */
     public final static String BACKIMG="backImg";
     /**
      * 背景透明度关键字
      */
     public final static String BACKGROUD_TRANSPARENCE="backgroundTransparence";
     /**
      * 背景绘制函数符号代表关键字
      */
     public final static String PAINT="paint";
     
     /**
      * Integer规定的该关键字的 索引位置，其中为-1时，表明其索引位置不强行规定
      */
     private final static LinkedHashMap<String,Integer> keyWords=new LinkedHashMap();
     public static void init(){
         keyWords.put(NAME,0);
         keyWords.put(SUBJECT_ID,1);
         keyWords.put(WIDTH,2);
         keyWords.put(HEIGHT,3);
         keyWords.put(X,4);
         keyWords.put(Y,5);
         keyWords.put(OPAQUE,-1);
         keyWords.put(ARRANGEMENT,-1);
         keyWords.put(DISABLE_TEXT,-1);
         keyWords.put(TEXT,-1);
         keyWords.put(FONT,-1);
         keyWords.put(FONT_COLOR,-1);
         keyWords.put(TEXT_TRANSPARENCE,-1);
         keyWords.put(DISABLE_ICON,-1);
         keyWords.put(ICON,-1);
         keyWords.put(ICON_TRANSPARENCE,-1);
         keyWords.put(BACKIMG,-1);
         keyWords.put(BACKGROUD_TRANSPARENCE,-1);
         keyWords.put(PAINT,-1);         
     }
     
      /**
      * 检查指定字PropertyUnit的索引index是否是强致性的
      * @param o  委托检查实例，便于指明报错地方
      * @param key 待检查字符串
      */
     public static void indexCheck(Object o,PropertyUnit pu){
          if (keyWords.size()<=0){
             init();
         } 
         containCheck(o,pu.name) ;
         int i=keyWords.get(pu.name);
         if(i!=-1 && i!=pu.index){
             try {
                 String s="pu.index:"+pu.index+" 不符合规约。规约为: pu.name";
                 s=s+pu.name+"的index为"+i+"。";
                 s=s+"出错对象为："+o;
                 throw new Exception(s);
             } catch (Exception ex) {
                 Logger.getLogger(PropertyCriterion.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     }  
     
     /**
      * 检查指定字符串key是否是 强制性关键字
      * @param o  委托检查实例，便于指明报错地方
      * @param key 待检查字符串
      */
     public static void containCheck(Object o,String key){
         if (keyWords.size()<=0){
             init();
         } 
         if (!keyWords.containsKey(key)){
             try {
                 throw new Exception("key:"+key+"不是关键字。出错对象为："+o);
             } catch (Exception ex) {
                 Logger.getLogger(PropertyCriterion.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     }  
     
     /**
      * 检查指定属性集合 是否符合 强制性规范；
      * @param o  委托检查实例，便于指明报错地方
      */
     public static void containCheck(Object o,Map<String,PropertyUnit> map){
         if (keyWords.size()<=0){
             init();
         } 
         for(Map.Entry<String,PropertyUnit> en:map.entrySet()){
             PropertyUnit pu=en.getValue();
             if(!en.getKey().equals(pu.name)){
                 String s="Map关键字Key'"+en.getKey()+"与PropertyUnit属性名不等";
                 s=s+o;
                 try {
                     throw new Exception(s);
                 } catch (Exception ex) {
                     Logger.getLogger(PropertyCriterion.class.getName()).log(Level.SEVERE, null, ex);
                 }
             }
             
            containCheck(o,pu.name);
            indexCheck(o,pu);
         }
         if (!map.containsKey(NAME)){
             String s1="待检map中没有包含关键字name";
             s1=s1+o;
             try {
                 throw new Exception(s1);
             } catch (Exception ex) {
                 Logger.getLogger(PropertyCriterion.class.getName()).log(Level.SEVERE, null, ex);
             }
         }
     }  
    
}
