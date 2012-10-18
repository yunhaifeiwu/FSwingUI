/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *所有主题相关的约束规范皆在这里
 */
public class SubjectCriterion {
   
            
    //<editor-fold desc="SubjectEntity 相关约束">
    public static final String MAIN="main";    
    public static final String ACTIVE="active";
    public static final String DISABLE="disable";  
    
    public static final String USER_NAME_SUBJECT_ENTITY="userSubjectEntity_";
     
    /**
     * Integer规定的该关键字的 索引位置，其中为-1时，表明其索引位置不强行规定
    */
    private final static LinkedHashMap<String,Integer> keySubjectEntity=new LinkedHashMap();
    /**
     * SubjectEntity 方面初始化 标准集合
     */
    public static void initSE(){
        keySubjectEntity.put(ACTIVE,-1);
        keySubjectEntity.put(DISABLE,-1);     
        keySubjectEntity.put(MAIN,-1);     
    }
    /**
     * SubjectEntity 方面,
     * 便于用户扩展。增加一个用户自定义关键字
     * Integer规定的该关键字的 索引位置，其中为-1时，表明其索引位置不强行规定
    */
    public static void putSE(String key,int index){
        keySubjectEntity.put(key, index);
    }
    
    /**
      * SubjectEntity方面约束。检查指定字符串key是否是 强制性关键字.
      * @param o  委托检查实例，便于指明报错地方
      * @param key 待检查字符串
      */
     public static void containCheckSE(Object o,String key){
         if (keySubjectEntity.size()<=0){
             initSE();
         } 
         if (!keySubjectEntity.containsKey(key)){
             try {          
                 throw new Exception("key:"+key+"不是关键字。出错对象为："+o);
             } catch (Exception ex) {
                 Logger.getLogger(SubjectCriterion.class.getName()).log(Level.SEVERE, null, ex);
             }
             
         }
     }  
     
     /**
      * SubjectEntity方面约束。检查指定字符串key是否是 强制性关键字.
      * @param o  委托检查实例，便于指明报错地方
      * @param key 待检查字符串
      */
     public static boolean containsKeySE(Object o,String key){
         if (keySubjectEntity.size()<=0){
             initSE();
         }        
        return  keySubjectEntity.containsKey(key);
     }  
     
     /**
      * SubjectEntity方面约束。检查指定属性集合 是否符合 强制性规范；
      * @param o  委托检查实例，便于指明报错地方
      */
     public static void containCheckSE(Object o,Map<String,SubjectEntity> map){
         if (keySubjectEntity.size()<=0){
             initSE();
         } 
         for(Map.Entry<String,SubjectEntity> en:map.entrySet()){
            containCheckSE(o,en.getKey());
         }
     }  
    //</editor-fold>
    
    //<editor-fold desc=" Subject相关约束">
    public static final String USER_NAME_SUBJECT="userSubject_";
             
    public static final String PRIMARYILY="primarily";
    public static final String PRIMARY="primary";
    public static final String DARKER="darker";
    public static final String DARK="dark";
    public static final String PALE="pale";
    public static final String PALER="paler";
    public static final String SPRINKLE1="sprinkle1";
    public static final String SPRINKLE2="sprinkle2";
    public static final String SPRINKLE3="sprinkle3";
    
    
     
       
    /**
     * Subject 方面
     * Integer规定的该关键字的 索引位置，其中为-1时，表明其索引位置不强行规定
    */
    private final static LinkedHashMap<String,Subject> keySubject=new LinkedHashMap();
    /**
     * Subjec方面初始化 标准集合
     */
    public static void initSub(){
        Subject  s=new Subject(PRIMARYILY,"主色");
        keySubject.put(PRIMARYILY,s);
        s=new Subject(PRIMARY,"次主色");
        keySubject.put(PRIMARY,s);   
        s=new Subject(DARKER,"深色");
        keySubject.put(DARKER,s);  
        s=new Subject(DARK,"次深色");
        keySubject.put(DARK,s); 
        s=new Subject(PALE,"浅色");
        keySubject.put(PALE,s);   
        s=new Subject(PALER,"次浅色");
        keySubject.put(PALER,s);  
        s=new Subject(SPRINKLE1,"点缀色1");
        keySubject.put(SPRINKLE1,s);   
        s=new Subject(SPRINKLE2,"点缀色2");
        keySubject.put(SPRINKLE2,s);   
        s=new Subject(SPRINKLE3,"点缀色3");
        keySubject.put(SPRINKLE3,s);   
    }
    /**
     * Subject 方面
     * 便于用户扩展。增加一个用户自定义关键字
     * Integer规定的该关键字的 索引位置，其中为-1时，表明其索引位置不强行规定
    */
    public static void putSub(String key,Subject subject){
        keySubject.put(key, subject);
    }
    
    /**
      * Subject 方面约束。检查指定字符串key是否是 强制性关键字.
      * @param o  委托检查实例，便于指明报错地方
      * @param key 待检查字符串
      */
     public static void containCheckSub(Object o,String key){
         if (keySubject.size()<=0){
             initSub();
         } 
         if (!keySubject.containsKey(key)){
             try {          
                 throw new Exception("key:"+key+"不是关键字。出错对象为："+o);
             } catch (Exception ex) {
                 Logger.getLogger(SubjectCriterion.class.getName()).log(Level.SEVERE, null, ex);
             }
             
         }
     }  
      
      /**
      * Subject 方面约束。检查指定字符串key是否是 强制性关键字.
      * @param o  委托检查实例，便于指明报错地方
      * @param key 待检查字符串
      */
     public static boolean containsKeySub(Object o,String key){
         
         if (keySubject.size()<=0){
             initSub();
         }            
        return  keySubject.containsKey(key);
     }  
     
     /**
      * Subject 方面约束。检查指定属性集合 是否符合 强制性规范；
      * @param o  委托检查实例，便于指明报错地方
      */
     public static void containCheckSub(Object o,Map<String,Subject> map){
         if (keySubject.size()<=0){
             initSub();
         } 
         for(Map.Entry<String,Subject> en:map.entrySet()){             
            containCheckSub(o,en.getKey());
         }
     }  

    /**
     * 获得主题关键字集合 
     */
    public static LinkedHashMap<String, Subject> getKeySubject() {
        if (keySubject.size()<=0){
             initSub();
         } 
        return keySubject;
    }
     
    
     
    //</editor-fold> 
 
}
