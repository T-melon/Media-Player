package ui;

import value.PlayerColor;

import java.awt.*;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;


/**
 * @author Tian
 */
public class ScrollBarUi extends BasicScrollBarUI {
    @Override
    protected void configureScrollBarColors()
//            配置滚动条颜色
    {
        trackColor = PlayerColor.ListEvenItemBackground;
        setThumbBounds(0, 0, 10, 10);
    }

    @Override
    public Dimension getPreferredSize(JComponent c) {
        c.setPreferredSize(new Dimension(5, 0));
        return super.getPreferredSize(c);
    }

    @Override
    public void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g;
        GradientPaint gp = null;
        if (this.scrollbar.getOrientation() == Adjustable.VERTICAL)
            //VERTICAL:垂直滚动
        {
            gp = new GradientPaint(0, 0, PlayerColor.ListEvenItemBackground, trackBounds.width, 0, PlayerColor.ListEvenItemBackground);
        }
        g2.setPaint(gp);
        g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
        //填充Track
        switch (trackHighlight) {
            case BasicScrollBarUI.DECREASE_HIGHLIGHT:
                this.paintDecreaseHighlight(g);
                break;
            case BasicScrollBarUI.INCREASE_HIGHLIGHT:
                this.paintIncreaseHighlight(g);
                break;
            default:
                break;
        }
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        g.translate(thumbBounds.x, thumbBounds.y);
        // 这句必需,不然拖动就失效了
        g.setColor(PlayerColor.LyricsBackground);
        // 设置把手颜色
        g.drawRoundRect(0, 0, 5, thumbBounds.height - 1, 5, 5);
        // 消除锯齿
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.addRenderingHints(rh);
        // 半透明
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
        g2.fillRoundRect(0, 0, 40, thumbBounds.height - 1, 5, 5);
    }

    @Override
    protected JButton createIncreaseButton(int orientation)
//    创建增加按钮
    {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(null);
        return button;

    }

    @Override
    protected JButton createDecreaseButton(int orientation) {
        JButton button = new JButton();
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusable(false);
        button.setBorder(null);
        return button;
    }

}