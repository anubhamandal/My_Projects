
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.awt.Color;
import java.util.*;

import java.net.* ;
import java.util.* ;
import java.io.* ;
import org.json.* ;
import com.fasterxml.jackson.databind.*;
import org.restlet.resource.*;
import org.restlet.representation.* ;
import org.restlet.ext.json.* ;
import org.restlet.data.* ;
import org.restlet.ext.jackson.*;

/**
 * Write a description of class BaseGraph here.
 * 
 * @author jonguan
 * @version 11-4-16
 */
public class BaseGraph extends World implements IServerCallbackDelegate
{

    // private static String url = "http://localhost:8080/graphgame";
    public ColorPicker colorPicker;
    Label validLabel;
    Label colorSelectLabel;
    Label turnLabel;
    public long startTime,stopTime;

    // Game mechanics
    public String playerName;
    public String currentPlayer;
    int desiredPlayers;
    private Integer gameId;
    boolean whetherValid = false;
    String colorToFill; // added 
    // The following is to keep track of the colors of the nodes
    public Map<Integer, String> colorMap = new HashMap<Integer, String>();
    public Map<Integer,Set<Integer>> connectedMap = new HashMap();

    // private ClientResource client = new ClientResource(url);

    /**
     * Constructor for objects of class BaseGraph.
     * 
     */
    public BaseGraph()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1000, 1000, 1);          
    }

    public BaseGraph(int worldWidth, int worldHeight, int cellSize){
        super(worldWidth, worldHeight, cellSize);
        startTime = System.currentTimeMillis();
        //GraphClient.getInstance().reset();

        // ATTENTION
        // GraphClient.getInstance().setDelegate(this); NEEDS to be called as soon as child subclass finishes init

    }

    public Color selectedColor() {
        return colorPicker.selectedColor();
    }

    public void updateColor(Color color) {
        String colString = "Color Selected: " + Utils.getInstance().colorToString(color);
        if (this.colorSelectLabel != null) {
            this.colorSelectLabel.setValue(colString);
        }
        if (this.turnLabel != null && isMyTurn() ) {
            this.turnLabel.setValue("Color the Region");
        }
    }

    public boolean isMyTurn() {
        return currentPlayer != null && currentPlayer.equals(playerName);
    }

    // TEMP - Connect to server with sockets
    public void joinGame(){

        // Single or multi-player game
        if (desiredPlayers == 1){
            currentPlayer = playerName;
        } else {
            // Init client server connection
            GraphClient.getInstance().setDelegate(this);

            // Register for game
            GraphAction graphAct = new GraphAction();
            graphAct.setPlayerId(playerName);
            graphAct.setAction("joinGame");
            graphAct.setNumPlayers(desiredPlayers);
            sendAction(graphAct);

        }
    }

    /**
     * Server call to set a country color
     */
    public void setCountryColor(Integer id){
        // Break if currentPlayer not this player
        if (! isMyTurn()) {
            updatePlayerTurnLabel();
            return;
        }
        if (selectedColor() == null){
            return;
        }
        // Local
        // colorMap.put(id, Utils.getInstance().colorToString(selectedColor()));
        colorToFill = Utils.getInstance().colorToString(selectedColor());
        if(desiredPlayers <=1){
            refreshNodeColors(id);
        }
        else{
        CheckNodeColors(id);
        if(whetherValid) {
            colorMap.put(id, Utils.getInstance().colorToString(selectedColor()));
            GraphAction graphAct = new GraphAction();
            // for multiplayer call the sever to update the colormap
            graphAct.setColor(Utils.getInstance().colorToString(selectedColor()));
            graphAct.setNodeId(id);
            graphAct.setAction("insertMove");
            graphAct.setPlayerId(playerName);
            graphAct.setGameId(gameId);
            sendAction(graphAct);
        }
       } 
       
    }

    /**
     * sendAction - sends actions to the server.
     * @param - graphAct: GraphAction - data to send to the server
     */
    public void sendAction(GraphAction graphAct){
        GraphClient.getInstance().sendAction(graphAct);
    }

    /**
     * A method to check if the adjacent colors of the graph nodes are different
     */
    public boolean checkValid() {
        // Implement this method in children
        return false;
    }

    /**
     * Receives move from GraphServer
     * move:String - in format of {
     *     colorMap:{nodeId:colorString},
     *     currentPlayer:string
     * }
     */
    public boolean receiveMove(String move){
        System.out.println("received " + move); 
        try{
            JSONObject json = new JSONObject(move);
            String err = json.optString("error");
            System.out.println("err is " + err);
            if (err != null && err.length() > 0 && turnLabel != null){
                turnLabel.setValue(err);
                return (err.indexOf("Bye") < 0);
            }
            JSONObject colMap = json.optJSONObject("colorMap");
            if (colMap != null){
                colorMap = Utils.getInstance().getMapFromJSON(colMap);
            }
            System.out.println(colorMap);
            currentPlayer = json.optString("currentPlayer");
            updatePlayerTurnLabel();
            refreshNodeColors();
        }catch (JSONException e){
            System.out.println(e);
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public void updatePlayerTurnLabel(){
        if (currentPlayer == null || currentPlayer.length() == 0) {
            turnLabel.setValue("Waiting for players");
        } else if (currentPlayer.equals(playerName)) {
            setGameTimeStart();
            turnLabel.setValue("Your Turn");
        } else {
            setGameTimeStart();
            turnLabel.setValue(currentPlayer + "'s turn");
        }
    }

    public void setGameTimeStart() {
        if (turnLabel.getValue().equals("Waiting for players")) {
            startTime = System.currentTimeMillis();
        }
    }

    /**
     * Refresh country node colors based on color map
     */
    public void CheckNodeColors(int id){
        List nodes = getObjects(Country.class);
        Iterator it = nodes.iterator();
        while(it.hasNext()){
            Country c = (Country)it.next();
            if(c.getId() == id && colorToFill != null) {
                Color color = Utils.getInstance().stringToColor(colorToFill);
                whetherValid = c.checkColor(color);
                return;
            }
        }
    }
        public void refreshNodeColors(int id){
        List nodes = getObjects(Country.class);
        Iterator it = nodes.iterator();
        while(it.hasNext()){
            Country c = (Country)it.next();
            if(c.getId() == id && colorToFill != null) {
                Color color = Utils.getInstance().stringToColor(colorToFill);
                whetherValid = c.updateColor(color);
                return;
            }
        }
    }
     /**
     * Refresh country colors based on color map
     */
    public void refreshNodeColors(){
        List nodes = getObjects(Country.class);
        Iterator it = nodes.iterator();
        while(it.hasNext()){
            Country c = (Country)it.next();
            String colorString = colorMap.get(c.getId());
            if(colorString != null) {
                Color color = Utils.getInstance().stringToColor(colorString);
                c.updateWholeCountry(color);
 
            }
        }
        checkEndGame();
    }

    /**
     * Check if game is over
     */
    public void checkEndGame(){
        List items = getObjects(Country.class);

        if (items.size() == colorMap.size()) {

            stopTime=System.currentTimeMillis();
            int timeTaken = (int)(stopTime-startTime)/1000;

            EndGame endgame = new EndGame(timeTaken);
            Greenfoot.setWorld(endgame);

        }
    }

    public void setGameId(Integer gameId){
        this.gameId = gameId;
    }

    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public void setCurrentPlayer(String playerName){
        this.currentPlayer = playerName;
    }

    public void setError(String err){
        if (err != null && err.length() > 0 && turnLabel != null){
            turnLabel.setValue(err);
        }
    }
}
