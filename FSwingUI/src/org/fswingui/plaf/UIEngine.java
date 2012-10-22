/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.plaf;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.JTableHeader;
import org.fswingui.plaf.config.FSUIConfig;
import org.fswingui.plaf.config.JsonConfig;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.Parameter;
import org.fswingui.plaf.tools.paint.expand.CrystalPaint;
import org.fswingui.plaf.ui.FButtonUI;
import org.fswingui.plaf.ui.FLabelUI;
import org.fswingui.plaf.ui.FListUI;
import org.fswingui.plaf.ui.FPanelUI;
import org.fswingui.plaf.ui.FTableHeaderUI;
import org.fswingui.plaf.ui.FTreeUI;
import sun.swing.SwingUtilities2;


/**
 * 1 对于获得SubjectModel 要么 <br/>
 *     通过组件名得到subjectID或通过类名得到SubjectID，然后在UIManger中取得〈br/>
 *   要么
 *     直接从组件中取得
 * 2 背景是通过调用SubjectModel.abstactPaint.paint方法完成。
 * 3 由UIManger的当前风格+当前件件主题ID+当前风格的颜色得到。当前颜色在当前风格
 */
public class UIEngine {
    // 内部函数之间的信息交互
    public static class Data{
        public String styleID;
        public String subjectID;
        public SubjectEntity subjectEntity;
        public Color mainColor;
        public boolean isBright=false;
    }
    
    public static final  String CURRENT_COLOR ="FSwingui.CurrentColor";
    public static final  String CURRENT_STYLEID ="FSwingui.StyleID";
    public static final  String PRE ="FSwingui.";
    public static final  String STYLE ="defaultStyle.";
    public static final  String SUBJECT ="subject:";
   
    //主题索引。Key为组件名，Value为主题ID
    private  static ConcurrentHashMap<String,String> subjectIndex=new ConcurrentHashMap() ;
    //主题仓库。Key为 风格ID +主题ID，值为实题实体
    private  static ConcurrentHashMap<String,SubjectEntity> subjectStore=new ConcurrentHashMap() ;
    //风格主色 集。Key为 风格ID ，值为主色
    private  static ConcurrentHashMap<String,Color> styleMainColor=new ConcurrentHashMap() ;
    private static FSUIConfig config;
    private static ColorModel colorModel;//这是颜色转换接口
   
     
    private Rectangle paintIconR = new Rectangle();
    private static Rectangle viewRect = new Rectangle();
    private static Rectangle textRect = new Rectangle();
    private static Rectangle iconRect = new Rectangle();
     //初始化标记   
    private static boolean initState=false;
    private transient static Color currentMainColor;//当前主色
    private transient static String currentStyle; //当前风格
    //<editor-fold desc="getter and setter ">
  

    /**
     * 主题索引。Key为组件名，Value为主题ID   
     */
    public static ConcurrentHashMap<String, String> getSubjectIndex() {
        return subjectIndex;
    }

    /**
     * 主题索引。Key为组件名，Value为主题ID
     */
    public static void setSubjectIndex(ConcurrentHashMap<String, String> subjectIndex) {
        UIEngine.subjectIndex = subjectIndex;
    }

    /**
     * 主题仓库。Key为 方格ID +主题ID，值为实题实体
     */
    public static ConcurrentHashMap<String, SubjectEntity> getSubjectStore() {
        return subjectStore;
    }

    /**
     * 主题仓库。Key为 方格ID +主题ID，值为实题实体
     */
    public static void setSubjectStore(ConcurrentHashMap<String, SubjectEntity> subjectStore) {
        UIEngine.subjectStore = subjectStore;
    }
        
    public static FSUIConfig getConfig() {
        
        if(config==null) {
            config=new JsonConfig();
        }     
        return config;
    }
    
  
    public static void setConfig(FSUIConfig config) {
        UIEngine.config = config;
    }

    /**
     * 风格的主体颜色。系统假想所有的主题按该颜色进行设置。当用户指定其他主体颜色时，
     * 系统试图根据主体颜色之间的特征，推算各个主题色相应的色值。
     * k---风格ID，V色
     */
    public static ConcurrentHashMap<String, Color> getStyleMainColor() {
        return styleMainColor;
    }

    /**
     * 风格的主体颜色。系统假想所有的主题按该颜色进行设置。当用户指定其他主体颜色时，
     * 系统试图根据主体颜色之间的特征，推算各个主题色相应的色值。
     * k---风格ID，V色
     */
    public static void setStyleMainColor(ConcurrentHashMap<String, Color> styleMainColor) {
        UIEngine.styleMainColor = styleMainColor;
        
    }

    public static Color getCurrentMainColor() {
        return currentMainColor;
    }

    public static void setCurrentMainColor(Color currentMainColor) {
        UIEngine.currentMainColor = currentMainColor;
        UIManager.put(UIEngine.CURRENT_COLOR,  currentMainColor);   
    }

    public static String getCurrentStyle() {
        return currentStyle;
    }

    public static void setCurrentStyle(String currentStyle) {
        UIEngine.currentStyle = currentStyle;
        UIManager.put(UIEngine.CURRENT_STYLEID, currentStyle );
    }
    
    
        
          
    
   //</editor-fold>
    
    public static boolean init(int initStyle){
        subjectIndex.clear();
        subjectStore.clear();
        
        switch (initStyle){
            case 0:
                if(config==null) {
                    config=new JsonConfig();
                }
                initState =config.readConfig();
                if(initState){
                   subjectIndex=config.getSubjectIndex();
                   subjectStore=config.getSubjectStore();
                   styleMainColor=config.getStyleMainColor();
                }
                 
                break;
            case 1:
                subjectIndex.put(JLabel.class.getSimpleName(),SubjectCriterion.PRIMARYILY);
                subjectIndex.put(JList.class.getSimpleName(), SubjectCriterion.PRIMARYILY);
                subjectIndex.put(JPanel.class.getSimpleName(), SubjectCriterion.PRIMARYILY);
                subjectIndex.put(JTree.class.getSimpleName(), SubjectCriterion.PRIMARYILY);
                subjectIndex.put(JTableHeader.class.getSimpleName(), SubjectCriterion.PRIMARYILY);
                subjectIndex.put(JLabel.class.getSimpleName(), SubjectCriterion.PRIMARYILY);
                subjectIndex.put(JMenuBar.class.getSimpleName(), SubjectCriterion.PRIMARYILY);
                subjectIndex.put(JMenu.class.getSimpleName(), SubjectCriterion.PRIMARYILY);
                subjectIndex.put("PanelUI", SubjectCriterion.PRIMARYILY);
                subjectIndex.put("LabelUI", SubjectCriterion.PRIMARYILY);
                subjectIndex.put("TreeUI", SubjectCriterion.PRIMARYILY);
                subjectIndex.put("ButtonUI", SubjectCriterion.PRIMARYILY);
                subjectIndex.put("ListUI", SubjectCriterion.PRIMARYILY); 
                subjectIndex.put("TableHeaderUI", SubjectCriterion.PRIMARYILY); 
                subjectIndex.put("MenuBarUI", SubjectCriterion.PRIMARYILY); 
                subjectIndex.put("MenuItemUI", SubjectCriterion.PRIMARYILY); 
                subjectIndex.put("ToolBarUI", SubjectCriterion.PRIMARYILY); 
                
//                subjectIndex.put("MenuUI", SubjectCriterion.PRIMARYILY); 
                
                 
                ImageIcon icon ;
                SubjectEntity sm=new SubjectEntity();
        //        sm.setText("textButtonddd");
                sm.setIconTransparence(0.9f);
                sm.setTextTransparence(0.9f);
                icon = new ImageIcon("src/org/fswingui/plaf/resource/backgound.jpg");
                sm.setBackgroundIcon(icon);
                sm.setBackgroundTransparence(0.2f);
                AbstractPaint root=new CrystalPaint();
                try {
                    root.addArg("azimuth", 0);
                } catch (Exception ex) {
                    Logger.getLogger(UIEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    root.addArg("ColorSource", Color.pink.darker());
                } catch (Exception ex) {
                    Logger.getLogger(UIEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
                sm.setPaint(root);
                sm.setArrangement(SubjectEntity.ICON_TOP_AND_TEXT_BOTTOM); 
              
                subjectStore.put(STYLE+SubjectCriterion.PRIMARYILY,sm );
                UIManager.put(CURRENT_STYLEID,STYLE.substring(0, STYLE.length()-1));
                break;
        }
        
        changeMainColor();
        return initState;
    }
    
    /**
     * 更改主色。一定要在subjectStore重新生成后使用。
     * 要更改颜色替换算法，请在调用ini()前，设置colorModel属性
     */
    public static void changeMainColor(){
       
//        currentMainColor;//当前主色
//     String currentStyle; //当前风格
       
        currentMainColor=(Color) UIManager.get(CURRENT_COLOR);
        if (currentMainColor==null ||  styleMainColor==null) {
            return;
        }
        currentStyle =  getCurrentStyleID();  
        Color oldMainColor=styleMainColor.get(currentStyle);
        //吏更paint中的色彩
        if (subjectStore==null) return;
        
        if(colorModel==null) {
           SimpleHueAutoChange scolorModel= new SimpleHueAutoChange();  
           colorModel =scolorModel;
           scolorModel.setNewMainColor(currentMainColor);
           scolorModel.setOldMainColor(oldMainColor);
        } else if (AbstractColorModel.class.isAssignableFrom(colorModel.getClass())){
           AbstractColorModel  scolorModel =(AbstractColorModel) colorModel;
           scolorModel.setNewMainColor(currentMainColor);
           scolorModel.setOldMainColor(oldMainColor);
        }
        
       
        
        for (Map.Entry<String,SubjectEntity> en:subjectStore.entrySet()){
            SubjectEntity sube=en.getValue();
            if(sube==null) continue;
            Color c=colorModel.getColorFromChange(sube.getFontColor());
            sube.setFontColor(c);
            AbstractPaint paint=sube.getPaint();
            if(paint==null) continue;
            for(Map.Entry<String,Parameter> en1:paint.getArgs().entrySet()){
                Parameter par=en1.getValue();
                if(par.type.equals(Color.class)){
                    Color c1=(Color) par.value;
                    par.value= colorModel.getColorFromChange(c1);
                    c1=(Color) par.defaultValue;
                    par.defaultValue=colorModel.getColorFromChange(c1);
                }
            }
        }
       
    }
    
    public static String getCurrentStyleID(){
        String styleID=(String) UIManager.get(CURRENT_STYLEID);

        if(styleID==null || styleID.equals(""))
        {//如果没有当前风格ID，则自动现有的当前风格ID;
            if(subjectStore==null || subjectStore.isEmpty()){
                return "" ;
            }
            String str=subjectStore.keys().nextElement();
            if (str==null || str.equals("")) return null;
            String strs[]=str.split("\\.");

            if (strs.length>0) {
               styleID=strs[0];
            }else {
                return "";
            }
        }
        return  styleID;
    }    
    
    public static  Data getData( JComponent c){
        Data dt=new Data();
        
        dt.styleID=(String) UIManager.get(CURRENT_STYLEID+"."+c.getName());
        if(dt.styleID==null || dt.styleID.equals("")){
            if(dt.styleID==null || dt.styleID.equals("")){
                dt.styleID=(String) UIManager.get(CURRENT_STYLEID);
            }

            if(dt.styleID==null || dt.styleID.equals(""))
            {//如果没有当前风格ID，则自动现有的当前风格ID;
                if(subjectStore==null || subjectStore.isEmpty()){
                    return dt ;
                }
                String str=subjectStore.keys().nextElement();
                if (str==null || str.equals("")) return null;
                String strs[]=str.split("\\.");

                if (strs.length>0) {
                   dt.styleID=strs[0];
                }else {
                    return dt;
                }
            }
        }
       
        dt.mainColor=(Color) UIManager.get(CURRENT_COLOR+"."+c.getName());
        if(dt.mainColor==null)dt.mainColor=(Color)UIManager.get(CURRENT_COLOR);
        
        if (SubjectEntityDoor.class.isAssignableFrom(c.getClass())){
            dt.subjectEntity= ((SubjectEntityDoor)c).getSubjectEntity();
        } else if (c.getName()!=null) {
            dt.subjectID=(String) UIManager.get(PRE+c.getName());
            dt.subjectID=dt.subjectID==null || "".equals(dt.subjectID)?                  
                 subjectIndex.get(c.getClass().getSimpleName()):dt.subjectID;
            dt.subjectID=dt.subjectID==null?
                    subjectIndex.get(c.getUIClassID()):dt.subjectID;
            dt.subjectID=dt.subjectID==null?"":dt.subjectID;
            dt.subjectEntity= subjectStore.get(dt.styleID+"."+dt.subjectID);
        } else if ( (c.getName()==null) && 
                (dt.subjectID==null || "".equals(dt.subjectID) )
        ){
            
            dt.subjectID=(String) subjectIndex.get(c.getClass().getSimpleName());
            dt.subjectID=dt.subjectID==null?
                    subjectIndex.get(c.getUIClassID()):dt.subjectID;
            dt.subjectID=dt.subjectID==null?"":dt.subjectID;
            dt.subjectEntity= subjectStore.get(dt.styleID+"."+dt.subjectID);
        }
        
        return dt;
    }
    public static void paint(Graphics g, JComponent c,Data data){
  
        data=data==null?getData(c):data;
        if (data==null) return;
        if (data.subjectEntity ==null){
            return;
        }
        
        SubjectEntity sm=data.subjectEntity;
        if (sm!=null ) c.setOpaque(sm.isOpaque());       
        paintBackgroudImage(g,c,data);
        paintBackgroud(g,c,data);
        paintIconText(g,c,data);
       
    }
    public static void paintIconText(Graphics g, JComponent c,Data data){
        data=data==null?getData(c):data;
       
        if (data==null) return;
        if (data.subjectEntity ==null) return;
        SubjectEntity sm=data.subjectEntity;
        if (sm.isDisableIcon() && sm.isDisableText() ) return;
        if ((sm.getIcon()==null) && (sm.getText()==null ) ) return;
         
        if (sm.getFont()!=null )  c.setFont(sm.getFont());
        if(sm.getFontColor()!=null) c.setForeground(sm.getFontColor());
        FontMetrics fm = SwingUtilities2.getFontMetrics(c, g);   
        
        int x=c.getX();
        int y=c.getY();
        int w=c.getWidth();
        int h=c.getHeight();
        Insets i = c.getInsets();
        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = w - (i.right + viewRect.x);
        viewRect.height = h - (i.bottom + viewRect.y);

        textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
        
        float it=sm.getIconTransparence();        
        float tt=sm.getTextTransparence();
        String text="";
        String smText=data.subjectEntity.getText();
        ImageIcon smIcon=data.subjectEntity.getIcon();
        if(sm.isDisableText()) smText=null;
        if(sm.isDisableIcon()) smIcon=null;
        
        Graphics2D  g2 =  (Graphics2D)g;  
        
        Composite alpha2;
        switch(sm.getArrangement()){
            case SubjectEntity.ICON_LEFT_AND_TEXT_RIGHT:
                //layoutCompoundLabel(JComponent c, FontMetrics fm, 
                //String text, Icon icon, int verticalAlignment, 
                //int horizontalAlignment, int verticalTextPosition, 
                //int horizontalTextPosition, Rectangle viewR, Rectangle 
                //iconR, Rectangle textR, int textIconGap) 
                //决定了 ”文字与图标“排列位置的字符串编码。 其中，Alignment组决定
                //”文字与图标“总的文字；TextPosition组决定了，文本以图标为中心的方置。
                //以下是图标在右，文标在左，整体居中对齐
                text=SwingUtilities.layoutCompoundLabel(
                    c, fm, smText, smIcon,
                    SwingConstants.CENTER, SwingConstants.CENTER,
                    SwingConstants.CENTER, SwingConstants.LEFT,
                    viewRect, iconRect, textRect,
                0);
                 
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, it);                
                g2.setComposite(alpha2);
                if (smIcon !=null) smIcon.paintIcon(c, g, iconRect.x, iconRect.y);
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tt); 
                g2.setComposite(alpha2);
               
                g2.setColor(c.getForeground());
                SwingUtilities2.drawStringUnderlineCharAt(c, g,text, -1,
                    textRect.x,textRect.y + fm.getAscent() 
                );      
            break;
            case SubjectEntity.ICON_RIGHT_AND_TEXT_LEFT:
                //layoutCompoundLabel(JComponent c, FontMetrics fm, 
                //String text, Icon icon, int verticalAlignment, 
                //int horizontalAlignment, int verticalTextPosition, 
                //int horizontalTextPosition, Rectangle viewR, Rectangle 
                //iconR, Rectangle textR, int textIconGap) 
                //决定了 ”文字与图标“排列位置的字符串编码。 其中，Alignment组决定
                //”文字与图标“总的文字；TextPosition组决定了，文本以图标为中心的方置。
                text=SwingUtilities.layoutCompoundLabel(
                    c, fm, smText, smIcon,
                    SwingConstants.CENTER, SwingConstants.CENTER,
                    SwingConstants.CENTER, SwingConstants.RIGHT,
                    viewRect, iconRect, textRect,
                0);                
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, it);
                g2.setComposite(alpha2);
                if (smIcon !=null) smIcon.paintIcon(c, g, iconRect.x, iconRect.y);
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tt);
                g2.setComposite(alpha2);

                g2.setColor(c.getForeground());
                 
                SwingUtilities2.drawStringUnderlineCharAt(c, g,text, -1,
                    textRect.x ,textRect.y + fm.getAscent() 
                );      
            break;
            case SubjectEntity.ICON_TOP_AND_TEXT_BOTTOM:
                //layoutCompoundLabel(JComponent c, FontMetrics fm, 
                //String text, Icon icon, int verticalAlignment, 
                //int horizontalAlignment, int verticalTextPosition, 
                //int horizontalTextPosition, Rectangle viewR, Rectangle 
                //iconR, Rectangle textR, int textIconGap) 
                //决定了 ”文字与图标“排列位置的字符串编码。 其中，Alignment组决定
                //”文字与图标“总的文字；TextPosition组决定了，文本以图标为中心的方置。
                text=SwingUtilities.layoutCompoundLabel(
                    c, fm, smText, smIcon,
                    SwingConstants.CENTER, SwingConstants.CENTER,
                    SwingConstants.BOTTOM, SwingConstants.CENTER,
                    viewRect, iconRect, textRect,
                0);                
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, it);
                g2.setComposite(alpha2);
                if (smIcon !=null) smIcon.paintIcon(c, g, iconRect.x, iconRect.y);
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tt);
                g2.setComposite(alpha2);

                g2.setColor(c.getForeground());
                SwingUtilities2.drawStringUnderlineCharAt(c, g,text, -1,
                    textRect.x ,textRect.y + fm.getAscent() 
                );      
            break;
            case SubjectEntity.ICON_BOTTOM_AND_TEXT_TOP:
                //layoutCompoundLabel(JComponent c, FontMetrics fm, 
                //String text, Icon icon, int verticalAlignment, 
                //int horizontalAlignment, int verticalTextPosition, 
                //int horizontalTextPosition, Rectangle viewR, Rectangle 
                //iconR, Rectangle textR, int textIconGap) 
                //决定了 ”文字与图标“排列位置的字符串编码。 其中，Alignment组决定
                //”文字与图标“总的文字；TextPosition组决定了，文本以图标为中心的方置。
                text=SwingUtilities.layoutCompoundLabel(
                    c, fm, smText, smIcon,
                    SwingConstants.CENTER, SwingConstants.CENTER,
                    SwingConstants.TOP, SwingConstants.CENTER,
                    viewRect, iconRect, textRect,
                0);                
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, it);
                g2.setComposite(alpha2);
                if (smIcon !=null) smIcon.paintIcon(c, g, iconRect.x, iconRect.y);
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tt);
                g2.setComposite(alpha2);
                
                g2.setColor(c.getForeground());
                SwingUtilities2.drawStringUnderlineCharAt(c, g,text, -1,
                    textRect.x ,textRect.y + fm.getAscent() 
                );      
            break;
            case SubjectEntity.ICON_BACK_AND_TEXT_FRONT:
                //layoutCompoundLabel(JComponent c, FontMetrics fm, 
                //String text, Icon icon, int verticalAlignment, 
                //int horizontalAlignment, int verticalTextPosition, 
                //int horizontalTextPosition, Rectangle viewR, Rectangle 
                //iconR, Rectangle textR, int textIconGap) 
                //决定了 ”文字与图标“排列位置的字符串编码。 其中，Alignment组决定
                //”文字与图标“总的文字；TextPosition组决定了，文本以图标为中心的方置。
                //以下是图标在右，文标在左，整体居中对齐
                text=SwingUtilities.layoutCompoundLabel(
                    c, fm, smText, smIcon,
                    SwingConstants.CENTER, SwingConstants.CENTER,
                    SwingConstants.CENTER, SwingConstants.CENTER,
                    viewRect, iconRect, textRect,
                0);                
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, it);
                g2.setComposite(alpha2);
                if (smIcon !=null) smIcon.paintIcon(c, g, iconRect.x, iconRect.y);
                alpha2 = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, tt);
                g2.setComposite(alpha2);

                g2.setColor(c.getForeground());
                SwingUtilities2.drawStringUnderlineCharAt(c, g,text, -1,
                    textRect.x ,textRect.y + fm.getAscent() 
                );      
            break;
              
        }
             
             
    }
    
    public static void paintBackgroud(Graphics g, JComponent c,Data data){
        data=data==null?getData(c):data;
        if (data==null) return;
        if (data.subjectEntity ==null) return;
        SubjectEntity sm=data.subjectEntity;
               
        if (sm.getPaint() !=null) try {
           
            sm.getPaint().paint(g, c);
        } catch (Exception ex) {
            Logger.getLogger(UIEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        Graphics2D  g2 =  (Graphics2D)g;  
        float bt=sm.getBackgroundTransparence();
        if (data.isBright) {
            bt=0.3f;          
            Composite alpha2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bt);
            g2.setComposite(alpha2);
            g2.setColor(c.getBackground());
            g2.fillRect(0, 0, c.getWidth(), c.getHeight());
        }
//        g2.dispose();     
    }
    public static void paintBackgroudImage(Graphics g, JComponent c,Data data){
        data=data==null?getData(c):data;
        if (data==null) return;
        if (data.subjectEntity ==null) return;
        SubjectEntity sm=data.subjectEntity;
        if (sm.getBackgroundIcon()==null) return;
        Image bi=sm.getBackgroundIcon().getImage();
        
        Graphics2D  g2 =  (Graphics2D)g;  
        float bt=sm.getBackgroundTransparence();
        if (data.isBright) 
            bt=0.8f; 
        Composite alpha2=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, bt);
        g2.setComposite(alpha2);
        int x=c.getX();
        int y=c.getY();
        int w=c.getWidth();
        int h=c.getHeight(); 
        
        g2.drawImage(bi,2,2,w-2,h-2, c);
//        g2.dispose();
//        sm.getBackgroundIcon().paintIcon(c, g, 5,5);
        
    } 
   

   
}
