 

import greenfoot.*;
import java.awt.Color;

public class Menu extends World
{
    private BaseGraph activeGraph;
    private static Integer WIDTH = 985;
    private static Integer HEIGHT = 700;
    Label label;
    // '1' key (Graph1) //Madhuri
    // '2' key (Graph2) //Jon
    // '3' key (Graph3) //Anubha
    //  '4' key (Graph4) //Shilpa

    //  '6' key (Graph6) //Veeresh

    public Menu()
    {
        super(985,700, 1); 
        init();
    }

    private void init(){
        GreenfootImage background = new GreenfootImage("BackgroundSingle.png");
        setBackground(background);
        background = getBackground();
        GreenfootImage text;
        Label welcomelabel = new Label("Welcome to the Graph Coloring Game!", 40);
        addObject(welcomelabel, WIDTH/2, 50);
        
        Label whatlabel = new Label("Graph Selection Menu", 32);
        addObject(whatlabel, WIDTH/2, 100);
        
        Label selectlabel = new Label("Type a number to begin game", 40);
        addObject(selectlabel, WIDTH/2, HEIGHT/2+70);
   
    }

    public void act()
    {
        if (activeGraph != null) {
            return;
        }

        if (Greenfoot.isKeyDown("1"))
        {
            activeGraph = new Graph1(1);
        }

        if ( Greenfoot.isKeyDown("2"))
        {
            activeGraph = new Graph2(1);
        }

        if ( Greenfoot.isKeyDown("3"))
        {
            activeGraph = new Graph3(1);
        }

        if ( Greenfoot.isKeyDown("4"))
        {
             activeGraph = new Graph4(1);
        }

        if (Greenfoot.isKeyDown("5"))
        {
            activeGraph = new Graph5(1);
        }

        if ( Greenfoot.isKeyDown("6"))
        {
            activeGraph = new Graph6(1);
        }

        try{
            if (activeGraph != null){
                String playerName = "player";
                activeGraph.setPlayerName(playerName);
                activeGraph.setCurrentPlayer(playerName);
                Greenfoot.setWorld(activeGraph);
            }
        }catch (Exception e){
            System.err.println(e);
        }

    }
    public BaseGraph getActiveGraph() {
        return activeGraph;
    }
}
