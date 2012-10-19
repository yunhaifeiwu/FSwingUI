
import java.awt.*;
import java.awt.event.*;
import java.util.Hashtable;
import javax.swing.*;

 

public class Button1 extends JFrame{
    protected Hashtable  m_lfs;
    public Button1() {
        super("Look and Feel [Resources]");
        setSize(400, 300);
        getContentPane().setLayout(new FlowLayout());
        JMenuBar menuBar = createMenuBar();
        setJMenuBar(menuBar);
        JPanel p = new JPanel();
        JButton bt1 = new JButton("Click Me");
        p.add(bt1);
        JButton bt2 = new JButton("Don't Touch Me");
        p.add(bt2);
        getContentPane().add(p);
        p = new JPanel();
        JCheckBox chk1 = new JCheckBox("I'm checked");
        chk1.setSelected(true);
        p.add(chk1);
        JCheckBox chk2 = new JCheckBox("I'm unchecked");
        chk2.setSelected(false);
        p.add(chk2);
        getContentPane().add(p);
        p = new JPanel();
        ButtonGroup grp = new ButtonGroup();
        JRadioButton rd1 = new JRadioButton("Option 1");
        rd1.setSelected(true);
        p.add(rd1);
        grp.add(rd1);
        JRadioButton rd2 = new JRadioButton("Option 2");
        p.add(rd2);
        grp.add(rd2);
        JRadioButton rd3 = new JRadioButton("Option 3");
        p.add(rd3);
        grp.add(rd3);
        getContentPane().add(p);
        JTextArea txt = new JTextArea(5, 30);
        getContentPane().add(txt);
        WindowListener wndCloser = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        addWindowListener(wndCloser);
        setVisible(true);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu mFile = new JMenu("File");
        mFile.setMnemonic('f');
        JMenuItem mItem = new JMenuItem("Exit");
        mItem.setMnemonic('x');
        ActionListener lstExit = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        };
        mItem.addActionListener(lstExit);
        mFile.add(mItem);
        menuBar.add(mFile);
        ActionListener lst = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            String str = e.getActionCommand();
            Object obj = m_lfs.get(str);
            if (obj != null)
                try {
                    String className = (String)obj;
                    Class lnfClass = Class.forName(className);
                    UIManager.setLookAndFeel(
                    (LookAndFeel)(lnfClass.newInstance()));
                    SwingUtilities.updateComponentTreeUI(
                    Button1.this);
                }
                catch (Exception ex) {
                    ex.printStackTrace();
                    System.err.println(ex.toString());
                }
            }
        };

        m_lfs = new Hashtable();
        UIManager.LookAndFeelInfo lfs[] = UIManager.getInstalledLookAndFeels();
        JMenu mLF = new JMenu("Look&Feel");
        mLF.setMnemonic('l');
        for (int k = 0; k < lfs.length; k++ ) {
            String name = lfs[k].getName();
            JMenuItem lf = new JMenuItem(name);
            m_lfs.put(name, lfs[k].getClassName());
            lf.addActionListener(lst);
            mLF.add(lf);
        }
        menuBar.add(mLF);
        return menuBar;
    }

    public static void main(String argv[]) {
        try {
            LookAndFeel malachite = new malachite.MalachiteLF();
            UIManager.LookAndFeelInfo info =
            new UIManager.LookAndFeelInfo(malachite.getName(),
            malachite.getClass().getName());
            UIManager.installLookAndFeel(info);
            UIManager.setLookAndFeel(malachite);
        }
        catch (Exception ex) {
            ex.printStackTrace();
            System.err.println(ex.toString());
        }
        new Button1();
    }

}