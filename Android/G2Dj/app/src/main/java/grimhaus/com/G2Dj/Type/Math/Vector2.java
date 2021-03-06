/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Math;

/**
 *
 * @author Joe
 */
public class Vector2
{
    //***************************
    //Special vectors definitions
    //***************************
    public static Vector2 Up   (){return new Vector2( 0, 1);}
    public static Vector2 Down (){return new Vector2( 0,-1);}
    public static Vector2 Left (){return new Vector2(-1, 1);}
    public static Vector2 Right(){return new Vector2( 1, 0);}
    public static Vector2 Zero (){return new Vector2( 0, 0);}
   
    //*************
    // Data members
    //*************
    public float x,y;

    //********************************
    //State mutating vector operations
    //********************************
    public Vector2 setFromB2DVec2 (final org.jbox2d.common.Vec2 aB2Vec2){x=aB2Vec2.x;y=aB2Vec2.y;return this;}
    public void setInPlace     (final float aX, final float aY){x=aX;y=aY;}
    public void addInPlace     (final Vector2 aVector){x += aVector.x; y += aVector.y;}
    public void subtractInPlace(final Vector2 aVector){x -= aVector.x; y -= aVector.y;}
    public void multiplyInPlace(final float   aScalar){x *= aScalar  ; y *= aScalar  ;}
    public void divideInPlace  (final float   aScalar){x /= aScalar  ; y /= aScalar  ;}
    
    public void normalize(){float l=length(); x/=l; y/=l;}
    //public void normalizeAndScale(final float aScalar){normalize(); multiplyInPlace(aScalar);}
    
    //******************************
    //Non mutating vector operations
    //******************************    
    public Vector2 add     (final Vector2 aVector){return new Vector2(x+aVector.x,y+aVector.y);}
    public Vector2 subtract(final Vector2 aVector){return new Vector2(x-aVector.x,y-aVector.y);}
    public Vector2 multiply(final float   aScalar){return new Vector2(x*aScalar  ,y*aScalar  );}
    public Vector2 divide  (final float   aScalar){return new Vector2(x/aScalar  ,y/aScalar  );}
    
    public Vector2 unit(){float l=length(); return new Vector2(x/l,y/l);}
    public float aspectRatio(){return (x / y); }
    public float length     (){return (float)java.lang.Math.sqrt((x*x) + (y*y));}
            
    //************
    //Constructors
    //************
    public Vector2(){this(0,0);}
    public Vector2(final float aScalar){this(aScalar,aScalar);}
    public Vector2(final float aX, final float aY){x=aX;y=aY;}
    public Vector2(final Vector2 aVector){x=aVector.x;y=aVector.y;}
    
    @Override public String toString(){return "{"+x+", "+y+"}";}
    
}
