/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.gui.component.extra;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.border.Border;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.gui.component.adapter.DivSta;
import org.fswingui.tools.gui.component.adapter.FocusAndDropMouseAdapter;
import org.fswingui.tools.gui.component.adapter.ScaleMouseAdapter;

/**
 *
 * @author cloud
 */
public interface Div extends DivSta,Cloneable {
    public final static String TYPE_PANEL="DivPanelExtra";
    public final static String TYPE_Combi="ComDivPanelExtra";
    
    /**
     * Div的名称，是唯一ID. 建议与view的name相同
     */
    public String getName();
    /**
     * Div的名称，是唯一ID. 建议与view的name相同
     */
    public void setName(String name);
    /**
     * 如果是选中的，设选中边框，如果非选中设成原来边框
     */
    public void setOldBorder(boolean selected);
    public String getType();
    public int getX();
    public int getY();
    public int getWidth();
    public int getHeight();
    public JComponent getView();
   
    /**
     * 得前后顺序号
     */
    public int getZOrder();
    /**
     * 父容器布局管理器为null
     */
    public void setBounds(int x, int y, int width, int height);
    
 

            
    /**
     * 把自身的相关属性，设定到currData的MapPropertys中.<br>
     * 子类必须实现。这个通常不对外调用。
     */
    public  void bind();
    /**
     * 当用户改变属性值时，本Panel按属性变化值进行设定.属性变化值于<br>
     * currData的MapPropertys中。
     */
    public void propertyChange(PropertyChangeEvent evt);
}
