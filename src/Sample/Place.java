package Sample;

import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JComponent;

abstract class Place extends JComponent {
    private boolean showed = false;
    private boolean marked = false;
    private boolean opened = false;
    private boolean visible = true;

    public Place(int x, int y) {
        this.setBounds(x, y, 200, 150);
        Dimension d = new Dimension(50, 50);
        this.setPreferredSize(d);
        this.setMinimumSize(d);
        this.setMaximumSize(d);
    }

    protected abstract void show(Graphics var1);

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.show(g);
    }

    public boolean getMarked() {
        return this.marked;
    }

    public boolean getVisible() {
        return this.visible;
    }

    public void setShowed(boolean b) {
        this.showed = b;
        this.repaint();
    }

    public boolean isShowed() {
        return this.showed;
    }

    public boolean isMarked() {
        return this.marked;
    }

    public void setMarked(boolean b) {
    }

    protected abstract Category getCat();
}

