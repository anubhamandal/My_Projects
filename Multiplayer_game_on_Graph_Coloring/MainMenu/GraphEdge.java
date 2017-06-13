 
 

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;

public class GraphEdge extends Actor
{
    private int node1, node2;
    
    public GraphEdge(int pNode1, int pNode2, int width, int height, int rot) {
        node1 = pNode1;
        node2 = pNode2;
        GreenfootImage img = new GreenfootImage(width, height);
        img.setColor(Color.black);
        img.fill();
        setImage(img);
        setRotation(rot);
    }
    
     
   
        
    public int getNode1() {
        return node1;
    }
    
    public int getNode2() {
        return node2;
    }
}
