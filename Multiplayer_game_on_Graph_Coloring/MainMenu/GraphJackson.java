 
 

 import java.util.*;
 
/**
 * This class represents the state of the game.
 * Code should be able to recreate state of the game based on return value of this class
 * This class should mirror game elements from BaseGraph
 */
public class GraphJackson {

    // Which player's turn
   // private int playerTurn;

    // hash map of {countryId:colorString}
    private Map<Double, String> colorMap = new HashMap<Double, String>();

   // private ArrayList<String> playerIds = new ArrayList<String>();
    

    //public int getPlayer() {
     //   return playerTurn;
    //}

    public Map getColorMap() {
        return colorMap;
    }

    //public ArrayList getPlayers() {
    //    return playerIds;
    //}


    
    //public void setPlayerTurn(int player) {
    //    playerTurn = player;
    //}

    public void setColorMap(Map colorMap) {
        this.colorMap = colorMap;
    }

    //public void setPlayerIds(ArrayList playerIds) {
    //    this.playerIds = playerIds;
    //}


}
