package Sample;

import java.awt.Color;
import javax.swing.JColorChooser;

public class Category {
    private String name;
    private JColorChooser jcc;
    private Color color;

    Category(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return this.name;
    }

    public Color getColor() {
        return this.color;
    }
}
