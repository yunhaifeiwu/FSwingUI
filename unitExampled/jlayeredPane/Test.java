/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.jlayeredPane;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;


public class Test extends JFrame implements ActionListener {

    private static final long serialVersionUID = 4785452373598819719L;

    private JButton b_a = null, b_b = null;

    private JLayeredPane lp = null; // 我们要用到的层

    public Test() {
        super("JLayeredPane");

        lp = this.getLayeredPane(); // 获取JLayeredPane

        b_a = new JButton("A");
        b_b = new JButton("B");

        b_a.addActionListener(this); // 按钮事件
        b_b.addActionListener(this);

        lp.add(b_a, new Integer(200)); // 将组件添加到JLayeredPane中，指定所在的层
        lp.add(b_b, new Integer(300));

        b_a.setBounds(new Rectangle(100, 100, 100, 100)); // Button出现位置
        b_a.setVisible(true); // 显示

        b_b.setBounds(new Rectangle(50, 50, 100, 100));
        b_b.setVisible(true);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(360, 260);
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("A")) { // 判断是哪个按钮的动作
            lp.setLayer(b_a, new Integer(300)); // 重新设置组件层数
            lp.setLayer(b_b, new Integer(200));
        } else if (e.getActionCommand().equals("B")){
            lp.setLayer(b_a, new Integer(200));
            lp.setLayer(b_b, new Integer(300));
        }
    }

    public static void main(String args[]) {
        new Test();
    }

}