
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class TriangleCountry here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TriangleCountry extends Country
{
    /**
     * Triangle will always be drawn with flat part on bottom and one point facing up. 
     * rotateDeg is measured in degrees rotation around the center clockwise.
     */
    public TriangleCountry(int x, int y, int width, int height, int id) {
        super(x, y, width, height, id);
    }

    /**
     * Check if mouse click coincides with image.
     * Since this is a triangle, we will split into 2 triangles, based on x position
     */
    boolean isClickInRange() {
        if (! this.addedToWorld) return false;
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (Greenfoot.getMouseInfo() == null) return false;

        // Check if click in box
        if (! super.isClickInRange()) return false;

        //Normalize x,y
        int selfx = getX();
        int selfy = getY();
        int clickx = mi.getX();
        int clicky = mi.getY();

        int x = clickx - selfx;
        int y = (clicky - selfy) * -1; // Y-axis is inverted in graph
        double halfHeight = height * 0.5;
        double halfWidth = width * 0.5;
        double slope = 1.0 * height / halfWidth;

        // Get rotation
        int rot = getRotation();
        // Break into half triangle regions
        switch (rot) {
            case 0:{
                if (x < 0){
                    return y < slope * x + halfHeight;
                }else {
                    return y < -slope * x + halfHeight;
                }
            }

            case 90: {
                slope = 1.0/slope; // invert slope, width/height b/c of rotation
                if (y > 0) { //top
                    return y < -slope * x + halfWidth/2;
                } else {
                    return y > slope * x - halfWidth/2;
                }
            }

            case 180:{
                if (x < 0) {
                    return y > -slope * x - halfHeight;
                } else {
                    return y > slope * x - halfHeight;
                }
            }

            case 270:{
                slope = 1.0/slope; // invert slope, height/width b/c of rotation
                if (y > 0) { // top
                    return y < slope * x + halfWidth/2;
                } else {
                    return y > -slope * x - halfWidth/2;
                }
            }

        }
        return true;

    }

    void drawInImage(GreenfootImage image) {
        int halfWidth = this.width / 2;
        int halfHeight = this.height / 2;
        int border = 1;
        image.setColor(lineColor);
        int[] xPoints = {xPos+border, xPos+halfWidth, xPos+width-border};
        int[] yPoints = {yPos+height-border, yPos+border, yPos+height-border};
        image.drawPolygon(xPoints, yPoints, 3);
        image.setColor(fillColor);
        image.fillPolygon(xPoints, yPoints, 3);
    }
}