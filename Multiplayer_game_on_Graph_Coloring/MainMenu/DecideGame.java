import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import org.json.* ;
import com.fasterxml.jackson.databind.*;
import org.restlet.resource.*;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;
import org.restlet.ext.jackson.*;
import java.util.*;
import game.GraphServer;
import java.awt.Color;

import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

/**
 * Write a description of class JoinGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class DecideGame extends World implements ActionListener
{
    GreenfootImage background = new GreenfootImage("Background.jpg");
    GreenfootImage text;
    private Button hostGame;
    private Button joinGame;
    private TextField numberofplayers;
    int graphNumber;
    int desiredPlayers;

    GraphServer graphServer;
    BaseGraph activegraph;
    List<GameMetaData> gamesList = new ArrayList<GameMetaData>();
    public DecideGame()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(985,700, 1); 
        init();
    }

    public void init() {
        setBackground(background);
        background = getBackground();

        text = new GreenfootImage("Host Game or Join Game", 32, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(text, 400-text.getWidth()/2, 130);
        hostGame = new Button("Host Game", 2000);
        hostGame.addActionListener(this);
        addObject(hostGame, 300, 241);

        joinGame = new Button("Join Game", 1001);
        joinGame.addActionListener(this);
        addObject(joinGame, 500, 241); 

    }

    public void actionPerformed(GUIComponent c){
        if(c == hostGame) 
        {
            //Logic to Host a New Game
            HostGame h = new HostGame();
            Greenfoot.setWorld(h);
        }
        else if(c == joinGame)
        {
            JoinGame g = new JoinGame();
            Greenfoot.setWorld(g);
        }   

    }

    public void act()
    {        
        // add the Host Game Logic based on the user input

    }
}
