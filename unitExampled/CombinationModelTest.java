/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample;

/**
 *
 * @author cloud
 */
public class CombinationModelTest {
    //在这里可以看出，可把A当作 数据结构，链表中的一个节点。
    //里面的属性a可看作是存储下一个节点的指针。于是，数据结构可以很快的用在
    //面向对象中了。
    class A {
        private  String name;
        public A (String name){
            this.name=name;
        }
        A a;//当然了，你可以使用List ,或Map之类的操作。
        public void add(A a){
            this.a=a;
        }
        public void remove(){
            this.a=null;
        }
        public void operate (){
            System.out.println("对象"+name+" 的操作。");
        }
        public A getNext(){
            return this.a;
        }
    }
    public void run(){
        A a=new A("a");
        A b=new A("b");
        a.add(b);
        A c=new A("c");
        b.add(c);
        b=new A("d");
        c.add(b);
        
        while (a!=null){
            a.operate();
            a=a.getNext();
        }
        
    }
    public static void main(String args[]) {
         CombinationModelTest dd =new CombinationModelTest();
         dd.run();
        
    }
}
