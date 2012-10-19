/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
/**
 * 代理类，不完成实际功能，他仅仅是调用另一个类的对象（暂称为委托方，本例中
 * 为BigCar实例）的方法。
 * 好处时，可以在委托方的基础上，扩展写相关代码。即，使用代理机制，预留了一个
 * 扩展接口，减少委托方与调用者之间的耦合（调用者不是直接调用BigCar的方法，而是调用
 * carPrOxy.当然生成CarProxy实例时，无法进行解耦，但这里仅藕合了一次。
 */

public class CarProxy implements InvocationHandler {    
    
/*
    public interface Car {
        public void description();
    }
 
    public class BigCar implements Car {
        public void description() {//描述 我是BigCar
            System.out.println("BigCar");
        }
    }
 */
    private Object delegate;//被代理对象，或即委托方
    public Object newInstance(Object delegate) {
        this.delegate = delegate;
        //装配，生成一个代理对象，且把委托方的实例注入到了这个代理对象中
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
                delegate.getClass().getInterfaces(), this);
    }
    
    /**
     * 这个方法 供代理机制使用 ，委托方相关方法的扩展代码，可以放在这里。
     * 有时，委托方，无法更改源代码，或不愿影院委托方的稳定性，可在这里扩展
     */
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable {
        //这里预留了扩展点。可根据实际需要，增加代码，完成某些功能。如日志记录等
        return method.invoke(delegate, args);
    }
    
    //测试 
    public static void main(String args[]) throws InstantiationException, 
            IllegalAccessException
    {   
        CarProxy cp = new CarProxy();//生成代理
        //把代理实例cp与委托实例BigCar相关联，生成接口类型的变量的实例
        Car car = (Car) cp.newInstance(BigCar.class.newInstance());
        //调用接口实量的相关方法
        car.description();
        //从使用看出，代理类生成了委托方的实例后，得到一个接口的实例，
        //然后使用该接口。从使用过程中，看出，代理类仅关联了委托方。
   
    }
}
