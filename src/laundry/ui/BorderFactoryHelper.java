package laundry.ui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BorderFactoryHelper {
    
    public static Border createRoundedBorder(int radius) {
        return new RoundedBorder(radius);
    }
    
    static class RoundedBorder implements Border {
        private int radius;
        
        public RoundedBorder(int radius) {
            this.radius = radius;
        }
        
        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
        }
        
        @Override
        public boolean isBorderOpaque() {
            return false;
        }
        
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new Color(200, 200, 200));
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }
    }
}