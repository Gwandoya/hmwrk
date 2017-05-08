package Sample;

import java.awt.Component;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class newNamedPlace extends JPanel {
    JTextField name;
    JLabel label;
    JPanel form = new JPanel();
    JPanel row1;

    newNamedPlace() {
        this.form.setLayout(new BoxLayout(this.form, 1));
        this.row1 = new JPanel();
        this.label = new JLabel("Name of the place: ");
        this.name = new JTextField(10);
        this.row1.add(this.label);
        this.row1.add(this.name);
        this.form.add(this.row1);
        JOptionPane.showOptionDialog((Component)null, this.form, "New named place", 2, 3, (Icon)null, (Object[])null, (Object)null);
    }

    public String getName() {
        return this.name.getText();
    }
}
