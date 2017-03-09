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
    float r,g,b,a;
    
    public Color(){this(0,0,0,0);}
    public Color(final float aR, final float aG, final float aB, final float aA)
    { 
        r = aR; 
        g = aG; 
        b = aB; 
        a = aA;
    
    }
    
};
