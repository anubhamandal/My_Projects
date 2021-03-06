  

/**
 * Write a description of class GraphAction here.
 * 
 * @author jonguan
 * @version 11-25-16
 */
public class GraphAction  
{
    // used for insertMove
    private String color;
    // required - playerName
    private String playerId;
    // used for insertMove
    private Integer nodeId;
    // Valid actions: getMoves, insertMove, createGame, getGames, joinGame
    private String action;
    // gameId is created by server and sent to client.  joinGame returns gameId to join
    private Integer gameId;
    // used for createGame
    private Integer numPlayers;
    // used for createGame
    private Integer graphNum;

    /**
     * Constructor for objects of class GraphAction
     */
    public GraphAction()
    {
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getNodeId(){
        return nodeId;
    }

    public void setNodeId(Integer nodeid){
        this.nodeId = nodeid;
    }

    public String getPlayerId(){
        return playerId;
    }

    public void setPlayerId(String playerId){
        this.playerId = playerId;
    }

    public void setAction(String action){
        this.action = action;
    }

    public String getAction() {
        return action;
    }

    public Integer getGameId(){
        return gameId;
    }

    public void setGameId(Integer gameId){
        this.gameId = gameId;
    }
    
    public Integer getNumPlayers(){
        return numPlayers;
    }
    
    public void setNumPlayers(Integer numPlayers){
        this.numPlayers = numPlayers;
    }
    
    public Integer getGraphNum(){
        return graphNum;
    }
    
    public void setGraphNum(Integer graphNum){
        this.graphNum = graphNum;
    }
    
    

}
