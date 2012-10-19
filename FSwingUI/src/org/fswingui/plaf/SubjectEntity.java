/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.awt.Color;
import java.awt.Font;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.fswingui.plaf.tools.paint.AbstractPaint;

/**
 *生成主题时，要对paint的 colourSubjectModel类型的属性传值，不传时。
 * paint将按自定义的默认值进行。
 */
public class SubjectEntity implements Cloneable {
    public static final int ICON_LEFT_AND_TEXT_RIGHT =0;
    public static final int ICON_RIGHT_AND_TEXT_LEFT =1;
    public static final int ICON_TOP_AND_TEXT_BOTTOM =2;
    public static final int ICON_BOTTOM_AND_TEXT_TOP =3;
    public static final int ICON_BACK_AND_TEXT_FRONT =4;
    public static final String ACTIVE=SubjectCriterion.ACTIVE;
    public static final String DISABLE=SubjectCriterion.DISABLE;
    
    private String subjectID;
    private AbstractPaint paint;
    private boolean opaque;
    private int arrangement=0;
    
    private boolean disableText=true;
    private String text;
    private float textTransparence=1f;
    private Font font;
    private Color fontColor;
   
     
    private boolean disableIcon=true;
    private ImageIcon  icon;
    private float iconTransparence=1f;;
    
    private ImageIcon  backgroundIcon;
    private float backgroundTransparence=1f;
    private LinkedHashMap<String,SubjectEntity>  relateSubjectEntity=null; 
    
    //<editor-fold desc="getter and setter">
    public boolean isDisableIcon() {
        return disableIcon;
    }

    public void setDisableIcon(boolean disableIcon) {
        this.disableIcon = disableIcon;
    }

    public boolean isDisableText() {
        return disableText;
    }

    public void setDisableText(boolean disableText) {
        this.disableText = disableText;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public AbstractPaint getPaint() {
        return paint;
    }

    public void setPaint(AbstractPaint paint) {
        this.paint = paint;
    }

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * 文本、图标位置
     */
    public int getArrangement() {
        return arrangement;
    }
    /**
     * 文本、图标位置
     */
    public void setArrangement(int arrangement) {
        this.arrangement = arrangement;
    }
    

    /**
     * 图标透明度
     */
    public float getIconTransparence() {
        return iconTransparence;
    }
    /**
     * 图标透明度
     */
    public void setIconTransparence(float iconTransparence) {
        this.iconTransparence = iconTransparence;
    }
    /**
     * 文本透明度
     */
    public float getTextTransparence() {
        return textTransparence;
    }
    /**
     * 文本透明度
     */
    public void setTextTransparence(float textTransparence) {
        this.textTransparence = textTransparence;
    }

    public ImageIcon getBackgroundIcon() {
        return backgroundIcon;
    }

    public void setBackgroundIcon(ImageIcon backgroundIcon) {
        this.backgroundIcon = backgroundIcon;
    }
    /**
     * 背景图片透明度
     */
    public float getBackgroundTransparence() {
        return backgroundTransparence;
    }
    /**
     * 背景图片透明度
     */
    public void setBackgroundTransparence(float backgroundTransparence) {
        this.backgroundTransparence = backgroundTransparence;
    }

    /**
     * 文本字体
     */
    public Font getFont() {
        return font;
    }    
    /**
     * 文本字体
     */
    public void setFont(Font font) {
        this.font = font;
    }
    /**
     * 文本字体颜色
     */
    public Color getFontColor() {
        return fontColor;
    }
    /**
     * 文本字体颜色
     */
    public void setFontColor(Color fontColor) {
        this.fontColor = fontColor;
    }

    /**
     * 组件是否透明，沿用JComponent的Opaque属性
     */
    public boolean isOpaque() {
        return opaque;
    }
    
    /**
     * 组件是否透明，沿用JComponent的Opaque属性
     */
    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }
    
    
    //</editor-fold> 
    
    //<editor-fold desc="relateSubjectEntity封装方法">
    public void clear() {
        if(relateSubjectEntity==null) return;
        relateSubjectEntity.clear();
    }

    public boolean containsKey(String key) {
        if(relateSubjectEntity==null) return false;
        return relateSubjectEntity.containsKey(key);
    }

    public Set<Entry<String, SubjectEntity>> entrySet() {
        if(relateSubjectEntity==null) return null;
        return relateSubjectEntity.entrySet();
    }
 

    public SubjectEntity get(String key) {
        if(relateSubjectEntity==null) return null;
        SubjectCriterion.containCheckSE(this, key);
        return relateSubjectEntity.get(key);
    }

    public boolean isEmpty() {
        if(relateSubjectEntity==null) return true;
        return relateSubjectEntity.isEmpty();
    }

    public Set<String> keySet() {
        if(relateSubjectEntity==null) return null;
        return relateSubjectEntity.keySet();
    }

    public SubjectEntity put(String key, SubjectEntity value) {
        if(relateSubjectEntity==null) {
            relateSubjectEntity=new LinkedHashMap();
        }
        SubjectCriterion.containCheckSE(this, key);
        return relateSubjectEntity.put(key, value);
    }

    public void putAll(Map<? extends String, ? extends SubjectEntity> m) {
        if(relateSubjectEntity==null) {
            relateSubjectEntity=new LinkedHashMap();
        }
        relateSubjectEntity.putAll(m);
        SubjectCriterion.containCheckSE(this, relateSubjectEntity);
    }

    public SubjectEntity remove(String key) {
        if(relateSubjectEntity==null) return null;
        return relateSubjectEntity.remove(key);
    }

    public int size() {
        if(relateSubjectEntity==null) return 0;
        return relateSubjectEntity.size();
    }

    public boolean containsValue(SubjectEntity value) {
        if(relateSubjectEntity==null) return false;
        return relateSubjectEntity.containsValue(value);
    }

    public Collection<SubjectEntity> values() {
        if(relateSubjectEntity==null) return null;
        return relateSubjectEntity.values();
    }
    //</editor-fold>
    
    @Override
    public SubjectEntity clone(){
        SubjectEntity o=null;
        try {
            o=(SubjectEntity) super.clone();        
            if (relateSubjectEntity==null) return null;
            LinkedHashMap<String,SubjectEntity> m=new LinkedHashMap();
            for(  Entry<String,SubjectEntity>  en:relateSubjectEntity.entrySet() ){
                String k=en.getKey(); 
                m.put(en.getKey() , en.getValue().clone());
            }
           o.relateSubjectEntity=  (LinkedHashMap<String, SubjectEntity>) m;            
        } catch (CloneNotSupportedException ex) {
            Logger.getLogger(SubjectEntity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return o;
    }
    
}
