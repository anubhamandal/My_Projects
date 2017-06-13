
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ListIterator;
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @author jonguan 
 * @version 11-4-16
 */
public class Graph2 extends BaseGraph
{

    /**
     * Constructor for objects of class MyWorld.
     * 
     */

    public Graph2(int numPlayers){
        super(985,700, 1);
        prepare();
        desiredPlayers = numPlayers;
        if (numPlayers > 1){
            GraphClient.getInstance().setDelegate(this);
        }
    }

    private void prepare() {

        TriangleCountry country1 = new TriangleCountry(0, 0, 600, 200, 1);
        TriangleCountry country2 = new TriangleCountry(0, 0, 600, 200, 2);
        TriangleCountry country3 = new TriangleCountry(0, 0, 400, 300, 3);
        TriangleCountry country4 = new TriangleCountry(0, 0, 400, 300, 4);

        addObject( country1, 300, 299);
        country1.setRotation(180);
        addObject( country2, 301, 500);
        addObject(country3, 149, 400);
        country3.setRotation(90);
        addObject(country4, 452, 400);
        country4.setRotation(270);

        validLabel = new Label("", 40);
        addObject(validLabel, 450, 40);

        turnLabel = new Label("Select a color", 25);
        addObject(turnLabel, 150, 40);

        colorSelectLabel = new Label("Color Selected: ", 20);
        addObject(colorSelectLabel, 100, 75);

        int numCol = 2;
        colorPicker = new ColorPicker(300, 75, numCol);
        addObject(colorPicker, 150, 150);

    }

}