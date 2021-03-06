/* 
 * G2Dj Game engine
 *  Written by Joseph Cameron
 */
package grimhaus.com.G2Dj.Type.Math;

/**
 *
 * @author Joe
 */
public class Vector3 
{
    //***************************
    //Special vectors definitions
    //***************************
    public static final Vector3 Up      (){return new Vector3( 0, 1, 0);}
    public static final Vector3 Down    (){return new Vector3( 0,-1, 0);}
    public static final Vector3 Left    (){return new Vector3(-1, 0, 0);}
    public static final Vector3 Right   (){return new Vector3( 1, 0, 0);}
    public static final Vector3 Forward (){return new Vector3( 0, 0, 1);}
    public static final Vector3 Backward(){return new Vector3( 0, 0,-1);}
    public static final Vector3 Zero    (){return new Vector3( 0, 0, 0);}
    
    //*************
    // Data members
    //*************
    public float x,y,z;
    
    //********************************
    //State mutating vector operations
    //********************************
    public Vector3 setInPlace     (final Vector3 aVector3){setInPlace(aVector3.x,aVector3.y,aVector3.z);return this;}
    public void setInPlace     (final float aX, final float aY, final float aZ){x=aX;y=aY;z=aZ;}
    public void addInPlace     (final Vector3 aVector){x+=aVector.x;y+=aVector.y;z+=aVector.z;}
    public void addInPlace     (final float aX, final float aY, final float aZ){x+=aX;y+=aY;z+=aZ;}
    public void addInPlace     (final float   aScalar){x+=aScalar  ;y+=aScalar  ;z+=aScalar  ;}
    public void subtractInPlace(final Vector3 aVector){x-=aVector.x;y-=aVector.y;z-=aVector.z;}
    public void multiplyInPlace(final float   aScalar){x*=aScalar  ;y*=aScalar  ;z*=aScalar;  }
    public void divideInPlace  (final float   aScalar){x/=aScalar  ;y/=aScalar  ;z/=aScalar;  }
    
    public void zero(){x=0;y=0;z=0;}
    public void normalize(){float l=length(); x/=l; y/=l; z/=l;}
    //public void normalizeAndScale(const float &aScalar);
    
    public void copy(final Vector3 aVector3){x=aVector3.x;y=aVector3.y;z=aVector3.z;}

    //******************************
    //Non mutating vector operations
    //******************************
    public Vector3 add     (final Vector3 aVector){return new Vector3(x+aVector.x,y+aVector.y,z+aVector.z);}
    public Vector3 subtract(final Vector3 aVector){return new Vector3(x-aVector.x,y-aVector.y,z-aVector.z);}
    public Vector3 multiply(final float   aScalar){return new Vector3(x*aScalar  ,y*aScalar  ,z*aScalar  );}
    public Vector3 divide  (final float   aScalar){return new Vector3(x/aScalar  ,y/aScalar  ,z*aScalar  );}
    
    public Vector3 unit(){float l=length(); return new Vector3(x/l,y/l,z/l);}
    public float length(){return (float)java.lang.Math.sqrt((x*x)+(y*y)+(z*z));}
        
    public boolean equals(final Vector3 aVector3){return aVector3.x == x && aVector3.y == y ? true : false;}
    
    //************
    //Constructors
    //************
    public Vector3(){this(0);}
    public Vector3(final Vector3 aVector3){x=aVector3.x;y=aVector3.y;z=aVector3.z;}
    public Vector3(final float aScalar){this(aScalar,aScalar,aScalar);}
    public Vector3(final float aX, final float aY, final float aZ){x=aX;y=aY;z=aZ;}
    //public Vector3(const btVector3 &aBulletVector);
    
    @Override public String toString(){return "{"+x+", "+y+", "+z+"}";}
    
}
