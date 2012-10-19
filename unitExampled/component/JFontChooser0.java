/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package unitExample.component;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.ScrollPane;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

/**
 *
 * @author cloud
 */
public class JFontChooser0 extends javax.swing.JDialog {


	// Variables declaration 
	private JPanel fontPanel;
	private JScrollPane jScrollPane1;
	private JLabel jLabel1;
	private JLabel jLabel3;
	private JLabel jLabel2;
	private JList lstSize;
	private JButton okButton;
	private JList lstFont;
	private JScrollPane jScrollPane2;
	private JList lstStyle;
	private JPanel mainPanel;
	private JButton cancelButton;
	private JPanel previewPanel;
	private JLabel lblPreview;
	private JPanel buttonPanel;
	private JScrollPane jScrollPane3;


	private int returnStatus = RET_CANCEL;


	//This is the status code returned if the cancel button is pressed.
	public static final int RET_CANCEL = 0;

	//This is the status code returned if the OK button is pressed.
	public static final int RET_OK = 1;

	//This is the font
	private Font font;
	// End of variables declaration.


	//Constructors
	//This creates a new form JFontChooser

	public JFontChooser0(java.awt.Frame parent, Font font) {
		super(parent);
		this.font = font;
		initComponents();
		lblPreview.setFont(font);
	}

	public JFontChooser0(java.awt.Frame parent) {
		super(parent);
		this.font = new Font("Dialog",Font.PLAIN,12);
		initComponents();
		lblPreview.setFont(font);
	}

	public JFontChooser0(Font font) {
		super((javax.swing.JFrame)null);
		this.font = font;
		initComponents();
		lblPreview.setFont(font);
	}

	public JFontChooser0() {
		super((javax.swing.JFrame)null);
		this.font = new Font("Dialog",Font.PLAIN,12);
		initComponents();
		lblPreview.setFont(font);
	}


	//This returns the font choosen by the user.
	public Font getFont(){
		return font;
	}

	//This returns the return status of this dialog - one of RET_OK or RET_CANCEL.
	public int getReturnStatus() {
		return returnStatus;
	}

	//The method initComponents begins here .
	//This method is always called from within the constructor.
	//The contents of this form are always regenerated from the editor.
	private void initComponents() 
	{
		java.awt.GridBagConstraints gridBagConstraints;

		mainPanel = new javax.swing.JPanel();
		fontPanel = new javax.swing.JPanel();
		jLabel1 = new javax.swing.JLabel();
		jLabel2 = new javax.swing.JLabel();
		jLabel3 = new javax.swing.JLabel();
		jScrollPane1 = new JScrollPane();
		lstFont = (JList) new JList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
		jScrollPane2 = new javax.swing.JScrollPane();
		lstStyle = new javax.swing.JList();
		jScrollPane3 = new javax.swing.JScrollPane();
		lstSize = new javax.swing.JList();
		previewPanel = new javax.swing.JPanel();
		lblPreview = new javax.swing.JLabel();
		buttonPanel = new javax.swing.JPanel();
		okButton = new javax.swing.JButton();
		cancelButton = new javax.swing.JButton();

		setTitle("SELECT FONT");
		setModal(true);
		setResizable(false);
		addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent evt) {
				closeDialog(evt);
			}
		});

		mainPanel.setLayout(new java.awt.GridLayout(2, 1));

		fontPanel.setLayout(new java.awt.GridBagLayout());

		jLabel1.setText("FONT");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.insets = new java.awt.Insets(0,0,0,0);
		gridBagConstraints.weightx = 2.0;
		fontPanel.add(jLabel1, gridBagConstraints);

		jLabel2.setText("STYLE");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
		fontPanel.add(jLabel2, gridBagConstraints);

		jLabel3.setText("Size");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
		gridBagConstraints.weightx = 0.2;
		fontPanel.add(jLabel3, gridBagConstraints);

		lstFont.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstFont.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				lstFontValueChanged(evt);
			}
		});

		jScrollPane1.setViewportView(lstFont);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.ipadx = 1;
		gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
		gridBagConstraints.weightx = 2.0;
		fontPanel.add(jScrollPane1, gridBagConstraints);

		lstStyle.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "Italic", "Bold Italic","Plain", "Bold",  };
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});
		lstStyle.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		lstStyle.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				lstStyleValueChanged(evt);
			}
		});

		jScrollPane2.setViewportView(lstStyle);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
		gridBagConstraints.ipadx = 1;
		gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
		fontPanel.add(jScrollPane2, gridBagConstraints);

		lstSize.setModel(new javax.swing.AbstractListModel() {
			String[] strings = { "8", "10", "11", "12", "14", "16", "20", "24", "28", "36", "48", "72", "96" };
			public int getSize() { return strings.length; }
			public Object getElementAt(int i) { return strings[i]; }
		});
		lstSize.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
		lstSize.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
			public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
				lstSizeValueChanged(evt);
			}
		});

		jScrollPane3.setViewportView(lstSize);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = 1;
		gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
		gridBagConstraints.weightx = 0.2;
		fontPanel.add(jScrollPane3, gridBagConstraints);

		mainPanel.add(fontPanel);

		previewPanel.setLayout(new java.awt.BorderLayout());

		previewPanel.setBorder(new javax.swing.border.TitledBorder(null, "PREVIEW", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.CENTER, new java.awt.Font("Dialog", 0, 12)));
		lblPreview.setFont(new java.awt.Font("Dialog", 0,5));
		lblPreview.setText("THIS IS HOW the text will look like AFTER YOU make the changes");
		previewPanel.add(lblPreview, java.awt.BorderLayout.CENTER);

		mainPanel.add(previewPanel);

		getContentPane().add(mainPanel, java.awt.BorderLayout.NORTH);

		buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

		okButton.setText("OK");
		okButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				okButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(okButton);

		cancelButton.setText("CANCELL");
		cancelButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				cancelButtonActionPerformed(evt);
			}
		});

		buttonPanel.add(cancelButton);

		getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

		pack();
		java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
		setSize(new java.awt.Dimension(443, 429));
		setLocation((screenSize.width-443)/2,(screenSize.height-429)/2);
	}//The function initComponents() ends here.



	//The function lstStyleValueChanged()starts here.
	private void lstStyleValueChanged(javax.swing.event.ListSelectionEvent evt) 
	{
		int style = -1;
		String selStyle = (String)lstStyle.getSelectedValue();
		if(selStyle=="Plain")
			style = Font.PLAIN;
		if(selStyle=="Bold")
			style = Font.BOLD;
		if(selStyle=="Italic")
			style = Font.ITALIC;
		if(selStyle=="Bold Italic")
			style = Font.BOLD + Font.ITALIC;

		font = new Font(font.getFamily(),style,font.getSize());
		lblPreview.setFont(font);
	}//The function lstStyleValueChanged() ends here.


	//The functionlstStyleValueChanged() starts here.
	private void lstSizeValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstSizeValueChanged
		int size = Integer.parseInt((String)lstSize.getSelectedValue());
		font = new Font(font.getFamily(),font.getStyle(),size);
		lblPreview.setFont(font);
	}//The functionlstStyleValueChanged() ends here.

	private void lstFontValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstFontValueChanged
		font = new Font((String)lstFont.getSelectedValue(),font.getStyle(),font.getSize());
		lblPreview.setFont(font);
	}//GEN-LAST:event_lstFontValueChanged

	private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
		doClose(RET_OK);
	}//GEN-LAST:event_okButtonActionPerformed

	private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
		doClose(RET_CANCEL);
	}//GEN-LAST:event_cancelButtonActionPerformed

	/** Closes the dialog */
	private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
		doClose(RET_CANCEL);
	}//GEN-LAST:event_closeDialog

	private void doClose(int retStatus) {
		returnStatus = retStatus;
		setVisible(false);
	}    
       
        public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {      
                JFontChooser0 f=new JFontChooser0();
                f.setVisible(true);
            }
        });
    } 
}

