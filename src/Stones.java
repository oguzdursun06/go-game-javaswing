import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;

import javax.swing.border.Border;

public class Stones implements Border {

    private int radius;
    private Color color;

    Stones(int radius,Color color) {
        this.radius = radius;
        this.color = color;
    }


    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
    }


    public boolean isBorderOpaque() {
        return true;
    }


    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        g.setColor(color);
        g.fillOval(x, y, width-1, height-1);
        
    }
}
