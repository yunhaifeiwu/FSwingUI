/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame;

import java.util.HashMap;
import java.util.Map;
import org.fswingui.tools.frame.model.DataBus;

/**
 * 具体创建框架各部份的类. 该类是一个转接类，具体完成由由其子类完成。<br> 
 * 具体负责的创建的子类，必须设定name，以标识自已完成的部份。
 * @author cloud
 */
public  class  AbstractCreatePart implements CreatePart{
    protected String name;
    protected DataBus dataBus;
    protected Map<String,AbstractCreatePart> map;

 
    public String getName() {
        return name;
    }

 
    public void setName(String name) {
        this.name = name;
    }
    
    

    
    public AbstractCreatePart(){
        map=null;
    }
    
    public AbstractCreatePart(boolean isRoot){
        if (isRoot){
            map=new HashMap();
        }
    }
    
    public AbstractCreatePart(DataBus dataBus,boolean isRoot){
        this.dataBus=dataBus;
        if (isRoot){
            map=new HashMap();
        }
    }
    
    public void reSetDataBus (){           
        for(CreatePart c:map.values()){
            c.setDataBus(this.dataBus);
        }
    }
    
    @Override
    public void add(AbstractCreatePart c){
        if (map==null) return;
        
        c.setDataBus(this.dataBus);
        if(map.containsKey(c.getName())) {
            map.remove(c.getName());
            map.put(c.getName(), c);
        } else {
             map.put(c.getName(), c);
        }
    }
    
    public void remover(String key){
        if (map==null) return;
        map.remove(key);
    }
   
    protected Object create (){return null;
    }
    
    
    @Override
    public Object create(String partName) {       
        if(map.containsKey(partName)) {
           return  map.get(partName).create();
        }
        return null;
    }

    @Override
    public void setDataBus(DataBus dataBus) {
        this.dataBus=dataBus;
    }

    @Override
    public DataBus getDataBus() {
        return dataBus;
    }
    
}
