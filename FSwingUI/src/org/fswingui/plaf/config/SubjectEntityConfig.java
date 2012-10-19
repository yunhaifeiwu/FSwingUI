/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf.config;

import java.awt.Color;
import java.awt.Font;
import java.util.LinkedHashMap;
import javax.swing.ImageIcon;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.tools.paint.AbstractPaint;

/**
 *
 * @author Administrator
 */
public class SubjectEntityConfig {
    private String subjectID;
    private String paint;
    private boolean opaque;
    private int arrangement=0;
    
    private boolean disableText=true;
    private String text;
    private float textTransparence=1f;
    private String font;
    private String fontColor;
   
     
    private boolean disableIcon=true;
    private String  icon;
    private float iconTransparence=1f;;
    
    private String  backgroundIcon;
    private float backgroundTransparence=1f;
    private LinkedHashMap<String,SubjectEntityConfig>  relateSubjectEntity=null; 

    public String getSubjectID() {
        return subjectID;
    }

    public void setSubjectID(String subjectID) {
        this.subjectID = subjectID;
    }

    public String getPaint() {
        return paint;
    }

    public void setPaint(String paint) {
        this.paint = paint;
    }

    public boolean isOpaque() {
        return opaque;
    }

    public void setOpaque(boolean opaque) {
        this.opaque = opaque;
    }

    public int getArrangement() {
        return arrangement;
    }

    public void setArrangement(int arrangement) {
        this.arrangement = arrangement;
    }

    public boolean isDisableText() {
        return disableText;
    }

    public void setDisableText(boolean disableText) {
        this.disableText = disableText;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public float getTextTransparence() {
        return textTransparence;
    }

    public void setTextTransparence(float textTransparence) {
        this.textTransparence = textTransparence;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public boolean isDisableIcon() {
        return disableIcon;
    }

    public void setDisableIcon(boolean disableIcon) {
        this.disableIcon = disableIcon;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public float getIconTransparence() {
        return iconTransparence;
    }

    public void setIconTransparence(float iconTransparence) {
        this.iconTransparence = iconTransparence;
    }

    public String getBackgroundIcon() {
        return backgroundIcon;
    }

    public void setBackgroundIcon(String backgroundIcon) {
        this.backgroundIcon = backgroundIcon;
    }

    public float getBackgroundTransparence() {
        return backgroundTransparence;
    }

    public void setBackgroundTransparence(float backgroundTransparence) {
        this.backgroundTransparence = backgroundTransparence;
    }

    public LinkedHashMap<String, SubjectEntityConfig> getRelateSubjectEntity() {
        return relateSubjectEntity;
    }

    public void setRelateSubjectEntity(LinkedHashMap<String, SubjectEntityConfig> relateSubjectEntity) {
        this.relateSubjectEntity = relateSubjectEntity;
    }

   
    
    
    
}
