package Sample;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class MapLocator extends JFrame {
    Boolean unsavednew = Boolean.valueOf(false);
    Boolean unsaved = Boolean.valueOf(false);
    PicturePanel pp = null;
    JFileChooser jfc = new JFileChooser(".");
    String[] message = new String[]{"Described Place", "Named Place"};
    int x;
    int y;
    Category cate;
    NamedPlace np;
    DescribedPlace dp;
    private JMenuBar menuBar;
    private JMenu archive;
    private JMenuItem menuNew;
    private JMenuItem menuOpen;
    private JMenuItem menuSave;
    private JMenuItem menuExit;
    private JComboBox options;
    private JButton search;
    private JButton hidepl;
    private JButton deletepl;
    private JButton whatis;
    private JButton hideca;
    private JButton newca;
    private JButton deleteca;
    private JLabel comboLabel;
    private JLabel invinsiblespace;
    private JLabel categories;
    private JLabel hiddenLabel;
    private JLabel hiddenLabel2;
    private JTextField searchField;
    private JTextField nameField;
    private JPanel panel;
    private JPanel east;
    private JPanel north;
    private JPanel eastBox;
    JColorChooser jcc;
    Map<Position, Place> where = new HashMap();
    CategoryListModel dataModel = new CategoryListModel();
    ArrayList<Category> catList = new ArrayList();
    JList<String> category;
    NamedPlaceListener namedLyss;
    DescribedPlaceListener describedLyss;
/**/
    MapLocator() {
        super("Map Locator");
        this.category = new JList(this.dataModel);
        this.namedLyss = new NamedPlaceListener(this);
        this.describedLyss = new DescribedPlaceListener(this);
        this.menuBar = new JMenuBar();
        this.setJMenuBar(this.menuBar);
        this.archive = new JMenu("Archive");
        this.menuBar.add(this.archive);
        this.menuNew = new JMenuItem("New map");
        this.archive.add(this.menuNew);
        this.menuNew.addActionListener(new NewAction(this));
        this.menuOpen = new JMenuItem("Open");
        this.archive.add(this.menuOpen);
        this.menuOpen.addActionListener(new OpenAction(this));
        this.menuSave = new JMenuItem("Save");
        this.archive.add(this.menuSave);
        this.menuSave.addActionListener(new SaveButtonAction(this));
        this.menuExit = new JMenuItem("Exit");
        this.archive.add(this.menuExit);
        this.menuExit.addActionListener(new ExitAction(this));
        this.north = new JPanel();
        this.add(this.north, "North");
        this.comboLabel = new JLabel("          New:   ");
        this.options = new JComboBox(this.message);
        this.options.addActionListener(new MouseListener(this));
        this.north.add(this.comboLabel);
        this.north.add(this.options);
        this.searchField = new JTextField("Search...     ");
        this.searchField.setColumns(15);
        this.searchField.addMouseListener(new SearchTextAction(this));
        this.north.add(this.searchField);
        this.invinsiblespace = new JLabel();
        this.invinsiblespace.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.north.add(this.invinsiblespace);
        this.search = new JButton("     Search     ");
        this.north.add(this.search);
        this.hidepl = new JButton("     Hide places     ");
        this.hidepl.addActionListener(new HidePlace(this));
        this.north.add(this.hidepl);
        this.deletepl = new JButton("     Delete places     ");
        this.deletepl.addActionListener(new DeletePlacesListener(this));
        this.north.add(this.deletepl);
        this.whatis = new JButton("     What is here?     ");
        this.north.add(this.whatis);
        JLabel emptyLabel = new JLabel();
        emptyLabel.setText("");
        emptyLabel.setBorder(new EmptyBorder(5, 100, 5, 0));
        this.north.add(emptyLabel);
        JLabel emptyLabel2 = new JLabel();
        emptyLabel2.setText("");
        emptyLabel2.setBorder(new EmptyBorder(5, 100, 5, 0));
        this.north.add(emptyLabel2);
        this.east = new JPanel();
        this.east.setLayout(new BoxLayout(this.east, 1));
        this.add(this.east);
        this.categories = new JLabel("Categories:");
        this.east.add(this.categories);
        this.category = new JList(this.dataModel);
        this.category.setVisibleRowCount(5);
        this.category.setFixedCellWidth(80);
        this.category.setLayoutOrientation(0);
        this.category.setSelectionMode(0);
        JScrollPane listScroller = new JScrollPane(this.category);
        listScroller.setMaximumSize(new Dimension(300, 250));
        this.east.add(listScroller);
        this.hideca = new JButton("Hide category");
        this.east.add(this.hideca);
        this.hideca.addActionListener(new HideCategoryListener(this));
        this.hiddenLabel = new JLabel();
        this.east.add(this.hiddenLabel);
        this.newca = new JButton("New category");
        this.east.add(this.newca);
        this.newca.addActionListener(new CategoryListener(this));
        this.deleteca = new JButton("Delete category");
        this.deleteca.addActionListener(new DeleteCategoryListener(this));
        this.east.add(this.deleteca);
        this.eastBox = new JPanel();
        this.eastBox.setLayout(new BorderLayout());
        this.add(this.eastBox, "East");
        this.eastBox.add(this.east);
        this.setLocationRelativeTo((Component)null);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(0);
        this.addWindowListener(new SaveAction(this));
        this.repaint();
        this.validate();
    }
}
/** TODO FIX UP CLASSES */


class MapLocator$CategoryListener implements ActionListener {
    MapLocator$CategoryListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, 1));
        JPanel row1 = new JPanel();
        this.this$0.add(row1);
        row1.add(new JLabel("Category name: "));
        MapLocator.access$1(this.this$0, new JTextField(15));
        row1.add(MapLocator.access$2(this.this$0));
        categoryPanel.add(row1);
        JPanel row2 = new JPanel();
        this.this$0.add(row2);
        row2.add(new JLabel("Choose a colour: "));
        this.this$0.jcc = new JColorChooser();
        row2.add(this.this$0.jcc);
        categoryPanel.add(row2);
        int answer = JOptionPane.showConfirmDialog((Component)null, categoryPanel, "New category", 2, 3);
        if(answer == 0) {
            String str = MapLocator.access$2(this.this$0).getText();
            Color clr = this.this$0.jcc.getColor();
            Category newCat = new Category(str, clr);
            this.this$0.catList.add(newCat);
            this.this$0.dataModel.removeAllElements();

            for(int i = 0; i < this.this$0.catList.size(); ++i) {
                Category tempCat = (Category)this.this$0.catList.get(i);
                this.this$0.dataModel.addElement(tempCat.getName());
            }
        } else if(answer == 2) {
            JOptionPane.showMessageDialog((Component)null, "Category not added");
        }

    }
}
class MapLocator$DeleteCategoryListener implements ActionListener {
    MapLocator$DeleteCategoryListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        int selectedIndex = this.this$0.category.getSelectedIndex();
        if(selectedIndex >= 0) {
            String str = (String)this.this$0.category.getSelectedValue();
            this.this$0.dataModel.remove(selectedIndex);

            for(int i = 0; i < this.this$0.catList.size(); ++i) {
                Category tmpCat = (Category)this.this$0.catList.get(i);
                String search = tmpCat.getName();
                if(search == str) {
                    Collection<Place> tmpPlace = this.this$0.where.values();
                    Iterator iter = tmpPlace.iterator();

                    while(iter.hasNext()) {
                        Place nextPlace = (Place)iter.next();
                        if(nextPlace.getCat() != null && nextPlace.getCat() == tmpCat) {
                            iter.remove();
                            this.this$0.pp.remove(nextPlace);
                        }
                    }

                    this.this$0.catList.remove(i);
                    this.this$0.repaint();
                }
            }
        }

    }
}

class MapLocator$DeletePlacesListener implements ActionListener {
    MapLocator$DeletePlacesListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        Collection<Place> tmpPlace = this.this$0.where.values();
        Iterator iter = tmpPlace.iterator();

        while(iter.hasNext()) {
            Place nextPlace = (Place)iter.next();
            nextPlace.setShowed(false);
            this.this$0.pp.remove(nextPlace);
            iter.remove();
            this.this$0.pp.repaint();
        }

    }
}

class MapLocator$DescribedPlaceListener extends MouseAdapter {
    MapLocator$DescribedPlaceListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void mouseClicked(MouseEvent mev) {
        int x = mev.getX();
        int y = mev.getY();
        String which = (String)this.this$0.category.getSelectedValue();
        Category cate = null;

        String search;
        for(int i = 0; i < this.this$0.catList.size(); ++i) {
            Category tmpCat = (Category)this.this$0.catList.get(i);
            search = tmpCat.getName();
            if(search == which) {
                cate = (Category)this.this$0.catList.get(i);
            }
        }

        newDescribedPlace newDP = new newDescribedPlace();
        String name = newDP.getName();
        search = newDP.getDescription();
        DescribedPlace dp = new DescribedPlace(x - 19, y - 38, name, search, cate);
        Position pos = new Position(x, y);
        this.this$0.where.put(pos, dp);
        this.this$0.pp.add(dp);
        dp.repaint();
        dp.validate();
        this.this$0.pp.removeMouseListener(this.this$0.describedLyss);
        this.this$0.pp.setCursor(Cursor.getDefaultCursor());
    }
}
class MapLocator$ExitAction implements ActionListener {
    MapLocator$ExitAction(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent e) {
        int answer = JOptionPane.showConfirmDialog((Component)null, "Do you wish to quit without saving?", "Exiting application", 0);
        if(answer == 0) {
            System.exit(0);
        }

    }
}

class MapLocator$HideCategoryListener implements ActionListener {
    MapLocator$HideCategoryListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        int selectedIndex = this.this$0.category.getSelectedIndex();
        if(selectedIndex >= 0) {
            String str = (String)this.this$0.category.getSelectedValue();

            for(int i = 0; i < this.this$0.catList.size(); ++i) {
                Category tmpCat = (Category)this.this$0.catList.get(i);
                String search = tmpCat.getName();
                if(search == str) {
                    Collection<Place> tmpPlace = this.this$0.where.values();
                    Iterator iter = tmpPlace.iterator();

                    while(iter.hasNext()) {
                        Place nextPlace = (Place)iter.next();
                        if(nextPlace.getCat() != null && nextPlace.getCat() == tmpCat) {
                            nextPlace.setShowed(false);
                            this.this$0.pp.repaint();
                        }
                    }
                }
            }
        }

    }
}

class MapLocator$HidePlace implements ActionListener {
    MapLocator$HidePlace(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        Collection<Place> tmpPlace = this.this$0.where.values();
        Iterator iter = tmpPlace.iterator();

        while(iter.hasNext()) {
            Place nextPlace = (Place)iter.next();
            nextPlace.setShowed(false);
            this.this$0.pp.repaint();
        }

    }
}


class MapLocator$MarkedPlace extends MouseAdapter {
    MapLocator$MarkedPlace(MapLocator var1) {
        this.this$0 = var1;
    }

    public void mouseClicked(MouseEvent mev) {
        if(mev.getButton() == 1) {
            Place p = (Place)mev.getSource();
            if(!p.isMarked()) {
                p.setMarked(true);
            } else if(p.isMarked()) {
                p.setMarked(false);
            }
        }

    }
}

class MapLocator$MouseListener implements ActionListener {
    MapLocator$MouseListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        Object c = ave.getSource();
        if(c == MapLocator.access$3(this.this$0)) {
            JComboBox cb = (JComboBox)ave.getSource();
            String passed = (String)cb.getSelectedItem();
            if(passed.equals("Named Place")) {
                this.this$0.pp.addMouseListener(this.this$0.namedLyss);
                this.this$0.pp.setCursor(Cursor.getPredefinedCursor(1));
            }

            if(passed.equals("Described Place")) {
                this.this$0.pp.addMouseListener(this.this$0.describedLyss);
                this.this$0.pp.setCursor(Cursor.getPredefinedCursor(1));
            }
        }

    }
}

public class MapLocator$NamedPlaceListener extends MouseAdapter {
    public MapLocator$NamedPlaceListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void mouseClicked(MouseEvent mev) {
        int x = mev.getX();
        int y = mev.getY();
        String which = (String)this.this$0.category.getSelectedValue();
        Category cate = null;

        for(int i = 0; i < this.this$0.catList.size(); ++i) {
            Category tmpCat = (Category)this.this$0.catList.get(i);
            String search = tmpCat.getName();
            if(search == which) {
                cate = (Category)this.this$0.catList.get(i);
            }
        }

        newNamedPlace nmp = new newNamedPlace();
        String name = nmp.getName();
        NamedPlace np = new NamedPlace(x - 19, y - 38, name, cate);
        this.this$0.pp.add(np);
        Position pos = new Position(x, y);
        this.this$0.where.put(pos, np);
        np.repaint();
        np.validate();
        this.this$0.pp.removeMouseListener(this.this$0.namedLyss);
        this.this$0.pp.setCursor(Cursor.getDefaultCursor());
    }
}

class MapLocator$NewAction implements ActionListener {
    MapLocator$NewAction(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        int answer = this.this$0.jfc.showOpenDialog(this.this$0);
        if(answer == 0) {
            File file = this.this$0.jfc.getSelectedFile();
            String filename = file.getAbsolutePath();
            if(this.this$0.pp != null) {
                this.this$0.remove(this.this$0.pp);
            }

            this.this$0.pp = new PicturePanel(filename);
            this.this$0.add(this.this$0.pp);
            this.this$0.pack();
            this.this$0.validate();
            this.this$0.repaint();
        }
    }
}
class MapLocator$OpenAction implements ActionListener {
    MapLocator$OpenAction(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        int state = this.this$0.jfc.showOpenDialog((Component)null);
        File file = this.this$0.jfc.getSelectedFile();
        if(file != null && state == 0) {
            JOptionPane.showMessageDialog((Component)null, file.getPath());
        } else if(state == 1) {
            JOptionPane.showMessageDialog((Component)null, "Canceled");
        }

    }
}
class MapLocator$SaveAction extends WindowAdapter {
    MapLocator$SaveAction(MapLocator var1) {
        this.this$0 = var1;
    }

    public void windowClosing(WindowEvent e) {
        int answer = JOptionPane.showConfirmDialog((Component)null, "Do you wish to quit without saving?", "Exiting application", 0);
        if(answer == 0) {
            this.this$0.setDefaultCloseOperation(3);
        } else {
            this.this$0.jfc.showSaveDialog((Component)null);
            this.this$0.jfc.getSelectedFile().getPath();
            this.this$0.unsaved = Boolean.valueOf(false);
            this.this$0.unsavednew = Boolean.valueOf(false);
        }

    }
}

class MapLocator$SaveButtonAction implements ActionListener {
    MapLocator$SaveButtonAction(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
        this.this$0.jfc.showSaveDialog((Component)null);
        this.this$0.jfc.getSelectedFile().getPath();
        this.this$0.unsaved = Boolean.valueOf(false);
        this.this$0.unsavednew = Boolean.valueOf(false);
    }
}

class MapLocator$SearchListener implements ActionListener {
    MapLocator$SearchListener(MapLocator var1) {
        this.this$0 = var1;
    }

    public void actionPerformed(ActionEvent ave) {
    }
}

class MapLocator$SearchTextAction extends MouseAdapter {
    MapLocator$SearchTextAction(MapLocator var1) {
        this.this$0 = var1;
    }

    public void mouseClicked(MouseEvent mev) {
        MapLocator.access$0(this.this$0).setText("");
    }
}
class MapLocator$ShowedPlace extends MouseAdapter {
    MapLocator$ShowedPlace(MapLocator var1) {
        this.this$0 = var1;
    }

    public void mouseClicked(MouseEvent mev) {
        if(mev.getButton() == 2) {
            Place p = (Place)mev.getSource();
            if(!p.isShowed()) {
                p.setShowed(true);
            } else if(p.isShowed()) {
                p.setShowed(false);
            }
        }

    }
}


