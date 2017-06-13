 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
/**
 * This is a class which draws the edges
 */
public class Edge extends Actor
{
    public Edge(int width, int height, int rot) {
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(Color.black);
        img.fill();
        setImage(img);
        setRotation(rot);
    }
}
