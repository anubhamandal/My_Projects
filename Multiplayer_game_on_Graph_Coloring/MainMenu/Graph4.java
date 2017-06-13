import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.util.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (SHILPA CHANDRA) 
 * @version (02/11/16)
 */
public class Graph4 extends BaseGraph
{

    
    public Graph4(int numPlayers){
        super(985,700, 1);
        prepare();
        desiredPlayers = numPlayers;
        if (numPlayers > 1){
            GraphClient.getInstance().setDelegate(this);
        }
    }

    private void prepare() 
    {

        DrawRegion drawregion = new DrawRegion(1);
        addObject(drawregion,240,150);
        DrawRegion drawregion2 = new DrawRegion(2);
        addObject(drawregion2,170,200);
        DrawRegion drawregion3 = new DrawRegion(3);
        addObject(drawregion3,370,200);
        DrawRegion drawregion4 = new DrawRegion(4);
        addObject(drawregion4,500,150);
        DrawRegion drawregion5 = new DrawRegion(5);
        addObject(drawregion5,570,200);
        DrawRegion drawregion6 = new DrawRegion(6);
        addObject(drawregion6,300,250);
        DrawRegion drawregion7 = new DrawRegion(7);
        addObject(drawregion7,200,300);
        DrawRegion drawregion8 = new DrawRegion(8);
        addObject(drawregion8,325,350);
        DrawRegion drawregion9 = new DrawRegion(9);
        addObject(drawregion9,525,350);
        DrawRegion drawregion10 = new DrawRegion(10);
        addObject(drawregion10,600,250);
        DrawRegion drawregion11 = new DrawRegion(11);
        addObject(drawregion11,250,400);
        DrawRegion drawregion12 = new DrawRegion(12);
        addObject(drawregion12,450,400);
        DrawRegion drawregion13 = new DrawRegion(13);
        addObject(drawregion13,650,400);
        DrawRegion drawregion14 = new DrawRegion(14);
        addObject(drawregion14,150,450);
        DrawRegion drawregion15 = new DrawRegion(15);
        addObject(drawregion15,350,450);
        DrawRegion drawregion16 = new DrawRegion(16);
        addObject(drawregion16,600,450);
        DrawRegion drawregion17 = new DrawRegion(17);
        addObject(drawregion17,650,300);

        colorSelectLabel = new Label("Color Selected: ", 20);
        addObject(colorSelectLabel, 100, 100);
        turnLabel = new Label("Select a Color", 25);
        addObject(turnLabel, 511, 55);      

        validLabel = new Label("", 20);
        addObject(validLabel, 787, 56);

        int numCol = 4;
        colorPicker = new ColorPicker(300, 75, numCol);
        addObject(colorPicker, 150, 30);
    }
}