/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.model.config;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import org.fswingui.plaf.Style;
import org.fswingui.plaf.Subject;
import org.fswingui.plaf.SubjectCriterion;
import org.fswingui.plaf.SubjectEntity;
import org.fswingui.plaf.config.AbstractFSUIConfig;
import org.fswingui.plaf.config.FSUIConfig;
import org.fswingui.plaf.config.PaintConfig;
import org.fswingui.plaf.config.ParameterConfig;
import org.fswingui.plaf.config.SubjectEntityConfig;
import org.fswingui.plaf.tools.paint.AbstractPaint;
import org.fswingui.plaf.tools.paint.BaseParameterCoding;
import org.fswingui.plaf.tools.paint.Parameter;
import org.fswingui.tools.frame.MainFrame;
import org.fswingui.tools.frame.model.BaseData;
import org.fswingui.tools.frame.model.CurrentData;
import org.fswingui.tools.frame.model.DataBus;
import org.fswingui.tools.frame.model.MapPropertys;
import org.fswingui.tools.frame.model.MapPropertys.PropertyUnit;
import org.fswingui.tools.frame.model.PropertyCriterion;
import org.fswingui.tools.frame.part.base.StylePanel;
import org.fswingui.tools.frame.part.base.pub.TreeData;
import org.fswingui.tools.frame.part.base.stylepanel.StylePopMenu;
import org.fswingui.tools.frame.part.extra.StylePanelExtra;
import org.fswingui.tools.gui.component.DivPanel;
import org.fswingui.tools.gui.component.extra.BaseDiv;
import org.fswingui.tools.gui.component.extra.ComDiv;
import org.fswingui.utility.MapSort;
import org.fswingui.utility.Utility;

/**
 *
 * @author cloud
 */
public abstract class AbstractFGuiConfig implements FGuiConfig{
    public final static String SUBJECT_STORE="subjectStore";
    public final static String PAINTS="paints";
    public final static String SUBJECT_INDEX="subjectIndex";
    public final static String STYLE_MAIN_COLOR="styleMainColor";
     
   
    private JFileChooser fileChooser;
     //全局变量，监时中转用
    private transient Stack<BaseDataConfig> stackbdcfg=new Stack();
    
    protected DataBus dataBus;
    protected CurrentDataCofig cdCfg;
    
     //主题索引。Key为组件名，Value为主题ID
    protected   ConcurrentHashMap<String,String> subjectIndex=new ConcurrentHashMap() ;
    //主题仓库。Key为 风格ID +主题ID，值为实题实体
    protected  ConcurrentHashMap<String,SubjectEntity> subjectStore=new ConcurrentHashMap() ;
    //风格主色 集。Key为 风格ID +主题ID，值为主色
    protected   ConcurrentHashMap<String,Color> styleMainColor=new ConcurrentHashMap() ;
    
    protected  Map <String,SubjectEntityConfig> subjectEntityConfigs=new LinkedHashMap();
    protected  Map<String,AbstractPaint> paints=new LinkedHashMap();
    protected  Map<String,PaintConfig> paintConfigs=new LinkedHashMap();
    

    public AbstractFGuiConfig(DataBus databus){
        this.dataBus=databus;
    }
    /**
     *主题索引。Key为组件名，Value为主题ID 
     */  
    public  ConcurrentHashMap<String, String> getSubjectIndex() {
        return subjectIndex;
    }
    /**
     *主题索引。Key为组件名，Value为主题ID 
     */    
    public  void setSubjectIndex(ConcurrentHashMap<String, String> subjectIndex) {
        this.subjectIndex = subjectIndex;
    }
    
    /**
     *主题仓库。Key为 风格ID +主题ID，值为实题实体
     */  
    public  ConcurrentHashMap<String, SubjectEntity> getSubjectStore() {
        return subjectStore;
    }
    /**
     *主题仓库。Key为 风格ID +主题ID，值为实题实体
     */  
    public  void setSubjectStore(ConcurrentHashMap<String, SubjectEntity> subjectStore) {
        this.subjectStore = subjectStore;
    }

    //风格主色 集。Key为 风格ID ，值为主色
    public  ConcurrentHashMap<String, Color> getStyleMainColor() {
        return styleMainColor;
    }

    //风格主色 集。Key为 风格ID ，值为主色
    public  void setStyleMainColor(ConcurrentHashMap<String, Color> styleMainColor) {
        this.styleMainColor = styleMainColor;
    }

    
    
    public Map<String, SubjectEntityConfig> getSubjectEntityConfigs() {
        return subjectEntityConfigs;
    }

    public void setSubjectEntityConfigs(Map<String, SubjectEntityConfig> subjectEntityConfigs) {
        this.subjectEntityConfigs = subjectEntityConfigs;
    }

    public Map<String, AbstractPaint> getPaints() {
        return paints;
    }

    public void setPaints(Map<String, AbstractPaint> paints) {
        this.paints = paints;
    }

    public Map<String, PaintConfig> getPaintConfigs() {
        return paintConfigs;
    }

    public void setPaintConfigs(Map<String, PaintConfig> paintConfigs) {
        this.paintConfigs = paintConfigs;
    }
 
    public void setDataBus(DataBus databus){
        this.dataBus=dataBus;

    }
     
    @Override
   public boolean readConfig(){
       return readConfig(FGuiConfig.configName);
   }
    @Override
    public boolean writeConfig(){
        
        File f=new File(FGuiConfig.configName);
        if(!f.exists()){
            f.mkdirs();
        }         
        return writeConfig(FGuiConfig.configName);
    }
    
    @Override
    public boolean readConfig(String filePathName){
        if (filePathName==null ||  "".equals(filePathName)) return false;
        File f=new File(filePathName);
        if(!f.exists()) {
            if (fileChooser==null) fileChooser=new JFileChooser();
            FileNameExtensionFilter  ff=
                new FileNameExtensionFilter ("json file",
                "json","js" 
            );
            fileChooser.addChoosableFileFilter(ff); 
            int returnVal = fileChooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                f=fileChooser.getSelectedFile();   
                filePathName=f.getName();
            }
        }
        CurrentData cd=dataBus.getCurrentData();
        cd.getAllData().clear();
        CurrentData.getPaints().getItems().clear();
        CurrentData.getSubjectEntitys().clear();
        CurrentData.getRootBaseDatas().clear();
        
        JPanel centerPanel=(JPanel)dataBus.getGuiParts().
                get(MainFrame.CENTER_PANEL);
        centerPanel.removeAll();
        
        return readConfigImpl(filePathName);
    }
    @Override
    public boolean writeConfig(String filePathName){
        if (filePathName==null ||  "".equals(filePathName)) return false;
        File f=new File(filePathName);
        if(f.exists()) f.delete();
        return writeConfigImpl(filePathName);
    }
   
    protected abstract boolean readConfigImpl(String filePathName);

    protected abstract boolean writeConfigImpl(String filePathName);
    
    //生成风格集树形组件的配置
    public void  styleTreeToConfig(){
        StylePanel  treePanel= (StylePanel) dataBus.getGuiParts().
                get(MainFrame.STYLE_PANEL);
        if (treePanel==null) return ;
        JTree tree=treePanel.getTree();
        if (tree==null) return ;
        DefaultMutableTreeNode root=(DefaultMutableTreeNode) tree.getModel().getRoot();
        if(root==null || root.getChildCount()<=0) return ;
        Map<String,StyleTreeConfig> stylecfg=  cdCfg.getStyleTreeConfigs();
        for(int i=0;i<root.getChildCount();i++){
            StyleTreeConfig scfg=new StyleTreeConfig();
            
            DefaultMutableTreeNode snode=(DefaultMutableTreeNode) root.getChildAt(i);
            TreeData td=(TreeData) snode.getUserObject();
            Style s=(Style) td.userData;
            scfg.setId(s.getId());
            if(s.getMainColor()!=null){
                scfg.setMainColor(Integer.toHexString(s.getMainColor().getRGB()));
            } else {
                scfg.setMainColor("");
            }
            
            scfg.setTitle(s.getTitle());
            stylecfg.put(scfg.getId(), scfg);
            
            if(snode.getChildCount()<=0) continue;
            for(int j=0;j<snode.getChildCount();j++){
                DefaultMutableTreeNode subnode=
                        (DefaultMutableTreeNode) snode.getChildAt(j);
                TreeData subtd=(TreeData) subnode.getUserObject();
                Subject sub=(Subject) subtd.userData;
                SubjectTreeConfig subcfg=new SubjectTreeConfig();
                subcfg.setId(sub.getId());
                subcfg.setTitle(sub.getTitle());
                scfg.getSubjects().put(subcfg.getId(), subcfg);
                DefaultMutableTreeNode node1=
                        (DefaultMutableTreeNode) subnode.getChildAt(0);
                TreeData td1=(TreeData) node1.getUserObject();
                DefaultMutableTreeNode subenodeds=null;
                DefaultMutableTreeNode cnodes=null;
                DefaultMutableTreeNode tempNode=null;
                
                if(subnode.getChildCount()>=2) {
                    tempNode=(DefaultMutableTreeNode) subnode.getChildAt(1);
                }
                if(td1.id.equals(StylePopMenu.SUBJECT_NODE)){
                    subenodeds=node1;
                    cnodes=tempNode;
                } else {
                    cnodes=node1;
                    subenodeds=tempNode;
                }
                if(subenodeds!=null ){
                    for (int k=0;k<subenodeds.getChildCount();k++){
                        DefaultMutableTreeNode subenode=
                            (DefaultMutableTreeNode) subenodeds.getChildAt(k);
                        TreeData subetd=(TreeData) subenode.getUserObject();
                        NodeCofig ncfg=new NodeCofig();
                        ncfg.setId(subetd.id);
                        ncfg.setTitle(subetd.title);
                        SubjectEntity sube=(SubjectEntity) subetd.userData;
                        ncfg.setValue(sube.getSubjectID());
                        subcfg.getSubjectEnity().put(subetd.id, ncfg);
                    }
                }
                if(cnodes!=null){
                    for (int k=0;k<cnodes.getChildCount();k++){
                        DefaultMutableTreeNode cnode=
                            (DefaultMutableTreeNode) cnodes.getChildAt(k);
                        TreeData ctd=(TreeData) cnode.getUserObject();
                        NodeCofig ncfg=new NodeCofig();
                        ncfg.setId(ctd.id);
                        ncfg.setTitle(ctd.title);
                        String c= (String) ctd.userData;
                        ncfg.setValue(c);
                        subcfg.getComponents().add(ncfg);
                    }
                }
                
                
                
            }
        }
        
       
    }
    public BaseDataConfig baseDataToConfig(BaseData bd){
        if (bd==null) return null;
        BaseDataConfig bdCfg=new BaseDataConfig();        
        bdCfg.setId(bd.getId());
        bdCfg.setDivClassName(bd.getDiv().getClass().getName());
        bdCfg.setViewClassName(bd.getDivView().getClass().getName());
        if(bd.getChildren()!=null){
            bdCfg.setChildren(new LinkedList<String>());
            bdCfg.setZorders(new LinkedHashMap());
            for(Map.Entry<String,BaseData> en:bd.getChildren().entrySet()){
                if(en.getValue()!=null && en.getValue().getId()!=null){
                    bdCfg.getChildren().add(en.getValue().getId());
                    int z=bd.getDivView().getComponentZOrder(en.getValue().getDivView());
                    bdCfg.getZorders().put(String.valueOf(z), en.getValue().getId());
                }                
            }
        }
        bdCfg.setContained(bd.isContained());
        bdCfg.setLeaf(bd.isLeaf());
        if(bd.getParent()!=null){
            bdCfg.setParent(bd.getParent().getId());
        }
        int x=bd.getDivView().getX();
        int y=bd.getDivView().getY();
        bd.getMapPropertys().getProperty(PropertyCriterion.X).value=x;
        bd.getMapPropertys().getProperty(PropertyCriterion.X).strValue=String.valueOf(x);
        bd.getMapPropertys().getProperty(PropertyCriterion.Y).value=y;
        bd.getMapPropertys().getProperty(PropertyCriterion.Y).strValue=String.valueOf(y);
        
        bdCfg.setPropertys(mapPropertysToConfig(bd.getMapPropertys()));
        bdCfg.setZorder(bd.getZorder());
        
         
        
        return bdCfg;
        
    }
    
    public Map<String,PropertyUnitConfig> mapPropertysToConfig(
            MapPropertys  mapPropertys)
    {
         if (mapPropertys==null) return null;
         Map<String,PropertyUnitConfig> mapCfgs=new LinkedHashMap();
         ParameterConfig pc=new ParameterConfig();
         for(Map.Entry<String,PropertyUnit> en:
                 mapPropertys.entrySet())
         {             PropertyUnit pu=en.getValue();
             PropertyUnitConfig pucfg=new PropertyUnitConfig();
             pucfg.name=pu.name;
             pucfg.index=pu.index;
             pucfg.strValue=pu.strValue;
             pucfg.title=pu.title;
             pucfg.type=pu.type.getName();
             
             pucfg.value=BaseClassStringChange.valueToString(pu.value);
             mapCfgs.put(en.getKey(), pucfg);
         }
         return mapCfgs;
    } 
    
    public CurrentDataCofig currentDataToConfig(CurrentData cd){
        if (cd==null)  return null;   
        JPanel centerPanel=(JPanel)dataBus.getGuiParts().
                get(MainFrame.CENTER_PANEL);
        
        CurrentDataCofig cfg=new CurrentDataCofig();
        String tempstr ="";
        if(cd.getCurrentMainColor()!=null) {
            tempstr= Integer.toHexString(cd.getCurrentMainColor().getRGB());
        }
        cfg.setCurrentMainColor(tempstr);
        tempstr=cd.getCurrentStyle();
        cfg.setCurrentStyle(tempstr);
                
        if(cd.getPaints()!=null && cd.getPaints().getItems()!=null){
            for(Map.Entry<String,AbstractPaint>en:
                    cd.getPaints().getItems().entrySet())
            {
                AbstractPaint  paint=en.getValue();  
                cfg.getPanit().put(PAINTS, paintToConfig(paint));
            }
        }
        
        if(CurrentData.getSubjectEntitys()!=null){
            for(Map.Entry<String,SubjectEntity>en:
                    CurrentData.getSubjectEntitys().entrySet())
            {
                SubjectEntity  sube=en.getValue();  
                cfg.getSubjectEntitys().put(en.getKey(),
                        subjectEntityToConfig(en.getKey(),sube));
            }
        }
        
        if(cd.getAllData()!=null){
             
            for(Map.Entry<String,BaseData>en: cd.getAllData().entrySet())
            {
                
                BaseData  bd=en.getValue();  
                BaseDataConfig bdcfg=baseDataToConfig(bd);
                int z=centerPanel.getComponentZOrder(bd.getDivView());
                cfg.getZorders().put(z, bdcfg.getId());
                cfg.getAllData().put(en.getKey(), bdcfg);
                
               
            }
        }
        if(CurrentData.getRootBaseDatas()!=null){
            for(Map.Entry<String,BaseData>en: 
                    CurrentData.getRootBaseDatas().entrySet())
            {
                BaseData  bd=en.getValue();  
                cfg.getRootBaseDatas().put(en.getKey(), bd.getId());
            }
        }
        cdCfg=cfg;    
        styleTreeToConfig();
        
        return cfg;        
    }
    
    public SubjectEntityConfig subjectEntityToConfig(String key,SubjectEntity se){
        SubjectEntityConfig subeCfg=new SubjectEntityConfig();
        subeCfg.setSubjectID(key);
        subeCfg.setArrangement(se.getArrangement());
        if(se.getBackgroundIcon()==null){
            subeCfg.setBackgroundIcon("");
        } else {
            subeCfg.setBackgroundIcon(se.getBackgroundIcon().getDescription());
        }        
        subeCfg.setBackgroundTransparence(se.getBackgroundTransparence());
        subeCfg.setDisableIcon(se.isDisableIcon());
        subeCfg.setDisableText(se.isDisableText());  
        if(se.getFont()==null){
            subeCfg.setFont("");
        } else {
            subeCfg.setFont(se.getFont().toString());
        }
        if(se.getFontColor()==null){
            subeCfg.setFontColor("");
        } else {
            subeCfg.setFontColor(Integer.toHexString(se.getFontColor().getRGB()));
        }
        
        if(se.getIcon()==null){
            subeCfg.setIcon("");
        } else {
            subeCfg.setIcon(se.getIcon().getDescription());
        }
        subeCfg.setIconTransparence(se.getIconTransparence());
        subeCfg.setOpaque(se.isOpaque());
        if(se.getPaint()==null){
            subeCfg.setPaint("");
        } else {
            AbstractPaint paint=se.getPaint();
            subeCfg.setPaint(paint.getPaintID());            
        }
        
        subeCfg.setText(se.getText());
        subeCfg.setTextTransparence(se.getTextTransparence());
        
        if (!se.isEmpty()){
            LinkedHashMap<String,SubjectEntityConfig> rl=new LinkedHashMap();
            subeCfg.setRelateSubjectEntity(rl);
            for(Map.Entry<String,SubjectEntity> ent:se.entrySet()){
                SubjectEntityConfig sec1=subjectEntityToConfig(ent.getValue().getSubjectID(),ent.getValue());
                rl.put(ent.getKey(), sec1);
            }
        }
       
        return subeCfg;
    }
     
    public PaintConfig paintToConfig(AbstractPaint paint){
        PaintConfig paintCfg=new PaintConfig();            

        paintCfg.setPaintID(paint.getPaintID());
        paintCfg.setType(paint.getClass().getName());
        paintCfg.setDescribe(paint.getDescribe());
        if (paintCfg.getArgs()==null) paintCfg.setArgs(new LinkedHashMap());
        for (Map.Entry<String,Parameter> en1:paint.getArgs().entrySet()){
            Parameter p=en1.getValue();
            ParameterConfig pc=new ParameterConfig();
            pc.setType(p.type.getName());
            pc.setBaseParameterCodingType(p.coding.getClass().getName());
            pc.setValue(p.coding.valueToString(p.value));
            pc.setDefaultValue(p.coding.valueToString(p.defaultValue));
            paintCfg.getArgs().put(en1.getKey(), pc);
        }
        return paintCfg;
    } 
   
    /**
     * 把cdCfg中的配置，转换后所得到对象存放到cd中。
     * @param cdCfg ------- 
     * @return  CurrentData  -----当前dataBus 中的CurrentData实例。<br>
     *          注意这是引用，非克隆
     */
    public CurrentData  cofingToCurrentData(CurrentDataCofig cdCfg){
        CurrentData cd=dataBus.getCurrentData();
        this.cdCfg=cdCfg;
        
        Color color=Utility.fromHexString(cdCfg.getCurrentMainColor());
        cd.setCurrentMainColor(color);
        cd.setCurrentStyle(cdCfg.getCurrentStyle());
        
        
        if(cdCfg.getPanit()!=null){
            for(Map.Entry<String,PaintConfig> en:cdCfg.getPanit().entrySet()){
                AbstractPaint paint=configToPaint(en.getValue());
                CurrentData.getPaints().putPaint(paint);
            }
        }
        
        if(cdCfg.getSubjectEntitys()!=null){
            for(Map.Entry<String,SubjectEntityConfig> en:
                    cdCfg.getSubjectEntitys().entrySet())
            {
                SubjectEntity  sube=configToSubjectEntity(en.getValue());
                CurrentData.getSubjectEntitys().put(en.getKey(), sube);
            }
        }
        
        
           
        
        if(cdCfg.getAllData()!=null){
            stackbdcfg.clear();
            for(Map.Entry<String,BaseDataConfig> en:
                    cdCfg.getAllData().entrySet())
            {
                BaseDataConfig bdcfg=en.getValue();
                BaseData  bd=configToBaseData(bdcfg);
                stackbdcfg.push(bdcfg);               
            }  
            
            
            compiBaseDataSort();
        }  
        

        if(cdCfg.getRootBaseDatas()!=null){
            for(Map.Entry<String,String> en:
                    cdCfg.getRootBaseDatas().entrySet())
            {
                 
                BaseData  bd=cd.getAllData().get(en.getValue());
                CurrentData.getRootBaseDatas().put(en.getKey(), bd);            
            }
        }  
        
        configToStyleTree();
        JPanel centerPanel=(JPanel)dataBus.getGuiParts().
                get(MainFrame.CENTER_PANEL);
        centerPanel.revalidate();
        centerPanel.repaint();
        
        return cd;
    }
    
    public void configToStyleTree(){
        StylePanel  treePanel= (StylePanel) dataBus.getGuiParts().
                get(MainFrame.STYLE_PANEL);
        if (treePanel==null) return ;
        
        JTree tree=treePanel.getTree();
        if (tree==null) return ;
        DefaultMutableTreeNode root=(DefaultMutableTreeNode) tree.getModel().getRoot();
        if(root==null ) return ;
        root.removeAllChildren();
        treePanel.reSetCellEditor();
        
      
        
        
        Map<String,StyleTreeConfig> stylecfgs=  cdCfg.getStyleTreeConfigs();
        if (stylecfgs==null || stylecfgs.isEmpty()) return;
        for(Map.Entry<String,StyleTreeConfig> en:stylecfgs.entrySet())
        {//生成风格节点
            StyleTreeConfig scfg=en.getValue();
            DefaultMutableTreeNode snode=new DefaultMutableTreeNode();
            
            Style style=new Style();
            style.setId(scfg.getId());
            style.setTitle(scfg.getTitle());               
            style.setMainColor(Utility.fromHexString(scfg.getMainColor()));            
            CurrentData.getStyles().put(style.getId(), style); 
            TreeData data=new TreeData(style.getId(),style.getTitle(),style);
            snode.setUserObject(data);         
            root.add(snode);
            if(scfg.getSubjects()==null || scfg.getSubjects().isEmpty()) continue;
             
            for(Map.Entry<String,SubjectTreeConfig> 
                    en1:scfg.getSubjects().entrySet())
            {//生成主题索引节点
                SubjectTreeConfig subcfg=en1.getValue();
                DefaultMutableTreeNode subnode=new DefaultMutableTreeNode();
                Subject sub=new Subject();
                
                sub.setId(subcfg.getId()); 
                sub.setTitle(subcfg.getTitle());
                if(SubjectCriterion.containsKeySub(this, sub.getId())){
                    SubjectCriterion.putSub(sub.getId(), sub);
                }                
                data=new TreeData(sub.getId(),sub.getTitle(),sub);
                subnode.setUserObject(data);      
                snode.add(subnode);
                
                if(subcfg.getSubjectEnity() !=null && 
                        !subcfg.getSubjectEnity().isEmpty())
                {//生成实体包节点
                    DefaultMutableTreeNode subesnode=new DefaultMutableTreeNode(); 
                    data=new TreeData(StylePopMenu.SUBJECT_NODE,"实体包",
                            StylePopMenu.SUBJECT_NODE);
                    subesnode.setUserObject(data);         
                    subnode.add(subesnode);
                    for(Map.Entry<String, NodeCofig> en2:
                            subcfg.getSubjectEnity().entrySet())
                    {//增加实体节点
                        NodeCofig ncfg=en2.getValue();
                        DefaultMutableTreeNode subenode=new DefaultMutableTreeNode(); 
                       
                       

                        SubjectEntity se=CurrentData.getSubjectEntitys().get(ncfg.getValue());
                        if(se==null) se=new SubjectEntity();
                        
                        sub.put(ncfg.getId(), se);
                        style.put(sub.getId(), sub);
                        data=new TreeData(ncfg.getId(),ncfg.getTitle(),se);
                        if( !SubjectCriterion.containsKeySE(this, ncfg.getId())){
                            SubjectCriterion.putSE(ncfg.getId(), -1);
                        }
                          
                        subenode.setUserObject(data);
                        subesnode.add(subenode);
                    }                    

                }
                
                DefaultMutableTreeNode csnode=new DefaultMutableTreeNode(); 
                Vector<String> v=new  Vector();
                data=new TreeData(StylePopMenu.COMPONENT_NODE,"所描绘组件", v);
                csnode.setUserObject(data);         
                subnode.add(csnode);
                if(subcfg.getComponents() !=null && 
                        !subcfg.getComponents().isEmpty())
                {//生成组件节点
                             
                
                    for(NodeCofig ncfg:
                            subcfg.getComponents())
                    {//增加组件节点
                        DefaultMutableTreeNode cnode=new DefaultMutableTreeNode();                         
                        if(v==null ) {
                            v=new Vector<String>();
                        }
                        String newS=ncfg.getValue();
                        v.addElement(newS);
                          
                        data =new TreeData();
                        data.id=ncfg.getId();
                        data.title=ncfg.getTitle();
                        data.userData=ncfg.getValue();
                        cnode.setUserObject(data);
                        csnode.add(cnode); 
                    }                    

                }
                
            }
            
        }
        
        tree.updateUI();
    }
    
    public void compiBaseDataSort(){
        if (cdCfg==null ||  cdCfg.getAllData()==null) return;
        Map<String,BaseData> allData=dataBus.getAllData();
        CurrentData cd=dataBus.getCurrentData();
        JPanel centerPanel=(JPanel)dataBus.getGuiParts().
                get(MainFrame.CENTER_PANEL);
        
        if(cdCfg.getZorders()!=null)
        {//处理根结点排序
            Map<Object,Object> map= MapSort.sortMapByKey(cdCfg.getZorders(), false);
            
            for   (Iterator it=map.keySet().iterator();it.hasNext();) { 
                Integer key=(Integer) it.next(); 
                String id=(String) map.get(key);
                
                BaseDataConfig bc= cdCfg.getAllData().get(id);
                BaseData bd=allData.get(bc.getId());
                centerPanel.add(bd.getDivView()); 
            } 
        }
        
        while (!stackbdcfg.empty()){//处理非根结点排序
            BaseDataConfig bc=stackbdcfg.pop();
            BaseData bd=allData.get(bc.getId());
           
                
            if(bc.getParent()==null || bc.getParent().equals("") 
                || bc.getParent().equals(bc.getId())  )
            {//没有父结点
                centerPanel.add(bd.getDivView());                
            }
                
            if(bc.getZorders()!=null && !bc.getZorders().isEmpty() )
            {//是结点
                for(Map.Entry<String,String> en:bc.getZorders().entrySet())
                {
                    BaseData bd1=allData.get(en.getValue());
                    bd.getDivView().add(bd1.getDivView());
                    bd1.getDiv().removeFocusAndDropMouseAdapter();
                    bd1.getDiv().removeScaleMouseAdapte();
                    bd1.getDiv().addUpDispatchMouseEvent(); 
                    if(bd.getChildren()==null) bd.setChildren(new LinkedHashMap());
                    bd.getChildren().put(bd1.getId(), bd1);
                    allData.get(bd1.getId()).setParent(bd);
                    
                }
            } 
        }
 
        
    }
    public BaseData configToBaseData(BaseDataConfig bc){
        if (bc==null || cdCfg==null) return null;
        Map<String,BaseData> allData=dataBus.getAllData();
        CurrentData cd=dataBus.getCurrentData();
        BaseData bd=new BaseData(cd);
        bd.setId(bc.getId());
        bd.setLeaf(bc.isLeaf());
        bd.setContained(bc.isContained());   
        MapPropertys mps=configToMapPropertys(bc.getPropertys());
        int x=(int) mps.getProperty(PropertyCriterion.X).value;
        int y=(int) mps.getProperty(PropertyCriterion.Y).value;
        int w=(int) mps.getProperty(PropertyCriterion.WIDTH).value;
        int h=(int) mps.getProperty(PropertyCriterion.HEIGHT).value;
        bd.setMapPropertys(mps);       
        bd.setZorder(bc.getZorder(), false);
        JPanel centerPanel=(JPanel)dataBus.getGuiParts().
                get(MainFrame.CENTER_PANEL);
        
        if(bc.getChildren()==null){
            BaseDiv fp=new BaseDiv(cd,centerPanel,bd.getId()  );      
            fp.init();
            fp.getView().setBounds(x, y, w, h);  
            fp.getView().setName(bd.getId());
            fp.getView().setOpaque((Boolean)
                    mps.getProperty(PropertyCriterion.OPAQUE).value);
            allData.put(bd.getId(), bd);     
            fp.bind();
        } else {
            ComDiv comdiv=new ComDiv(cd,centerPanel,bd.getId());           
            comdiv.getView().setBounds(x, y, w, h);
            comdiv.getView().setName(bd.getId());            
            comdiv.init();
            allData.put(bd.getId(), bd);   
            comdiv.bind();           
          
                
        }
        return bd;
    }
   
    public  MapPropertys configToMapPropertys(Map<String,PropertyUnitConfig> map )
    {
       if(map==null || map.size()<=00) return null;
       MapPropertys mapp=new MapPropertys();
       for(Map.Entry<String,PropertyUnitConfig> en:map.entrySet()){
           PropertyUnitConfig pc=en.getValue();
           PropertyUnit pu=mapp.put(en.getKey());
           pu.index=pc.index;
           pu.title=pc.title;
           pu.strValue=pc.strValue;
           try {
               pu.type=Class.forName(pc.type);
           } catch (ClassNotFoundException ex) {
               Logger.getLogger(AbstractFGuiConfig.class.getName()).log(Level.SEVERE, null, ex);
           }
           
           pu.value=BaseClassStringChange.valueFromString(pc.type, pc.value);
           pu.disable=pc.disable;        
           
             
       }
           
       return mapp;
   }
   
    public AbstractPaint configToPaint(PaintConfig paintCfg) {
        if (paintCfg==null) return null;
        AbstractPaint paint=null;
        if(paintCfg.getType()!=null && ! paintCfg.getType().equals("")){
            Class cls;
            try {
                cls = Class.forName(paintCfg.getType());
                try {
                    paint=(AbstractPaint) cls.newInstance();
                } catch (InstantiationException | IllegalAccessException ex) {
                    Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            return null;
        }
        paint.setPaintID(paintCfg.getPaintID());
        paint.setDescribe(paint.getDescribe());
        if(paintCfg.getArgs()!=null || !paintCfg.getArgs().isEmpty()){
            LinkedHashMap<String, Parameter> args =new LinkedHashMap();
            paint.setArgs(args);
            for(Map.Entry<String,ParameterConfig> en:
                    paintCfg.getArgs().entrySet())
            {
                Parameter par=new Parameter();
                ParameterConfig parCfg1=en.getValue();
                BaseParameterCoding bcode=null;
                if(parCfg1.getBaseParameterCodingType()==null|| 
                        parCfg1.getBaseParameterCodingType().equals(""))
                {
                    bcode=new BaseParameterCoding(par);
                } else {
                    Class cls=null;
                    try {
                        cls = Class.forName(parCfg1.getBaseParameterCodingType());
                        try {
                            bcode=(BaseParameterCoding) cls.newInstance();
                            bcode.setParameter(par);
                        } catch (InstantiationException | IllegalAccessException ex) {
                            Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   
                        
                    
                }
                try {
                    par.type=Class.forName(  parCfg1.getType()) ;
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(AbstractFSUIConfig.class.getName()).log(Level.SEVERE, null, ex);
                }
                par.coding=bcode;
                
                par.value=bcode.valueFromString(parCfg1.getValue());
                par.defaultValue=bcode.valueFromString(parCfg1.getDefaultValue());
                if(parCfg1.getAllowNull()==null || parCfg1.getAllowNull().equals("")){
                     par.allowNull=false;
                } else {
                    par.allowNull=Boolean.valueOf(parCfg1.getAllowNull());
                }
               args.put(en.getKey(), par);
                
            }
        }
        return paint;
        
    }
    
    public SubjectEntity   configToSubjectEntity   (SubjectEntityConfig subeCfg){
        if (subeCfg==null) return null;
        SubjectEntity se=new SubjectEntity();
        se.setArrangement(subeCfg.getArrangement());
        if(subeCfg.getBackgroundIcon()!=null && !subeCfg.getBackgroundIcon().equals("")){
            ImageIcon icon= new ImageIcon(subeCfg.getBackgroundIcon());   
            se.setBackgroundIcon(icon);
        }else {
            se.setBackgroundIcon(null);
        } 
        se.setBackgroundTransparence(subeCfg.getBackgroundTransparence());
        se.setDisableIcon(subeCfg.isDisableIcon());
        se.setDisableText(subeCfg.isDisableText());
        if(subeCfg.getFont()!=null && !subeCfg.getFont().equals("")){
            Font font=Font.decode(subeCfg.getFont());
            se.setFont(font);
        }else {
            se.setFont(null);
        } 
        if(subeCfg.getFontColor()!=null && !subeCfg.getFontColor().equals("")){
            Color c=Utility.fromHexString(subeCfg.getFontColor());
            se.setFontColor(c);
        }else {
            se.setFontColor(null);
        } 
        if(subeCfg.getIcon()!=null && !subeCfg.getIcon().equals("")){
            ImageIcon icon= new ImageIcon(subeCfg.getIcon());   
            se.setIcon(icon);
        }else {
            se.setIcon(null);
        } 
        se.setIconTransparence(subeCfg.getIconTransparence());
        se.setOpaque(subeCfg.isOpaque());
        
         
        se.setPaint(paints.get(subeCfg.getPaint()));//==================================================
        
        se.setSubjectID(subeCfg.getSubjectID());
        se.setText(subeCfg.getText());
        se.setTextTransparence(subeCfg.getTextTransparence());
        
        if(subeCfg.getRelateSubjectEntity()!=null){
            for(Map.Entry<String,SubjectEntityConfig> en:
                    subeCfg.getRelateSubjectEntity().entrySet())
            {
                 SubjectEntity se1=configToSubjectEntity(en.getValue());
                 se.put(en.getKey(), se1);
            }
        }
       
        
        
         
        return se;
        
    }
    
   
}
