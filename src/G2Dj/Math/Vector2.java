/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package G2Dj.Math;

/**
 *
 * @author Joe
 */
public class Vector2 extends VectorBase
{
    public float x,y;

    //Constructors
    public Vector2(){this(0,0);}
    public Vector2(final float aScalar){this(aScalar,aScalar);}
    public Vector2(final float aX, final float aY) { x = aX; y = aY; }
    public Vector2(final Vector2 aVector){x=aVector.x; y=aVector.y;}
    
    //State mutating vector operations
    public void addInPlace     (final VectorBase aVector){x += aVector.getX(); y += aVector.getY();}
    public void subtractInPlace(final VectorBase aVector){x -= aVector.getX(); y -= aVector.getY();}
    
    public void multiplyInPlace(final float aScalar){x *= aScalar; y *= aScalar;}
    public void divideInPlace  (final float aScalar){x /= aScalar; y /= aScalar;}
   
    public void normalize(){float l=length(); x/=l; y/=l;}
    //public void normalizeAndScale(final float aScalar){normalize(); multiplyInPlace(aScalar);}
    
    //ConstVector definitions
    public static final ConstVector2 Up    = new ConstVector2( 0, 1);
    public static final ConstVector2 Down  = new ConstVector2( 0,-1);
    public static final ConstVector2 Left  = new ConstVector2(-1, 0);
    public static final ConstVector2 Right = new ConstVector2( 1, 0);
    public static final ConstVector2 Zero  = new ConstVector2( 0, 0);
    
    //Special vector construction functions
    public static Vector2 Up   (){return new Vector2( 0, 1);}
    public static Vector2 Down (){return new Vector2( 0,-1);}
    public static Vector2 Left (){return new Vector2(-1, 1);}
    public static Vector2 Right(){return new Vector2( 1, 0);}
    public static Vector2 Zero (){return new Vector2( 0, 0);}

    //************************************
    // VectorBase interface implementation
    //************************************
    @Override
    protected float getX() {return x;}

    @Override
    protected float getY() {return y;}

}
