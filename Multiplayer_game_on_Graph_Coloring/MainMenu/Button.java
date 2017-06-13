import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Button extends GUIComponent {
    private static final double ASCENT_MULT = .85;
    private static final Color DEFAULT_HOVER = new Color(238, 238, 238);
    private GreenfootImage up;
    private GreenfootImage down;
    private GreenfootImage hover;
    private GreenfootImage bgUp;
    private GreenfootImage bgDown;
    private GreenfootImage bgHover;
    private Color bgColorDown = Color.LIGHT_GRAY;
    private Color bgColorHover = DEFAULT_HOVER;
    private boolean hoverState;

    public Button(String text, int idNumber) {
        super(text,Color.cyan);
        setID(idNumber);
        repaint();
    }
    

    public void act() {
        if (isEnabled()) {
            super.act(); // check for focus request
            if (Greenfoot.mouseMoved(this)) {
                setImage(hover);
            } else if (Greenfoot.mouseMoved(null)) {
                setImage(up);
            }
            if (Greenfoot.mousePressed(this)) {
                setImage(down);
            }
            if (Greenfoot.mouseClicked(null)
            || Greenfoot.mouseDragEnded(null)) {
                setImage(up);
            }
            if (Greenfoot.mouseClicked(this)) {
                setImage(hover);
                fireActionEvent();
            }
        }
    }

    public void paintComponent(Graphics g) {
        hoverState = (hover == getImage());

        int width = getWidth();
        int height = getHeight();
        if (!isFixedSize()) {

            Dimension d = getTextDimension(getText(), g);
            width = d.width;
            height = d.height;
        }

        if (bgUp == null) {
            up = prepareImage(up, getBackground(), width, height);
        } else {
            up.drawImage(bgUp, 0, 0);
        }

        if (bgDown == null) {
            down = prepareImage(down, bgColorDown, width, height);
        } else {
            down.drawImage(bgDown, 0, 0);
        }

        if (bgHover == null) {
            hover = prepareImage(hover, bgColorHover, width, height);
        } else {
            hover.drawImage(bgHover, 0, 0);
        }
        setImage(up);
    }

    private GreenfootImage prepareImage(GreenfootImage img, Color bgColor,
    int imgWidth, int imgHeight) {

        if (img == null || img.getWidth() != imgWidth
        || img.getHeight() != imgHeight || bgColor.getAlpha() < 255) {
            img = new GreenfootImage(imgWidth, imgHeight);
        }
        img.setColor(bgColor);
        img.fill();
        return img;
    }

    protected void paintBorder(Graphics g) {
        super.paintBorder(up.getAwtImage().getGraphics());
        super.paintBorder(down.getAwtImage().getGraphics());
        super.paintBorder(hover.getAwtImage().getGraphics());
    }

    public void paintText(Graphics g) {
        String btnText = getText();
        if (btnText == null || btnText.length() == 0) return;
        String[] lines = splitLines(btnText, g);

        if (getFont() != null) up.setFont(getFont());
        if (getForeground() != null) up.setColor(getForeground());
        printText(up, lines, 0);

        if (getFont() != null) down.setFont(getFont());
        if (getForeground() != null) down.setColor(getForeground());
        printText(down, lines, 1);

        if (getFont() != null) hover.setFont(getFont());
        if (getForeground() != null) hover.setColor(getForeground());
        printText(hover, lines, 0);

        if (hoverState) {
            setImage(hover);
        } else {
            setImage(up);
        }
    }

    private void printText(GreenfootImage img, String[] lines, int offset) {
        Graphics g = img.getAwtImage().getGraphics();
        if (getFont() != null) g.setFont(getFont());
        FontMetrics fm = g.getFontMetrics();
        int lineHeight = (int) (fm.getHeight() * ASCENT_MULT);
        int height = lineHeight * lines.length;
        int width = 0;
        int y = lineHeight + (img.getHeight() - height - fm.getDescent()) / 2;
        for (int i = 0; i < lines.length; i++) {
            int lineWidth = fm.stringWidth(lines[i]);
            width = Math.max(width, lineWidth);
            int x = (img.getWidth() - lineWidth) / 2;
            img.drawString(lines[i], x + offset, y + offset);
            y += lineHeight;
        }
        if (isFocusOwner()) {
            img.setColor(Color.GRAY);
            int x = (img.getWidth() - width) / 2 - 1;
            y = (img.getHeight() - height) / 2;
            img.drawRect(x + offset, y + offset, width + 1, height);
        }
    }
}
