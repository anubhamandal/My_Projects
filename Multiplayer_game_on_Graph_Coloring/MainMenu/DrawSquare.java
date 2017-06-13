import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import greenfoot.GreenfootImage.*;
import java.lang.Object;
import java.awt.Color;
import java.util.*;

/**
 * Shape class for Graph 5
 * 
 * @author (Anubha) 
 * @version (11-08-2016)
 */
public class DrawSquare extends Country implements iRegion
{
    private int id;
    Color colorToFill=null;
    String filledColorString;

    public DrawSquare(int nodeid) 
    {
        this.id = nodeid;
        GreenfootImage img = new GreenfootImage(51, 51);
        img.setColor(Color.black);
        img.drawRect(0,0,50,50);
        setImage(img);
    }    

    public Integer getId(){
        return id;
    }

    public void act()
    {
        if (Greenfoot.mouseClicked(this))
        {
            BaseGraph world = (BaseGraph)getWorld();
            world.setCountryColor(id);
        }
    }

    // Validation for the color selected for the object, overriden from the parent class
    public boolean checkColor(Color needToColor)
    {
        {
            filledColorString = Utils.getInstance().colorToString(needToColor);
            BaseGraph world = (BaseGraph)getWorld();
            if(filledColorString==null){
                world.validLabel.setValue("Please select a Color");
                return false;
            }
            for(DrawSquare ds : getIntersectingObjects(DrawSquare.class))
            {
                String adjColor = world.colorMap.get(ds.getId());
                if(adjColor!=null && adjColor.equals(filledColorString))
                {
                    world.validLabel.setValue("Invalid Color");
                    Greenfoot.playSound("invalid.wav");
                    return false;
                }
            }
            world.validLabel.setValue("Valid Color");
            Greenfoot.playSound("valid.wav");
            return true;
        }
    }

    public boolean updateColor(Color color)
    {
        if(checkColor(color)){
            colorToFill = color;
            updateImage();
            BaseGraph world = (BaseGraph)getWorld();
            String filledColorString = Utils.getInstance().colorToString(colorToFill);
            world.colorMap.put(id,filledColorString);
            world.checkEndGame(); 
            return true;
        }
        return false;
    }

    void updateWholeCountry(Color color){
        colorToFill = color;
        updateImage();
    }

    void updateImage(){
        getImage().setColor(colorToFill);
        getImage().fillRect(0,0,50,50);;
    }
}