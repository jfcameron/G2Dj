/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Type.Graphics;

/**
 *
 * @author Joe
 */
public class Color
{
    public float r,g,b,a;
    
    public Color(){this(0,0,0,0);}
    public Color(final float aR, final float aG, final float aB, final float aA)
    { 
        r = aR; 
        g = aG; 
        b = aB; 
        a = aA;
    
    }
    
    //
    // Special colors
    //
    public static Color Red        (){return new Color(1.0f,0.0f,0.0f,1.0f);}
    public static Color Green      (){return new Color(0.0f,1.0f,0.0f,1.0f);}
    public static Color Blue       (){return new Color(0.0f,0.0f,1.0f,1.0f);}
    public static Color DeathlyPink(){return new Color(1.0f,0.2f,0.8f,1.0f);}
    
    public static Color CornflowerBlue()
    {
        return new Color
        (
            0.3921568627450980392156862745098f,
            0.58431372549019607843137254901961f,
            0.92941176470588235294117647058824f,
            1.0f
        
        );
    
    }
    
    
    
};
