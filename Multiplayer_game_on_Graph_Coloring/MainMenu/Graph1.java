
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.*;
import java.util.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Graph1 extends BaseGraph
{


    public Graph1(int numPlayers)
    {    

        super(985,700, 1); 
        prepare();
        desiredPlayers = numPlayers;
        if (numPlayers > 1){
            GraphClient.getInstance().setDelegate(this);
        }
    }

    private void prepare() {
        GreenfootImage bg = getBackground();
        bg.setColor(Color.white);
        bg.fill();
        //A-2 B-1 c-2 D-5 E-3 F-5 G-3 H-3 I-4J-5  k2 L3 M6 N4 O3 P3

        Set<Integer> h1 = new HashSet<Integer>();
        h1.add(5);
        h1.add(10);
        connectedMap.put(1,h1);

        Set<Integer> h2 = new HashSet<Integer>();
        h2.add(3);
        connectedMap.put(2,h2);

        Set<Integer> h3 = new HashSet<Integer>();
        h3.add(2);
        h3.add(4);
        connectedMap.put(3,h3);

        Set<Integer> h4 = new HashSet<Integer>();
        h4.add(3);
        h4.add(5);
        h4.add(6);
        h4.add(10);
        h4.add(8);
        connectedMap.put(4,h4);

        Set<Integer> h5 = new HashSet<Integer>();
        h5.add(1);
        h5.add(7);
        h5.add(4);
        connectedMap.put(5,h5);

        Set<Integer> h6 = new HashSet<Integer>();
        h6.add(9);
        h6.add(7);
        h6.add(8);
        h6.add(4);
        h6.add(10);
        connectedMap.put(6,h6);

        Set<Integer> h7 = new HashSet<Integer>();
        h7.add(5);
        h7.add(9);
        h7.add(6);
        connectedMap.put(7,h7);

        Set<Integer> h8 = new HashSet<Integer>();
        h8.add(4);
        h8.add(10);
        h8.add(6);
        connectedMap.put(8,h8);

        Set<Integer> h9 = new HashSet<Integer>();
        h9.add(7);
        h9.add(6);
        h9.add(13);
        h9.add(14);
        connectedMap.put(9,h9);

        Set<Integer> h10 = new HashSet<Integer>();
        h10.add(1);
        h10.add(12);
        h10.add(4);
        h10.add(6);
        h10.add(8);
        connectedMap.put(10,h10);

        Set<Integer> h11 = new HashSet<Integer>();
        h11.add(12);
        h11.add(13);
        connectedMap.put(11,h11);

        Set<Integer> h12 = new HashSet<Integer>();
        h12.add(11);
        h12.add(10);
        h12.add(13);
        connectedMap.put(12,h12);

        Set<Integer> h13 = new HashSet<Integer>();
        h13.add(11);
        h13.add(12);
        h13.add(9);
        h13.add(14);
        h13.add(15);
        h13.add(16);
        connectedMap.put(13,h13);

        Set<Integer> h14 = new HashSet<Integer>();
        h14.add(9);
        h14.add(13);
        h14.add(15);
        h14.add(16);
        connectedMap.put(14,h14);

        Set<Integer> h15 = new HashSet<Integer>();
        h15.add(13);
        h15.add(14);
        h15.add(16);
        connectedMap.put(15,h15);

        Set<Integer> h16 = new HashSet<Integer>();
        h16.add(13);
        h16.add(14);
        h16.add(15);
        connectedMap.put(16,h16);

        // adding nodes 

        addObject(new Node(1), 43, 545);   //1
        addObject(new Node(2), 138, 490);
        addObject(new Node(3), 245, 464);
        addObject(new Node(4), 355, 347);
        addObject(new Node(5), 296, 192);
        addObject(new Node(6), 495, 265);
        addObject(new Node(7), 588, 172);
        addObject(new Node(8), 478, 391);
        addObject(new Node(9), 655, 286);
        addObject(new Node(10), 541, 562);
        addObject(new Node(11), 676, 486);
        addObject(new Node(12), 742, 651);
        addObject(new Node(13), 837, 566);
        addObject(new Node(14), 758, 111);
        addObject(new Node(15), 864, 365);
        addObject(new Node(16), 957, 260);

        // adding edges for the graph 
        addObject(new GraphEdge(1, 5, 2, 400, 35), 157,358);
        addObject(new GraphEdge(1, 10, 2, 452, 90), 290,560);
        addObject(new GraphEdge(10, 12, 2, 175, 120), 640,609);
        addObject(new GraphEdge(12, 13, 2, 100, 30), 789,620);
        addObject(new GraphEdge(13, 11, 2, 132, 120), 754,530);
        addObject(new GraphEdge(12, 11, 2, 150, -10), 704,579);
        addObject(new GraphEdge(2, 3, 2, 65, 80), 193,484);
        addObject(new GraphEdge(3, 4, 2, 115, 35), 299,406);
        addObject(new GraphEdge(5, 7, 2, 250, 90), 445,191);
        addObject(new GraphEdge(9, 7, 2, 85, 145), 623,229);
        addObject(new GraphEdge(9, 6, 2, 120, 90), 573,280);
        addObject(new GraphEdge(7, 6, 2, 100, 40), 550,234);
        addObject(new GraphEdge(4, 5, 2, 120, -20), 331,267);
        addObject(new GraphEdge(4, 6, 2, 120, 70), 427,308);
        addObject(new GraphEdge(4, 10, 2, 245, 145), 447,454);
        addObject(new GraphEdge(6, 10, 2, 255, -10), 529,413);
        addObject(new GraphEdge(8, 10, 2, 135, -15), 511,476);
        addObject(new GraphEdge(8, 4, 2, 88, 100), 421,361);
        addObject(new GraphEdge(8, 6, 2, 80, 10), 486,329);
        addObject(new GraphEdge(9, 13, 2, 290, 145), 752,424);
        addObject(new GraphEdge(9, 14, 2, 155, 30), 706,200);
        addObject(new GraphEdge(16, 14, 2, 218, 120), 871,182);
        addObject(new GraphEdge(16, 15, 2, 90, 40), 910,313);
        addObject(new GraphEdge(16, 13, 2, 300, 20), 913,424);
        addObject(new GraphEdge(13, 14, 2, 415, -10), 799,338);
        addObject(new GraphEdge(13, 15, 2, 160, 10), 862,465);
        addObject(new GraphEdge(14, 15, 2, 230, -20), 811,239);

        // add color pallet 
        colorPicker = new ColorPicker(300, 65, 4);
        addObject (colorPicker, 150, 50);

        turnLabel = new Label("Select a Color", 25);
        addObject(turnLabel, 511, 55);      

        validLabel = new Label("", 20);
        addObject(validLabel, 787, 56);

        colorSelectLabel = new Label("Color Selected: ", 20);
        addObject(colorSelectLabel, 190, 111);
        Greenfoot.start();

    }
}