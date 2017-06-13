
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;

public class Node extends Country implements iRegion
{
    private Integer title;
    Color colorToFill=null;
    String filledColorString;
    boolean isValid=true,isGameOver=false;

    public Node(int pTitle) {
        title = pTitle;
        GreenfootImage img = new GreenfootImage(50, 50);
        img.setColor(Color.black);
        img.drawOval(0,0,50,50);
        setImage(img);
    }

    public int getTitle() {
        return title;
    }

    public void act() 
    {
        if (Greenfoot.mouseClicked(this)){
            isValid=true;
            BaseGraph world = (BaseGraph)getWorld();
            world.setCountryColor(title);
        } 

    }   

    public void getColorToFill(){ 
        BaseGraph world = (BaseGraph)getWorld();
        Color selectedColor = world.selectedColor();
        if(selectedColor != null){
            colorToFill = selectedColor;
        }
    }

    // Overridden method 
    // Validation for the color selected for the object
    public boolean checkColor(Color needToColor){
        String needtoColorString =  Utils.getInstance().colorToString(needToColor);
        BaseGraph world = (BaseGraph)getWorld();
        if(needtoColorString==null){
            world.validLabel.setValue("Please select a Color");
            return false;
        }
        Set<Integer> connectedNodes = world.connectedMap.get(title);
       
        Iterator iterator = connectedNodes.iterator(); 
        while (iterator.hasNext()){
            Integer adjNode = (Integer)iterator.next();
            String adjColor =world.colorMap.get(adjNode);
            
            if(adjColor!=null && adjColor.equals(needtoColorString)){
                world.validLabel.setValue("Invalid Color");
				Greenfoot.playSound("invalid.wav");
                return false;
            }
        }

        world.validLabel.setValue("Valid Color");
		Greenfoot.playSound("valid.wav");
        return true;
    }

    public boolean updateColor(Color color){
        if(checkColor(color)){
            colorToFill = color;
            updateImage();
            BaseGraph world = (BaseGraph)getWorld();
            String filledColorString = Utils.getInstance().colorToString(colorToFill);
            world.colorMap.put(title,filledColorString);
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
       getImage().fillOval(0,0,50,50);
    }
    
   public Integer getId(){
        return title;
    }
}
