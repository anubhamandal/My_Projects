/**
 * Write a description of class RegionMaker here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class RegionMaker  
{
    // instance variables - replace the example below with your own
    private iRegion drawsquare;
    private iRegion drawrect;
    private iRegion drawregion;
    private iRegion node;
    private iRegion triangle;
    private int id;

    /**
     * Constructor for objects of class RegionMaker
     */
    public RegionMaker()
    {
        drawsquare = new DrawSquare(id);
        drawrect = new DrawRect(id);
        drawregion = new DrawRegion(id);
        node = new Node(id);
    }

    public void drawSquare(int id)
    {
        drawsquare.draw(id);
    }

    public void drawRect(int id)
    {
        drawrect.draw(id);
    }

    
    public void drawRegion(int id)
    {
        drawregion.draw(id);
    }
    
    public void drawNode(int id)
    {
        node.draw(id);
    }
}
