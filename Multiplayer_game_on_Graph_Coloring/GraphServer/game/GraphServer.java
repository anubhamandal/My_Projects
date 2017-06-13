package game; 

import java.util.*;
import java.util.stream.*;
import org.json.*;

/**
 * Write a description of class GraphServer here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class GraphServer
{

    private volatile static GraphServer gserver;
    //private String currentPlayer;
    //private Integer desiredNumPlayers;
    //private ArrayList<String> playerArray = new ArrayList<String>();
    private Integer gameCount = 0;

    // multi game, multi-player
    // {gameId:Integer : {gameId:Integer, desiredNumPlayers:Integer, currentPlayer:String, numPlayersWaiting:Integer}}
    private Map<Integer, JSONObject> gameMetaDataMap;

    // {gameId:Integer : {nodeId:Integer : color:String}}
    private Map<Integer, Map<Integer, String>> gameColorMap;

    // {gameId:Integer : [playerIds]}
    private Map<Integer, ArrayList<String>> playerMap;

    // {nodeId: color}
    //private Map<Integer, String> colorMap;

    /**
     * Constructor for objects of class GraphServer
     */
    private GraphServer()
    {
        gameMetaDataMap = new HashMap<Integer, JSONObject>();
        gameColorMap = new HashMap<Integer, Map<Integer, String>>();
        playerMap = new HashMap<Integer, ArrayList<String>>();
    }

    public static GraphServer getInstance() {
        if (gserver == null) {
            synchronized (GraphServer.class) {
                gserver = new GraphServer();
            }
        }
        return gserver;
    }

    /**
     * parse Socket commands
     * Format:
     * {action:getMoves,insertMove,createGame,joinGame,getGames
     *  playerId:String (required),
     *  nodeId:Integer (required for insertMove),
     *  color:String (required for insertMove)
    gameId:String (future feature - multiple concurrent game support),
    metaData:{
    numPlayers:Integer (used for createGame - future feature - more than 2 player game),
    graphNum: Integer (Map number from 1-6)

    }
    }
     */
    public JSONObject parseCommand(String command){
        //Turn into JSON object first
        JSONObject json = new JSONObject(command);
        Integer gameId = json.optInt("gameId");

        switch (json.getString("action")) {
            case "getMoves":
            return getMovesJson("", gameId);
            case "insertMove":
            {
                Map<Integer, String> colorMap = gameColorMap.get(gameId);

                JSONObject gameMetaData = gameMetaDataMap.get(gameId);
                ArrayList<String> playerArray = playerMap.get(gameId);
                if (gameMetaData.getInt("numPlayers") > playerArray.size()){
                    return getMovesJson("Waiting for players", gameId);
                }
                String player = json.getString("playerId");
                String currentPlayer = gameMetaData.getString("currentPlayer");
                if (player.equals(currentPlayer)){
                    this.insertMove(json.getInt("nodeId"), json.getString("color"), gameId);
                    // Next player moves
                    int playerIdx = playerArray.indexOf(player);
                    int nextIdx = ++playerIdx % playerArray.size();
                    currentPlayer = playerArray.get(nextIdx);
                    // Save currentPlayer into meta Data
                    gameMetaData.put("currentPlayer", currentPlayer);
                    gameMetaDataMap.put(gameId, gameMetaData);
                }
                return getMovesJson("", gameId);
            }
            case "createGame":
            {
                // Should have minimum number of players, playerId, graphNum
                // RETURNS: graphId, gameMetaData
                JSONObject metaData = new JSONObject();
                // Increment gameCount
                gameCount++;

                metaData.put("gameId", gameCount);
                metaData.put("numPlayers", json.getInt("numPlayers"));
                metaData.put("graphNum", json.getInt("graphNum"));
                gameMetaDataMap.put(gameCount, metaData);
                gameColorMap.put(gameCount, new HashMap<Integer, String>());
                // Register game
                String playerId = json.getString("playerId");
                ArrayList<String> playerArray = new ArrayList<String>();
                playerArray.add(playerId);
                playerMap.put(gameCount, playerArray);

                JSONObject retJSON = new JSONObject(metaData.toString());
                retJSON.put("error", "Waiting for players");

                System.out.println(retJSON.toString());
                return retJSON;
            }
            case "getGames":
            {
                // Return list of games
                return getGamesJson();
            }
            case "joinGame":
            {
                // Requirements: playerId, gameId                
                String player = json.getString("playerId");

                // Error check player name
                ArrayList<String> playerArray = playerMap.get(gameId);
                if (playerArray.contains(player)) {
                    return getMovesJson("name exists; try again. Bye", gameId);
                }

                // Set current player
                JSONObject gameMetaData = gameMetaDataMap.get(gameId);
                String currentPlayer = gameMetaData.optString("currentPlayer");
                Integer desiredNumPlayers = gameMetaData.getInt("numPlayers");

                playerArray.add(player);
                // Error check multi player
                if (playerArray.size() < desiredNumPlayers) {
                    playerMap.put(gameId, playerArray);
                    gameMetaData.put("players", playerArray);
                    gameMetaDataMap.put(gameId, gameMetaData);
                    return getMovesJson("Waiting for players", gameId);
                } else if (playerArray.size() == desiredNumPlayers){
                    currentPlayer = playerArray.get(0);
                    gameMetaData.put("currentPlayer", currentPlayer);
                    gameMetaData.put("players", playerArray);
                    gameMetaDataMap.put(gameId, gameMetaData);
                    return getMovesJson("", gameId);
                } else {
                    playerArray.remove(playerArray.size()-1);
                    return getMovesJson("Sorry. Game full. Bye", gameId);
                }
            }
            case "resetGame":
            {
                resetGame(gameId);
                return getGamesJson();
            }
            case "resetAll":
            {
                resetAll();
                return getGamesJson();
            }
            default:
            return getMovesJson("", gameId);
        }

    }

    public void resetGame(Integer gameId) {
        gameMetaDataMap.remove(gameId);
        gameColorMap.remove(gameId);
        playerMap.remove(gameId);
    }

    public void resetAll(){
        gameMetaDataMap.clear();
        gameColorMap.clear();
        playerMap.clear();
        gameCount = 0;
    }

    public void insertMove(Integer nodeId, String color, Integer gameId) {
        Map<Integer, String> colorMap = gameColorMap.get(gameId);
        colorMap.put(nodeId, color);
        gameColorMap.put(gameId, colorMap);
    }

    public Map getMoves(Integer gameId) {  
        return gameColorMap.get(gameId);
    }

    public JSONObject getMovesJson(String error, Integer gameId){
        JSONObject j = new JSONObject();
        if (error.length() > 0) 
            j.put("error", error);

        if (gameId > 0) {
            j.put("colorMap", new JSONObject(gameColorMap.get(gameId)));
            j.put("currentPlayer", gameMetaDataMap.get(gameId).optString("currentPlayer"));
            j.put("gameMetaData", gameMetaDataMap.get(gameId));
        }
        System.out.println(j);
        return j;
    }

    public JSONObject getGamesJson(){
        JSONObject j = new JSONObject();
        Collection<JSONObject> availableGames = gameMetaDataMap.values();
        Iterator<JSONObject> it = availableGames.iterator();
        while( it.hasNext() ) {
            JSONObject game = it.next();
            JSONArray array = game.optJSONArray("players");
            if( array != null && array.length() >= game.getInt("numPlayers") ) 
                it.remove();
        }
        j.put("games", availableGames );
        System.out.println(j);
        return j;
    }

    public String getNodeColor(Integer nodeId, Integer gameId){
        Map<Integer, String> colorMap = gameColorMap.get(gameId);
        return colorMap.get(nodeId);

    }

}
