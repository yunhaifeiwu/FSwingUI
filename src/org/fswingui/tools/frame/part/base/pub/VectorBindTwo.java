/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.fswingui.tools.frame.part.base.pub;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Vector;

/**
 *有两个vector，这两个vecto都有关联属性，以作两个需要相互发送信息的组件，通过
 * 本抽象而相互耦合。
 */
public class VectorBindTwo<V>  {
    private Vector<V> vectorA=new Vector();
    private Vector<V> vectorB=new Vector();
    
    //<editor-fold   desc="事件"> 
    private PropertyChangeSupport vectorAChanges = new PropertyChangeSupport(this);
    private PropertyChangeSupport vectorBChanges = new PropertyChangeSupport(this);
   
    /**
    * 注册mapAChanges属性事件
    */
    public void addVectorAChangesListener (PropertyChangeListener listener) {
        vectorAChanges.addPropertyChangeListener(listener);
    }
    /**
    * 注销mapAChanges属性事件
    */
    public void removeVectorAChangesListene (PropertyChangeListener listener) {
        vectorAChanges.removePropertyChangeListener (listener);        
    }
    
    /**
    * 注册mapBChanges属性事件
    */
    public void addVectorBChangesListener (PropertyChangeListener listener) {
        vectorBChanges.addPropertyChangeListener(listener);
    }
    /**
    * 注销mapBChanges属性事件
    */
    public void removeVectorBChangesListene (PropertyChangeListener listener) {
        vectorBChanges.removePropertyChangeListener (listener);        
    }
    
    //</editor-fold>
    
    
    //<editor-fold desc="封装方法 vectorA">
    public synchronized void addElementA(V obj) {
       
        PropertyChangeEvent e=new PropertyChangeEvent(this, "add", null, obj);
        vectorAChanges.firePropertyChange(e);     
        vectorA.addElement(obj);
    }
    
    public synchronized void insertElementAtA(V obj, int index) {
        PropertyChangeEvent e=new PropertyChangeEvent(this, "insert", null, obj);
        vectorAChanges.firePropertyChange(e);     
        vectorA.insertElementAt(obj, index);
    }
    
    public synchronized void setElementAtA(V obj, int index) {
        V old=vectorA.elementAt(index);        
        PropertyChangeEvent e=new PropertyChangeEvent(this, "set", old, obj);
        vectorAChanges.firePropertyChange(e);     
        vectorA.setElementAt(obj, index);
    }
    
    public synchronized void removeAllElementsA() {
        PropertyChangeEvent e=new PropertyChangeEvent(this, "removeAll", vectorA.clone(), null);
        vectorAChanges.firePropertyChange(e);    
        vectorA.removeAllElements();
    }
 
    public synchronized void removeElementAtA(int index) {
        V old=vectorA.elementAt(index);
        PropertyChangeEvent e=new PropertyChangeEvent(this, "remove", old, null);
        vectorAChanges.firePropertyChange(e);    
        vectorA.removeElementAt(index);
    }
  
 
    public synchronized boolean removeElementA(Object obj) { 
        PropertyChangeEvent e=new PropertyChangeEvent(this, "remove", obj, null);
        vectorAChanges.firePropertyChange(e);    
        return vectorA.removeElement(obj);
    }
    
    public synchronized V elementAtA(int index) {
        return vectorA.elementAt(index);
    }

 
    public Enumeration<V> elementsA() {
        
        return vectorA.elements();
    }

    
 
    public synchronized V firstElementA() {
        return vectorA.firstElement();
    }
    
    public synchronized V lastElementA() {
        return vectorA.lastElement();
    }
    
    public synchronized int capacityA() {
        return vectorA.capacity();
    }


  
    public boolean containsA(Object o) {
        return vectorA.contains(o);
    }
    
   
 
    public synchronized boolean containsAllA(Collection<?> c) {
        return vectorA.containsAll(c);
    }

 
  
 

 
    public int indexOfA(Object o) {
        return vectorA.indexOf(o);
    }

 
    public synchronized int indexOfA(Object o, int index) {
        return vectorA.indexOf(o, index);
    }
 
 
    public synchronized boolean isEmptyA() {
        return vectorA.isEmpty();
    }

 
    public synchronized Iterator<V> iteratorA() {
        return vectorA.iterator();
    } 

 
    public synchronized int lastIndexOfA(Object o) {
        return vectorA.lastIndexOf(o);
    }

 
    public synchronized ListIterator<V> listIteratorA() {
        return vectorA.listIterator();
    }
 
    public synchronized ListIterator<V> listIteratorA(int index) {
        return vectorA.listIterator(index);
    }
  
 
    public synchronized int lastIndexOfA(Object o, int index) {
        return vectorA.lastIndexOf(o, index);
    }

   
 
    public synchronized int sizeA() {
        return vectorA.size();
    }
 
    public synchronized List<V> subListA(int fromIndex, int toIndex) {
        return vectorA.subList(fromIndex, toIndex);
    }
    
    //</editor-fold>
    
    //<editor-fold desc="封装方法 vectorB">
    public synchronized void addElementB(V obj) {
        PropertyChangeEvent e=new PropertyChangeEvent(this, "add", null, obj);
        vectorBChanges.firePropertyChange(e);    
        vectorB.addElement(obj);
    }
    
    public synchronized void insertElementAtB(V obj, int index) {
        PropertyChangeEvent e=new PropertyChangeEvent(this, "insert", null, obj);
        vectorBChanges.firePropertyChange(e);    
        vectorB.insertElementAt(obj, index);
    }
    
    public synchronized void setElementAtB(V obj, int index) {
        V old=vectorB.elementAt(index);
        PropertyChangeEvent e=new PropertyChangeEvent(this, "set", old, obj);
        vectorBChanges.firePropertyChange(e);    
        vectorB.setElementAt(obj, index);
    }
    
    public synchronized void removeAllElementsB() {
        PropertyChangeEvent e=new PropertyChangeEvent(this, "removeAll", vectorB.clone(), null);
        vectorBChanges.firePropertyChange(e);    
        vectorB.removeAllElements();
    }
 
    public synchronized void removeElementAtB(int index) {
        V old=vectorB.elementAt(index);
        PropertyChangeEvent e=new PropertyChangeEvent(this, "remove", old, null);
        vectorBChanges.firePropertyChange(e);    
        vectorB.removeElementAt(index);
    }
  
 
    public synchronized boolean removeElementB(Object obj) {
        PropertyChangeEvent e=new PropertyChangeEvent(this, "remove", obj, null);
        vectorBChanges.firePropertyChange(e);
        return vectorB.removeElement(obj);
    }
    
    public synchronized V elementAtB(int index) {
        return vectorB.elementAt(index);
    }

 
    public Enumeration<V> elementsB() {
        return vectorB.elements();
    }

 
    public synchronized V firstElementB() {
        return vectorB.firstElement();
    }
    
    public synchronized V lastElementB() {
        return vectorB.lastElement();
    }
    
    public synchronized int capacityB() {
        return vectorB.capacity();
    }


  
    public boolean containsB(Object o) {
        return vectorA.contains(o);
    }
    
   
 
    public synchronized boolean containsAllB(Collection<?> c) {
        return vectorB.containsAll(c);
    }

 
  
 

 
    public int indexOfB(Object o) {
        return vectorB.indexOf(o);
    }

 
    public synchronized int indexOfB(Object o, int index) {
        return vectorB.indexOf(o, index);
    }
 
 
    public synchronized boolean isEmptyB() {
        return vectorB.isEmpty();
    }

 
    public synchronized Iterator<V> iteratorB() {
        return vectorB.iterator();
    } 

 
    public synchronized int lastIndexOfB(Object o) {
        return vectorB.lastIndexOf(o);
    }

 
    public synchronized ListIterator<V> listIteratorB() {
        return vectorB.listIterator();
    }
 
    public synchronized ListIterator<V> listIteratorB(int index) {
        return vectorB.listIterator(index);
    }
  
 
    public synchronized int lastIndexOfB(Object o, int index) {
        return vectorB.lastIndexOf(o, index);
    }

   
 
    public synchronized int sizeB() {
        return vectorB.size();
    }
 
    public synchronized List<V> subListB(int fromIndex, int toIndex) {
        return vectorB.subList(fromIndex, toIndex);
    }
    
    //</editor-fold>
    
}
