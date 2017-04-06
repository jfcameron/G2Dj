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
    public Vector2 setInPlace     (final float aX, final float aY){x=aX;y=aY;return this;}
    public Vector2 addInPlace     (final Vector2 aVector){x += aVector.x; y += aVector.y;return this;}
    public Vector2 subtractInPlace(final Vector2 aVector){x -= aVector.x; y -= aVector.y;return this;}
    public Vector2 multiplyInPlace(final float   aScalar){x *= aScalar  ; y *= aScalar  ;return this;}
    public Vector2 divideInPlace  (final float   aScalar){x /= aScalar  ; y /= aScalar  ;return this;}
    
    public Vector2 normalize(){float l=length(); x/=l; y/=l;return this;}
    //public void normalizeAndScale(final float aScalar){normalize(); multiplyInPlace(aScalar);}
    public Vector2 zero(){x=0;y=0;return this;}
    /*public Vector2 dot(final Vector2 aVector, float aInnerAngle)
    {
        Vector2 a = aVector.abs();
        Vector2 b = abs();
        
        
        
    }*/
    
    public Vector2 absInPlace(){x=x<0?-1f*x:x;y=y<0?-1f*y:y;return this;}
    
    //******************************
    //Non mutating vector operations
    //******************************
    public Vector2 abs(){return new Vector2(x<0?-1f*x:x,y<0?-1f*y:y);}
    
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
    public Vector2(final org.jbox2d.common.Vec2 aVector){x=aVector.x;y=aVector.y;}
    
    @Override public String toString(){return "{"+x+", "+y+"}";}
    
}
