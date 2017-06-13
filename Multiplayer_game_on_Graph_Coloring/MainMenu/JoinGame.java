import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import org.json.* ;
import com.fasterxml.jackson.databind.*;
import org.restlet.resource.*;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;
import org.restlet.ext.jackson.*;
import java.util.*;
import java.util.stream.*;
import game.GraphServer;
import java.awt.Color;
/**
 * Write a description of class JoinGame here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class JoinGame extends World implements ActionListener,IServerCallbackDelegate
{
    BaseGraph activegraph;
    List<GameMetaData> gamesList = new ArrayList<GameMetaData>();
    public int graphNumber;
    private String playerName;
    GreenfootImage background = new GreenfootImage("BackgroundMulti.png");

    Button createGame, back, joinGame;
    TextField joinGameTextField;
    private GameMetaData chosenGame;
    private static Integer WIDTH = 985;
    private static Integer HEIGHT = 700;

    public JoinGame()
    {    
        super(WIDTH,HEIGHT, 1); 
        setBackground(background);
        GraphClient.getInstance().setDelegate(this);
        init();
        getGamesFromServer();
    }

    public void init(){
        // Back Button
        back = new Button("Back", 8474);
        back.addActionListener(this);
        addObject(back, 50, 50);  

        // Ask for player name
        playerName = Greenfoot.ask("What is your player name?");
    }
    // Prepare the action and send the action to the client 

    public void getGamesFromServer(){
        GraphAction graphAct = new GraphAction();
        graphAct.setAction("getGames");
        GraphClient.getInstance().sendAction(graphAct);
    }

    public void joinGame(Integer gameId){
        GraphAction graphAct = new GraphAction();
        graphAct.setAction("joinGame");
        graphAct.setGameId(gameId);
        graphAct.setPlayerId(playerName);
        GraphClient.getInstance().sendAction(graphAct);
    }

    /**
     * receive available games from the server
     */
    public boolean receiveMove(String move){
        boolean retVal = true;
        System.out.println("received " + move); 
        try{
            JSONObject json = new JSONObject(move);
            String err = json.optString("error");
            System.out.println("err is " + err);
            if (err != null && err.length() > 0 ){
                retVal = (err.indexOf("Bye") < 0);
            }

            JSONArray array = json.optJSONArray("games");
            if (array != null){
                // get games
                populateGamesList(array);
                showCurrentGames();
            } else if (json.optString("gameMetaData") != null){
                // joined a game
                transitionToGraph(move);
            }

        }catch (JSONException e){
            System.out.println(e);
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return retVal;
    }

    private void populateGamesList(JSONArray array){
        for(int i = 0 ; i < array.length() ; i++){
            GameMetaData gm =  new GameMetaData();
            gm.gameid = array.getJSONObject(i).getInt("gameId");
            gm.numofPlayers = array.getJSONObject(i).getInt("numPlayers");
            gm.graphnumber = array.getJSONObject(i).getInt("graphNum");
            gamesList.add(gm);
        }
    }

    private void transitionToGraph(String move){

        int graphNum = chosenGame.graphnumber;
        BaseGraph bg = getGraphGameFromNum(graphNum);

        bg.setGameId(chosenGame.gameid);
        bg.setPlayerName(playerName);
        bg.receiveMove(move);

        Greenfoot.setWorld(bg);
    }

    private GameMetaData metaDataForId(Integer id){
        List<GameMetaData> result = gamesList.stream().filter(metaData -> metaData.gameid == id).collect(Collectors.toList()); 
        if (result.size() > 0){
            return result.get(0);
        }
        return null;
    }

    //show currrent games in greenfoot
    public void showCurrentGames(){  
        int datax = 100;

        if(gamesList.size()>0){
            for(int i=0;i<gamesList.size();i++){
                System.out.println("i =" + i);
                GameMetaData metaData = gamesList.get(i);
                StringBuffer sb = new StringBuffer();
                sb.append("Game Id: ");
                sb.append(metaData.gameid);
                sb.append(" ; #Players: ");
                sb.append(metaData.numofPlayers);
                sb.append(" ;Graph# ");
                sb.append(metaData.graphnumber);
                GreenfootImage gd =  new GreenfootImage( sb.toString(),20,Color.black, new Color(0, 0, 0, 0));
                background.drawImage(gd, (WIDTH-gd.getWidth())/2,datax );
                datax += (30);

            }

            // Select Game label

            GreenfootImage select =  new GreenfootImage( "Select GameId to join", 20, Color.black, new Color(0, 0, 0, 0));
            int halfX = WIDTH/2;
            background.drawImage(select, halfX-select.getWidth()/2, HEIGHT-70);
            // select game text field
            joinGameTextField = new TextField("", 10);
            joinGameTextField.requestFocus();    
            addObject(joinGameTextField, halfX-joinGameTextField.getWidth()-50, HEIGHT-30);

            joinGame = new Button("Join Game", 743473);
            joinGame.addActionListener(this);
            addObject(joinGame, halfX+50, HEIGHT-30);

        }
        else{

            GreenfootImage text = new GreenfootImage("No games available yet.  Create one?", 32, Color.black, new Color(0, 0, 0, 0));
            background.drawImage(text, (WIDTH-text.getWidth())/2, 130);
            createGame = new Button("Create Game", 2000);
            createGame.addActionListener(this);
            addObject(createGame, 392, 241);

        }
        setBackground(background);
    }

    public void actionPerformed(GUIComponent c){
        if(c == back){
            DecideGame d = new DecideGame();
            Greenfoot.setWorld(d);
            return;
        } 

        if (c == createGame){
            HostGame hg = new HostGame();
            hg.setPlayerName(playerName);
            Greenfoot.setWorld(hg);
            return;
        }
        if (c == joinGame){
            Integer gameId = new Integer(joinGameTextField.getText());
            chosenGame = metaDataForId(gameId);
            joinGame(gameId);
            return;
        }
    }

    private BaseGraph getGraphGameFromNum(int num){
        BaseGraph bg = null;

        switch (num){
            case 1:
            bg = new Graph1(chosenGame.numofPlayers);
            break;
            case 2:
            bg = new Graph2(chosenGame.numofPlayers);
            break;
            case 3:
            bg = new Graph3(chosenGame.numofPlayers);
            break;
            case 4:
            bg = new Graph4(chosenGame.numofPlayers);
            break;
            case 5:
            bg = new Graph5(chosenGame.numofPlayers);
            break;
            case 6:
            bg = new Graph6(chosenGame.numofPlayers);
            break;
        }
        return bg;
    }

    public void act()
    {        
        // add the JoinGame Logic based on the user input
    }
}

class GameMetaData{
    int gameid;
    int numofPlayers;
    int graphnumber;
}

