package Sample;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class newDescribedPlace extends JPanel {
    JTextField name;
    JTextArea description;
    JLabel nameOfPlace;
    JLabel descriptionOfPlace;

    newDescribedPlace() {
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, 1));
        JPanel row1 = new JPanel();
        this.nameOfPlace = new JLabel("Name of the place:");
        row1.add(this.nameOfPlace);
        this.name = new JTextField(10);
        row1.add(this.name);
        JPanel row2 = new JPanel();
        this.descriptionOfPlace = new JLabel("Description of the place:");
        row2.add(this.descriptionOfPlace);
        this.description = new JTextArea(2, 10);
        this.description.setColumns(10);
        this.description.setLineWrap(true);
        this.description.setWrapStyleWord(false);
        row2.add(this.description);
        form.add(row1);
        form.add(row2);
        JOptionPane.showOptionDialog((Component)null, form, "New described place", 2, 3, (Icon)null, (Object[])null, (Object)null);
    }

    public String getName() {
        return this.name.getText();
    }

    public String getDescription() {
        return this.description.getText();
    }
}
