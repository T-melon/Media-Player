package ui;

import value.PlayerColor;

import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
//RenderingHints类定义了多种着色微调,它们存储在一个映射集的Graphics2D里

/**
 * @author Tian
 * 美化JSlider,主要通过public void paintThumb()及public void paintTrack()
 * 不直接用paint()是因为paint()方法通过间接调用这两个方法进行绘制,换言之paint方法里包含这两个方法
 * 故直接重写paintThumb()及paintTrack()即可
 *
 * JSlider是Swing中的滑块控件,在交互过程中用户可拖动它来实现数值的调整
 * 它具有3个基本参数,分别为:最小值,最大值和初始值,默认值分别为:0,100,50
 * 滑块的值发生改变时,会产生ChangeEvent事件,因此必须事先为其绑定ChangeListener监听器,并在响应函数中使用getValue方法跟踪其最新值
 * 滑块可以具有多种式样,首先是标尺
 * 使用setMajorTickSpacing方法设置大格子的间距,setMinorTickSpacing设置小格子的间距,效果类似于直尺中的厘米刻度与毫米刻度
 * 使用setPaintTicks(bool agr)设置是否显示标尺
 * 使用setSnapToTicks设置滑块强制对应到标尺,开启后,点击滑块时它会跳到最近的下一刻度上
 * 使用setInverted可以设置标尺方向反转
 * 使用setPaintLabels可强制显示标尺刻度的标签,可使用setLabelTable为标尺设置各刻度对应的JLabel
 * 其参数为一个Hashtable<Integer , Component>(),储存了刻度值与JLabel的对应表,当然也可以为所有JLabel添加图标
 */
public class SliderUi extends BasicSliderUI {

    public SliderUi(JSlider b) {
        super(b);
    }

    /**
     * 指定方法重写
     * <img width="640" height="320" src="https://raw.github.com/wiki/ReactiveX/RxJava/images/rx-operators/legend.png" alt="">
     *     注释添加图片
     */
    @Override
    public void paintThumb(Graphics g) { //绘制游标
        Graphics2D g2d = (Graphics2D) g;
//        把绘制区的x,y点坐标定义为坐标系的原点
//        这句话不可省略,否则拖动失效
//        实际上,渲染引擎在绘制Graphics2D图形前会查看7个主要的属性:Paint,Stroke,Font,Transformation,Clipping space,Rendering hints,Compositing rule
        BasicStroke stroke = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
//        在JAVA 2D开发中,BasicStroke用于定义线条的特征(图形或文字的轮廓)
//        width:线条宽度
//        cap:只能取3个值:CAP_BUTT,CAP_ROUND or CAP_SQUARE,表示不同的线端
//        join:表示当两条线连接时,连接处的形状,可以取JOIN_ROUND,JOIN_BEVEL,or JOIN_MITER
//        端点样式使用默认的CAP_SQUARE,默认接头样式使用默认的JOIN_MITER
        g2d.setStroke(stroke);
//        调用Graphics2D类中的setStroke方法将新创建的BasicStroke对象设置进去
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
//        组合规则决定图形之间颜色的相互影响,例如图片或图形的不透明度属于整个分类
//        Java 2D允许分配透明(alpha)值,以便底层的图形可以显示出来,通常我们会创建一个java.awt.AlphaComposite对象,然后传入 setComposite()方法的实现
//        通常,用AlphaComposite.getInstance()方法,配合一定的混合规则和透明度值,创建AlphaComposite对象
//        Java 2D内建了一些符合 Porter-Duff 组合规则的混合规则,但在处理不透明时,通常会使用AlphaComposite.SRC_OVER
//        透明值由透明到不透明是在0.0和1.0之间
//        有AlphaComposite.CLEAR/DST/DST_ATOP/DST_IN/DST_OUT/DST_OVER/SRC/SRC_ATOP/SRC_IN/SRC_OUT/SRC_OVER - 源覆盖在目标之上
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        Rendering hints是Graphics2D对象描绘基本类型时使用的各种描绘方法
//        setRenderingHint() 方法的参数是一个键值对的形式,KEY_ANTIALIASING决定是否使用抗锯齿,可能的值有VALUE_ANTIALIAS_ON(使用抗锯齿),VALUE_ANTIALIAS_OFF(不使用抗锯齿)和VALUE_ANTIALIAS_DEFAULT(默认的抗锯齿)
//        两个最常用的设置是抗锯齿(混合锯齿柔滑锯齿线)和高质量渲染
        GradientPaint gp = new GradientPaint(0, 0, PlayerColor.Default, 0, thumbRect.height, PlayerColor.Default);
//        java.awt.GradientPaint类用颜色渐变填充一个区域,构造函数制定比例和颜色,图形引擎会从第一个点到第二个点之间线性变化两个颜色,还可以指定颜色图案是否允许重复
//        thumbRect:滑块的触摸范围
        g2d.setPaint(gp);
//        新的paint可以同时作用在边线和填充上
//        Graphics2D类可以用setPaint()和getPaint()方法配制paint属性
//        paint可以是单色,渐变和图案,任何paint都需要实现java.awt.Paint接口
        g2d.fillOval(thumbRect.x, thumbRect.y + 5, 10, 10);
//        填充椭圆方法画点
        g2d.drawLine(0, thumbRect.height / 2, thumbRect.x + 8, thumbRect.height / 2);
//        绘制一条连接由坐标对指定的两个点的线条
    }

    @Override
    public void paintTrack(Graphics g) { //绘制滑道
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        // 设定渐变
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g2d.setPaint(new GradientPaint(0, 0, PlayerColor.SliderStart, 0, trackRect.height, PlayerColor.SliderEnd, true));
        g2d.setStroke(new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2d.drawLine(8, trackRect.height / 2 + 1, trackRect.width + 8, trackRect.height / 2 + 1);
    }
}
