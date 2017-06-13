/**
 * Write a description of class iRegion here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.awt.Color;

public interface iRegion  
{
    /**
     * Server responds with move to client
     * If continue, returns true;
     * If game over, return false;
     */
    public Integer getId();
    public boolean checkColor(Color needToColor);
    public boolean updateColor(Color color);
    public void draw(int id);
}