package Sample;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.JComponent;

class DescriptionText extends JComponent {
    private boolean open = false;
    private Color color;
    private String str;
    private int x;
    private int y;
    private int row;

    public DescriptionText(Position p, String s) {
        this.color = Color.black;
        this.x = p.getX();
        this.y = p.getY();
        this.str = s;
        this.row = (this.str.length() / 10 + 1) * 13;
        this.setBounds(this.x + 20, this.y - 10, 60, this.row);
        Dimension d = new Dimension(60, this.row);
        this.setPreferredSize(d);
        this.setMaximumSize(d);
        this.setMinimumSize(d);
    }

    public void setOpen() {
        if(this.open) {
            this.open = false;
            this.repaint();
        } else {
            this.open = true;
        }

        this.repaint();
    }

    public boolean getOpen() {
        return this.open;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(this.open) {
            g.setColor(Color.white);
            g.fillRect(0, 0, 60, this.row);
            String strt = this.str;
            g.setColor(this.color);
            g.setFont(new Font("TimesRoman", 0, 12));
            int index = 10;
            int yPos = 10;

            String str1;
            for(str1 = this.str; index < this.str.length(); index += 10) {
                str1 = strt.substring(index - 10, index);
                g.drawString(str1, 2, yPos);
                yPos += 13;
            }

            g.drawString(str1, 2, yPos);
        }

    }
}
