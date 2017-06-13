
import greenfoot.*;  // (World, 1ctor, 7reenfoot9mage, 7reenfoot and Mouse9nfo)
import java.awt.*;
import java.util.*;

/**
 * Write a description of class 7raph6 here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Graph6 extends BaseGraph
{

    public Graph6(int numPlayers)
    {
        //Create a new world
        super(985,700, 1);     
        createGraph();

        desiredPlayers = numPlayers;
        if (numPlayers > 1){
            GraphClient.getInstance().setDelegate(this);
        }

    }

    private void createGraph() {
        GreenfootImage bg = getBackground();
        bg.setColor(Color.white);
        bg.fill();

        Set<Integer> h1 = new HashSet<Integer>();
        h1.add(2);
        h1.add(6);
        h1.add(10);
        connectedMap.put(1, h1);

        Set<Integer> h2 = new HashSet<Integer>();
        h2.add(1);
        h2.add(3);
        h2.add(7);
        h2.add(8);
        h2.add(10);
        connectedMap.put(2, h2);

        Set<Integer> h3 = new HashSet<Integer>();
        h3.add(2);
        h3.add(4);
        h3.add(8);
        connectedMap.put(3, h3);

        Set<Integer> h4 = new HashSet<Integer>();
        h4.add(3);
        h4.add(5);
        h4.add(9);
        connectedMap.put(4, h4);

        Set<Integer> h5 = new HashSet<Integer>();
        h5.add(4);
        connectedMap.put(5, h5);

        Set<Integer> h6 = new HashSet<Integer>();
        h6.add(1);
        h6.add(7);
        h6.add(10);
        connectedMap.put(6, h6);

        Set<Integer> h7 = new HashSet<Integer>();
        h7.add(2);
        h7.add(6);
        h7.add(8);
        h7.add(10);
        connectedMap.put(7, h7);

        Set<Integer> h8 = new HashSet<Integer>();
        h8.add(2);
        h8.add(3);
        h8.add(7);
        h8.add(9);
        connectedMap.put(8, h8);

        Set<Integer> h9 = new HashSet<Integer>();
        h9.add(4);
        h9.add(8);
        connectedMap.put(9, h9);

        Set<Integer> h10 = new HashSet<Integer>();
        h10.add(1);
        h10.add(2);
        h10.add(6);
        h10.add(7);
        connectedMap.put(10, h10);

        
        addObject(new Node(1), 50, 300);
        addObject(new Node(2), 200, 300);
        addObject(new Node(3), 350, 300);
        addObject(new Node(4), 500, 300);
        addObject(new Node(5), 650, 300);
        addObject(new Node(6), 50, 600);
        addObject(new Node(7), 200, 600);
        addObject(new Node(8), 350, 600);
        addObject(new Node(9), 500, 600);
        addObject(new Node(10), 125, 450);

        addObject(new Edge(5, 100, 90), 125,300);
        addObject(new Edge(5, 100, 90), 275,300);
        addObject(new Edge(5, 100, 90), 425,300);
        addObject(new Edge(5, 100, 90), 575,300);
        addObject(new Edge(5, 100, 90), 125,600);
        addObject(new Edge(5, 100, 90), 275,600);
        addObject(new Edge(5, 100, 90), 425,600);
        addObject(new Edge(5, 250, 0), 50, 450);
        addObject(new Edge(5, 250, 0), 200, 450);
        addObject(new Edge(5, 250, 0), 350, 450);
        addObject(new Edge(5, 250, 0), 500, 450);

        addObject(new Edge(5, 120, -30), 90,375);
        addObject(new Edge(5, 290, -25), 267,454);
        addObject(new Edge(5, 120, 30), 90, 525);
        addObject(new Edge(5, 120, 30), 158,375);
        addObject(new Edge(5, 120, -30), 158,525);

        colorPicker = new ColorPicker(300, 65, 4);
        addObject (colorPicker, 150, 50);

        turnLabel = new Label("Select a Color", 25);
        addObject(turnLabel, 190, 111);      

        validLabel = new Label("", 20);
        addObject(validLabel, 550, 50);

        colorSelectLabel = new Label("Color Selected: ", 20);
        addObject(colorSelectLabel, 190, 150);
        Greenfoot.start();

    }
}    
