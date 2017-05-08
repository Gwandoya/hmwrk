package Sample;

import java.awt.Color;
import java.awt.Graphics;

class DescribedPlace extends Place {
    String name;
    String description;
    Category cat;

    public DescribedPlace(int x, int y, String name, String description, Category cat) {
        super(x, y);
        this.name = name;
        this.description = description;
        this.cat = cat;
    }

    public DescribedPlace(int x, int y, String name, String description) {
        super(x, y);
        this.name = name;
        this.description = description;
    }

    protected void show(Graphics g) {
        if(this.cat != null) {
            g.setColor(this.cat.getColor());
        } else if(this.cat == null) {
            g.setColor(Color.BLACK);
        }

        int[] x = new int[]{0, 20, 40};
        int[] y = new int[]{0, 40, 0};
        g.fillPolygon(x, y, 3);
    }

    public String getDescription() {
        return this.description;
    }

    protected Category getCat() {
        return this.cat;
    }
}
