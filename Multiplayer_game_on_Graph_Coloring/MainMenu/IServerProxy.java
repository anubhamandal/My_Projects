/**
 * Write a description of class IServerProxy here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public interface IServerProxy  
{
    /**
     * Send a stringified JSON command
     */
    public void send(String payload);

    /**
     * Set a callback delegate object
     */
    public void setDelegate(IServerCallbackDelegate delegate);
}
