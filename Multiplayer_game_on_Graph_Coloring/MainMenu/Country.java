
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.List;
import java.util.ListIterator;

/**
 * Write a description of class Country here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Country extends Actor implements iRegion
{
    Color lineColor = Color.YELLOW;
    Color fillColor = Color.GRAY;
    static final Color transparent = new Color(0,0,0,0);
    int width = 600;
    int height = 200;
    int xPos = 0;
    int yPos = 0;
    private int clickCount;
    boolean isClicked = isClickInRange();
    boolean addedToWorld = false;

    int id;

    public void addedToWorld(World world) {
        this.addedToWorld = true;
    }

    /**
     * Act - do whatever the Country wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act() 
    {
        if (Greenfoot.mouseClicked(this)){

            if (! isClickInRange()) {
                // Forward click to any intersecting objects
                List<Country> countries = this.getIntersectingObjects(Country.class);
                ListIterator<Country> li = countries.listIterator();
                while (li.hasNext()){
                    Country c = li.next();
                    c.mouseClicked();
                }
            } else {
                mouseClicked();
            }
        }
    }    

    void mouseClicked() {

        if (! isClickInRange()){
            return;
        }

        BaseGraph world = (BaseGraph)getWorld();
        fillColor = world.selectedColor();
        world.setCountryColor(id);
    }

    boolean isClickInRange() {
        if (! this.addedToWorld) return false;
        MouseInfo mi = Greenfoot.getMouseInfo();
        if (Greenfoot.getMouseInfo() == null) return false;
        int selfx = getX();
        int selfy = getY();
        int clickx = mi.getX();
        int clicky = mi.getY();

        int x = clickx - selfx;
        int y = clicky - selfy;
        int rot = getRotation();
        return Math.abs(x) < width/2.0 && Math.abs(y) < height/2.0;
    }

    /**
     * A function to check the validity of country color.  
     * true if adjacent colors do not match this country color
     * false if adjacent countries have color that match this country color
     * Ignores GRAY, which is uninitialized color
     */
    // Validation for the color selected for the object
    public boolean checkColor(Color needToColor){
        String needtoColorString =  Utils.getInstance().colorToString(needToColor);
        if (needToColor == Color.GRAY) {
            return true;
        }
        BaseGraph world = (BaseGraph)getWorld();
        List<Country> countries = getIntersectingObjects(Country.class);
        ListIterator<Country> it = countries.listIterator();
        while (it.hasNext()){
            Country c = it.next();
            String adjColor =world.colorMap.get(c.getId());
            if (adjColor!=null && adjColor.equals(needtoColorString)){
                world.validLabel.setValue("Invalid Color");
                Greenfoot.playSound("invalid.wav");
                return false;
            }
        }
        world.validLabel.setValue("Valid Color");
        Greenfoot.playSound("valid.wav");
        return true;
    }

    public Country() {
        updateImage();
    }

    public Country(int x, int y, int w, int h, int id) {
        xPos = x;
        yPos = y;
        width = w;
        height = h;
        this.id = id;
        updateImage();
    }

    public void draw(int id)
    {
        this.id = id;
        GreenfootImage img = new GreenfootImage(51, 51);
        img.setColor(Color.black);
        img.drawRect(0,0,50,50);
        setImage(img);
    }
    /**
     * Method used to update color of country
     */
    public boolean updateColor(Color color){
        if(checkColor(color)){
            fillColor = color;
            updateImage();
            BaseGraph world = (BaseGraph)getWorld();
            String filledColorString = Utils.getInstance().colorToString(fillColor);
            world.colorMap.put(id,filledColorString);
            world.checkEndGame(); 
            return true;
        }
        return false;
    }

    void updateWholeCountry(Color color){
        fillColor = color;
        updateImage();

    }

    /**
     * Update the image on screen to show the current value.
     */
    void updateImage()
    {
        GreenfootImage image = new GreenfootImage(width, height);
        drawInImage(image);
        setImage(image);
    }

    void drawInImage(GreenfootImage image) {
        image.setColor(lineColor);
        image.drawRect(0, 0, width-1, height-1);
        image.setColor(fillColor);
        image.fillRect(1, 1, width-2, height-2);
    }

    public Color getColor() {
        return fillColor;
    }

    public Integer getId(){
        return this.id;
    }

}