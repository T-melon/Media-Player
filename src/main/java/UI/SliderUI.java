package UI;

import Value.PlayerColor;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

public class SliderUI extends BasicSliderUI {

    public SliderUI(JSlider b) {
        super(b);
    }

    @Override
    public void paintThumb(Graphics g) { //绘制游标
        Graphics2D g2d = (Graphics2D) g;
        BasicStroke stroke = new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(stroke);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp = new GradientPaint(0, 0, PlayerColor.Default, 0, thumbRect.height, PlayerColor.Default);
        g2d.setPaint(gp);
        g2d.fillOval(thumbRect.x, thumbRect.y + 5, 10, 10);
        BasicStroke stroke1 = new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        g2d.setStroke(stroke1);
        g2d.drawLine(0, thumbRect.height / 2, thumbRect.x + 8, thumbRect.height / 2);
    }

    @Override
    public void paintTrack(Graphics g) { //绘制滑道
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);// 设定渐变
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setPaint(new GradientPaint(0, 0, PlayerColor.SliderStart, 0, trackRect.height, PlayerColor.SliderEnd, true));
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(8, trackRect.height / 2 + 1, trackRect.width + 8, trackRect.height / 2 + 1);
    }
}
