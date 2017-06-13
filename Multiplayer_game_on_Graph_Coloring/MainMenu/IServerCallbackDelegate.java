 

/**
 * IServerCallbackDelegate - Implements protocol for Observer pattern callback from server.
 * 
 * @author jonguan
 * @version 11-27-16
 */
public interface IServerCallbackDelegate  
{
    /**
     * Server responds with move to client
     * If continue, returns true;
     * If game over, return false;
     */
    public boolean receiveMove(String move);
}
