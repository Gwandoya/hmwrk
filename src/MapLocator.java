import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JComponent;
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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class MapLocator extends JFrame {
	
	Color ccolor;
	JColorChooser cc = new JColorChooser();
	JFileChooser jfc = new JFileChooser(".");
	
	String filename = "";
	ImageIcon mapPhoto;
	
	HashMap<Position, Place> places = new HashMap<Position, Place>();
	DefaultListModel<Category> listModel = new DefaultListModel<Category>();
	String[] options = { "Place", "Described Place" };
	JComboBox<String> combobox = new JComboBox<String>(options);

	JList<Category> list;

	Boolean notsaved = false;
	Boolean whichplace = false;
	Boolean newplace = false;
	Boolean newunsaved = false;
	Boolean opened = false;
	
	Map map = new Map();
	
	JLabel invinsiblespace = new JLabel();
	JLabel invinsiblespace2 = new JLabel();
	JLabel invinsiblespace3 = new JLabel();
	JLabel invinsiblespace4 = new JLabel();
	JLabel mappingboard = new JLabel();
	JTextField searchfield = new JTextField("Search", 10);
	
	JMenu topmenu = new JMenu("Archive");
	JMenuItem newmap = new JMenuItem("New map");
	JMenuItem save = new JMenuItem("Save");
	JMenuItem open = new JMenuItem("Open");
	JMenuItem exit = new JMenuItem("Exit");
	
	JButton searchbutton = new JButton("Search");
	JButton hideplace = new JButton("Hide places");
	JButton deleteplace = new JButton("Delete places");
	JButton which = new JButton("Which place is here?");
		
	JButton newcat = new JButton("New category");
	JButton deletecat = new JButton("Delete category");
	JButton hidecat = new JButton("Hide category");
	
	JScrollPane textscrollbar;
	JScrollPane mapscrollingbar;

	MapLocator() {
		
		super("DSV Map-finder");
		setLayout(new BorderLayout());
		
		JPanel bottomp = new JPanel();		
		JPanel combinedpanel = new JPanel();
		JPanel rightpanel = new JPanel();
		JMenuBar topbar = new JMenuBar();
	
		
		bottomp.setLayout(new BoxLayout(bottomp, BoxLayout.X_AXIS));
		topbar.add(topmenu);
		topbar.add(Box.createHorizontalGlue());
		topmenu.add(newmap);
		topmenu.add(open);
		topmenu.add(save);
		topmenu.add(exit);
		
		Dimension d = new Dimension(200, 25);
		combobox.setPreferredSize(d);
		combobox.setMaximumSize(d);
		combobox.setMinimumSize(d);
		searchfield.setPreferredSize(d);
		searchfield.setMaximumSize(d);
		searchfield.setMinimumSize(d);
		
		
		invinsiblespace = new JLabel();
		invinsiblespace2 = new JLabel();
		invinsiblespace3 = new JLabel();
		invinsiblespace4 = new JLabel();

		invinsiblespace.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
		invinsiblespace2.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
		invinsiblespace3.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));
		invinsiblespace4.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 5));

		bottomp.add(new JLabel("New:"));
		bottomp.add(combobox);
		bottomp.add(searchfield);
		bottomp.add(invinsiblespace);
		bottomp.add(searchbutton);
		bottomp.add(invinsiblespace2);
		bottomp.add(hideplace);
		bottomp.add(invinsiblespace3);
		bottomp.add(deleteplace);
		bottomp.add(invinsiblespace4);
		bottomp.add(which);
		
		bottomp.add(Box.createHorizontalGlue());

		combinedpanel.setLayout(new BoxLayout(combinedpanel, BoxLayout.Y_AXIS));
		combinedpanel.add(topbar);
		combinedpanel.add(bottomp);
		add(combinedpanel, BorderLayout.NORTH);
		
		rightpanel.setLayout(new BoxLayout(rightpanel, BoxLayout.Y_AXIS));

		list = new JList<Category>(listModel);
		list.setPreferredSize(new Dimension(100, 100));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		textscrollbar = new JScrollPane(list);
		textscrollbar.setPreferredSize(new Dimension(100, 100));

		rightpanel.add(Box.createVerticalGlue());
		rightpanel.add(new JLabel("Categories list:"));
		rightpanel.add(textscrollbar);
		rightpanel.add(newcat);
		rightpanel.add(deletecat);
		rightpanel.add(hidecat);
		
		rightpanel.add(Box.createVerticalGlue());
		add(rightpanel, BorderLayout.EAST);

		map.setLayout(null);

		mapscrollingbar = new JScrollPane(map);
		mapscrollingbar.setPreferredSize(new Dimension(400, 400));
		add(mapscrollingbar, BorderLayout.CENTER);

		map.addMouseListener(new MapAdapter());
		addWindowListener(new ExitListener());
		
		combobox.addActionListener(new NewPlace());
		newcat.addActionListener(new NewCategory());
		deletecat.addActionListener(new DeleteCategory());
		hidecat.addActionListener(new HideCategory());
		
		which.addActionListener(new WhatPlace());
		deleteplace.addActionListener(new DeletePlace());
		hideplace.addActionListener(new HidePlace());
		searchbutton.addActionListener(new Search());
		searchfield.addMouseListener(new SearchField());
		
		newmap.addActionListener(new NewMap());
		open.addActionListener(new OpenButton());
		save.addActionListener(new SaveButton());
		exit.addActionListener(new ExitButton());
		
		list.addListSelectionListener(new List());
		cc.getSelectionModel().addChangeListener(new ColorSelection());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(850, 550);
		setMinimumSize(new Dimension(800, 500));
		setVisible(true);
	}

	class NewPlace implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			newplace = true;
			notsaved = true;
		}
	}
	
	/* Open a new map/image */
	class NewMap implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (notsaved) {
				int warning = JOptionPane.showConfirmDialog(null,
						"The progress hasn't been saved, would you like to exit anyways?",
						"Warning!", JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE);
				if (warning == JOptionPane.YES_OPTION) {
					changeToNewMap();
				} else if (warning == JOptionPane.NO_OPTION) {
					return;
				}
			} else {
				changeToNewMap();
			}
		}

		public void changeToNewMap() {
			try {
				places.clear();
				map.newMap();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	class List implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent lev) {
			for (Place p : places.values()) {
				if (!p.getVisible()
						&& p.getCategory() == list.getSelectedValue()) {
					p.setVisible();
				}
			}
		}
	}

	/* Open button in archive menubar */
	class OpenButton implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (notsaved) {
				int warning = JOptionPane.showConfirmDialog(null,
						"Progress hasn't been saved, do you still "
								+ "want to open a new map", "Warning!",
						JOptionPane.YES_NO_OPTION, JOptionPane.ERROR_MESSAGE);
				if (warning == JOptionPane.YES_OPTION) {
					places.clear();
					int answer = jfc.showOpenDialog(MapLocator.this);
					if (answer != JFileChooser.APPROVE_OPTION) {
						return;
					}
				} else if (warning != JOptionPane.YES_OPTION) {
					return;
				}
			}
			openNewMap();
		}

		public void openNewMap() {
			map.setMapLink("");
			places.clear();
			listModel.clear();
			
			int answer = jfc.showOpenDialog(MapLocator.this);
			if (answer != JFileChooser.APPROVE_OPTION) {
				return;
			}
			
			File file = jfc.getSelectedFile();
			String filename = file.getAbsolutePath();

			try {
				
				FileInputStream fis = new FileInputStream(filename);
				ObjectInputStream ois = new ObjectInputStream(fis);
				Object o = ois.readObject();
				SaveThings a = (SaveThings)o;
				System.out.println("b");
				map.setMap(a.getMapLink());
				
				for (Object ob : a.getCategories().toArray()) {
					if (ob instanceof Category) {
						listModel.addElement((Category) ob);
					}
				}
				for (Position p : a.getPlaces().keySet()) {
					Place placeone = a.getPlaces().get(p);
					Place placetwo;
					for (Object ob : listModel.toArray()) {
						Category c = (Category) ob;

						if (c.getName().equals(placeone.getCategoriname())) {
							list.setSelectedIndex(listModel.indexOf(ob));
							System.out.println("tilldelad");
						}
					}
					
					if (placeone instanceof DescribedPlace) {
						placetwo = new DescribedPlace(placeone.getName(),
								placeone.getPosition(),
								((DescribedPlace) placeone).getDescription());

					} else {
						placetwo = new Place(placeone.getName(), placeone.getPosition());
					}
					if (placeone.getCategoriname().length() > 0) {
						placetwo.setCategory(list.getSelectedValue());
					}
					
					places.put(p, placetwo);
					map.add(placetwo.getPointer());
				}
				
				map.repaint();
				pack();
				validate();
				repaint();
				ois.close();
				fis.close();
				
			} catch (FileNotFoundException e) {
				showMessageDialog(MapLocator.this, "Could not open the file.");
				} catch (IOException e) {
				showMessageDialog(MapLocator.this,
						"Error! - IO exception" + e.getMessage());
					} catch (ClassNotFoundException e) {
				showMessageDialog(MapLocator.this,
						"Couldn't find the class:" + e.getMessage());
				} // Error message
			
			pack();
			validate();
			repaint();
			opened = true;
			notsaved = false;
			newunsaved = false;
		}
	}

	/* Save position of places */
	class SaveThings implements Serializable {

		private static final long serialVersionUID = 1L;
		String mapLink;
		HashMap<Position, Place> theplaces;
		DefaultListModel<Category> existingCategories;

		SaveThings() {
			mapLink = map.getMapLink();
			theplaces = places;
			existingCategories = listModel;
		}

		public String getMapLink() {
			return mapLink;
		}

		public HashMap<Position, Place> getPlaces() {
			return theplaces;
		}

		public DefaultListModel<Category> getCategories() {
			return existingCategories;
		}
	}

	/* Save button in archive menubar for opening files later */
	class SaveButton implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (notsaved && filename.length() > 0) {
				SaveProcess();
			} else if (notsaved) {
				jfc.showSaveDialog(null);
				filename = jfc.getSelectedFile().getAbsolutePath();
				SaveProcess();
			}

			notsaved = false;
			newunsaved = false;
		}

		public void SaveProcess() {
			
			try {
				
				FileOutputStream fos = new FileOutputStream(filename);
				ObjectOutputStream oos = new ObjectOutputStream(fos);
				SaveThings ss = new SaveThings();
				
					for (Position p : ss.getPlaces().keySet()) {
						ss.getPlaces().get(p).getPointer().setML();
						ss.getPlaces().get(p).removeCategory();
				}
				
				oos.writeObject(ss);
				oos.flush();
				oos.close();
				fos.flush();
				fos.close();
				
				for (Position p : ss.getPlaces().keySet()) {
				     Place pl = ss.getPlaces().get(p);
				     ss.getPlaces().get(p).getPointer().setML();
				     
				     	for (Object ob : listModel.toArray()) {
				     		Category c = (Category) ob;

				     		if (c.getName().equals(pl.getCategoriname())) {
				     			list.setSelectedIndex(listModel.indexOf(ob));
				     		}
				     	}
				     ss.getPlaces().get(p).setCategory(list.getSelectedValue());
				    }
				
			} catch (FileNotFoundException e) {
				showMessageDialog(MapLocator.this, "Could not open the file!");
				} catch (IOException e) {
					showMessageDialog(MapLocator.this, "Error: " + e.getMessage());
				}
			}
		}

	/* Exit button in archive menubar */
	class ExitButton implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (notsaved) {
				int warning = JOptionPane.showConfirmDialog(null,
						"Unsaved progress, do you want to proceed to exit?",
						"Warning!", JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE);
				if (warning == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
				if (warning != JOptionPane.YES_OPTION)
					return;
			}

			System.exit(0);
		}

	}

	class ExitListener extends WindowAdapter {
		@Override
		public void windowClosing(WindowEvent Exit) {
			if (notsaved) {
				int g = JOptionPane.showConfirmDialog(null,
						"You have unsaved changes, do you still want to exit?",
						"Warning!", JOptionPane.YES_NO_OPTION,
						JOptionPane.ERROR_MESSAGE);
				if (g == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
					if (g != JOptionPane.YES_OPTION) {
						return;
					}
			} else {
				System.exit(0);
			}
		}

	}

	/* New category button listener */
	class NewCategory implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			//CategoryFrame catf = new CategoryFrame();
			
			JPanel categoryPanel = new JPanel();
			categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
			JPanel row1 = new JPanel();
			add(row1);
			
			row1.add(new JLabel("Category name: "));
			JTextField nameField = new JTextField(15);
			row1.add(nameField);
			categoryPanel.add(row1);
			
			JPanel row2 = new JPanel();
			add(row2);
			row2.add(new JLabel("Choose a colour for your category: "));
			row2.add(cc);
			categoryPanel.add(row2);
			showConfirmDialog(MapLocator.this, categoryPanel, "New Category",
					JOptionPane.OK_CANCEL_OPTION);
			String strnameField = nameField.getText();
			Category cat = new Category(strnameField, ccolor);
			listModel.addElement(cat);
			notsaved = true;
		}
	}

	/* Delete category button listener */
	class DeleteCategory implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			
			if (places.size() > 0) {
				
				Iterator<HashMap.Entry<Position, Place>> iterator = places
						.entrySet().iterator();
				while (iterator.hasNext()) {
					HashMap.Entry<Position, Place> entry = iterator.next();
					
					if (entry.getValue().getCategory() == list
							.getSelectedValue()) {
						if (entry.getValue().getVisible()) {
							entry.getValue().setVisible();
						}
						iterator.remove();
					}
				}
			}
			
			if (list.getSelectedIndex() != -1) {
				int index = list.getSelectedIndex();
				listModel.remove(index);
				notsaved = true;
			}
		}
	}

	/* Hide category button listener */
	class HideCategory implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Place p : places.values()) {
				if (p.getCategory() == list.getSelectedValue() && p.isVisible()) {
					p.setVisible();
					if (p.getMarked()) {
						p.setMarked();
					}
				}
			}
		}
	}

	/* Which place button listener */
	class WhatPlace implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			whichplace = true;
		}
	}

	/* Delete place button listener */
	class DeletePlace implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			if (places.size() > 0) {
				
				Iterator<HashMap.Entry<Position, Place>> iterator = places
						.entrySet().iterator();
				
				while (iterator.hasNext()) {
					
					HashMap.Entry<Position, Place> entry = iterator.next();
					
					if (entry.getValue().getMarked()) {
						entry.getValue().setVisible();
						iterator.remove();
						notsaved = true;
					}
				}
			}
		}
	}
	
	/* Hidden place button listener */
	class HidePlace implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			for (Place p : places.values()) {
				if (p.getMarked()) {
					p.setVisible();
					p.setMarked();
				}
			}
		}
	}

	class Search implements ActionListener {
		public void actionPerformed(ActionEvent ave) {
			
			searchfield.setText("Search");
			String string = searchfield.getText();
			
			for (Place p : places.values()) {
				if (p.getName().equals(string)) {
					if (!p.getVisible()) {
						p.setVisible();
					}
					
					if (!p.getMarked()) {
						p.setMarked();
					}
					
				} else if (p.getMarked()) {
					p.setMarked();
				}

			}
		}
	}

	class SearchField extends MouseAdapter {
		public void mouseClicked(MouseEvent mev) {
			if (searchfield.getText().equals("Search")) {
				searchfield.setText("");
			}
		}
	}

	class MapAdapter extends MouseAdapter {
		public void mouseEntered(MouseEvent mev) {
			if (newplace || whichplace) {
				setCursor(Cursor.CROSSHAIR_CURSOR);
			}
		}

		public void mouseExited(MouseEvent mev) {
			setCursor(Cursor.DEFAULT_CURSOR);
		}

		public void mouseClicked(MouseEvent mev) {
			
			int x = mev.getX();
			int y = mev.getY();
			Position pos = new Position(x, y);
			
			if (opened == true) {
				if (newplace) {
					newplace = false;
					setCursor(Cursor.DEFAULT_CURSOR);
					PlaceWindow pw = new PlaceWindow();
					
					showConfirmDialog(MapLocator.this, pw, "New place",
							JOptionPane.OK_CANCEL_OPTION);
					Place place;
					if (pw.correct()) {
						if (combobox.getSelectedIndex() == 0) {
							place = new Place(pw.getName(), pos);
						} else {
							place = new DescribedPlace(pw.getName(), pos,
									pw.getDescription());
						}
							if (list.getSelectedIndex() != -1) {
								place.setCategory(list.getSelectedValue());
						}
						
						places.put(pos, place);
						map.add(place.getPointer());
						map.repaint();
					}
				} else if (whichplace) {
					whichplace = false;
					setCursor(Cursor.DEFAULT_CURSOR);
					for (Place p : places.values()) {
						if ((x - 15) <= p.getPosition().getX()
								&& p.getPosition().getX() <= (x + 15)
								&& (y - 15) <= p.getPosition().getY()
								&& p.getPosition().getY() <= (y + 15)) {
							if (!p.getVisible()) {
								p.setVisible();
							}
						}
					}
				}
			}
		}
	}

	class SelectedPlace extends MouseAdapter implements Serializable {
		public void mouseClicked(MouseEvent mev) {
			if (SwingUtilities.isLeftMouseButton(mev)) {
				if (mev.getSource() instanceof MousePointer) {
					MousePointer mp = (MousePointer) mev.getSource();
					Place p = places.get(mp.getPosition());
					p.setMarked();
				}
			} else if (SwingUtilities.isRightMouseButton(mev)) {
				if (mev.getSource() instanceof MousePointer) {
					MousePointer mp = (MousePointer) mev.getSource();
					Place p = places.get(mp.getPosition());
					p.setOpen();
				}
			}
		}
	}

	class PlaceDisplay extends JComponent implements Serializable {
		private boolean open = false;
		private Color color = Color.black;
		private String string;
		private int x, y;
		private int rad;

		public PlaceDisplay(Position p, String s) {
			x = p.getX();
			y = p.getY();
			string = s;
			rad = (int) (((string.length() / 10) + 1) * 13);
			
			setBounds(x + 20, y - 10, 60, rad);
			Dimension d = new Dimension(60, rad);
			setPreferredSize(d);
			setMaximumSize(d);
			setMinimumSize(d);
			
			if (list.getSelectedIndex() != -1) {
				color = (listModel.get(list.getSelectedIndex()).getColor());
			}
		}

		public void setOpen() {
			if (open) {
				open = false;
				
				repaint();
			} else
				open = true;
			
			repaint();
		}

		public boolean getOpen() {
			return open;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (open) {
				int index = 10;
				int yPos = 10;
				
				g.setColor(color.white);
				g.fillRect(0, 0, 60, rad);
				String str = string;
				g.setColor(color);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 12));
				
				String string1 = string;
				
				while (index < string.length()) {
					string1 = str.substring(index - 10, index);
					g.drawString(string1, 2, yPos);
					yPos += 13;
					index += 10;
				}
				g.drawString(string1, 2, yPos);
			}
		}
	}

	class NameBox extends JComponent implements Serializable {
		
		private boolean open = false;
		private Color color = Color.black;
		private String text;
		private int x, y;

		public NameBox(String name, Position p) {
			this.text = name;
			this.x = p.getX();
			this.y = p.getY();
			
			setBounds((x - 100), (y - 35), 200, 25);
			Dimension d = new Dimension(200, 25);
			setPreferredSize(d);
			setMaximumSize(d);
			setMinimumSize(d);
			
			if (list.getSelectedIndex() != -1) {
				color = (listModel.get(list.getSelectedIndex()).getColor());
			}
		}

		public void setOpen() {
			if (open) {
				open = false;
				
				repaint();
			} else {
				open = true;
				
				repaint();
			}
		}

		public boolean getOpen() {
			return open;
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (open) {
				int stringLen = (int) g.getFontMetrics()
						.getStringBounds(text, g).getWidth();
				int start = (100) - (stringLen / 2);
				
				g.setColor(color);
				g.setFont(new Font("TimesRoman", Font.PLAIN, 18));
				g.drawString(text, start, 20);
			}
		}
	}

	class SelectedBox extends JComponent implements Serializable {
		
		private Color color = Color.black;
		private int x;
		private int y;
		private int[] xPoints = { 0, 10, 10, 0, 0 };
		private int[] yPoints = { 0, 0, 10, 10, 0 };
		private boolean marked = false;

		public SelectedBox(Position p) {
			this.x = p.getX();
			this.y = p.getY();
			
			setBounds(x - 4, y - 10, 15, 15);
			Dimension d = new Dimension(15, 15);
			setPreferredSize(d);
			setMaximumSize(d);
			setMinimumSize(d);
			
			if (list.getSelectedIndex() != -1) {
				color = (listModel.get(list.getSelectedIndex()).getColor());
			}
		}

		public void setMarked() {
			if (marked) {
				marked = false;
				
				repaint();
			} else {
				marked = true;
				
				repaint();
			}
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (marked) {
				g.setColor(color);
				g.drawPolygon(xPoints, yPoints, xPoints.length);
			}
		}

		public boolean getMarked() {
			return marked;
		}
	}

	/* Mouse coordinates */
	class MousePointer extends JComponent implements Serializable {
		private int[] xPoints = { 0, 10, 5, 0 };
		private int[] yPoints = { 0, 0, 10, 0 };
		private Color color = Color.black;
		private int x;
		private int y;
		private Position p;
		private boolean ml = false;
		private MouseListener MouseL;

		public MousePointer(Position p) {
			this.p = p;
			this.x = p.getX();
			this.y = p.getY();
			
			setBounds(x - 5, y - 10, 10, 10);
			Dimension d = new Dimension(10, 10);
			setPreferredSize(d);
			setMaximumSize(d);
			setMinimumSize(d);
			
			if (list.getSelectedIndex() != -1) {
				color = (listModel.get(list.getSelectedIndex()).getColor());
			}
			setML();
		}

		public void setML() {
			if (ml) {
				removeMouseListener(MouseL);
				ml = false;
			} else {
				addMouseListener(MouseL = new SelectedPlace());
				ml = true;
			}
		}

		public Position getPosition() {
			return p;
		}

		public String getCoordinate() {
			return x + ", " + y;
		}

		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.setColor(color);
			Polygon p = new Polygon(xPoints, yPoints, xPoints.length);
			g.fillPolygon(p);
		}
	}

	class PlaceWindow extends JPanel {
		private JTextField namefield = new JTextField(10);
		private JTextArea descriptionarea = new JTextArea(15, 15);

		public PlaceWindow() {
			setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			
			JPanel first = new JPanel();
			JPanel second = new JPanel();
			first.add(new JLabel("Name: "));
			first.add(namefield);
			add(first);

			
			if (combobox.getSelectedIndex() == 1) {
				descriptionarea.setLineWrap(true);
				descriptionarea.setWrapStyleWord(true);
				second.setLayout(new BoxLayout(second, BoxLayout.Y_AXIS));
				second.add(new JLabel("Description"));
				descriptionarea.setBounds(0, 0, 150, 150);
				descriptionarea.setPreferredSize(new Dimension(150, 150));
				descriptionarea.setMinimumSize(new Dimension(150, 150));
				descriptionarea.setMaximumSize(new Dimension(150, 150));
				second.add(descriptionarea);
				add(second);
			}
		}

		public boolean correct() {
			if (combobox.getSelectedIndex() == 0) {
				if (namefield.getText().isEmpty()) {
					return false;
				} else
					return true;
			} else if (namefield.getText().isEmpty() || descriptionarea.getText().isEmpty()) {
				return false;
			}
			return true;
		}

		public String getName() {
			return namefield.getText();
		}

		public String getDescription() {
			return descriptionarea.getText();
		}
	}

	class ColorSelection implements ChangeListener {
		public void stateChanged(ChangeEvent ce) {
			ccolor = cc.getColor();
		}
	}

	class Map extends JComponent implements Serializable {

		private static final long serialVersionUID = 1L;
		int h;
		int w;
		String mapLink = "";

		public Map() {
		}

		protected void newMap() throws IOException {
			int answer = jfc.showOpenDialog(MapLocator.this);
			if (answer == JFileChooser.APPROVE_OPTION) {
				File file = jfc.getSelectedFile();
				String filename = file.getAbsolutePath();
				map.setMap(filename);
			}
		}

		public void setMap(String filename) {
			mapLink = filename;
			mapPhoto = new ImageIcon(filename);
			while (mapPhoto.getIconHeight() == -1) {
		}
			
			setWH();
			Dimension d = new Dimension(mapPhoto.getIconWidth(),
					mapPhoto.getIconHeight());
			map.setPreferredSize(d);
			map.setMinimumSize(d);
			map.setMaximumSize(d);
			map.validate();
			map.repaint();
			
			mapscrollingbar.setPreferredSize(d);
			mapscrollingbar.validate();
			mapscrollingbar.repaint();
			
			pack();
			validate();
			repaint();
			
			notsaved = true;
			newunsaved = true;
			opened = true;
		}

		public String getMapLink() {
			return mapLink;
		}

		public void setMapLink(String s) {
			mapLink = s;
		}

		public void setWH() {
			h = mapPhoto.getIconHeight();
			w = mapPhoto.getIconWidth();

		}

		protected void paintComponent(Graphics g) {
			if (mapLink.length() > 0) {
				super.paintComponent(g);
				g.drawImage(mapPhoto.getImage(), 0, 0, w, h, this);
			}
		}
	}

	class Place extends JComponent implements Serializable {

		private static final long serialVersionUID = 1L;
		
		private String name;
		private Position position;
		private String nameofcategory = "";
		
		private boolean marked = false;
		private boolean open = false;
		private boolean visible = true;
		private boolean categoryexists = false;
		
		private Category category;
		private MousePointer pointer;
		private SelectedBox sb;
		private NameBox nb;

		public Place(String name, Position p) {
			pointer = new MousePointer(p);
			sb = new SelectedBox(p);
			nb = new NameBox(name, p);
			this.name = name;
			this.position = p;
		}

		public boolean getMarked() {
			return marked;
		}

		public boolean getVisible() {
			return visible;
		}

		public MousePointer getPointer() {
			return pointer;
		}

		public SelectedBox getSelectedBox() {
			return sb;
		}

		public NameBox getNameBox() {
			return nb;
		}

		public Category getCategory() {
			return category;
		}

		public void removeCategory() {
			category = null;
		}

		public void setCategory(Category c) {
			category = c;
			categoryexists = true;
			nameofcategory = c.getName();
		}

		public String getCategoriname() {
			return nameofcategory;
		}

		public boolean getHasCategory() {
			return categoryexists;
		}

		public String getName() {
			return name;
		}

		public Position getPosition() {
			return position;
		}

		public void setOpen() {
			if (nb.getOpen()) {
				nb.setOpen();
				nb.setVisible(false);
				map.remove(nb);
				
				map.repaint();
			} else {
				nb.setOpen();
				nb.setVisible(true);
				map.add(nb);
				nb.repaint();
				
				map.repaint();
			}
		}

		public void setMarked() {
			if (sb.getMarked()) {
				marked = false;
				sb.setVisible(false);
				sb.setMarked();
				map.remove(sb);
				
				map.repaint();
			} else {
				marked = true;
				sb.setMarked();
				sb.setVisible(true);
				map.add(sb);
				
				sb.repaint();
				map.repaint();
			}
		}

		public void setVisible() {
			if (visible) {
				visible = false;
				pointer.setVisible(false);
				sb.setVisible(false);
				nb.setVisible(false);
				
				map.repaint();
			} else {
				visible = true;
				pointer.setVisible(true);
				map.add(pointer);
				
				map.repaint();
			}
		}
	}

	class DescribedPlace extends Place {
		
		private String description;
		private PlaceDisplay pd;

		public DescribedPlace(String name, Position p, String description) {
			super(name, p);
			pd = new PlaceDisplay(p, description);
			this.description = description;
		}

		public String getDescription() {
			return description;
		}

		public void setOpen() {
			if (pd.open) {
				pd.setOpen();
				pd.setVisible(false);
				map.remove(pd);
				map.repaint();
			} else {
				pd.setOpen();
				pd.setVisible(true);
				map.add(pd);
				pd.repaint();
				map.repaint();
			}
			super.setOpen();
		}

		public void setVisible() {
			if (super.isVisible()) {
				pd.setVisible(false);
			} else {
				pd.setVisible(true);
			}
			super.setVisible();
		}
	}

	class PhotoPlace extends Place {
		private Image i;

		public PhotoPlace(String name, Position p, Image picture) {
			super(name, p);
		}

		public Image getImage() {
			return i;
		}
	}

	public static void main(String[] args) {
		new MapLocator();

	}
}
