/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Math;

/**
 *
 * @author Joe
 */
public class IntVector2 
{
    public int x,y;
    
    public IntVector2(){this(0,0);}
    public IntVector2(final int aX, final int aY){x = aX; y = aY;}
    
    public Vector2 toVector2(){return new Vector2(x,y);}
    
    public IntVector2 setInPlace (final int aX, final int aY){x=aX;y=aY;return this;}
    
    //public Vector2 divideToFloatVec(final float aScalar){return new Vector2(x/aScalar,y/aScalar);}
    
    public static IntVector2 Zero(){return new IntVector2(0,0);}
    
    @Override public String toString(){return "{"+x+", "+y+"}";}
    
}
