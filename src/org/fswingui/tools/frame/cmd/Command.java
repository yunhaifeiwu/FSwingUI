/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.cmd;

import org.fswingui.tools.frame.model.DataBus;

/**
 *
 *提供了 基本的参数传递，与标准的调用执行程序。
 * 具体命令要增加执行函数的参数，请构造函数或设置属性完成
 */
public abstract class Command {   
    
    protected DataBus dataBus;    
    protected String id;

    public Command(DataBus dataBus, String id) {
        this.dataBus = dataBus;
        this.id = id;
    }
    
    
    
    
    public DataBus getDataBus() {
        return dataBus;
    }

    public void setDataBus(DataBus dataBus) {
        this.dataBus = dataBus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    
    
    public abstract void excute();
    
}
