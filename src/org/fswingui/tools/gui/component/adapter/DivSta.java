/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component.adapter;

import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.gui.component.extra.Div;

/**
 *本接口，要求实现Div的基础Swing组件必须实现，否则基础Swing组件不能被本框架使用
 */
public interface DivSta {
    public CurrentData getCurrentData();
    public void setCurrentData(CurrentData currentData);
    /**
     * 要求父容器为绝对布局
     * 组件放大缩小
     */
    public void addScaleMouseAdapte();
    /**
     * 要求父容器为绝对布局
     * 组件放大缩小
     */
    public void removeScaleMouseAdapte();
    /**
     * 要求父容器为绝对布局
     * 组件拖动
     */
    public void addFocusAndDropMouseAdapter();
    /**
     * 要求父容器为绝对布局
     * 组件拖动
     */
    public void removeFocusAndDropMouseAdapter();
    /**
     * 向父容器上传鼠标事件
     */
    public void addUpDispatchMouseEvent();
    /**
     *停止向父容器上传鼠标事件
     */
    public void removeUpDispatchMouseEvent();
}
