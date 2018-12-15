import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

class Homework implements Serializable {
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
	public String getDue() {
		return due;
	}
	public void setDue(String due) {
		this.due = due;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Homework(String name, String subject, String description, String due) {	
		this.name = name;
		this.subject = subject;
		this.description = description;
		this.due = due;
	}
	public String toString() {
		return String.format("%s > %s > %s > %s",name,subject,description,due);
	}
}

class StatPanel extends JPanel { 
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
		repaint();
	}
	public StatPanel() {
		setPreferredSize(new Dimension(600,100));
	}
}

class HomeworkIO { //controller for "Write homework to text file" use case
	public boolean writePoints(ArrayList<Homework> homework, File f) {
		try {
			PrintWriter pw = new PrintWriter(new BufferedWriter(
					new FileWriter(f)));
			for (Homework h : homework) {
				pw.println(h);
			}
			pw.close();
			return true;
		} catch(Exception ex) {
			return false;
		}
	}
}

public class MainApp extends JFrame {
	ArrayList<Homework> homework = new ArrayList<Homework>();
	public void configureUI() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(200,200,750,500);
		setTitle("Homework Schedule");
		Container c = getContentPane();
		c.setLayout(new BorderLayout());
		StatPanel span = new StatPanel();
		span.setMessage("Add homework to begin, or open file.");
		String idleMessage = "Open file, add assignment, or edit exisitng assignment.";
		FileFilter filter = new FileNameExtensionFilter("binary","bin");
		JFileChooser jfc = new JFileChooser();
		jfc.addChoosableFileFilter(filter);
		jfc.setFileFilter(filter);
		filter = new FileNameExtensionFilter("text","txt");
		jfc.addChoosableFileFilter(filter);
		//Create the JList
		DefaultListModel<String> listModel = new DefaultListModel<>();
		JList<String> hlist = new JList<String>(listModel);
		hlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		hlist.setLayoutOrientation(JList.VERTICAL);
		hlist.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(hlist);
		listScroller.setPreferredSize(new Dimension(250, 80));
						
		JPanel panNorth = new JPanel();
		JPanel panCenter = new JPanel();
		JPanel panSouth = new JPanel();
		
		MainApp mapp = this;
		
		JLabel lblDate = new JLabel("Date");
	
		JMenuBar bar = new JMenuBar();
		JMenu mnuFile = new JMenu("File");
		JMenuItem miSave = new JMenuItem("Save");
		miSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f;
				HomeworkIO homeworkWriter = new HomeworkIO();
				if (jfc.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					f = jfc.getSelectedFile();
					homeworkWriter.writePoints(homework,f);
				}
			}
		});
		JMenuItem miOpen = new JMenuItem("Open");
		miOpen.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File f;
				if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					f = jfc.getSelectedFile();
					homework.clear();
					try {
						Scanner sc = new Scanner(f);
						String line;
						String[] parts;
						Homework h;
						while (sc.hasNextLine()) {
							line = sc.nextLine().trim();
							if (!line.equals("")) {
								parts = line.split(">");
								h = new Homework(parts[0],parts[1],parts[2],parts[3]);
								homework.add(h);
								listModel.addElement(parts[0] + "    --    " + parts[3]);
							}	
						}
						sc.close();
						span.setMessage(idleMessage);
						repaint();
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null,"Save file wasn't formatted correctly.");
					}
				}
			}
		});
		JMenuItem miRefresh = new JMenuItem("Refresh");
		miRefresh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				for (Homework homwrk : homework) {
					if (homwrk.getDue().strip().equals(lblDate.getText().strip())) {
						listModel.addElement(homwrk.getName() + "    --    " + homwrk.getDue());	
					}
				} 
				span.setMessage(idleMessage);
				repaint();
			}
		});
		JMenuItem miExit = new JMenuItem("Exit");
		miExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenu mnuView = new JMenu("View");
		JMenuItem miViewAll = new JMenuItem("View All");
		miViewAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listModel.clear();
				for (Homework homwrk : homework) {
					listModel.addElement(homwrk.getName() + "    --    " + homwrk.getDue());	
				} 
				span.setMessage(idleMessage);
				repaint();
			}
		});
		JMenuItem miViewDate = new JMenuItem("View By Date");
		miViewDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calendar cal = new Calendar(mapp,true);
				cal.setVisible(true);
				cal.getDay();
				cal.getMonth();
				cal.getYear();
				lblDate.setText(cal.getMonth() + "/" + cal.getDay() + "/" + cal.getYear()); 
				listModel.clear();
				for (Homework homwrk : homework) {
					if (homwrk.getDue().strip().equals(lblDate.getText().strip())) {
						listModel.addElement(homwrk.getName() + "    --    " + homwrk.getDue());	
					}
				} 
				span.setMessage(idleMessage);
				repaint();
		}
		});
		JMenu mnuEditAdd = new JMenu("Edit/Add");
		JMenuItem miAdd = new JMenuItem("Add Assignment");
		miAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditAssignment ea = new EditAssignment(mapp,true);
				ea.setVisible(true);	
				homework.add(new Homework(ea.getName(),ea.getSubject(),ea.getDescription(),ea.getDue()));
				span.setMessage(idleMessage);
				repaint();
			} 
		});
		JMenuItem miEdit = new JMenuItem("Edit Assignment");
		miEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditAssignment ea = new EditAssignment(mapp,true);
				int pos = hlist.getSelectedIndex();
				span.setMessage("Please highlight an assignment to edit before selecting 'Edit'.");
				if (pos >= 0) {
					Homework hmwrk = homework.get(pos);
					ea.setName(hmwrk.getName());
					ea.setSubject(hmwrk.getSubject());
					ea.setDescription(hmwrk.getDescription());
					ea.setDue(hmwrk.getDue());
					ea.setVisible(true);
					homework.set(pos,new Homework(ea.getName(),ea.getSubject(),ea.getDescription(),ea.getDue()));
					span.setMessage(idleMessage);
					repaint();
				} else {
					span.setMessage("Please highlight an assignment to edit before selecting 'Edit'.");
				}
			}
		});
		JMenuItem miDelete = new JMenuItem("Delete Assignment");
		miDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				span.setMessage("Please highlight an assignment to delete before selecting 'Delete'.");
				int pos = hlist.getSelectedIndex();
				if (pos >= 0) {
					listModel.remove(pos);
					homework.remove(pos);
					span.setMessage(idleMessage);
					repaint();
				} else {
					span.setMessage("Please highlight an assignment to delete before selecting 'Delete'.");
				}
			}
		});
		mnuFile.add(miOpen);
		mnuFile.add(miSave);
		mnuFile.add(miRefresh);
		mnuFile.add(miExit);
		
		mnuView.add(miViewAll);
		mnuView.add(miViewDate);
		
		mnuEditAdd.add(miAdd);
		mnuEditAdd.add(miEdit);
		mnuEditAdd.add(miDelete);
		
		bar.add(mnuFile);
		bar.add(mnuView);
		bar.add(mnuEditAdd);
		
		setJMenuBar(bar);
		
		panNorth.add(lblDate);
		
		panCenter.add(hlist);
	
		panSouth.add(span);
				
		c.add(panCenter,BorderLayout.CENTER);
		c.add(panSouth,BorderLayout.SOUTH);
		c.add(panNorth,BorderLayout.NORTH);
	}
	public MainApp() {
		configureUI();
	}
}


