 
 

import greenfoot.*;
import java.awt.Color;

public class Text extends Actor
{
    static Color creationColor = Color.black;
    static int creationFontsize = 24;
    private Color color = creationColor;
    private int fontsize = creationFontsize;
    
    public Text(String text)
    {
        setText(text);
    }

    public void setText(String text)
    {
        setImage(new GreenfootImage(text, fontsize, color, null));
    }
}