package Sample;

import java.awt.Color;
import java.awt.Graphics;

class NamedPlace extends Place {
    Category cat;
    String name;

    public NamedPlace(int x, int y, String name, Category cat) {
        super(x, y);
        this.cat = cat;
        this.name = name;
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

    protected Category getCat() {
        return this.cat;
    }
}
