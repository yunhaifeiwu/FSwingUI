package unitExample;


import java.awt.Component;
import java.awt.Container;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;


/**
 * 测试javaBean 的关联属性。<br/>
 * 关联属性，可以简单理解成 一个属性变化事件。<br/>
 * 运用场合，可以是观察模式所应用场所，即一处变化，其他别处也将跟随变化.<br/>
 * 本示例，展示了，Student对象的no变化时，UseCase对象的no跟着变化。
 * @author cloud
 */
public class PropertyChangeBindTest {
    
class A {}
class B{
    private A a;
    public void setA(A a){
        this.a=a;
    }
}
    /**
     * 内部类，一个javaBean. no为 被监控的属性，他一变化，就会通知监控他的对象相应变化。<br/>
     * 这里，必须定义一个PropertyChangeSupport 对象。 当属性变化时，将使用该对象的
     * firePropertyChange 方法，通知要变化的其他类实例，当然，在使用前需要完成注册。
     * 
     */
    class Student {
        
        /**
         * 定义了一个PropertyChangeSupport对象。这是必须的。
         */
        private PropertyChangeSupport changes = new PropertyChangeSupport(this);
        private String no="张三";

        public String getNo() {
            
            return no;
        }
        
        /**
         * 当no变化时，这里先要调用firePropertyChange ，发布通知。<br/>
         * 其中第一个参数 重新定义了属性名，以便被通知方区别接收。
         */
        public void setNo(String no) {
            changes.firePropertyChange("no", this.no, no);
            this.no = no;
            
        }
        /**
         * 注册 需要通知的其他类实例
         */
        public void addPropertyChangeListener (PropertyChangeListener listener) {
            changes.addPropertyChangeListener(listener);
        }
        /**
         * 注销 需要通知的其他类实例
         */
        public void removePropertyChangeListener (PropertyChangeListener listener) {
            changes.removePropertyChangeListener (listener);
        }
       
    }
    
    /**
     * 这是一个需要接收属性变化的类，或即是一个监听者，他要监听一个属性变化。<br/>
     * 必须继承 PropertyChangeListener接口，并实现propertyChange方法。
     */
    class UseCase implements PropertyChangeListener  {
        public  String no="";
        private  Student std;
        /**
         * 生成实例时，必须注入一个被监听的类实例
         */
        public UseCase(Student std){
            this.std=std;
            /**
             * 向被监听者 进行注册，表示自已需要监察其属生变化
             */
            this.std.addPropertyChangeListener(this);
        }
        
        
        @Override
        public void propertyChange(PropertyChangeEvent evt) {      
            /**
             * 有可能 该监听者，有众多不同的需要监听的类实例，所以有第一个判断。<br/>
             * 又有可能 同一个类需要监听的属性有多个，所以有第二个判断。<br/>
             * 当然在被监听者中，在发布通知时，进行编码，可以达到去除第一个判断的作用。
             */
            if ( (evt.getSource().getClass().equals(Student.class)) &&
                    (evt.getPropertyName().equals("no"))   ) {  
                 no="由"+evt.getOldValue()+"变成"+evt.getNewValue();       
            }          
        }
       
    }
    
    /**
     * 测试方法
     */
    public void run(){        
        Student sdt=new Student();
        UseCase u=new UseCase(sdt);
        System.out.println("========显示初值===============");
        System.out.println("sdt.no:"+sdt.no+"          ;u.no:"+u.no);  
        
        System.out.println("========属性改变，并显示========");
        sdt.setNo("李四");
        System.out.println("sdt.no:"+sdt.no+"          ;u.no:"+u.no);  
    }
    public static void main(String args[]) {
        PropertyChangeBindTest ma=new PropertyChangeBindTest();
        ma.run();
        
    }
}

