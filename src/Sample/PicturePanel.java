package Sample;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.LayoutManager;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

class PicturePanel extends JPanel {
    private ImageIcon picture;

    public PicturePanel(String filename) {
        this.setLayout((LayoutManager)null);
        this.picture = new ImageIcon(filename);
        int w = this.picture.getIconWidth();
        int h = this.picture.getIconHeight();
        Dimension d = new Dimension(w, h);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(this.picture.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
    }
}
