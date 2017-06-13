
import greenfoot.*;
import java.awt.Color;
import org.json.* ;

/**
 * Write a description of class HostGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class HostGame extends World implements ActionListener, IServerCallbackDelegate
{
    Button back;
    Button create;
    TextField graphNum, numPlayers, playerName;

    GreenfootImage background = new GreenfootImage("BackgroundMulti.png");
    /**
     * Constructor for objects of class HostGame
     */
    public HostGame()
    {
        super(985,700, 1);  
        GraphClient.getInstance().setDelegate(this);
        init();
    }

    public void init() {
        setBackground(background);
        background = getBackground();

        GreenfootImage text = new GreenfootImage("Host Game", 32, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(text, (800-text.getWidth())/2, 130);

        // Player Name
        GreenfootImage nameText = new GreenfootImage("Player Name", 20, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(nameText, 100, 250);
        playerName = new TextField("", 10);
        playerName.requestFocus();    
        addObject(playerName, 400, 260);

        // Graph Number
        GreenfootImage graphText = new GreenfootImage("Graph Number (1-6)", 20, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(graphText, 100, 300);
        graphNum = new TextField("", 10);
        addObject(graphNum, 400, 310);

        // Number players
        GreenfootImage playersText = new GreenfootImage("Number Players (2-4)", 20, Color.black, new Color(0, 0, 0, 0));
        background.drawImage(playersText, 100, 350);
        numPlayers = new TextField("", 10);

        addObject(numPlayers, 400, 360);

        // Create Button
        create = new Button("Create", 743473);
        create.addActionListener(this);
        addObject(create, (800-create.getWidth())/2, 450);

        // Back Button
        back = new Button("Back", 8474);
        back.addActionListener(this);
        addObject(back, 50, 50); 
    }

    public void actionPerformed(GUIComponent c){
        if(c == back){
            DecideGame d = new DecideGame();
            Greenfoot.setWorld(d);
            return;
        } 

        if (c == create){
            submitDetails();
        }
    }

    private void submitDetails() {
        String graphNo = graphNum.getText();
        String numPeeps = numPlayers.getText();
        String playName = playerName.getText();

        GraphAction graphAct = new GraphAction();
        graphAct.setAction("createGame");
        graphAct.setGraphNum(new Integer(graphNo));
        graphAct.setNumPlayers(new Integer(numPeeps));
        graphAct.setPlayerId(playName);

        GraphClient.getInstance().sendAction(graphAct);

    }

    public boolean receiveMove(String move){
        System.out.println("received " + move); 
        try{
            JSONObject json = new JSONObject(move);
            String err = json.optString("error");
            System.out.println("err is " + err);
            

            Integer gameId = json.getInt("gameId");
            BaseGraph bg = getGraphGameFromNum(graphNum.getText());
            bg.setGameId(gameId);
            bg.setPlayerName(playerName.getText());
            bg.setError(err);
            
            // Send off to game
            Greenfoot.setWorld(bg);

            if (err != null && err.length() > 0 ){
                return (err.indexOf("Bye") < 0);
            }
            
        }catch (JSONException e){
            System.out.println(e);
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private BaseGraph getGraphGameFromNum(String num){
        BaseGraph bg = null;
        Integer numPeeps = new Integer(numPlayers.getText());
        switch (num){
            case "1":
            bg = new Graph1(numPeeps);
            break;
            case "2":
            bg = new Graph2(numPeeps);
            break;
            case "3":
            bg = new Graph3(numPeeps);
            break;
            case "4":
            bg = new Graph4(numPeeps);
            break;
            case "5":
            bg = new Graph5(numPeeps);
            break;
            case "6":
            bg = new Graph6(numPeeps);
            break;
        }
        return bg;
    }
    
    public void setPlayerName(String playName){
        this.playerName.setText(playName);
    }

}
