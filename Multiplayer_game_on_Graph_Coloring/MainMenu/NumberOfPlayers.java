import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import javax.swing.JOptionPane;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
/**
 * Write a description of class NumberOfPlayers here.
 * 
 * @author (SHILPA CHANDRA) 
 * @version (02/12/16)
 */
public class NumberOfPlayers extends World implements ActionListener
{
    private TextField numberofplayers;
    private Button singleplayer;
    private Button multiplayer;
    private boolean enabled = true;
    BaseGraph activegraph;
    private Button colorfulBtn;
    private Border blueBorder = new LineBorder(Color.BLUE, 3);
    GreenfootImage background = new GreenfootImage("Background.jpg");
    GreenfootImage text;
    int graphnumber;
    /**
     * Constructor for objects of class NumberOfPlayers.
     * 
     */
    public NumberOfPlayers()
    {
        super(985,700, 1);  

        setBackground(background);
        background = getBackground();

        text = new GreenfootImage("Welcome to the Graph Game!!", 50, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(text, 500-text.getWidth()/2, 100);
        text = new GreenfootImage("Please Select Single or Multi Player", 32, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(text, 500-text.getWidth()/2, 250);
        singleplayer = new Button("Single Player", 2000);
        singleplayer.addActionListener(this);
        addObject(singleplayer, 370, 370);
        multiplayer = new Button("Multi Player", 1001);
        multiplayer.addActionListener(this);
        addObject(multiplayer, 600, 370);

        prepare();
    }

    public void actionPerformed(GUIComponent c) {
        if(c==singleplayer || c== numberofplayers)
        {
            Greenfoot.setWorld(new Menu());
        }
        else if(c==multiplayer)
        {
            DecideGame decideGame = new DecideGame();
            Greenfoot.setWorld(decideGame);
        }

    }

    public void act()
    {
        try{
            if (activegraph != null){
                Greenfoot.setWorld(activegraph);
            }
        }catch (Exception e){
            System.err.println(e);
        }
    }

    /**
     * Prepare the world for the start of the program.
     * That is: create the initial objects and add them to the world.
     */
    private void prepare()
    {
    }
}

