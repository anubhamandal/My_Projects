package game; 

/**
 * Write a description of class GraphAction here.
 * 
 * @author jonguan
 * @version 11-25-16
 */
public class GraphAction  
{
    private String color;
    private String playerId;
    private Integer nodeId;
    private String action;
    private Integer gameId;
    private Integer numPlayers;

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

}
