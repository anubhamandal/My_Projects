import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;

/**
 * Write a description of class MyWorld here.
 * 
 * @author (Anubha) 
 * @version (a version number or a date)
 */
public class Graph5 extends BaseGraph
{


    public Graph5(int numPlayers){
        super(985,700, 1); 
        prepare();
        desiredPlayers = numPlayers;
        if (numPlayers > 1){
            GraphClient.getInstance().setDelegate(this);
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
        DrawSquare drawsquare1=new DrawSquare(1);
        addObject(drawsquare1,419,116);
        DrawSquare drawsquare2 = new DrawSquare(2);
        addObject(drawsquare2,392,165);
        DrawSquare drawsquare3 = new DrawSquare(3);
        addObject(drawsquare3,441,165);
        DrawSquare drawsquare4 = new DrawSquare(4);
        addObject(drawsquare4,418,214);
        DrawSquare drawsquare5 = new DrawSquare(5);
        addObject(drawsquare5,369,214);
        DrawSquare drawsquare6 = new DrawSquare(6);
        addObject(drawsquare6,469,214);
        DrawSquare drawsquare7 = new DrawSquare(7);
        addObject(drawsquare7,394,263);
        DrawSquare drawsquare8 = new DrawSquare(8);
        addObject(drawsquare8,443,263);
        DrawSquare drawsquare9 = new DrawSquare(9);
        addObject(drawsquare9,492,263);
        DrawSquare drawsquare10 = new DrawSquare(10);
        addObject(drawsquare10,345,263);
        DrawSquare drawsquare11 = new DrawSquare(11);
        addObject(drawsquare11,420,312);
        DrawSquare drawsquare12 = new DrawSquare(12);
        addObject(drawsquare12,469,312);
        DrawSquare drawsquare13 = new DrawSquare(13);
        addObject(drawsquare13,371,312);
        DrawSquare drawsquare14 = new DrawSquare(14);
        addObject(drawsquare14,518,312);
        DrawSquare drawsquare15 = new DrawSquare(15);
        addObject(drawsquare15,322,312);
        DrawSquare drawsquare16 = new DrawSquare(16);
        addObject(drawsquare16,395,361);
        DrawSquare drawsquare17 = new DrawSquare(17);
        addObject(drawsquare17,444,361);
        DrawSquare drawsquare18 = new DrawSquare(18);
        addObject(drawsquare18,493,361);
        DrawSquare drawsquare19 = new DrawSquare(19);
        addObject(drawsquare19,346,361);
        DrawSquare drawsquare20 = new DrawSquare(20);
        addObject(drawsquare20,420,410);
        DrawSquare drawsquare21 = new DrawSquare(21);
        addObject(drawsquare21,469,410);
        DrawSquare drawsquare22 = new DrawSquare(22);
        addObject(drawsquare22,371,410);
        DrawSquare drawsquare23 = new DrawSquare(23);
        addObject(drawsquare23,400,459);
        DrawSquare drawsquare24 = new DrawSquare(24);
        addObject(drawsquare24,451,459);
        DrawSquare drawsquare25 = new DrawSquare(25);
        addObject(drawsquare25,273,312);
        DrawSquare drawsquare26 = new DrawSquare(26);
        addObject(drawsquare26,567,312);
        DrawSquare drawsquare27 = new DrawSquare(27);
        addObject(drawsquare27,591,405);
        DrawSquare drawsquare28 = new DrawSquare(28);
        addObject(drawsquare28,541,255);
        DrawSquare drawsquare29 = new DrawSquare(29);
        addObject(drawsquare29,542,376);
        DrawSquare drawsquare30 = new DrawSquare(30);
        addObject(drawsquare30,297,378);
        DrawSquare drawsquare31 = new DrawSquare(31);
        addObject(drawsquare31,224,331);
        DrawSquare drawsquare32 = new DrawSquare(32);
        addObject(drawsquare32,518,189);
        DrawSquare drawsquare33 = new DrawSquare(33);
        addObject(drawsquare33,320,177);
        DrawSquare drawsquare34 = new DrawSquare(34);
        addObject(drawsquare34,271,192);
        DrawSquare drawsquare35 = new DrawSquare(35);
        addObject(drawsquare35,224,282);
        DrawSquare drawsquare36 = new DrawSquare(36);
        addObject(drawsquare36,590,233);
        DrawSquare drawsquare37 = new DrawSquare(37);
        addObject(drawsquare37,500,474);
        DrawSquare drawsquare38 = new DrawSquare(38);
        addObject(drawsquare38,639,233);
        DrawSquare drawsquare39 = new DrawSquare(39);
        addObject(drawsquare39,627,282);
        DrawSquare drawsquare40 = new DrawSquare(40);
        addObject(drawsquare40,222,192);
        DrawSquare drawsquare41 = new DrawSquare(41);
        addObject(drawsquare41,248,143);
        DrawSquare drawsquare42 = new DrawSquare(42);
        addObject(drawsquare42,518,140);
        DrawSquare drawsquare43 = new DrawSquare(43);
        addObject(drawsquare43,567,164);
        DrawSquare drawsquare44 = new DrawSquare(44);
        addObject(drawsquare44,175,307);

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