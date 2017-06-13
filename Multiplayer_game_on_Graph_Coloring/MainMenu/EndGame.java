import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

import java.awt.Color;
/**
 * Write a description of class EndGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class EndGame extends World
{
    
    /**
     * Constructor for objects of class EndGame.
     * 
     */
    int timeTaken;
    public EndGame(int timeTaken)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        
        super(985,700, 1);  
        this.timeTaken = timeTaken;
        init();
    }
      
    private void init(){
        GreenfootImage background = new GreenfootImage("Background.jpg");
        setBackground(background);
        background = getBackground();
        GreenfootImage text1,text2;
        text1 = new GreenfootImage("Game Over!!!:)" , 60, Color.black, new Color(0, 0, 0, 0));
        Greenfoot.playSound("Game_Over.mp3");
        background.drawImage(text1, 400-text1.getWidth()/2, 50);
        text2 = new GreenfootImage("Finished in "+ timeTaken + " seconds!" , 60, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(text2, 400-text2.getWidth()/2, 200);
    }
}
