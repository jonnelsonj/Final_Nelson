import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Month;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
	
class StatusPanel extends JPanel { 
	private String message;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString(message, 10, 20);
		
	}
	public StatusPanel() {
		setPreferredSize(new Dimension(400,100));
		
	}
}

public class Calendar extends JDialog {
	private String month;
	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}
	

	private String year;
	private String day;
	public void configureUI() {
		setDefaultCloseOperation(HIDE_ON_CLOSE);
		setBounds(100,100,400,400);
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		StatusPanel span = new StatusPanel();
		span.setMessage("Please enter the month,"
				+ " then press enter to continue. ");
		
		JPanel panNorth = new JPanel();
		JPanel panCenter = new JPanel();
		JPanel panSouth = new JPanel();
		panCenter.setLayout(new GridLayout(5,7));
		
		JButton[] buttons = new JButton[36];
		for (int i = 0; i<31; i++) {
			buttons[i] = new JButton("" + (i+1));
			buttons[i].addActionListener(
					new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							JButton btnClicked = (JButton)(e.getSource());
							setDay(btnClicked.getText());
							if (getMonth() != null && getDay() != null && getYear() != null) {
								span.setMessage(getMonth() + 
										"/" + getDay() + "/" + getYear());
								repaint();
							}
						}
					});
		}
		
		JLabel lblMonth = new JLabel("Month (MM): ");
		JTextField txtMonth = new JTextField(5);
		lblMonth.setLabelFor(txtMonth);
		txtMonth.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (Integer.parseInt(txtMonth.getText()) > 12 
								|| Integer.parseInt(txtMonth.getText()) < 0) {
							span.setMessage("Please enter a valid month, then press enter to continue. ");
							repaint();
						} else {
							setMonth(txtMonth.getText());
							if (Integer.parseInt(txtMonth.getText()) == 4 
									|| Integer.parseInt(txtMonth.getText()) == 6 
									|| Integer.parseInt(txtMonth.getText()) == 9 
									|| Integer.parseInt(txtMonth.getText()) == 11) {
								buttons[30].setEnabled(false);
							} else if (Integer.parseInt(txtMonth.getText()) == 2) {
								for (int i = 29; i < 31; i++) {
									buttons[i].setEnabled(false);
								}
							} 
							span.setMessage("Please enter the year, then press enter to continue. ");
							repaint();
					
						}
					}
				});
		
		JLabel lblYear = new JLabel("Year (YYYY): ");
		JTextField txtYear = new JTextField(5);
		lblYear.setLabelFor(txtYear);
		txtYear.addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						setYear(txtYear.getText());
						span.setMessage("Please choose a date, then press OK to continue. ");
						repaint();
					}
				});
		
		for (int k = 0; k<31; k++) {
			panCenter.add(buttons[k]);
		}
		
		JButton btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setYear(txtYear.getText());
				setMonth(txtMonth.getText());
				setVisible(false);
			}
		});
		
		panNorth.add(lblMonth);
		panNorth.add(txtMonth);
		panNorth.add(lblYear);
		panNorth.add(txtYear);
		panSouth.add(btnOK);
		panSouth.add(span);
		c.add(panCenter,BorderLayout.CENTER);
		c.add(panSouth,BorderLayout.SOUTH);
		c.add(panNorth,BorderLayout.NORTH);
	}

	public Calendar(JFrame owner, boolean modal) {
		super(owner,modal);
		configureUI();
	}	
}
