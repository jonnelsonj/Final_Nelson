import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class EditAssignment extends JDialog {
	private String name;
	private String subject;
	private String due;
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getDue() {
		return due;
	}
	public void setDue(String due) {
		this.due = due;
	}
	public void configureUI() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100,100,400,300);
		Container c = getContentPane();
		c.setLayout(new FlowLayout());
		
		JLabel lblName = new JLabel("Assignment Name: ");
		JTextField txtName = new JTextField(30);
		System.out.print(txtName.getText());
		txtName.setText("Assignment Name");
		txtName.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == 62) { //don't all character ">" because it is used to parse text file
					e.consume();  // ignore event
				}
			}
		});
		
		JLabel lblSub = new JLabel("Subject: ");
		JTextField txtSub = new JTextField(30);
		System.out.print(txtSub.getText());
		txtSub.setText("Subject");
		txtSub.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == 62) { 
					e.consume();  
				}
			}
		});
		
		JLabel lblDesc = new JLabel("Description: ");
		JTextField txtDesc = new JTextField(30);
		System.out.print(txtDesc.getText());
		txtDesc.setText("Description");
		txtDesc.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == 62) { 
					e.consume();  
				}
			}
		});
		
		JLabel lblDue = new JLabel("Due date: ");
		JTextField txtDue = new JTextField(30);
		System.out.print(txtDue.getText());
		txtDue.setText("Due");
		txtDue.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (c == 62) { 
					e.consume();  			  
				}
			}
		});
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setName(txtName.getText());
				setSubject(txtSub.getText());
				setDescription(txtDesc.getText());
				setDue(txtDue.getText());
				setVisible(false);
			}
		});
		
		c.add(lblName);
		c.add(txtName);
		c.add(lblSub);
		c.add(txtSub);
		c.add(lblDesc);
		c.add(txtDesc);
		c.add(lblDue);
		c.add(txtDue);
		c.add(btnOK);
		
	}
	
	public EditAssignment(JFrame owner, boolean modal) {
		super(owner,modal);
		configureUI();
	}
}